package g26.eDucaApp.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "students")
public class Student extends User {

    @Column(name = "n_mec", unique = true, nullable = false)
    private long n_mec;

    @Column(name = "school", nullable = false)
    private String school;

    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private S_class s_class;

    public Student() {
        super.setUserType(UserType.Student);
    }

}
