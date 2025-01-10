package org.sangahn.sangahncloudboot.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.sangahn.sangahncloudboot.user.domain.UserEntity;
import org.sangahn.sangahncloudboot.user.dto.LoginDTO;
import org.sangahn.sangahncloudboot.user.exception.LoginException;
import org.sangahn.sangahncloudboot.user.repository.LoginRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class LoginService {

    private final LoginRepository loginRepository;

    private final PasswordEncoder passwordEncoder;

    public LoginDTO authenticate(String userId, String password) {

        Optional<UserEntity> result = loginRepository.findById(userId);

        UserEntity user = result.orElseThrow(() -> LoginException.BAD_AUTH.getException());

        String enterPw = user.getUserPassword();
        boolean match = passwordEncoder.matches(password, enterPw);

        if(!match) {
            log.error("User ID and password do not match");
            throw LoginException.BAD_AUTH.getException();
        }

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserId(userId);
        loginDTO.setPassword(enterPw);
        loginDTO.setUserName(user.getUserName());
        loginDTO.setUserEmail(user.getUserEmail());

        return loginDTO;
    }
}
