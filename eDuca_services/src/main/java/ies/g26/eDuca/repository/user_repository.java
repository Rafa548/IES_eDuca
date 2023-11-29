package ies.g26.eDuca.repository;

import ies.g26.eDuca.model.User;
import ies.g26.eDuca.model.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface user_repository extends JpaRepository<User, Long> {
    User findByName(String name);
    List<User> findAllByType(UserType type);
}
