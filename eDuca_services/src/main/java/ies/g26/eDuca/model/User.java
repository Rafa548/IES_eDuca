package ies.g26.eDuca.model;

import jakarta.persistence.*    ;
import lombok.Data;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@Data

public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;



    @Column(name = "name", nullable = false)
    private String name;

    @Id
    @Column(name = "email", unique = true, nullable = false)
    private String email;


    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType type;


    // Constructors
    public User() {
    }

    public User(String name, String email, String password, UserType type) {
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
            
        User user = (User) o;
        return email == user.email;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    protected void setUserType(UserType userType) {
        this.type = userType;
    }
}
