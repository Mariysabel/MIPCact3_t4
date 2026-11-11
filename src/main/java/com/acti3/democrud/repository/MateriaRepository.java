package com.acti3.democrud.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.acti3.democrud.models.Materia;

public interface MateriaRepository extends JpaRepository<Materia, Integer> {
}