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
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private final JavaMailSender mailSender;
    private final EmailCrudRepository emailCrudRepository;

    @Transactional
    public void sendVerificationEmail(EmailVerificationRequest emailVerificationRequest) throws MessagingException, IOException {
        String verificationCode = generateVerificationCode();
        String htmlContent = getHtmlContent(verificationCode);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom("dsm2024gram@gmail.com");
        helper.setTo(emailVerificationRequest.getEmail());
        helper.setSubject("KillerGram 이메일 인증");
        helper.setText(htmlContent, true);

        mailSender.send(message);

        LocalDateTime end = LocalDateTime.now().plusMinutes(5);

        Email email = new Email(emailVerificationRequest.getEmail(), verificationCode, false, end);
        emailCrudRepository.save(email);
    }

    private String generateVerificationCode() {
        Random random = new Random();
        return String.format("%04d", random.nextInt(10000));
    }

    @Value("${email.template.path}")
    private String templatePath;

    public String getHtmlContent(String verificationCode) throws IOException {
        String content = Files.readString(Path.of(templatePath));
        return content.replace("${verificationCode}", verificationCode);
    }
}