package g26.eDucaApp.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "grades")
public class Grade {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "grade", nullable = false)
        private int grade;

        @ManyToOne
        @JoinColumn(name = "student", nullable = false)
        @JsonIgnoreProperties({"grades", "studentclass"})
        private Student student;

        @ManyToOne
        @JoinColumn(name = "subject_id", nullable = false)
        @JsonIgnoreProperties({"teachers", "teaching_assignments","classes","grades","teacher"})
        private Subject subject;

        @ManyToOne
        @JoinColumn(name = "teacher_id", nullable = false)
        @JsonIgnoreProperties({"subjects","grades","classes","teaching_assignments"})
        private Teacher teacher;
}
