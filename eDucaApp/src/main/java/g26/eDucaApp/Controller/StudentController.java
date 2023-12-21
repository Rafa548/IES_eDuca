package g26.eDucaApp.Controller;

import g26.eDucaApp.Model.S_class;
import g26.eDucaApp.Model.Student;
import g26.eDucaApp.Services.EducaServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(maxAge = 3600)
@RestController
@AllArgsConstructor
@RequestMapping("students")
@RestControllerAdvice
public class StudentController {

    private EducaServices studentService;


    @Operation(summary = "Create Student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Student created", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "409", description = "Student already exists", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
    })
    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody Map<String, String> body){
        //System.out.println(body);
        String name = body.get("name");
        String email = body.get("email");
        String password = body.get("password");
        String school = "SampleSchool";
        String studentclass = body.get("studentclass");
        String nmec = body.get("nmec");
        S_class studentClass = studentService.getS_classByClassname(studentclass);

        Student student = new Student();
        student.setName(name);
        student.setNmec(Long.parseLong(nmec));
        student.setEmail(email);
        student.setPassword(password);
        student.setSchool(school);
        student.setStudentclass(studentClass);

        Student savedStudent = studentService.createStudent(student);
        if (savedStudent == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    @Operation(summary = "Get Student by Nmec")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the student with the given nmec", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
    })
    @GetMapping("{nmec}")
    public ResponseEntity<Student> getStudentById(@PathVariable("nmec") Long nmec){
        Student student = studentService.getStudentByNmec(nmec);
        if (student == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @Operation(summary = "Get Student by Email or Name or Nmec or Class or School")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the student with the given email or name or nmec or class or school", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
    })
    @GetMapping
    public ResponseEntity<?> getStudents(@RequestParam(value = "email", required = false) String studentEmail,
                                         @RequestParam(value = "name", required = false) String studentName,
                                         @RequestParam(value = "nmec", required = false) Long studentNmec,
                                         @RequestParam(value = "studentclass", required = false) S_class studentclass,
                                         @RequestParam(value = "school", required = false) String school){
        if (studentEmail != null) {
            Student student = studentService.getStudentByEmail(studentEmail);
            if (student == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(student, HttpStatus.OK);
        } else if (studentName != null) {
            Student student = studentService.getStudentByName(studentName);
            if (student == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(student, HttpStatus.OK);
        } else if (studentNmec != null && studentNmec != 0) {
            Student student = studentService.getStudentByNmec(studentNmec);
            if (student == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(student, HttpStatus.OK);
        } else if (studentclass != null) {
            List<Student> student = studentService.getStudentByS_class(studentclass);
            if (student == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(student, HttpStatus.OK);
        } else if (school != null) {
            List<Student> student = studentService.getStudentBySchool(school);
            if (student == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(student, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.OK);
        }
    }

    @Operation(summary = "Update Student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student updated", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "409", description = "Student already exists", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))

    })
    @PutMapping("{nmec}")
    public ResponseEntity<Student> updateStudent(@PathVariable("nmec") Long nmec,
                                                 @RequestBody Map<String, String> updates) {
        Student studentToUpdate = studentService.getStudentByNmec(nmec);

        if (studentToUpdate != null) {
            if (updates.containsKey("name")) {
                studentToUpdate.setName(updates.get("name"));
            }

            if (updates.containsKey("email")) {
                studentToUpdate.setEmail(updates.get("email"));
            }

            if (updates.containsKey("password")) {
                studentToUpdate.setPassword(updates.get("password"));
            }

            if (updates.containsKey("studentclass")) {
                S_class updatedClass = studentService.getS_classByClassname(updates.get("studentclass"));
                studentToUpdate.setStudentclass(updatedClass);
            }

            Student updatedStudent = studentService.updateStudent(studentToUpdate);
            if (updatedStudent == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete Student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student deleted", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
    })
    @DeleteMapping("{nmec}")
    public ResponseEntity<?> deleteStudent(@PathVariable("nmec") Long nmec){
        studentService.deleteStudent(nmec);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
