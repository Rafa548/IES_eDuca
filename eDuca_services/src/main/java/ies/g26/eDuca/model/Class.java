package ies.g26.eDuca.model;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Class {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long class_id;

    @OneToMany(mappedBy = "Class", cascade = CascadeType.ALL)
    private List<Subject> subjects;


    @OneToMany(mappedBy = "schoolClass", cascade = CascadeType.ALL)
    private List<Student> students;

    @ManyToMany
    @JoinTable(
            name = "class_teacher",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id")
    )
    private List<Teacher> teachers;

    public Class() {
    }

    public Class(List<Subject> subjects, List<Student> students, List<Teacher> teachers) {
        this.subjects = subjects;
        this.students = students;
        this.teachers = teachers;
    }
}
