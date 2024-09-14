package gram.killergram.domain.user.domain;

import gram.killergram.domain.user.domain.type.Ability;
import gram.killergram.domain.user.domain.type.Gender;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "student")
@Getter
@NoArgsConstructor
public class Student {

    @OneToOne
    @JoinColumn(name = "user_id")
    @Column(name = "student_id")
    private User student_id;

    @Column(name = "name" , nullable = false , columnDefinition = "VARCHAR(50)")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender" , nullable = false , columnDefinition = "VARCHAR(20)")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "ability" , nullable = false , columnDefinition = "VARCHAR(20)")
    private Ability ability;

    @Column(name = "school_number" , nullable = false , columnDefinition = "CHAR(20)")
    private String school_number;

    @Builder
    public Student(String name, Gender gender, Ability ability, String school_number) {
        this.name = name;
        this.gender = gender;
        this.ability = ability;
        this.school_number = school_number;
    }
}
