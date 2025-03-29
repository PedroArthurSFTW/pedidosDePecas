package com.lacamentopeca.pedidosDePecas.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "usuarios")
@Entity(name = "usuarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Usuarios implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuarios_seq")
    @SequenceGenerator(name = "usuarios_seq", sequenceName = "usuarios_seq", allocationSize = 1)
    private Integer id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRoles role;
    private boolean nonLocked;
    private String non_locked_name;
    private boolean accountNonExpired;
    private boolean credentialsNonExpired;
    private boolean enabled;

    public Usuarios(String username, String password, UserRoles role){
        this.username = username;
        this.password = password;
        this.role = role;
        this.non_locked_name = "ATIVO";
        this.nonLocked = true;
        this.accountNonExpired = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRoles.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_TECNICO"));
        else if (this.role == UserRoles.SUPERTECNICO) return List.of(new SimpleGrantedAuthority("ROLE_TECNICO"), new SimpleGrantedAuthority("ROLE_USER"));
        else if (this.role == UserRoles.TECNICO) return List.of(new SimpleGrantedAuthority("ROLE_TECNICO"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return nonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
