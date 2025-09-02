package com.example.sahtyapp1.Repository;

import com.example.sahtyapp1.Entity.Role;
import com.example.sahtyapp1.Entity.UserRoleMed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleMedRepo extends JpaRepository<UserRoleMed, Long> {
    Boolean existsByPieceIdentiteMed(Long pieceIdentiteMed);

}
