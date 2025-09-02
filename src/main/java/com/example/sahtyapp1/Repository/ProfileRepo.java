package com.example.sahtyapp1.Repository;

import com.example.sahtyapp1.Entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepo extends JpaRepository<Profile , Long> {
}
