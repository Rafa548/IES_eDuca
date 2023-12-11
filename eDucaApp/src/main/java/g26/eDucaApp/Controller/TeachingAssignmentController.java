package g26.eDucaApp.Controller;
import g26.eDucaApp.Model.Teacher;
import g26.eDucaApp.Model.S_class;
import g26.eDucaApp.Model.Subject;
import g26.eDucaApp.Services.EducaServices;
import lombok.AllArgsConstructor;
import g26.eDucaApp.Model.Teaching_Assignment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@AllArgsConstructor
@RequestMapping("teaching_assignments")
public class TeachingAssignmentController {

        private EducaServices teachingAssignmentService;

        @PostMapping
        public ResponseEntity<?> createTeachingAssignment(@RequestBody Teaching_Assignment teachingAssignment){
            Teaching_Assignment savedTeachingAssignment = teachingAssignmentService.createTeachingAssignment(teachingAssignment);
            return new ResponseEntity<>(savedTeachingAssignment, HttpStatus.CREATED);
        }

        @GetMapping("{id}")
        public ResponseEntity<?> getTeachingAssignmentById(@PathVariable("id") Long id){
            Teaching_Assignment teachingAssignment = teachingAssignmentService.getTeachingAssignmentById(id);
            return new ResponseEntity<>(teachingAssignment, HttpStatus.OK);
        }

        @GetMapping
        public ResponseEntity<?> getTeachingAssignments(@RequestParam(value="email", required = false) String email,
                                                        @RequestParam(value="class", required = false) S_class s_class,
                                                        @RequestParam(value="subject", required = false) Subject subject){
            if (email != null) {
                Teacher teacher = teachingAssignmentService.getTeacherByEmail(email);
                List<Teaching_Assignment> teachingAssignments = teachingAssignmentService.getTeachingAssignmentByTeacher(teacher);
                return new ResponseEntity<>(teachingAssignments, HttpStatus.OK);
            } else if (s_class != null) {
                List<Teaching_Assignment> teachingAssignments = teachingAssignmentService.getTeachingAssignmentByS_class(s_class);
                return new ResponseEntity<>(teachingAssignments, HttpStatus.OK);
            } else if (subject != null) {
                List<Teaching_Assignment> teachingAssignments = teachingAssignmentService.getTeachingAssignmentBySubject(subject);
                return new ResponseEntity<>(teachingAssignments, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(teachingAssignmentService.getAllTeachingAssignments(), HttpStatus.OK);
            }
        }




}
