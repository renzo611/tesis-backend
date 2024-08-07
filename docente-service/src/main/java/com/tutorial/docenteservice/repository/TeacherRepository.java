package com.tutorial.docenteservice.repository;

import com.tutorial.docenteservice.entity.Availability;
import com.tutorial.docenteservice.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer>{
    boolean existsByLegajo(String legajo);
    boolean existsByDni(String dni);
    Optional<Teacher> findById(int id);
    boolean existsByEmail(String email);
}
