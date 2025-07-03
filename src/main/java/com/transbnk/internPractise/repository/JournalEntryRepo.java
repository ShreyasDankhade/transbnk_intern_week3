package com.transbnk.internPractise.repository;

import com.transbnk.internPractise.entity.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JournalEntryRepo extends JpaRepository<JournalEntry,Long>, JpaSpecificationExecutor<JournalEntry> {


}
