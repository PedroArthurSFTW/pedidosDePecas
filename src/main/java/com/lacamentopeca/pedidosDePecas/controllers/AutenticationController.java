package com.lacamentopeca.pedidosDePecas.controllers;

import com.lacamentopeca.pedidosDePecas.DTO.LockAccountRequest;
import com.lacamentopeca.pedidosDePecas.model.Pedidos;
import com.lacamentopeca.pedidosDePecas.repositories.UsuariosRepository;
import com.lacamentopeca.pedidosDePecas.DTO.AutenticationDTO;
import com.lacamentopeca.pedidosDePecas.DTO.LoginResponseDTO;
import com.lacamentopeca.pedidosDePecas.DTO.RegisterUsuarios;
import com.lacamentopeca.pedidosDePecas.model.Usuarios;
import com.lacamentopeca.pedidosDePecas.infra.security.TokenService;
import com.lacamentopeca.pedidosDePecas.infra.security.UsernameAlreadyExistsException;
import com.lacamentopeca.pedidosDePecas.services.AuthorizationService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("auth")
public class AutenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthorizationService usuarioService;
    @Autowired
    private TokenService tokenService;

    @GetMapping("/all-usuarios")
    public ResponseEntity<List<Usuarios>> getAllUsuarios(){
        List<Usuarios> usuarios = usuarioService.findAll();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Usuarios>> searchUsers(@RequestParam("keyword") String keyword) {
        List<Usuarios> usuarios = usuarioService.getUsuario(keyword);
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/locked")
    public ResponseEntity<List<Usuarios>> lockedAccount(@RequestBody LockAccountRequest request) {
        List<Usuarios> usuarios = usuarioService.toggleLockAccount(request.getId(), request.getValida());
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AutenticationDTO data, HttpServletResponse response){

        try {
            String username = data.getUsername().toLowerCase();
            var usernamePassword = new UsernamePasswordAuthenticationToken(username, data.getPassword());
            var auth = this.authenticationManager.authenticate(usernamePassword);
            if (auth.isAuthenticated()) {
                var usuario = (Usuarios) auth.getPrincipal();
                var token = tokenService.generateToken((Usuarios) auth.getPrincipal());
                var role = usuario.getRole();
                var id = usuario.getId();
                var loginResponseDTO = new LoginResponseDTO(token, username, id, role);
                response.setHeader("Access-Control-Allow-Origin", "*");
                response.addHeader("Authorization", "Bearer " + token);
                return ResponseEntity.ok(loginResponseDTO);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterUsuarios data) {
        try {
            return usuarioService.register(data);
        } catch (UsernameAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }
    }

}
