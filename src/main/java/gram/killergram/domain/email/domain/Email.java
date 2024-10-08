package gram.killergram.domain.email.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "email_tbl")
public class Email {

    @Id
    @Column(name = "email", nullable = false, unique = true, columnDefinition = "VACHAR(200)")
    @jakarta.validation.constraints.Email
    private String email;

    @Column(name = "authorization_token", nullable = false, columnDefinition = "CHAR(6)")
    private String authorizationToken;

    @Column(name = "authorization_status", nullable = false)
    private Boolean authorizationStatus;

    @Column(name="certified_time", nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime certifiedTime;

    public void changeStatusTrue(){
        this.authorizationStatus = Boolean.TRUE;
    }
}
