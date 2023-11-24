package g26.eDucaApp.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "students")
public class Student {

    @Column(name = "n_mec", unique = true, nullable = false)
    private long n_mec;

    @Column(name = "school", nullable = false)
    private String school;

    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private S_class s_class;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()){
            return false;
        }

        Student user = (Student) o;
        return email == user.email;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

}
