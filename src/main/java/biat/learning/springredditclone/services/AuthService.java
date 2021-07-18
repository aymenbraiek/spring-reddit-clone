package biat.learning.springredditclone.services;

import biat.learning.springredditclone.domain.User;
import biat.learning.springredditclone.domain.VerificationToken;
import biat.learning.springredditclone.repositories.UserRepository;
import biat.learning.springredditclone.repositories.VerificationTokenRepository;
import biat.learning.springredditclone.web.dto.RegisterRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;

    @Transactional
    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        //disable when auth is success his trans
        user.setEnabled(false);
        userRepository.save(user);
        // this method check Token
        String token = generateVerificationToken(user);
    }

    public String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }


}
