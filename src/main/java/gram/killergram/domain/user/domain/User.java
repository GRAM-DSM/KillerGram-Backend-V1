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
    private UUID user_id;

    @Column(nullable = false,length = 30)
    private String account_id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String device_token;

    @Column(nullable = false,length = 30)
    private Authority authority;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Student student;
}
