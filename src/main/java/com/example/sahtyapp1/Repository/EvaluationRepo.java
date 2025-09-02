package com.example.sahtyapp1.Repository;

import com.example.sahtyapp1.Entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationRepo   extends JpaRepository<Evaluation, Long> {
}
