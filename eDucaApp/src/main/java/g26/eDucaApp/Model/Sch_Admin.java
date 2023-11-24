package g26.eDucaApp.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sch_admins")
public class Sch_Admin implements Serializable {

        @Column(name = "school", nullable = false)
        private String school;

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        @Column(name = "name", nullable = false)
        private String name;

        @Column(name = "email", unique = true, nullable = false)
        private String email;

        @Column(name = "password", nullable = false)
        private String password;

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()){
                        return false;
                }

                Sch_Admin user = (Sch_Admin) o;
                return email == user.email;
        }

        @Override
        public int hashCode() {
                return Objects.hash(email);
        }

}
