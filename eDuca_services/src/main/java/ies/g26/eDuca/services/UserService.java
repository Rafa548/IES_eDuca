package ies.g26.eDuca.services;
import ies.g26.eDuca.model.User;
import java.util.List;
import ies.g26.eDuca.model.UserType;


public interface UserService {
    List<User> getUserByName();
    User getUserByEmail(String email);
    List<User> getAllUsersByType(UserType type);
    void saveOrUpdateUser(User user);
    void deleteUser(Long n_mec);
    boolean existsByEmail(String email);
    boolean existsByN_mec(Long n_mec);



    
}
