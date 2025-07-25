package com.transbnk.internPractise.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {
    private String username;
    private String roles;
    private String email;
    private List<JournalEntryDto> JournalEntries;
}
