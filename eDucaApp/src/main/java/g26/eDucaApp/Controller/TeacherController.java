package g26.eDucaApp.Controller;


import g26.eDucaApp.Model.S_class;
import g26.eDucaApp.Model.Teacher;
import g26.eDucaApp.Model.TeacherUpdateRequest;
import g26.eDucaApp.Services.EducaServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        for (GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()){
            if (grantedAuthority.getAuthority().equals("ADMIN")) {
                Teacher savedTeacher = teacherService.createTeacher(teacher);
                return new ResponseEntity<>(savedTeacher, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
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
    public ResponseEntity<Teacher> updateTeacher(@PathVariable("nmec") Long nmec, @RequestBody TeacherUpdateRequest updates){
        System.out.println(updates);
        for (GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            if (grantedAuthority.getAuthority().equals("ADMIN") || grantedAuthority.getAuthority().equals("TEACHER")) {
                Teacher teacherToUpdate = teacherService.getTeacherByN_mec(nmec);
                List<S_class> classes = new ArrayList<>();
                //System.out.println(updates);

                if (teacherToUpdate != null) {
                    if (updates.getName() != null) {
                        teacherToUpdate.setName(updates.getName());
                    }
                    if (updates.getEmail() != null) {
                        teacherToUpdate.setEmail(updates.getEmail());
                    }
                    if (updates.getSchool() != null) {
                        teacherToUpdate.setSchool(updates.getSchool());
                    }
                    if (updates.getPassword() != null) {
                        teacherToUpdate.setPassword(updates.getPassword());
                    }
                    if (updates.getSubjects() != null) {
                        teacherToUpdate.setSubjects(updates.getSubjects());
                    }
                    if (updates.getClasses() != null && updates.getClasses().length > 0  ) {
                        for (String classname : updates.getClasses()) {
                            S_class s_class = teacherService.getS_classByClassname(classname);
                            classes.add(s_class);
                        }
                        teacherToUpdate.setClasses(classes);
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

