package ies.g26.eDuca.model;

import ies.g26.eDuca.model.User;
import jakarta.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("Teacher")
public class Teacher extends User {
    @Id
    @Column(name = "n_mec", nullable = false)
    private long n_mec;

    @Column(name = "school", nullable = false)
    private String school;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<Subject> subjects;

    @ManyToMany(mappedBy = "teachers")
    private List<Class> classes;

    public Teacher() {
    }

    public Teacher(String name, String email, String password, UserType type, long n_mec, String school, List<Subject> subjects) {
        super(name, email, password, type);
        this.n_mec = n_mec;
        this.school = school;
        this.subjects = subjects;
    }
}
