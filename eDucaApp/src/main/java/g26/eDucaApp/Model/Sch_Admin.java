package g26.eDucaApp.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class Sch_Admin extends User{

        @Column(name = "school", nullable = false)
        private String school;

        public Sch_Admin() {
                super.setUserType(UserType.Sch_Admin);
        }
}
