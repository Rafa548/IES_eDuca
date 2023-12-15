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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@CrossOrigin(maxAge = 3600)
@RestController
@AllArgsConstructor
@RequestMapping("subjects")
@PreAuthorize("hasAuthority('ADMIN')")
public class SubjectController {
    private EducaServices subjectService;

    @Operation(summary = "Create Subject")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Subject created", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "409", description = "Subject already exists", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
    })
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

    @Operation(summary = "Get Subject by Name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subject found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Subject not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
    })
    @GetMapping
    public ResponseEntity<?> getSubject(@RequestParam(value = "name", required = false) String subjectName){
        for (GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()){
            if (grantedAuthority.getAuthority().equals("ADMIN")) {
                if (subjectName != null) {
                    Subject subject = subjectService.getSubjectByName(subjectName);
                    if (subject == null) {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    }
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

    @Operation(summary = "Delete Subject")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subject deleted", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Subject not found", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json"))
    })
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
