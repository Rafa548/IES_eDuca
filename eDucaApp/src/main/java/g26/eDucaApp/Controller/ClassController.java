package g26.eDucaApp.Controller;

import g26.eDucaApp.Model.Grade;
import g26.eDucaApp.Model.S_class;
import g26.eDucaApp.Model.Student;
import g26.eDucaApp.Services.EducaServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(maxAge = 3600)
@RestController
@AllArgsConstructor
@RequestMapping("classes")
public class ClassController {

    private EducaServices educaServices;

    // build create Class REST API
    @PostMapping
    public ResponseEntity<S_class> createClass(@RequestBody S_class s_class){
        S_class savedClass = educaServices.createS_class(s_class);
        return new ResponseEntity<>(savedClass, HttpStatus.CREATED);
    }

    // build get Class by name REST API
    @GetMapping("{classname}")
    public ResponseEntity<S_class> getClassByName(@PathVariable("classname") String classname){
        S_class s_class = educaServices.getS_classByClassname(classname);
        return new ResponseEntity<>(s_class, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<?> getClasses(@RequestParam(value = "classname", required = false) String c_name ){
         if  (c_name != null){
             S_class s_class = educaServices.getS_classByClassname(c_name);
             return new ResponseEntity<>(s_class, HttpStatus.OK);
         }
         else {
             return new ResponseEntity<>(educaServices.getAllS_class(), HttpStatus.OK);
         }
    }

    @GetMapping("{classname}/students")
    public ResponseEntity<?> getStudentsByClassname(@PathVariable("classname") String classname){
        S_class s_class = educaServices.getS_classByClassname(classname);
        return new ResponseEntity<>(s_class.getStudents(), HttpStatus.OK);
    }

    @GetMapping("{classname}/students/{nmec}")
    public ResponseEntity<?> getStudentByClassnameAndNmec(@PathVariable("classname") String classname, @PathVariable("nmec") Long nmec){
        S_class s_class = educaServices.getS_classByClassname(classname);
        List<Student> students = s_class.getStudents();
        for (Student student : students){
            if (student.getNmec().equals(nmec)){
                return new ResponseEntity<>(student, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("{classname}/{nmec}/{subject}/grade")
    public ResponseEntity<?> getStudentGradeByClassnameAndNmecAndSubject(@PathVariable("classname") String classname, @PathVariable("nmec") Long nmec, @PathVariable("subject") String subject) {
        S_class s_class = educaServices.getS_classByClassname(classname);
        List<Student> students = s_class.getStudents();

        for (Student student : students) {
            if (student.getNmec().equals(nmec)) {
                List<Integer> studentGrades = new ArrayList<>();
                System.out.println(student.getGrades());
                for (Grade grade : student.getGrades()) {
                    System.out.println(grade.getSubject());
                    System.out.println(grade.getSubject().getName());
                    if (grade.getSubject().getName().equals(subject)) {
                        studentGrades.add(grade.getGrade());
                    }
                }

                if (studentGrades.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.OK).body("N/A");
                }

                double median;
                int size = studentGrades.size();
                if (size % 2 == 0) {
                    median = (studentGrades.get(size / 2 - 1) + studentGrades.get(size / 2)) / 2.0;
                } else {
                    median = studentGrades.get(size / 2);
                }

                // Return the median
                return ResponseEntity.status(HttpStatus.OK).body(median);
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found in the class.");
    }


    @PutMapping("{classname}")
    public ResponseEntity<?> updateClass(@PathVariable("classname") String classname, @RequestBody S_class s_class){
        s_class = educaServices.getS_classByClassname(classname);
        s_class.setClassname(classname);
        S_class updatedClass = educaServices.updateS_class(s_class);
        return new ResponseEntity<>(updatedClass, HttpStatus.OK);
    }

    @DeleteMapping("{classname}")
    public ResponseEntity<?> deleteClass(@PathVariable("classname") String classname){
        educaServices.deleteS_class(classname);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
