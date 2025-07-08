package com.transbnk.internPractise.service;

import com.transbnk.internPractise.entity.JournalEntry;
import com.transbnk.internPractise.entity.User;
import com.transbnk.internPractise.repository.JournalEntryRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class JournalEntryService {

    private final JournalEntryRepo JournalEntryRepo;

    private final UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String username){
        try{
            User user = userService.findByUserName(username);
            journalEntry.setDate(LocalDateTime.now());
            journalEntry.setUser(null);
            JournalEntry saved = JournalEntryRepo.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);

        } catch (Exception e) {
            System.out.println(e);
            throw new NullPointerException("Null values not allowed: "+e);
        }
    }

    public void oldEntry(JournalEntry journalEntry){
        try{
            JournalEntryRepo.save(journalEntry);

        } catch (Exception e) {
            log.error("Error:", e);
        }
    }

    public List<JournalEntry> getEnteries(){
        return JournalEntryRepo.findAll();
    }

    public Optional<JournalEntry> getEntryById(Long id){
        return JournalEntryRepo.findById(id);
    }

    public void deleteEntryById(Long id){
        JournalEntryRepo.deleteById(id);
    }


}
