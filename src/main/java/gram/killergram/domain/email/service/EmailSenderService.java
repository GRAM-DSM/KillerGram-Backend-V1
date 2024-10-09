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
        helper.setFrom(senderEmail);
        helper.setTo(emailVerificationRequest.getEmail());
        helper.setSubject("KillerGram 이메일 인증");
        helper.setText(htmlContent, true);

        mailSender.send(message);

        LocalDateTime end = LocalDateTime.now().plusMinutes(5);
        Email email = new Email(emailVerificationRequest.getEmail(), verificationCode, false, end);
        emailCrudRepository.save(email);
    }

    private String generateVerificationCode() {
        SecureRandom random = new SecureRandom();
        return String.format("%04d", random.nextInt(10000));
    }

    @Value("${email.template.path}")
    private String templatePath;

    private String getHtmlContent(String verificationCode) throws IOException {
        // 템플릿 파일 내용을 읽어옵니다.
        String content = Files.readString(Path.of(templatePath), StandardCharsets.UTF_8);
        System.out.println("템플릿 내용 읽기 전: " + content); // 템플릿 내용을 로그로 출력
        content = content.replace("{{verificationCode}}", verificationCode);
        System.out.println("변경된 템플릿 내용: " + content); // 변경 후의 내용을 로그로 출력
        return content;
    }
}
