package pl.zielinski.shop.security.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zielinski.shop.common.mail.EmailClientService;
import pl.zielinski.shop.security.model.User;
import pl.zielinski.shop.security.model.dto.ChangePassword;
import pl.zielinski.shop.security.model.dto.EmailObject;
import pl.zielinski.shop.security.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LostPasswordService {

    @Value("${app.serviceAddress}")
    private String serviceAddress;
    private final UserRepository userRepository;
    private final EmailClientService emailClientService;

    @Transactional
    public void sendLostPasswordLink(EmailObject email) {

        User user = userRepository.findByUsername(email.getEmail()).orElseThrow(
                () -> new RuntimeException("Taki email nie istnieje"));

        String hash = generateHashForLostPassword(user);

        user.setHash(hash);
        user.setHashDate(LocalDateTime.now());

        emailClientService.getInstance()
                .send(email.getEmail(), "Zresetuj hasło",
                        createMessage(createLink(hash)));
    }

    private String createMessage(String link) {
        return "Wygenerowaliśmy dla Ciebie link do zmiany hasła" +
                "\n\nKliknij link, żeby zresetować hasło: " +
                "\n" + link +
                "\n\nDziękujemy";
    }

    private String createLink(String hash) {
        return serviceAddress + "/lostPassword/" + hash;
    }

    private String generateHashForLostPassword(User user) {
        String hash = user.getId() + user.getUsername() + user.getPassword() + LocalDateTime.now();

        return DigestUtils.sha256Hex(hash);

    }

    @Transactional
    public void changePassword(ChangePassword changePassword) {

        if (!Objects.equals(changePassword.getPassword(), changePassword.getRepeatPassword())) {
            throw new RuntimeException("Hasła nie są takie same");
        }

        User user = userRepository.findByHash(changePassword.getHash())
                .orElseThrow(() -> new RuntimeException("Nieprawidłowy link"));

        if (user.getHashDate().plusMinutes(10).isAfter(LocalDateTime.now())) {
            user.setPassword("{bcrypt}" + changePassword.getPassword());
            user.setHash(null);
            user.setHashDate(null);
        } else {
            throw new RuntimeException("Link stracił ważność");
        }
    }
}
