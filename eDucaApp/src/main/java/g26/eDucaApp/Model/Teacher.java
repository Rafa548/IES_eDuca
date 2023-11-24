package g26.eDucaApp.Model;

import g26.eDucaApp.Model.User;
import g26.eDucaApp.Model.S_class;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "teachers")
public class Teacher extends User {


    @Column(name = "n_mec", nullable = false)
    private long n_mec;

    @Column(name = "school", nullable = false)
    private String school;

    @ManyToMany(mappedBy = "teachers")
    private List<S_class> s_classes;

    public Teacher() {
        super.setUserType(UserType.Teacher);
    }
}
