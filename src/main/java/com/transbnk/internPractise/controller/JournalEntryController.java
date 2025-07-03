package com.transbnk.internPractise.controller;

import com.transbnk.internPractise.entity.JournalEntry;
import com.transbnk.internPractise.entity.User;
import com.transbnk.internPractise.service.JournalEntryService;
import com.transbnk.internPractise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("/get-journal/{username}")
    public ResponseEntity<?> getAllJournalsEntriesOfUsers(@PathVariable String username) {
        User userByUserName = userService.findUserByUserName(username);

        if(userByUserName != null){
        List<JournalEntry> all = userByUserName.getJournalEntries();
        if(all !=null && !all.isEmpty()){
            return new ResponseEntity<>(journalEntryService.getEnteries(), HttpStatus.OK);
        }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/post-journal/{username}")
    public ResponseEntity<JournalEntry> postJournal(@RequestBody JournalEntry entry, @PathVariable String username) {
        try{
            journalEntryService.saveEntry(entry,username);
            return new ResponseEntity<>(entry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{id}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable Long id) {
        Optional<JournalEntry> journalEntry = journalEntryService.getEntryById(id);
        return journalEntry.map(entry -> new ResponseEntity<>(entry, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable Long id) {
        journalEntryService.deleteEntryById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update-journal/{username}/{id}")
    public ResponseEntity<JournalEntry> updateJournalEntryById(@PathVariable Long id,
                                                               @RequestBody JournalEntry newJournalEntry,
                                                               @PathVariable String username)
    {
        JournalEntry newEntry = journalEntryService.getEntryById(id).orElse(null);
        if(newEntry!=null) {
            newEntry.setTitle((newJournalEntry.getTitle() != null && !newJournalEntry.getTitle().isEmpty() ? newJournalEntry.getTitle() : newEntry.getTitle()));
            newEntry.setContent((newJournalEntry.getContent() != null && !newJournalEntry.getContent().isEmpty() ? newJournalEntry.getContent() : newEntry.getContent()));
            journalEntryService.oldEntry(newEntry);
            return new ResponseEntity<>(newEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
