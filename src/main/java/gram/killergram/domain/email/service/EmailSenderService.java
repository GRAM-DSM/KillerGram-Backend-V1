package gram.killergram.domain.email.service;

import gram.killergram.domain.email.domain.Email;
import gram.killergram.domain.email.presentation.dto.request.EmailVerificationRequest;
import gram.killergram.domain.email.repository.EmailCrudRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private final JavaMailSender mailSender;
    private final EmailCrudRepository emailCrudRepository;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Transactional
    public void sendVerificationEmail(EmailVerificationRequest emailVerificationRequest) throws MessagingException, IOException {
        String verificationCode = generateVerificationCode();
        String htmlContent = getHtmlContent(verificationCode);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom("me@donghyun.cc");
        helper.setTo(emailVerificationRequest.getEmail());
        helper.setSubject("KillerGram 이메일 인증");
        helper.setText(htmlContent, true);

        mailSender.send(message);

        LocalDateTime end = LocalDateTime.now().plusMinutes(5);
        Email email = new Email(emailVerificationRequest.getEmail(), verificationCode, false, end);
        emailCrudRepository.save(email);
    }

    //generate random authentication code
    private String generateVerificationCode() {
        SecureRandom random = new SecureRandom();
        return String.format("%04d", random.nextInt(10000));
    }

    //to find where is html template
    @Value("${email.template.path}")
    private String templatePath;

    // To script random code in htm then, return html + random code
    private String getHtmlContent(String verificationCode) throws IOException {
        String content = Files.readString(Path.of(templatePath), StandardCharsets.UTF_8);
        String html = content.replace("{{verificationCode}}", verificationCode);
        return html;
    }
}
