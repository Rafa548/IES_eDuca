package g26.eDucaApp.Model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "classes")
public class S_class {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "classname", nullable = false)
    private String classname;

    @ManyToMany
    @JoinTable(
            name = "class_subject", // Join table name
            joinColumns = @JoinColumn(name = "class_id"), // Column name for S_class's ID
            inverseJoinColumns = @JoinColumn(name = "subject_id") // Column name for Subject's ID
    )
    @JsonIgnoreProperties("class_subject")
    private List<Subject> subjects;

    @OneToMany(mappedBy = "studentclass", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("studentclass")
    private List<Student> students;

    @ManyToMany
    @JoinTable(
            name = "class_teacher",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id")
    )
    @JsonIgnoreProperties("class_teacher")
    private List<Teacher> teachers;


}
