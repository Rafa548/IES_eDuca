package g26.eDucaApp.Controller;

import g26.eDucaApp.Model.S_class;
import g26.eDucaApp.Services.EducaServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("classes")
public class ClassController {

    private EducaServices educaServices;

    // build create Class REST API
    @PostMapping
    public ResponseEntity<S_class> createClass(@RequestBody S_class s_class){
        S_class savedClass = educaServices.createS_class(s_class);
        return new ResponseEntity<>(savedClass, HttpStatus.CREATED);
    }

    // build get Class by name REST API
    @GetMapping("{classname}")
    public ResponseEntity<S_class> getClassByName(@PathVariable("classname") String classname){
        S_class s_class = educaServices.getS_classByClassname(classname);
        return new ResponseEntity<>(s_class, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<?> getClasses(@RequestParam(value = "classname", required = false) String c_name ){
         if  (c_name != null){
             S_class s_class = educaServices.getS_classByClassname(c_name);
             return new ResponseEntity<>(s_class, HttpStatus.OK);
         }
         else {
             return new ResponseEntity<>(educaServices.getAllS_class(), HttpStatus.OK);
         }
    }

    @GetMapping("{classname}/students")
    public ResponseEntity<?> getStudentsByClassname(@PathVariable("classname") String classname){
        S_class s_class = educaServices.getS_classByClassname(classname);
        return new ResponseEntity<>(s_class.getStudents(), HttpStatus.OK);
    }



}
