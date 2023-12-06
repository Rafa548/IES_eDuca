package g26.eDucaApp.Services;

import g26.eDucaApp.Dao.request.SigninReq;
import g26.eDucaApp.Dao.response.JwtAuthenticationResponse;
import g26.eDucaApp.Model.Sch_Admin;
import g26.eDucaApp.Model.Student;
import g26.eDucaApp.Model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    private EducaServices educaServices;
    private final JwtService jwtService;
    @Autowired
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse signin(SigninReq request) {
        String email = request.getEmail();
        String password = request.getPassword();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (AuthenticationException e) {
            throw new IllegalArgumentException("Invalid credentials.");
        }

        UserDetails userDetails = getUserDetailsByEmail(email);

        if (userDetails != null) {
            var jwt = jwtService.generateToken(userDetails);
            return JwtAuthenticationResponse.builder().token(jwt).build();
        } else {
            throw new IllegalArgumentException("User details not found.");
        }
    }

    private UserDetails getUserDetailsByEmail(String email) {
        Teacher teacher = educaServices.getTeacherByEmail(email);
        Sch_Admin schAdmin = educaServices.getSch_AdminByEmail(email);
        Student student = educaServices.getStudentByEmail(email);

        if (teacher != null) {
            return convertToTeacherDetails(teacher);
        } else if (schAdmin != null) {
            return convertToSch_AdminDetails(schAdmin);
        } else if (student != null) {
            return convertToStudentDetails(student);
        }

        return null;
    }

    private UserDetails convertToTeacherDetails(Teacher teacher) {
        return org.springframework.security.core.userdetails.User
                .withUsername(teacher.getEmail())
                .password(teacher.getPassword())
                .authorities(new SimpleGrantedAuthority("ROLE_TEACHER"))
                .build();
    }

    private UserDetails convertToStudentDetails(Student student) {
        return org.springframework.security.core.userdetails.User
                .withUsername(student.getEmail())
                .password(student.getPassword())
                .authorities(new SimpleGrantedAuthority("ROLE_STUDENT"))
                .build();
    }

    private UserDetails convertToSch_AdminDetails(Sch_Admin sch_admin) {
        return org.springframework.security.core.userdetails.User
                .withUsername(sch_admin.getEmail())
                .password(sch_admin.getPassword())
                .authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))
                .build();
    }

}

