package g26.eDucaApp.Controller;


import g26.eDucaApp.Model.Teacher;
import g26.eDucaApp.Services.EducaServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("teachers")
public class TeacherController {

    private EducaServices teacherService;

    @PostMapping
    public ResponseEntity<Teacher> createTeacher(@RequestBody Teacher teacher){
        Teacher savedTeacher = teacherService.createTeacher(teacher);
        return new ResponseEntity<>(savedTeacher, HttpStatus.CREATED);
    }

    @GetMapping("{nmec}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable("nmec") Long nmec){
        Teacher teacher = teacherService.getTeacherByN_mec(nmec);
        return new ResponseEntity<>(teacher, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getTeachers(@RequestParam(value="email", required = false) String teacherEmail,
                                         @RequestParam(value="name", required = false) String teacherName,
                                         @RequestParam(value="nmec", required = false) Long teacherNmec,
                                         @RequestParam(value="school", required = false) String school){
        if (teacherEmail != null) {
            Teacher teacher = teacherService.getTeacherByEmail(teacherEmail);
            return new ResponseEntity<>(teacher, HttpStatus.OK);
        } else if (teacherName != null) {
            Teacher teacher = teacherService.getTeacherByName(teacherName);
            return new ResponseEntity<>(teacher, HttpStatus.OK);
        } else if (teacherNmec != null && teacherNmec != 0) {
            Teacher teacher = teacherService.getTeacherByN_mec(teacherNmec);
            return new ResponseEntity<>(teacher, HttpStatus.OK);
        } else if (school != null) {
            List<Teacher> teachers = teacherService.getTeacherBySchool(school);//não devia retornar uma lista????<Rafa ..........>
            return new ResponseEntity<>(teachers, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(teacherService.getAllTeachers(), HttpStatus.OK);
        }
    }

    @DeleteMapping("{nmec}")
    public ResponseEntity<?> deleteTeacher(@PathVariable("nmec") Long nmec){
        teacherService.deleteTeacher(nmec);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("{nmec}/subjects")
    public ResponseEntity<?> getTeacherSubjects(@PathVariable("nmec") Long nmec){
        Teacher teacher = teacherService.getTeacherByN_mec(nmec);
        return new ResponseEntity<>(teacher.getSubjects(), HttpStatus.OK);
    }

    @GetMapping("{nmec}/classes")
    public ResponseEntity<?> getTeacherClasses(@PathVariable("nmec") Long nmec){
        Teacher teacher = teacherService.getTeacherByN_mec(nmec);
        return new ResponseEntity<>(teacher.getClasses(), HttpStatus.OK);
    }



}
