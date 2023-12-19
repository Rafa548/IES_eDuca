package g26.eDucaApp.Controller;
import g26.eDucaApp.Model.Grade;
import g26.eDucaApp.Model.Student;
import g26.eDucaApp.Model.Subject;
import g26.eDucaApp.Model.Teacher;
import g26.eDucaApp.Services.EducaServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;
import java.util.Map;
@CrossOrigin(maxAge = 3600)
@RestController
@AllArgsConstructor
@RequestMapping("grades")
public class GradeController {

    private EducaServices gradeService;
    private EducaServices studentService;
    private EducaServices subjectServices;
    private EducaServices teacherServices;
    private EducaServices gradeServices;

    @Operation(summary = "Create Grade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Grade created", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "409", description = "Grade already exists", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
    })
    @PostMapping
    public ResponseEntity<Grade> createGrade(@RequestBody Map<String, String> updates) {
        if (!updates.containsKey("email_s") || !updates.containsKey("subject") || !updates.containsKey("email_t") || !updates.containsKey("grade")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String email_s = updates.get("email_s");
        String subject_name = updates.get("subject");
        String email_t = updates.get("email_t");
        String gradeStr = updates.get("grade");
        int gradeValue = Integer.parseInt(gradeStr);

        Student student = studentService.getStudentByEmail(email_s);
        if (student == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Subject subject = subjectServices.getSubjectByName(subject_name);
        if (subject == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Teacher teacher = teacherServices.getTeacherByEmail(email_t);
        if (teacher == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Grade newGrade = new Grade();
        newGrade.setStudent(student);
        newGrade.setSubject(subject);
        newGrade.setTeacher(teacher);
        newGrade.setGrade(gradeValue);

        if (gradeService.getGradeByStudentAndSubject(student, subject).size() > 19) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }   

        gradeServices.createGrade(newGrade);
        return new ResponseEntity<>(newGrade, HttpStatus.CREATED);
    }


    @Operation(summary = "Get Grade by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Grade found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Grade not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
    })
    @GetMapping("{id}")
    public ResponseEntity<Grade> getGradeById(@PathVariable("id") Long id){
        Grade grade = gradeService.getGradeById(id);
        if (grade == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(grade, HttpStatus.OK);
    }

    @Operation(summary = "Get Grades by a specific Teacher")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Grade found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Grade not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
    })
    @GetMapping("teacher/{t_nmec}")
    public ResponseEntity<?> getGradeByTeacher(@PathVariable("t_nmec") Long nmec){
        Teacher teacher = gradeService.getTeacherByN_mec(nmec);
        if (teacher == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Grade> grade = gradeService.getGradeByTeacher(teacher);
        if (grade == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(grade, HttpStatus.OK);
    }

    @Operation(summary = "Get Grades by a specific Student nmec or Subject or Teacher or all Grades")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Grade found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Grade not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
    })
    @GetMapping
    public ResponseEntity<?> getGrades(@RequestParam(value = "nmec", required = false) Long nmec,
                                       @RequestParam(value = "subject", required = false) Subject subject,
                                       @RequestParam(value = "teacher", required = false) Teacher teacher){
        if (nmec != null) {
            Student student = studentService.getStudentByNmec(nmec);
            if (student == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            List<Grade> grade1 = gradeService.getGradeByStudent(student);
            if (grade1 == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(grade1, HttpStatus.OK);
        } else if (subject != null) {
            List<Grade> grade1 = gradeService.getGradeBySubject(subject);
            if (grade1 == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(grade1, HttpStatus.OK);
        } else if (teacher != null) {
            List<Grade> grade1 = gradeService.getGradeByTeacher(teacher);
            if (grade1 == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(grade1, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(gradeService.getAllGrades(), HttpStatus.OK);
        }
    }

    @Operation(summary = "Update Grade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Grade updated", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Grade not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
    })
    @PutMapping("{id}")
    public ResponseEntity<Grade> updateGrade(@PathVariable("id") Long id, @RequestBody Grade grade){
        Grade gradeToUpdate = gradeService.getGradeById(id);
        if (gradeToUpdate == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        gradeToUpdate.setGrade(grade.getGrade());
        Grade updatedGrade = gradeService.createGrade(gradeToUpdate);
        if (updatedGrade == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(updatedGrade, HttpStatus.OK);
    }

    @Operation(summary = "Delete Grade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Grade deleted", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Grade not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
    })
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteGrade(@PathVariable("id") Long id){
        gradeService.deleteGrade(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
