package ies.g26.eDuca.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Sys_Admin")
public class Sys_Admin extends User{


    public Sys_Admin() {
    }

    public Sys_Admin(String name, String email, String password, UserType type) {
        super(name, email, password, type);
    }
}
