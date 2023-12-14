package g26.eDucaApp.Controller;


import g26.eDucaApp.Model.Sch_Admin;
import g26.eDucaApp.Services.EducaServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 3600)
@RestController
@AllArgsConstructor
@RequestMapping("school_admin")
public class Sch_AdminController {
    // not tested as in the frontend there is no way to create a school admin

    private EducaServices sch_adminService;

    @PostMapping
    public ResponseEntity<?> createSch_Admin(@RequestBody Sch_Admin sch_admin) {
        Sch_Admin savedSch_Admin = sch_adminService.createSch_Admin(sch_admin);
        return new ResponseEntity<>(savedSch_Admin, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getSch_Admin(@RequestParam(value = "email", required = false) String sch_adminEmail,
                                          @RequestParam(value = "name", required = false) String sch_adminName,
                                          @RequestParam(value = "school", required = false) String school) {
        if (sch_adminEmail != null) {
            Sch_Admin sch_admin = sch_adminService.getSch_AdminByEmail(sch_adminEmail);
            return new ResponseEntity<>(sch_admin, HttpStatus.OK);
        } else if (sch_adminName != null) {
            Sch_Admin sch_admin = sch_adminService.getSch_AdminByName(sch_adminName);
            return new ResponseEntity<>(sch_admin, HttpStatus.OK);
        } else if (school != null) {
            Sch_Admin sch_admin = sch_adminService.getSch_AdminBySchool(school);
            return new ResponseEntity<>(sch_admin, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(sch_adminService.getAllSch_Admin(), HttpStatus.OK);
        }
    }

    @DeleteMapping("{email}")
    public ResponseEntity<?> deleteSch_Admin(@PathVariable("email") String email) {
        Sch_Admin sch_admin = sch_adminService.getSch_AdminByEmail(email);
        sch_adminService.deleteSch_Admin(sch_admin.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("{email}")
    public ResponseEntity<?> updateSch_Admin(@PathVariable("email") String email, @RequestBody Sch_Admin sch_admin) {
        sch_admin = sch_adminService.getSch_AdminByEmail(email);
        sch_admin.setEmail(email);
        Sch_Admin updatedSch_Admin = sch_adminService.updateSch_Admin(sch_admin);
        return new ResponseEntity<>(updatedSch_Admin, HttpStatus.OK);
    }

}
