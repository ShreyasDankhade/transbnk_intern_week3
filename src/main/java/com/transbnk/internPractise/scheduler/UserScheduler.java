package com.transbnk.internPractise.scheduler;


import com.transbnk.internPractise.entity.User;
import com.transbnk.internPractise.repository.UserRepo;
import com.transbnk.internPractise.service.EmailService;
import com.transbnk.internPractise.service.SentimentService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@AllArgsConstructor
public class UserScheduler {

    private final UserRepo userRepo;
    private SentimentService sentimentService;
    private EmailService emailService;

// add cron expression in double quotes
//@Scheduled(cron = "0 0 9 * * SUN")
@Scheduled(cron = "0 * * * * *")
    public void fetchUsersNSendMail(){
    System.out.println("Scheduled task running at: " + LocalDateTime.now());
    List<User> userList = userRepo.findAll();
        for(User user: userList){
            String sentiment = sentimentService.getSentiment();
            emailService.sendMail(user.getEmail(), "Sentiment of last 7 days", sentiment);
            System.out.println("Mail Send Successfully");
        }
    }
}
