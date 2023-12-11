package g26.eDucaApp.Controller;

import g26.eDucaApp.Model.S_class;
import g26.eDucaApp.Model.Student;
import g26.eDucaApp.Services.EducaServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(maxAge = 3600)
@RestController
@AllArgsConstructor
@RequestMapping("students")
public class StudentController {

    private EducaServices studentService;


    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody Map<String, String> body){
        System.out.println(body);
        String name = body.get("name");
        String email = body.get("email");
        String password = body.get("password");
        String school = "SampleSchool";
        String studentclass = body.get("studentclass");
        String nmec = body.get("nmec");
        //String nmec = "12345554532";
        //String nmec = generateUniqueIdentifier();

        S_class studentClass = studentService.getS_classByClassname(studentclass);

        Student student = new Student();
        student.setName(name);
        student.setNmec(Long.parseLong(nmec));
        student.setEmail(email);
        student.setPassword(password);
        student.setSchool(school);
        student.setStudentclass(studentClass);

        Student savedStudent = studentService.createStudent(student);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    @GetMapping("{nmec}")
    public ResponseEntity<Student> getStudentById(@PathVariable("nmec") Long nmec){
        Student student = studentService.getStudentByNmec(nmec);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

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

    @PutMapping("{nmec}")
    public ResponseEntity<Student> updateStudent(@PathVariable("nmec") Long nmec,
                                                 @RequestBody Map<String, String> updates) {
        Student studentToUpdate = studentService.getStudentByNmec(nmec);

        if (studentToUpdate != null) {
            if (updates.containsKey("name")) {
                studentToUpdate.setName(updates.get("name"));
            }

            if (updates.containsKey("email")) {
                studentToUpdate.setEmail(updates.get("email"));
            }

            if (updates.containsKey("password")) {
                studentToUpdate.setPassword(updates.get("password"));
            }

            if (updates.containsKey("studentclass")) {
                S_class updatedClass = studentService.getS_classByClassname(updates.get("studentclass"));
                studentToUpdate.setStudentclass(updatedClass);
            }

            Student updatedStudent = studentService.updateStudent(studentToUpdate);
            return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{nmec}")
    public ResponseEntity<?> deleteStudent(@PathVariable("nmec") Long nmec){
        studentService.deleteStudent(nmec);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
