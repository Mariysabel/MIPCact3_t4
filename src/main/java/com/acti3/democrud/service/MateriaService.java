
package com.acti3.democrud.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.acti3.democrud.DTO.MateriaDTO;
import com.acti3.democrud.models.Materia;
import com.acti3.democrud.repository.MateriaRepository;

@Service
@Transactional
public class MateriaService {

    @Autowired
    private MateriaRepository repo;

    // CONVERTIR A DTO
    private MateriaDTO convertirDTO(Materia materia) {

        MateriaDTO dto = new MateriaDTO();

        dto.setId(materia.getId());
        dto.setNombre(materia.getNombre());
        dto.setCreditos(materia.getCreditos());

        dto.setAlumnos(
            materia.getAlumnos()
                .stream()
                .map(alumno -> alumno.getNombre())
                .collect(Collectors.toList())
        );

        return dto;
    }

    // LISTAR
    public List<MateriaDTO> listar() {

        return repo.findAll()
                .stream()
                .map(this::convertirDTO)
                .collect(Collectors.toList());
    }

    // BUSCAR POR ID
    public MateriaDTO buscar(Integer id) {

        Materia materia = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Materia no encontrada"));

        return convertirDTO(materia);
    }

    // GUARDAR
    public MateriaDTO guardar(MateriaDTO dto) {

        Materia materia = new Materia();

        materia.setNombre(dto.getNombre());
        materia.setCreditos(dto.getCreditos());

        return convertirDTO(repo.save(materia));
    }

    // ACTUALIZAR
    public MateriaDTO actualizar(Integer id, MateriaDTO dto) {

        Materia materia = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Materia no encontrada"));

        materia.setNombre(dto.getNombre());
        materia.setCreditos(dto.getCreditos());

        return convertirDTO(repo.save(materia));
    }

    // ELIMINAR
    public void eliminar(Integer id) {

        Materia materia = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Materia no encontrada"));

        if (!materia.getAlumnos().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se puede eliminar una materia que tiene alumnos");
        }

        repo.delete(materia);
    }
}
