package gram.killergram.domain.user.domain;

import gram.killergram.domain.user.domain.type.Ability;
import gram.killergram.domain.user.domain.type.Gender;
import gram.killergram.domain.vote.domain.VoteUser;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "student")
@Getter
@NoArgsConstructor
@Table(name = "student_tbl")
public class Student {

    @Id
    @Column(name = "user_id", unique = true, nullable = false, columnDefinition = "BINARY(16)")
    private UUID userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(50)")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false, columnDefinition = "VARCHAR(20)")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "ability", nullable = false, columnDefinition = "VARCHAR(20)")
    private Ability ability;

    @Column(name = "school_number", nullable = false, columnDefinition = "CHAR(20)")
    private String schoolNumber;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VoteUser> voteUser = new ArrayList<>();

    @Builder
    public Student(User user, String name, Gender gender, Ability ability, String schoolNumber) {
        this.user = user;
        this.name = name;
        this.gender = gender;
        this.ability = ability;
        this.schoolNumber = schoolNumber;
    }
}


