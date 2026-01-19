package com.example.demo.services;

import com.example.demo.Dtos.AuthRequest;
import com.example.demo.Dtos.Roles;
import com.example.demo.config.PasswordUtil;
import com.example.demo.entities.Users;
import com.example.demo.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.demo.config.Jwt.JwtUtil;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public Users getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<Users> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public ResponseEntity<?> makeUser(AuthRequest request){
        String email = request.getEmail();
        String password = request.getPassword();
        Roles role = request.getRole();
        String username= request.getName_user();
        Long storeU_id = request.getStoreU_id();
        Optional<Users> user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            Users new_user = new Users();
            new_user.setEmail_user(email);
            new_user.setPassword_user(PasswordUtil.hashPassword(password));
            new_user.setRole(role);
            new_user.setName_user(username);
            new_user.setStoreU_id(storeU_id);
            userRepository.save(new_user);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado correctamente. Ahora debe iniciar sesión.");
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ya registrado");
        }
    }

    public ResponseEntity<?> createUser(AuthRequest request, HttpServletRequest httpRequest) {
        String authHeader = httpRequest.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String admin_email= jwtUtil.getEmailFromToken(token);
            Optional<Users> userAdmin = userRepository.findByEmail(admin_email);

            if(!(userAdmin.isEmpty()) && request.getRole()== Roles.ADMINISTRATOR && userAdmin.get().getRole()== Roles.SUPERADMINISTRATOR) {
                return makeUser(request);
            }

            else if (!(userAdmin.isEmpty()) && request.getRole()== Roles.EMPLOYEE && ( userAdmin.get().getRole()== Roles.ADMINISTRATOR || userAdmin.get().getRole()== Roles.SUPERADMINISTRATOR )){
                if (userAdmin.get().getRole()== Roles.SUPERADMINISTRATOR){
                    return makeUser(request);
                }
                else{
                    request.setStoreU_id(userAdmin.get().getStoreU_id());
                    return makeUser(request);
                }
            }
            else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No Authorization role");
            }
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token not received");
        }
    }

    public void updateUser(Users user) {
        user.setPassword_user(PasswordUtil.hashPassword(user.getPassword_user()));
        userRepository.update(user);
    }

    public void deleteUser(Long id) {
        userRepository.delete(id);
    }

    public ResponseEntity<?> verifyUser(AuthRequest request) {

        String email = request.getEmail();
        String password = request.getPassword();

        Optional<Users> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado");
        }
        else if (PasswordUtil.verifyPassword(password, user.get().getPassword_user())) {
            String token = jwtUtil.generateToken(email, user.get().getRole(), user.get().getStoreU_id(), user.get().getName_user());
            return ResponseEntity.ok(Map.of("token", token));
        }
        else if (user.get().getPassword_user().equals(password)) {
            String token = jwtUtil.generateToken(email, user.get().getRole(), user.get().getStoreU_id(), user.get().getName_user());
            return ResponseEntity.ok(Map.of("token", token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }


}
