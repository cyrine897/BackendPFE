package com.example.sahtyapp1.Repository;

import com.example.sahtyapp1.Entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

@Repository
public interface ConsultationRepo extends JpaRepository<Consultation , Long> {
}
