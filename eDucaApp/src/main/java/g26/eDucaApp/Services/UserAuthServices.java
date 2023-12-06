package g26.eDucaApp.Services;

import g26.eDucaApp.Model.Sch_Admin;
import g26.eDucaApp.Model.Student;
import g26.eDucaApp.Model.Teacher;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAuthServices implements UserServices{

    @Autowired
    private EducaServices educaServices;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Teacher teacher = educaServices.getTeacherByEmail(username);
                if (teacher != null) {
                    return buildUserDetails(teacher.getEmail(), teacher.getPassword(), "TEACHER");
                }

                Student student = educaServices.getStudentByEmail(username);
                if (student != null) {
                    return buildUserDetails(student.getEmail(), student.getPassword(), "STUDENT");
                }

                Sch_Admin schAdmin = educaServices.getSch_AdminByEmail(username);
                if (schAdmin != null) {
                    return buildUserDetails(schAdmin.getEmail(), schAdmin.getPassword(), "ADMIN");
                }

                throw new UsernameNotFoundException("User not found");
            }

            private UserDetails buildUserDetails(String username, String password, String role) {
                System.out.println(new SimpleGrantedAuthority(role));
                String formattedRole = "ROLE_" + role;
                return org.springframework.security.core.userdetails.User
                        .withUsername(username)
                        .password(password)
                        .authorities((new SimpleGrantedAuthority(formattedRole)))
                        .build();
            }
        };
    }
}

