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

@CrossOrigin(maxAge = 3600)
@RestController
@AllArgsConstructor
@RequestMapping("grades")
public class GradeController {
    private EducaServices gradeService;

    @PostMapping
    public ResponseEntity<Grade> createGrade(@RequestBody Grade grade){
        Grade savedGrade = gradeService.createGrade(grade);
        return new ResponseEntity<>(savedGrade, HttpStatus.CREATED);
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
    public ResponseEntity<?> getGrades(@RequestParam(value = "student", required = false) Student student,
                                       @RequestParam(value = "subject", required = false) Subject subject,
                                       @RequestParam(value = "teacher", required = false) Teacher teacher){
        if (student != null) {
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
