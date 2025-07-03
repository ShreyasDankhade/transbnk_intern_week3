package com.transbnk.internPractise.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserExcel {

    @Id
    private Long id;
    private String username;
    private String role;
    private String email;
}
