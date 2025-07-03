package com.transbnk.internPractise.service;


import com.transbnk.internPractise.entity.JournalEntry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SentimentService {
    public String getSentiment(){
        return "This the Scheduled Mailed. The Sentiment has been Received";
    }
}
