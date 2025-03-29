package com.lacamentopeca.pedidosDePecas.controllers;

import com.lacamentopeca.pedidosDePecas.DTO.*;
import com.lacamentopeca.pedidosDePecas.exceptions.AuthenticationException;
import com.lacamentopeca.pedidosDePecas.model.Usuarios;
import com.lacamentopeca.pedidosDePecas.infra.security.TokenService;
import com.lacamentopeca.pedidosDePecas.infra.security.UsernameAlreadyExistsException;
import com.lacamentopeca.pedidosDePecas.services.AuthService;
import com.lacamentopeca.pedidosDePecas.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("auth")
@RequiredArgsConstructor
public class AutenticationController {

    private final AuthService authService;
    private final UserService usuarioService;
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
        List<Usuarios> usuarios = usuarioService.searchUsers(keyword);
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
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody @Valid AutenticationDTO data,
            HttpServletResponse response
    ) {
        try {
            LoginResponseDTO authResponse = authService.authenticate(data);
            response.addHeader("Authorization", "Bearer " + authResponse.token());
            return ResponseEntity.ok(authResponse);
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponseDTO> register(@RequestBody @Valid RegisterUsuarios data) {
        try {
            UsuarioResponseDTO response = usuarioService.register(data);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UsernameAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}
