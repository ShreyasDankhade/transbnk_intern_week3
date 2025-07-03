    package com.transbnk.internPractise.entity;

    import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
    import com.fasterxml.jackson.annotation.JsonManagedReference;
    import jakarta.persistence.*;
    import lombok.Data;
    import lombok.NoArgsConstructor;
    import lombok.NonNull;

    import java.util.ArrayList;
    import java.util.List;

    @Entity
    @Table(name = "users_table")
    @Data
    @NoArgsConstructor
    public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @Column(unique = true)
        @NonNull
        private String username;

        @NonNull
        private String password;

        private String roles;

        private String email;

        private String status;

        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        @JsonIgnoreProperties("user")
        private List<JournalEntry> journalEntries = new ArrayList<>();
    }
