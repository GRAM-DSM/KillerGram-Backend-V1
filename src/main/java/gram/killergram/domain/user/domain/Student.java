package gram.killergram.domain.user.domain;

import gram.killergram.domain.user.domain.type.Abillity;
import gram.killergram.domain.user.domain.type.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "student")
@Getter
@Setter
@NoArgsConstructor
public class Student {
    @OneToOne
    @JoinColumn(name = "user_id")
    private User student_id;

    @Column(nullable = false,length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 20)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 20)
    private Abillity abillity;

    @Column(nullable = false,columnDefinition = "CHAR(20)")
    private String school_number;
}
