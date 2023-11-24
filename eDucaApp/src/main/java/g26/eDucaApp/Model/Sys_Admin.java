package g26.eDucaApp.Model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter

@Entity
@DiscriminatorValue("Sys_Admin")
public class Sys_Admin extends User{


    public Sys_Admin() {
        super.setUserType(UserType.Sys_Admin);
    }
}
