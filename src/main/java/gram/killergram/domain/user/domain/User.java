package gram.killergram.domain.user.domain;

import gram.killergram.domain.user.domain.type.Authority;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = "user")
@Getter
@NoArgsConstructor
@Table(name = "user_tbl")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", unique = true, nullable = false, columnDefinition = "BINARY(16)")
    private UUID userId;

    @Column(name = "account_id", nullable = false, columnDefinition = "VARCHAR(50)")
    private String accountId;

    @Column(name = "password", nullable = false, columnDefinition = "VARCHAR(255)")
    private String password;

    @Column(name = "device_token", nullable = false, columnDefinition = "VARCHAR(255)")
    private String deviceToken;

    @Enumerated(EnumType.STRING)
    @Column(name = "authority", nullable = false, columnDefinition = "VARCHAR(30)")
    private Authority authority;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Student student;

    @Builder
    public User(String accountId, String password, String deviceToken, Authority authority) {
        this.accountId = accountId;
        this.password = password;
        this.deviceToken = deviceToken;
        this.authority = authority;
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}

