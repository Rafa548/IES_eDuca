package ies.g26.eDuca.services;
import ies.g26.eDuca.model.User;
import java.util.List;
import ies.g26.eDuca.model.UserType;


public interface UserService  {
    public List<User> getAllUsers();
    public User getUserByN_mec(Long n_mec);
    public User getUserByEmail(String email);
    public List<User> getAllUsersByType(UserType type);
    public void saveOrUpdateUser(User user);
    public void deleteUser(Long n_mec);
    public boolean existsByEmail(String email);
    public boolean existsByN_mec(Long n_mec);
    
}
