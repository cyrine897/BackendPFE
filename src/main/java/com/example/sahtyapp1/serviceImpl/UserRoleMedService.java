package com.example.sahtyapp1.serviceImpl;

import com.example.sahtyapp1.Repository.UserRoleMedRepo;
import com.example.sahtyapp1.SeviceInterf.UserRoleMedServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleMedService implements UserRoleMedServiceInt {
    @Autowired
    private UserRoleMedRepo userRoleMedRepo;

    public boolean checkIfPieceIdentiteMedExists(Long pieceIdentiteMed) {
        return userRoleMedRepo.existsByPieceIdentiteMed(pieceIdentiteMed);
    }
}
