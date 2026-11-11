
package com.acti3.democrud.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.acti3.democrud.DTO.AlumnoDTO;
import com.acti3.democrud.models.Alumno;
import com.acti3.democrud.models.Materia;
import com.acti3.democrud.repository.AlumnoRepository;
import com.acti3.democrud.repository.MateriaRepository;

@Service
public class AlumnoService {

    @Autowired
    private AlumnoRepository repo;

    @Autowired
    private MateriaRepository materiaRepo;

    private AlumnoDTO convertirDTO(Alumno alumno) {

        AlumnoDTO dto = new AlumnoDTO();

        dto.setId(alumno.getId());
        dto.setNombre(alumno.getNombre());
        dto.setEdad(alumno.getEdad());

        if (alumno.getMateria() != null) {
            dto.setMateriaId(alumno.getMateria().getId());
            dto.setMateriaNombre(alumno.getMateria().getNombre());
        }

        return dto;
    }

    public List<AlumnoDTO> listar() {
        return repo.findAll()
                .stream()
                .map(this::convertirDTO)
                .collect(Collectors.toList());
    }

    public AlumnoDTO buscar(Integer id) {

        Alumno alumno = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Alumno no encontrado"));

        return convertirDTO(alumno);
    }

    public AlumnoDTO guardar(AlumnoDTO dto) {

        Alumno alumno = new Alumno();

        alumno.setNombre(dto.getNombre());
        alumno.setEdad(dto.getEdad());

        Materia materia = materiaRepo.findById(dto.getMateriaId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Materia no encontrada"));

        alumno.setMateria(materia);

        return convertirDTO(repo.save(alumno));
    }

    public AlumnoDTO actualizar(Integer id, AlumnoDTO dto) {

        Alumno alumno = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Alumno no encontrado"));

        alumno.setNombre(dto.getNombre());
        alumno.setEdad(dto.getEdad());

        Materia materia = materiaRepo.findById(dto.getMateriaId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Materia no encontrada"));

        alumno.setMateria(materia);

        return convertirDTO(repo.save(alumno));
    }

    public void eliminar(Integer id) {

        if (!repo.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Alumno no encontrado");
        }

        repo.deleteById(id);
    }
}
