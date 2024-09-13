package gram.killergram.domain.user.domain;

import gram.killergram.domain.user.domain.type.Authority;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity(name = "user")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private UUID user_id;

    @Column(name = "account_id", nullable = false, columnDefinition = "VARCHAR(50)")
    private String account_id;

    @Column(name = "password", nullable = false, columnDefinition = "VARCHAR(255)")
    private String password;

    @Column(name = "device_token", nullable = false, columnDefinition = "VARCHAR(255)")
    private String device_token;

    @Enumerated(EnumType.STRING)
    @Column(name = "authority", nullable = false, columnDefinition = "VARCHAR(30)")
    private Authority authority;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Student student;
}
