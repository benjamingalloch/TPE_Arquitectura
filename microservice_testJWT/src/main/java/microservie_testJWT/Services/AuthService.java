package microservie_testJWT.Services;

import microservie_testJWT.Security.Model.AuthResponse;
import microservie_testJWT.Security.Model.LoginRequest;

public interface AuthService {
    public AuthResponse login(LoginRequest loginRequest);
    public AuthResponse register(LoginRequest loginRequest);
}
