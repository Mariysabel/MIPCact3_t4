package com.acti3.democrud.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.acti3.democrud.models.Alumno;

public interface AlumnoRepository extends JpaRepository<Alumno, Integer> {
}