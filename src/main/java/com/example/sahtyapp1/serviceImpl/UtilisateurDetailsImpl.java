package com.example.sahtyapp1.serviceImpl;


import com.example.sahtyapp1.Entity.Role;
import com.example.sahtyapp1.Entity.Utilisateur;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
@Getter
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class UtilisateurDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;

    private final Long id;
    private final String username;
    private final String email;

    @NotBlank
    private final String prenom;

    @NotBlank
    private final String nom;

    @NotBlank
    private final String adresse;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private final LocalDate dateNaissance;

    private final Long numero;
    private final String rolee;

    @JsonIgnore
    private final String password;

    private final Set<Role> roles;
    private final Collection<? extends GrantedAuthority> authorities;

    public static UtilisateurDetailsImpl build(Utilisateur utilisateur) {
        Set<Role> roles = utilisateur.getRoles();
        Collection<? extends GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return UtilisateurDetailsImpl.builder()
                .id(utilisateur.getIdUser())
                .username(utilisateur.getUsername())
                .email(utilisateur.getEmail())
                .password(utilisateur.getPassword())
                .prenom(utilisateur.getPrenom())
                .nom(utilisateur.getNom())
                .adresse(utilisateur.getAdresse())
                .dateNaissance(utilisateur.getDate_naissance())
                .numero(utilisateur.getNumero())
                .roles(roles)
                .authorities(authorities)
                .rolee(utilisateur.getRolee())
                .build();
    }

    // États du compte → toujours true
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
