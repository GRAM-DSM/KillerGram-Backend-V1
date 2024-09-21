package gram.killergram.domain.user.service;

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
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private final JavaMailSender mailSender;

    @Transactional
    public String sendVerificationEmail(String email) throws MessagingException, IOException {
        String verificationCode = generateVerificationCode();
        String htmlContent = getHtmlContent(verificationCode);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom("noreply@killergram.com");
        helper.setTo(email);
        helper.setSubject("KillerGram 이메일 인증");
        helper.setText(htmlContent, true);

        mailSender.send(message);

        return verificationCode;
    }

    private String generateVerificationCode() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(10000));
    }

    @Value("${email.template.path}")
    private String templatePath;

    public String getHtmlContent(String verificationCode) throws IOException {
        String content = Files.readString(Path.of(templatePath));
        return content.replace("${verificationCode}", verificationCode);
    }
}