package com.transbnk.internPractise.controller;


import com.transbnk.internPractise.dto.LoginRequestDto;
import com.transbnk.internPractise.entity.User;
import com.transbnk.internPractise.entity.UserExcel;
import com.transbnk.internPractise.helper.ExcelToDbHelper;
import com.transbnk.internPractise.service.ExcelService;
import com.transbnk.internPractise.service.SpecificationService;
import com.transbnk.internPractise.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;


import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final SpecificationService specificationService;
    private final ExcelService excelService;
    private final AuthenticationManager authenticationManager;


    @GetMapping()
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
    }

    @GetMapping("/pageable-user")
    public ResponseEntity<?> getUserByField(Pageable pageable, @RequestParam String field) {
        Page<User> usersPage = userService.getAllUsers(pageable, field);

        if (usersPage.hasContent()) {
            return new ResponseEntity<>(usersPage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/specification")
    public ResponseEntity<?> getUsersSpeci(@RequestParam String column, @RequestParam String value, @RequestParam(defaultValue = "EQUAL") String operation, @RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "ASC") String direction) {
        try {
            List<User> users = specificationService.getUsers(column, value, operation, sortBy, direction);

            if (users.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No users found matching the filter criteria.");
            }
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error has occured" + e.getMessage());
        }
    }

    @GetMapping("/excel")
    public ResponseEntity<Resource> download() {
        String filename = "users.xlsx";
        ByteArrayInputStream dataStream = excelService.getactualData();
        InputStreamResource file = new InputStreamResource(dataStream);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename).contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")).body(file);
    }

    @GetMapping("/excel-user")
    public List<UserExcel> getAllExcelUser() {
        return this.excelService.getAllExcelUsers();
    }

    //    CSRF Token Generation
    @GetMapping("/csrf")
    public CsrfToken getToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }


    @PostMapping("/excel/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        if (ExcelToDbHelper.checkExcelFormat(file)) {
            this.excelService.save(file);
            return ResponseEntity.of(Optional.of(Map.of("message", "file is uploaded successfully")));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Please Upload an Excel file(xlsx/csv). No other format Allowed.");
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            userService.saveUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            String token = userService.verify(loginRequestDto);
            // At this point, authentication was successful
            return ResponseEntity.ok(token);

        } catch (AuthenticationException ex) {
            // Handles incorrect username/password
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        } catch (Exception e) {
            // Any other unexpected errors
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }


    @GetMapping("/id/{id}")
    public ResponseEntity<User> updateUserById(@PathVariable Long id) {
        Optional<User> userById = userService.getUserById(id);
        return userById.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("update-user/{username}")
    public ResponseEntity<User> updateUserById(@RequestBody User user, @PathVariable String username) {
        User newUser = userService.findByUserName(username);

        if (newUser != null) {
            newUser.setUsername(user.getUsername());
            newUser.setPassword(user.getPassword());
            newUser.setRoles(user.getRoles());
            newUser.setEmail(user.getEmail());
            userService.saveUser(newUser);
            return new ResponseEntity<>(newUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("delete-user/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}


//    @PostMapping("/login")
//    public ResponseEntity<String> loginUser(@RequestBody LoginRequestDto loginRequestDto) {
//        try {
/// /            Authentication authenticate = authenticationManager.authenticate(
/// /                    new UsernamePasswordAuthenticationToken(
/// /                            loginRequestDto.getUsername(), loginRequestDto.getPassword()
/// /                    )
/// /            );
//            User foundUser = userService
//                    .findByUserName(loginRequestDto
//                            .getUsername());
//
//            if (foundUser == null) {
//                return ResponseEntity
//                        .status(HttpStatus.UNAUTHORIZED)
//                        .body("Invalid username");
//            }
//
//            // Password check
//            if (!foundUser.getPassword()
//                    .equals(loginRequestDto.getPassword())) {
//                return ResponseEntity
//                        .status(HttpStatus.UNAUTHORIZED)
//                        .body("Incorrect password");
//            }
//
//            return ResponseEntity
//                    .status(HttpStatus.ACCEPTED)
//                    .body("Logged In Successfully");
//
//        } catch (Exception e) {
//            return ResponseEntity
//                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("An error occurred: " + e.getMessage());
//        }
//    }
