package g26.eDucaApp.Controller;


import g26.eDucaApp.Dao.request.SigninReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import g26.eDucaApp.Dao.response.JwtAuthenticationResponse;
import g26.eDucaApp.Services.AuthenticationService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
@RestControllerAdvice
public class AuthController {

    private final AuthenticationService AuthenticationService;
    @Operation(summary = "Signin")
    @ApiResponses (value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Login Successful"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid Credentials")
    })
    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninReq request) {
        var response = AuthenticationService.signin(request);
        if (response == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(response);
    }

}
