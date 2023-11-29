package g26.eDucaApp.Repository;

import g26.eDucaApp.Model.Sys_Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface Sys_AdminRepository  extends JpaRepository<Sys_Admin, Long>{
    Optional<Sys_Admin> findByEmail(String email);

    Optional<Sys_Admin> findByName(String name);

}
