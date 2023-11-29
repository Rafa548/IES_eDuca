package ies.g26.eDuca.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Student")
public class Student extends User {

    @Column(name = "n_mec", unique = true, nullable = false)
    private long n_mec;

    @Column(name = "school", nullable = false)
    private String school;

    @ManyToOne
    @Column(name = "class", nullable = false)
    private Class classes;

    public Student() {
        super.setUserType(UserType.Student);
    }

    public Student(String name, String email, String password, UserType type, long n_mec, String school, Class classes) {
        super(name, email, password, type);
        this.n_mec = n_mec;
        this.school = school;
        this.classes = classes;
    }



}
