package microservie_testJWT.Security.Model;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AuthResponse {
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }
}
