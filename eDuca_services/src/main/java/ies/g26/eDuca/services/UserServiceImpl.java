package ies.g26.eDuca.services;

import ies.g26.eDuca.model.User;
import ies.g26.eDuca.model.UserType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public User getUserByN_mec(Long n_mec) {
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        return null;
    }

    @Override
    public List<User> getAllUsersByType(UserType type) {
        return null;
    }

    @Override
    public void saveOrUpdateUser(User user) {

    }

    @Override
    public void deleteUser(Long n_mec) {

    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    @Override
    public boolean existsByN_mec(Long n_mec) {
        return false;
    }
}
