package g26.eDucaApp.Controller;
import g26.eDucaApp.Model.Grade;
import g26.eDucaApp.Model.Student;
import g26.eDucaApp.Model.Subject;
import g26.eDucaApp.Model.Teacher;
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
@RequestMapping("grades")
public class GradeController {

    private EducaServices gradeService;
    private EducaServices studentService;
    private EducaServices subjectServices;
    private EducaServices teacherServices;
    private EducaServices gradeServices;

    @PostMapping
    public ResponseEntity<Grade> createGrade(@RequestBody Map<String, String> updates) {
        if (!updates.containsKey("email_s") || !updates.containsKey("subject") || !updates.containsKey("email_t") || !updates.containsKey("grade")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String email_s = updates.get("email_s");
        String subject_name = updates.get("subject");
        String email_t = updates.get("email_t");
        String gradeStr = updates.get("grade");
        int gradeValue = Integer.parseInt(gradeStr);

        Student student = studentService.getStudentByEmail(email_s);
        Subject subject = subjectServices.getSubjectByName(subject_name);
        Teacher teacher = teacherServices.getTeacherByEmail(email_t);

        Grade newGrade = new Grade();
        newGrade.setStudent(student);
        newGrade.setSubject(subject);
        newGrade.setTeacher(teacher);
        newGrade.setGrade(gradeValue);

        gradeServices.createGrade(newGrade);

        return new ResponseEntity<>(newGrade, HttpStatus.CREATED);
    }


    @GetMapping("{id}")
    public ResponseEntity<Grade> getGradeById(@PathVariable("id") Long id){
        Grade grade = gradeService.getGradeById(id);
        return new ResponseEntity<>(grade, HttpStatus.OK);
    }

    @GetMapping("teacher/{t_nmec}")
    public ResponseEntity<?> getGradeByTeacher(@PathVariable("t_nmec") Long nmec){
        Teacher teacher = gradeService.getTeacherByN_mec(nmec);
        List<Grade> grade = gradeService.getGradeByTeacher(teacher);
        return new ResponseEntity<>(grade, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getGrades(@RequestParam(value = "nmec", required = false) Long nmec,
                                       @RequestParam(value = "subject", required = false) Subject subject,
                                       @RequestParam(value = "teacher", required = false) Teacher teacher){
        if (nmec != null) {
            Student student = studentService.getStudentByNmec(nmec);
            List<Grade> grade1 = gradeService.getGradeByStudent(student);
            return new ResponseEntity<>(grade1, HttpStatus.OK);
        } else if (subject != null) {
            List<Grade> grade1 = gradeService.getGradeBySubject(subject);
            return new ResponseEntity<>(grade1, HttpStatus.OK);
        } else if (teacher != null) {
            List<Grade> grade1 = gradeService.getGradeByTeacher(teacher);
            return new ResponseEntity<>(grade1, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(gradeService.getAllGrades(), HttpStatus.OK);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Grade> updateGrade(@PathVariable("id") Long id, @RequestBody Grade grade){
        Grade gradeToUpdate = gradeService.getGradeById(id);
        gradeToUpdate.setGrade(grade.getGrade());
        Grade updatedGrade = gradeService.createGrade(gradeToUpdate);
        return new ResponseEntity<>(updatedGrade, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteGrade(@PathVariable("id") Long id){
        gradeService.deleteGrade(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
