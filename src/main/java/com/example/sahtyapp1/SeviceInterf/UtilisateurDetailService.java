package com.example.sahtyapp1.SeviceInterf;

import com.example.sahtyapp1.Entity.Utilisateur;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UtilisateurDetailService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
