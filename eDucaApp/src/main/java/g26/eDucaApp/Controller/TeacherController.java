package g26.eDucaApp.Controller;


import g26.eDucaApp.Model.S_class;
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
                                         @RequestParam(value="school", required = false) String school,
                                         @RequestParam(value = "subject") String subject,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "1") int size){
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
            List<Teacher> teachers = teacherService.getTeacherBySchool(school);
            return new ResponseEntity<>(teachers, HttpStatus.OK);
        }else if(subject != null) {
            List<Teacher> teachers = teacherService.getTeacherBySubject(subject);
            return new ResponseEntity<>(teachers, HttpStatus.OK);
        }else{
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

    @PutMapping("{nmec}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable("nmec") Long nmec,
                                                 @RequestBody Map<String, String> updates){
        Teacher teacherToUpdate = teacherService.getTeacherByN_mec(nmec);

        if (teacherToUpdate != null) {
            if (updates.containsKey("name")) {
                teacherToUpdate.setName(updates.get("name"));
            }

            if (updates.containsKey("email")) {
                teacherToUpdate.setEmail(updates.get("email"));
            }

            if (updates.containsKey("password")) {
                teacherToUpdate.setPassword(updates.get("password"));
            }

            if (updates.containsKey("classes")) {
                List<S_class> updatedClasses = (List<S_class>) teacherService.getS_classByClassname(updates.get("classes"));
                teacherToUpdate.setClasses(updatedClasses);
            }
        }

        return new ResponseEntity<>(teacherToUpdate, HttpStatus.OK);
    }


}
