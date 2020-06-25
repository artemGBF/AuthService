package com.gbf.auth.service;

import com.gbf.auth.model.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserValidationService {

    public void validateUser(User user) {
        if (user.getPasswordChangeDate() == null) {
            throw new RuntimeException("change your temporary password");
        }
        if (LocalDate.now().minusDays(90L).isAfter(user.getPasswordChangeDate())) {
            throw new RuntimeException("it s time to change password");
        }
    }

}
