package ies.g26.eDuca.repository;

import ies.g26.eDuca.model.user;
import ies.g26.eDuca.model.u_type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface user_repository extends JpaRepository<user, Long> {
    Integer countByEmail(String email);
    user findByN_mec(Long n__mec);
    List<user> findAllByType(u_type type);
}
