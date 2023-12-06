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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    private EducaServices educaServices;

    @Autowired
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    @Autowired
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse signin(SigninReq request) {

        //System.out.println(request.getPassword());
        //System.out.println(bcryptEncoder.encode(request.getPassword()));
        String email = request.getEmail();
        String password = request.getPassword();

        // Authenticate using AuthenticationManager
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
        } catch (AuthenticationException e) {
            // Handle authentication failure (e.g., invalid credentials)
            throw new IllegalArgumentException("Invalid email or password.", e);
        }

        Teacher teacher = educaServices.getTeacherByEmail(request.getEmail());
        Sch_Admin sch_admin = educaServices.getSch_AdminByEmail(request.getEmail());
        Student student = educaServices.getStudentByEmail(request.getEmail());

        if (teacher != null) {
            var userDetails = convertToTeacherDetails(teacher);
            var jwt = jwtService.generateToken(userDetails);
            return JwtAuthenticationResponse.builder().token(jwt).build();
        }
        else if (sch_admin != null) {
            var userDetails = convertToSch_AdminDetails(sch_admin);
            var jwt = jwtService.generateToken(userDetails);
            return JwtAuthenticationResponse.builder().token(jwt).build();
        }
        else if (student != null) {
            var userDetails = convertToStudentDetails(student);
            var jwt = jwtService.generateToken(userDetails);
            return JwtAuthenticationResponse.builder().token(jwt).build();
        }
        else {
            throw new IllegalArgumentException("Invalid email or password.");
        }
    }

    private UserDetails convertToTeacherDetails(Teacher teacher) {
        return org.springframework.security.core.userdetails.User
                .withUsername(teacher.getEmail())
                .password(teacher.getPassword())
                .build();
    }

    private UserDetails convertToStudentDetails(Student student) {
        return org.springframework.security.core.userdetails.User
                .withUsername(student.getEmail())
                .password(student.getPassword())
                .build();
    }

    private UserDetails convertToSch_AdminDetails(Sch_Admin sch_admin) {
        return org.springframework.security.core.userdetails.User
                .withUsername(sch_admin.getEmail())
                .password(sch_admin.getPassword())
                .build();
    }
}

