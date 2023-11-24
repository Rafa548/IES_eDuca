package g26.eDucaApp.Repository;


import g26.eDucaApp.Model.S_class;
import g26.eDucaApp.Model.Sch_Admin;
import g26.eDucaApp.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface Sch_AdminRepository extends JpaRepository<Sch_Admin, Long>{
    Optional<Sch_Admin> findByEmail(String email);

    Optional<Sch_Admin> findByName(String name);

    Optional<Sch_Admin> findBySchool(String school);

    List<Student> findBySClass(S_class sClass);
}
