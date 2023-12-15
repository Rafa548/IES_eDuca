package g26.eDucaApp.Controller;

import g26.eDucaApp.Model.Grade;
import g26.eDucaApp.Model.S_class;
import g26.eDucaApp.Model.Student;
import g26.eDucaApp.Model.Subject;
import g26.eDucaApp.Services.EducaServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("classes")
@RestControllerAdvice
public class ClassController {

    private EducaServices educaServices;


    @Operation(summary = "Create a new class")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Class created", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Class already exists", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Forbidden (Not enough authorizations)", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))})
    @PostMapping
    public ResponseEntity<S_class> createClass(@RequestBody Map<String,String> updates){
        for (GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            if (grantedAuthority.getAuthority().equals("ADMIN")) {
                if (!updates.containsKey("classname")) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
                String classname = updates.get("classname");
                S_class s_class = new S_class();
                s_class.setClassname(classname);
                s_class.setSchool("Sammple School");
                s_class.setTeachers(new ArrayList<>());
                s_class.setSubjects(new ArrayList<>());
                s_class.setStudents(new ArrayList<>());
                s_class.setTeaching_assignments(new ArrayList<>());
                List<Subject> existingSubjects = educaServices.getAllSubjects();
                for (Subject existingSubject : existingSubjects) {
                    s_class.getSubjects().add(existingSubject);
                }
                S_class savedClass = educaServices.createS_class(s_class);
                return new ResponseEntity<>(savedClass, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @Operation(summary = "Get a class by its name")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Class found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Class not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Forbidden (Not enough authorizations)", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))})
    @GetMapping("{classname}")
    public ResponseEntity<S_class> getClassByName(@PathVariable("classname") String classname){
        for (GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            if (grantedAuthority.getAuthority().equals("ADMIN")){
                S_class s_class = educaServices.getS_classByClassname(classname);
                if (s_class == null) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>(s_class, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @Operation(summary = "Get a class by its id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Students found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Forbidden (Not enough authorizations)", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))})
    @GetMapping("/byID/{id}")
    public ResponseEntity<S_class> getClassById(@PathVariable("id") Long id){
        for (GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            if (grantedAuthority.getAuthority().equals("ADMIN")) {
                S_class s_class = educaServices.getS_classById(id);
                if (s_class == null) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>(s_class, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }


    @Operation(summary = "Get all classes or a class by its name")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Classes found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Forbidden (Not enough authorizations)", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))})
    @GetMapping
    public ResponseEntity<?> getClasses(@RequestParam(value = "classname", required = false) String c_name ){
        for (GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            if (grantedAuthority.getAuthority().equals("ADMIN")) {
                if (c_name != null) {
                    S_class s_class = educaServices.getS_classByClassname(c_name);
                    if (s_class == null) {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    }
                    return new ResponseEntity<>(s_class, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(educaServices.getAllS_class(), HttpStatus.OK);
                }
            }
            else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @Operation(summary = "Get all students from a class")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Students found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Forbidden (Not enough authorizations)", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))})
    @GetMapping("{classname}/students")
    public ResponseEntity<?> getStudentsByClassname(@PathVariable("classname") String classname){
        for (GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            if (grantedAuthority.getAuthority().equals("ADMIN") || grantedAuthority.getAuthority().equals("TEACHER")){
                S_class s_class = educaServices.getS_classByClassname(classname);
                return new ResponseEntity<>(s_class.getStudents(), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }


    @Operation(summary = "Get the average of the student grades in a specific subject (by classname and nmec)")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Average found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Forbidden (Not enough authorizations)", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Student not found in the class", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))})
    @GetMapping("{classname}/{nmec}/{subject}/grade")
    public ResponseEntity<?> getStudentGradeByClassnameAndNmecAndSubject(@PathVariable("classname") String classname, @PathVariable("nmec") Long nmec, @PathVariable("subject") String subject) {
        for (GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            if (grantedAuthority.getAuthority().equals("ADMIN")|| grantedAuthority.getAuthority().equals("STUDENT")){
                S_class s_class = educaServices.getS_classByClassname(classname);
                List<Student> students = s_class.getStudents();

                for (Student student : students) {
                    if (student.getNmec().equals(nmec)) {
                        List<Integer> studentGrades = new ArrayList<>();
                        //System.out.println(student.getGrades());
                        for (Grade grade : student.getGrades()) {
                            //System.out.println(grade.getSubject());
                            //System.out.println(grade.getSubject().getName());
                            if (grade.getSubject().getName().equals(subject)) {
                                studentGrades.add(grade.getGrade());
                            }
                        }

                        if (studentGrades.isEmpty()) {
                            return ResponseEntity.status(HttpStatus.OK).body("N/A");
                        }

                        double median;
                        int size = studentGrades.size();
                        if (size % 2 == 0) {
                            median = (studentGrades.get(size / 2 - 1) + studentGrades.get(size / 2)) / 2.0;
                        } else {
                            median = studentGrades.get(size / 2);
                        }

                        // Return the median
                        return ResponseEntity.status(HttpStatus.OK).body(median);
                    }
                }

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found in the class.");
            }
            else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @Operation(summary = "Update the class name")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Class updated", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Class not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Forbidden (Not enough authorizations)", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))})
    @PutMapping("{classname}")
    public ResponseEntity<?> updateClass(@PathVariable("classname") String classname, @RequestBody S_class s_class){
        for (GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            if (grantedAuthority.getAuthority().equals("ADMIN")){
                s_class = educaServices.getS_classByClassname(classname);
                if (s_class == null) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                s_class.setClassname(classname);
                S_class updatedClass = educaServices.updateS_class(s_class);
                return new ResponseEntity<>(updatedClass, HttpStatus.OK);
            }
            else {
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }


    @Operation(summary = "Delete a student from a class")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Student deleted", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Student not found in the class", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Forbidden (Not enough authorizations)", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))})
    @DeleteMapping("{classname}/students/{nmec}")
    public ResponseEntity<?> deleteStudentFromClass(@PathVariable("classname") String classname, @PathVariable("nmec") Long nmec){
        for (GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            //System.out.println(grantedAuthority.getAuthority());
            //System.out.println(grantedAuthority.getAuthority().equals("ADMIN"));
            if (grantedAuthority.getAuthority().equals("ADMIN")){
                S_class s_class = educaServices.getS_classByClassname(classname);
                if (s_class == null) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                List<Student> students = s_class.getStudents();
                for (Student student : students) {
                    if (student.getNmec().equals(nmec)) {
                        students.remove(student);
                        student.setStudentclass(null);
                        s_class.setStudents(students);
                        educaServices.updateS_class(s_class);
                        return new ResponseEntity<>(HttpStatus.OK);
                    }
                }
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @Operation(summary = "Delete a class")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Class deleted", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Class not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Forbidden (Not enough authorizations)", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))})
    @DeleteMapping("{classname}")
    public ResponseEntity<?> deleteClass(@PathVariable("classname") String classname){

        for (GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()){
            if (grantedAuthority.getAuthority() == "ADMIN"){
                for (Student student : educaServices.getS_classByClassname(classname).getStudents()){
                    student.setStudentclass(null);
                    for (Grade grade : student.getGrades()){
                        educaServices.deleteGrade(grade.getId());
                    }
                    educaServices.updateStudent(student);
                }
                educaServices.deleteS_class(classname);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
