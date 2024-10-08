package gram.killergram.domain.email.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "email_tbl")
public class Email {

    @Id
    @Column(name = "email", nullable = false, unique = true, columnDefinition = "VACAHR(200)")
    private String email;

    @Column(name = "authorization_token", nullable = false, columnDefinition = "INT(6)")
    private Integer authorizationToken;

    @Column(name = "authorization_status", nullable = false)
    private Boolean authorizationStatus;
}
