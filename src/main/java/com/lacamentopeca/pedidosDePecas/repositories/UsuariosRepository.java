package com.lacamentopeca.pedidosDePecas.repositories;

import com.lacamentopeca.pedidosDePecas.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.List;

public interface UsuariosRepository extends JpaRepository<Usuarios, Integer> {
    //List<Usuarios> findAllActiveTrue();
    UserDetails findByUsername(String username);
    @Query("SELECT u FROM usuarios u WHERE u.username LIKE %:keyword%")
    List<Usuarios> findByUsernameContaining(@Param("keyword") String keyword);
}
