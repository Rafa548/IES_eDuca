package g26.eDucaApp.Model;
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
    private Long class_id;

    @ManyToMany
    @JoinTable(
            name = "class_subject", // Join table name
            joinColumns = @JoinColumn(name = "class_id"), // Column name for S_class's ID
            inverseJoinColumns = @JoinColumn(name = "subject_id") // Column name for Subject's ID
    )
    private List<Subject> subjects;

    @OneToMany(mappedBy = "studentclass", cascade = CascadeType.ALL)
    private List<Student> students;

    @ManyToMany
    @JoinTable(
            name = "class_teacher",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id")
    )
    private List<Teacher> teachers;


}
