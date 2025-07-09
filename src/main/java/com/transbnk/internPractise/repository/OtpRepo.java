package com.transbnk.internPractise.repository;

import com.transbnk.internPractise.entity.Otp;
import com.transbnk.internPractise.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepo extends JpaRepository<Otp, Long> {
    Optional<Otp> findTopByUserOrderByCreatedAtDesc(User user);
}
