package com.transbnk.internPractise.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.transbnk.internPractise.service.AwsS3FileUploadService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/storage")
@AllArgsConstructor
public class AwsBucketController {

    private final AwsS3FileUploadService awsS3FileUploadService;

    @PostMapping("/uploadFile")
    public ResponseEntity<?> uploadFile(@RequestParam(value = "file") MultipartFile file) {
        try {
            awsS3FileUploadService.uploadFile(file.getOriginalFilename(), file);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("File Uploaded to the S3 bucket");
        } catch (IOException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Error while uploading file: " + e.getMessage());
        }
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
        byte[] data = awsS3FileUploadService.dowloadFile(fileName);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        try {
            awsS3FileUploadService.deleteFile(fileName);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("File Deleted from the S3 bucket");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occcured: " + e.getMessage());
        }
    }
}
