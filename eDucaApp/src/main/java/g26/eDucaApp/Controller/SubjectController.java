package g26.eDucaApp.Controller;


import g26.eDucaApp.Model.Subject;
import g26.eDucaApp.Model.S_class;
import g26.eDucaApp.Model.Teacher;
import g26.eDucaApp.Services.EducaServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("subjects")

public class SubjectController {
    private EducaServices subjectService;

    @PostMapping
    public ResponseEntity<?> createSubject(@RequestBody Subject subject) {
        Subject savedSubject = subjectService.createSubject(subject);
        return new ResponseEntity<>(savedSubject, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getSubject(@RequestParam(value = "name", required = false) String subjectName,
                                        @RequestParam(value = "teacher", required = false) Teacher subjectTeacher){
        if (subjectName != null) {
            Subject subject = subjectService.getSubjectByName(subjectName);
            return new ResponseEntity<>(subject, HttpStatus.OK);
        }else if (subjectTeacher != null) {
            Subject subject = subjectService.getSubjectByTeacher(subjectTeacher);
            return new ResponseEntity<>(subject, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(subjectService.getAllSubjects(), HttpStatus.OK);
        }
    }

    @DeleteMapping("{name}")
    public ResponseEntity<?> deleteSubject(@PathVariable("name") String name) {
        Subject subject = subjectService.getSubjectByName(name);
        subjectService.deleteSubject(subject.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
