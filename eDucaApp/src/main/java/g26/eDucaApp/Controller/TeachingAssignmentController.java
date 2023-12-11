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
import java.util.Map;

@CrossOrigin(maxAge = 3600)
@RestController
@AllArgsConstructor
@RequestMapping("teaching_assignments")
public class TeachingAssignmentController {

        private EducaServices teachingAssignmentService;

        @PostMapping
        public ResponseEntity<?> createTeachingAssignment(@RequestBody Map<String, String> updates){

            if (!updates.containsKey("email") || !updates.containsKey("class") || !updates.containsKey("subject")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            String email = updates.get("email");
            String classname = updates.get("class");
            String subjectname = updates.get("subject");
            Teacher teacher = teachingAssignmentService.getTeacherByEmail(email);
            S_class s_class = teachingAssignmentService.getS_classByClassname(classname);
            Subject subject = teachingAssignmentService.getSubjectByName(subjectname);
            Teaching_Assignment teachingAssignment = new Teaching_Assignment();
            teachingAssignment.setSclass(s_class);
            teachingAssignment.setSubject(subject);
            teachingAssignment.setTeacher(teacher);

            teachingAssignmentService.createTeachingAssignment(teachingAssignment);

            return new ResponseEntity<>(teachingAssignment, HttpStatus.OK);
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


        @DeleteMapping
        public ResponseEntity<?> deleteTeachingAssignment(@RequestBody Map<String, String> updates){
            if (!updates.containsKey("email") || !updates.containsKey("class") || !updates.containsKey("subject")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            String email = updates.get("email");
            String classname = updates.get("class");
            String subjectname = updates.get("subject");
            Teacher teacher = teachingAssignmentService.getTeacherByEmail(email);
            S_class s_class = teachingAssignmentService.getS_classByClassname(classname);
            Subject subject = teachingAssignmentService.getSubjectByName(subjectname);
            Teaching_Assignment teachingAssignment = teachingAssignmentService.getTeachingAssignmentByTeacherAndS_classAndSubject(teacher, s_class, subject);
            teachingAssignmentService.deleteTeachingAssignment(teachingAssignment.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        }




}
