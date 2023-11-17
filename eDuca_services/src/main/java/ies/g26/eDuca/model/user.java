package ies.g26.eDuca.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Data
@Table(name = "user")
public class user implements Serializable {
    
    // Attributes
    @Id
    @Column(name = "n_mec", nullable = false)
    private long n_mec;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    //@JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private u_type type;

    // Constructors
    public user() {
    }

    public user(long n_mec, String name, String email, String password, userType type) {
        this.n_mec = n_mec;
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()){
            return false;
        }
            
        user user = (user) o;
        return n_mec == user.n_mec;
    }

    @Override
    public int hashCode() {
        return Objects.hash(n_mec);
    }
}
