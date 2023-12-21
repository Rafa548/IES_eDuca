package g26.eDucaApp.Controller;
import g26.eDucaApp.Model.Teacher;
import g26.eDucaApp.Model.S_class;
import g26.eDucaApp.Model.Subject;
import g26.eDucaApp.Services.EducaServices;
import lombok.AllArgsConstructor;
import g26.eDucaApp.Model.Teaching_Assignment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;
import java.util.Map;

@CrossOrigin(maxAge = 3600)
@RestController
@AllArgsConstructor
@RequestMapping("teaching_assignments")
public class TeachingAssignmentController {

        private EducaServices teachingAssignmentService;

        @Operation(summary = "Create Teaching Assignment")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "201", description = "Teaching Assignment created", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                @ApiResponse(responseCode = "400", description = "Invalid request body", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                @ApiResponse(responseCode = "409", description = "Teaching Assignment already exists", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
        })
        @PostMapping
        public ResponseEntity<?> createTeachingAssignment(@RequestBody Map<String, String> updates){
            for (GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
                if (grantedAuthority.getAuthority().equals("ADMIN")) {
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
                } else {
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }
            }
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        @Operation(summary = "Get Teaching Assignment by Id")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Teaching Assignment found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                @ApiResponse(responseCode = "404", description = "Teaching Assignment not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
        })
        @GetMapping("{id}")
        public ResponseEntity<?> getTeachingAssignmentById(@PathVariable("id") Long id){
            for (GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()){
                if (grantedAuthority.getAuthority().equals("ADMIN")) {
                    Teaching_Assignment teachingAssignment = teachingAssignmentService.getTeachingAssignmentById(id);
                    if(teachingAssignment == null){
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    }
                    return new ResponseEntity<>(teachingAssignment, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }
            }
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        @Operation(summary = "Get Teaching Assignment by Teacher's email or Class name or Subject")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Teaching Assignment found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                @ApiResponse(responseCode = "404", description = "Teaching Assignment not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
        })
        @GetMapping
        public ResponseEntity<?> getTeachingAssignments(@RequestParam(value="email", required = false) String email,
                                                        @RequestParam(value="class_name", required = false) String class_name,
                                                        @RequestParam(value="subject", required = false) Subject subject){
            if (email != null) {
                Teacher teacher = teachingAssignmentService.getTeacherByEmail(email);
                List<Teaching_Assignment> teachingAssignments = teachingAssignmentService.getTeachingAssignmentByTeacher(teacher);
                if(teachingAssignments == null){
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>(teachingAssignments, HttpStatus.OK);
            } else if (class_name != null) {
                S_class s_class = teachingAssignmentService.getS_classByClassname(class_name);
                List<Teaching_Assignment> teachingAssignments = teachingAssignmentService.getTeachingAssignmentByS_class(s_class);
                if(teachingAssignments == null){
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>(teachingAssignments, HttpStatus.OK);
            } else if (subject != null) {
                List<Teaching_Assignment> teachingAssignments = teachingAssignmentService.getTeachingAssignmentBySubject(subject);
                if(teachingAssignments == null){
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>(teachingAssignments, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(teachingAssignmentService.getAllTeachingAssignments(), HttpStatus.OK);
            }
            
        }

        @Operation(summary = "Delete Teaching Assignment")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Teaching Assignment deleted", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
                @ApiResponse(responseCode = "404", description = "Teaching Assignment not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
        })
        @DeleteMapping
        public ResponseEntity<?> deleteTeachingAssignment(@RequestBody Map<String, String> updates){
            for (GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
                if (grantedAuthority.getAuthority().equals("ADMIN")) {
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
                } else {
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }
            }
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }




}