package g26.eDucaApp.Controller;


import g26.eDucaApp.Model.S_class;
import g26.eDucaApp.Model.Teacher;
import g26.eDucaApp.Services.EducaServices;
import lombok.AllArgsConstructor;

import org.apache.kafka.common.protocol.types.Field.Str;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<Teacher> createTeacher(@RequestBody Map<String, Object> body){
        
        String name = (String) body.get("name");
        String email = (String) body.get("email");
        String password = (String) body.get("password");
        String nmec = (String)body.get("nmec").toString();
        String school = "SampleSchool";
        List<S_class> classes = (List<S_class>) body.get("classes");
        

        Teacher teacher = new Teacher();
        teacher.setName(name);
        teacher.setEmail(email);
        teacher.setPassword(password);
        teacher.setNmec(Long.parseLong(nmec));
        teacher.setClasses(classes);
        teacher.setSchool(school);

        System.out.println(teacher);

        Teacher savedTeacher = teacherService.createTeacher(teacher);
        return new ResponseEntity<>(savedTeacher, HttpStatus.CREATED);

    }

    @GetMapping("{nmec}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable("nmec") Long nmec){
        for (GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()){
            if (grantedAuthority.getAuthority().equals("ADMIN")) {
                Teacher teacher = teacherService.getTeacherByN_mec(nmec);
                return new ResponseEntity<>(teacher, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping
    public ResponseEntity<?> getTeachers(@RequestParam(value="email", required = false) String teacherEmail, @RequestParam(value="name", required = false) String teacherName,
                                         @RequestParam(value="nmec", required = false) Long teacherNmec, @RequestParam(value="school", required = false) String school)
    {
        for (GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()){
            if (grantedAuthority.getAuthority().equals("ADMIN") || grantedAuthority.getAuthority().equals("TEACHER")) {
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
                }else {
                    return new ResponseEntity<>(teacherService.getAllTeachers(), HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("{nmec}")
    public ResponseEntity<?> deleteTeacher(@PathVariable("nmec") Long nmec){
        for (GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            if (grantedAuthority.getAuthority().equals("ADMIN")) {
                teacherService.deleteTeacher(nmec);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("{nmec}/subjects")
    public ResponseEntity<?> getTeacherSubjects(@PathVariable("nmec") Long nmec){
        for (GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            if (grantedAuthority.getAuthority().equals("ADMIN") || grantedAuthority.getAuthority().equals("TEACHER")) {
                Teacher teacher = teacherService.getTeacherByN_mec(nmec);
                return new ResponseEntity<>(teacher.getSubjects(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("{nmec}/classes")
    public ResponseEntity<?> getTeacherClasses(@PathVariable("nmec") Long nmec){
        for (GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()){
            if (grantedAuthority.getAuthority().equals("ADMIN") || grantedAuthority.getAuthority().equals("TEACHER")) {
                Teacher teacher = teacherService.getTeacherByN_mec(nmec);
                return new ResponseEntity<>(teacher.getClasses(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PutMapping("{nmec}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable("nmec") Long nmec, @RequestBody Map<String, String> updates) {
        for (GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            if (grantedAuthority.getAuthority().equals("ADMIN") || grantedAuthority.getAuthority().equals("TEACHER")) {
                Teacher teacherToUpdate = teacherService.getTeacherByN_mec(nmec);

                System.out.println(updates);

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

                teacherService.updateTeacher(teacherToUpdate);

                return new ResponseEntity<>(teacherToUpdate, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
