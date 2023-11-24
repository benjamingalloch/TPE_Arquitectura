package microservie_testJWT.Services;

import microservie_testJWT.Entities.Role;
import microservie_testJWT.Entities.Usuario;
import microservie_testJWT.Repositories.RepositorioUsuario;
import microservie_testJWT.Security.Config.servicioJWT;
import microservie_testJWT.Security.Model.AuthResponse;
import microservie_testJWT.Security.Model.LoginRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final RepositorioUsuario repositorioUsuario;
    private final AuthenticationManager authenticationManager;
    private final servicioJWT jwtServ;
    private final PasswordEncoder passwordEncoder;
    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        var user = repositorioUsuario.findByUsername(loginRequest.getUsername()).orElseThrow();
        var token = jwtServ.generateToken(user);

        AuthResponse aux = new AuthResponse(token);
        return aux;
    }

    @Override
    public AuthResponse register(LoginRequest loginRequest) {
        var user = Usuario.builder()
                .username(loginRequest.getUsername())
                .password(passwordEncoder.encode(loginRequest.getPassword()))
                .role(Role.USUARIO)
                .build();
        repositorioUsuario.save(user);
        var token = jwtServ.generateToken(user);
        return new AuthResponse(token);
    }
}
