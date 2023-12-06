package g26.eDucaApp.Controller;

import g26.eDucaApp.Model.S_class;
import g26.eDucaApp.Model.Student;
import g26.eDucaApp.Services.EducaServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("students")
public class StudentController {

    private EducaServices studentService;

    @CrossOrigin(maxAge = 3600)
    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student){
        Student savedStudent = studentService.createStudent(student);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    @CrossOrigin(maxAge = 3600)
    @GetMapping("{nmec}")
    public ResponseEntity<Student> getStudentById(@PathVariable("nmec") Long nmec){
        Student student = studentService.getStudentByNmec(nmec);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @CrossOrigin(maxAge = 3600)
    @GetMapping
    public ResponseEntity<?> getStudents(@RequestParam(value = "email", required = false) String studentEmail,
                                         @RequestParam(value = "name", required = false) String studentName,
                                         @RequestParam(value = "nmec", required = false) Long studentNmec,
                                         @RequestParam(value = "studentclass", required = false) S_class studentclass,
                                         @RequestParam(value = "school", required = false) String school){
        if (studentEmail != null) {
            Student student = studentService.getStudentByEmail(studentEmail);
            return new ResponseEntity<>(student, HttpStatus.OK);
        } else if (studentName != null) {
            Student student = studentService.getStudentByName(studentName);
            return new ResponseEntity<>(student, HttpStatus.OK);
        } else if (studentNmec != null && studentNmec != 0) {
            Student student = studentService.getStudentByNmec(studentNmec);
            return new ResponseEntity<>(student, HttpStatus.OK);
        } else if (studentclass != null) {
            List<Student> student = studentService.getStudentByS_class(studentclass);
            return new ResponseEntity<>(student, HttpStatus.OK);
        } else if (school != null) {
            List<Student> student = studentService.getStudentBySchool(school);
            return new ResponseEntity<>(student, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.OK);
        }
    }

    @CrossOrigin(maxAge = 3600)
    @PutMapping("{nmec}")
    public ResponseEntity<Student> updateStudent(@PathVariable("nmec") Long nmec,
                                                  @RequestBody Student student){

        student.setNmec(nmec);
        Student updatedStudent = studentService.updateStudent(student);
        return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
    }

    @CrossOrigin(maxAge = 3600)
    @DeleteMapping("{nmec}")
    public ResponseEntity<?> deleteStudent(@PathVariable("nmec") Long nmec){
        studentService.deleteStudent(nmec);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
