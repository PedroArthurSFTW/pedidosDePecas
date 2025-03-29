package com.lacamentopeca.pedidosDePecas.services;

import com.lacamentopeca.pedidosDePecas.DTO.RegisterUsuarios;
import com.lacamentopeca.pedidosDePecas.DTO.UsuarioResponseDTO;
import com.lacamentopeca.pedidosDePecas.infra.security.UsernameAlreadyExistsException;
import com.lacamentopeca.pedidosDePecas.model.Usuarios;
import com.lacamentopeca.pedidosDePecas.repositories.UsuariosRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UsuariosRepository usuariosRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuariosRepository.findByUsername(username);
    }

    public List<Usuarios> searchUsers(String keyword) {
        return usuariosRepository.findByUsernameContaining(keyword);
    }

    public Integer obterIdPorNomeDeUsuario(String username) {
        Usuarios user = (Usuarios) usuariosRepository.findByUsername(username);
        if (user != null) {
            return user.getId();
        }
        return null;
    }

    public List<Usuarios> toggleLockAccount(Integer id, Integer valida) {
        Optional<Usuarios> optionalUsuarios = usuariosRepository.findById(id);
        if (optionalUsuarios.isPresent()) {
            Usuarios usuarios = optionalUsuarios.get();
            if (valida == 1) {
                usuarios.setNonLocked(true);
                usuarios.setNon_locked_name("ATIVO");
            } else if (valida == 0) {
                usuarios.setNonLocked(false);
                usuarios.setNon_locked_name("INATIVO");
            } else {
                throw new IllegalArgumentException("Valor de 'valida' inválido");
            }

            usuariosRepository.save(usuarios);
            return usuariosRepository.findAll();
        } else {
            throw new EntityNotFoundException("Usuário não encontrado");
        }
    }

    public UsuarioResponseDTO register(RegisterUsuarios data) {
        if (usuariosRepository.findByUsername(data.username()) != null) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }

        String encryptedPassword = passwordEncoder.encode(data.password());
        Usuarios newUsuario = new Usuarios(data.username(), encryptedPassword, data.role());
        Usuarios usuarioSalvo = usuariosRepository.save(newUsuario);

        return new UsuarioResponseDTO(usuarioSalvo);
    }

    public List<Usuarios> findAll() {
        return usuariosRepository.findAll();
    }
}