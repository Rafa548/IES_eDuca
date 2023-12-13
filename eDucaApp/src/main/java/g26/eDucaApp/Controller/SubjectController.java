package g26.eDucaApp.Controller;


import g26.eDucaApp.Model.Subject;
import g26.eDucaApp.Model.S_class;
import g26.eDucaApp.Model.Teacher;
import g26.eDucaApp.Services.EducaServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 3600)
@RestController
@AllArgsConstructor
@RequestMapping("subjects")
@PreAuthorize("hasAuthority('ADMIN')")
public class SubjectController {
    private EducaServices subjectService;

    @PostMapping
    public ResponseEntity<?> createSubject(@RequestBody Subject subject) {
        for (GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()){
            if (grantedAuthority.getAuthority().equals("ADMIN")) {
                Subject savedSubject = subjectService.createSubject(subject);
                return new ResponseEntity<>(savedSubject, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping
    public ResponseEntity<?> getSubject(@RequestParam(value = "name", required = false) String subjectName){
        for (GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()){
            if (grantedAuthority.getAuthority().equals("ADMIN")) {
                if (subjectName != null) {
                    Subject subject = subjectService.getSubjectByName(subjectName);
                    return new ResponseEntity<>(subject, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(subjectService.getAllSubjects(), HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("{name}")
    public ResponseEntity<?> deleteSubject(@PathVariable("name") String name) {
        for (GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()){
            if (grantedAuthority.getAuthority().equals("ADMIN")) {
                Subject subject = subjectService.getSubjectByName(name);
                subjectService.deleteSubject(subject.getId());
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

}
