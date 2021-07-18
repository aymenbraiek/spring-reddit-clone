package biat.learning.springredditclone.services;

import biat.learning.springredditclone.domain.User;
import biat.learning.springredditclone.web.dto.RegisterRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
public class AuthService {

    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());
        user.setCreated(Instant.now());
        //disable lorsque l'authentification est efféctué avec succes sera enabled true
        user.setEnabled(false);
    }
}
