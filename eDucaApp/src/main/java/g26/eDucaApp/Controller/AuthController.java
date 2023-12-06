package g26.eDucaApp.Controller;


import g26.eDucaApp.Dao.request.SigninReq;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import g26.eDucaApp.Dao.response.JwtAuthenticationResponse;
import g26.eDucaApp.Services.AuthenticationService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {

    private final AuthenticationService AuthenticationService;

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninReq request) {
        var response = AuthenticationService.signin(request);
        return ResponseEntity.ok(response);
    }

}
