package ies.g26.eDuca.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Sch_Admin")
public class Sch_Admin extends User{

        @Column(name = "school", nullable = false)
        private String school;

        public Sch_Admin() {
        }

        public Sch_Admin(String name, String email, String password, UserType type,String school) {
            super(name, email, password, type);
            this.school = school;
        }
}
