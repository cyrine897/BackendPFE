package com.example.sahtyapp1.SeviceInterf;

public interface Password {
     boolean resetPassword(String token, String newPassword) ;
     void generateResetToken(String email) ;

}
