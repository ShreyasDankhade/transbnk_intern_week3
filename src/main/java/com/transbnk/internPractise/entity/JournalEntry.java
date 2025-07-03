package com.transbnk.internPractise.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="journal_table")
@Data
@NoArgsConstructor
public class JournalEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NonNull
    private String title;
    private String content;
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name="user_id",referencedColumnName = "id")
    private User user;
}