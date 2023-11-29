package g26.eDucaApp.Controller;


import g26.eDucaApp.Model.Sys_Admin;
import g26.eDucaApp.Services.EducaServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("sys_admin")
public class Sys_AdminController {
    private EducaServices sys_adminService;

    @PostMapping
    public ResponseEntity<?> createSys_Admin(@RequestBody Sys_Admin sys_admin) {
        Sys_Admin savedSys_Admin = sys_adminService.createSys_Admin(sys_admin);
        return new ResponseEntity<>(savedSys_Admin, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getSys_Admin(@RequestParam(value = "email", required = false) String sys_adminEmail,
                                        @RequestParam(value = "name", required = false) String sys_adminName){
        if (sys_adminEmail != null) {
            Sys_Admin sys_admin = sys_adminService.getSys_AdminByEmail(sys_adminEmail);
            return new ResponseEntity<>(sys_admin, HttpStatus.OK);
        }else if (sys_adminName != null) {
            Sys_Admin sys_admin = sys_adminService.getSys_AdminByName(sys_adminName);
            return new ResponseEntity<>(sys_admin, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(sys_adminService.getAllSys_Admin(), HttpStatus.OK);
        }
    }

    @DeleteMapping("{email}")
    public ResponseEntity<?> deleteSys_Admin(@PathVariable("email") String email) {
        Sys_Admin sys_admin = sys_adminService.getSys_AdminByEmail(email);
        sys_adminService.deleteSys_Admin(sys_admin.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("{email}")
    public ResponseEntity<?> updateSys_Admin(@PathVariable("email") String email, @RequestBody Sys_Admin sys_admin) {
        sys_admin = sys_adminService.getSys_AdminByEmail(email);
        sys_admin.setEmail(email);
        Sys_Admin updatedSys_Admin = sys_adminService.updateSys_Admin(sys_admin);
        return new ResponseEntity<>(updatedSys_Admin, HttpStatus.OK);
    }
}
