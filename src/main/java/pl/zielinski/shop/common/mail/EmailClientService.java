package pl.zielinski.shop.common.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class EmailClientService {
    @Value("${app.email.sender}")
    private String isFakeProp;
    private final Map<String, EmailSender> senderMap;

    public EmailSender getInstance() {
        if(isFakeProp.equals("fakeEmailService")) {
            return senderMap.get("fakeEmailService");
        }
        return senderMap.get("emailSimpleService");
    }

    public EmailSender getInstance(String beanName) {
        if(isFakeProp.equals("fakeEmailService")) {
            return senderMap.get("fakeEmailService");
        }
        return senderMap.get("emailSimpleService");
    }

}
