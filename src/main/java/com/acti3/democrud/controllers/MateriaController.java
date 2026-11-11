
package com.acti3.democrud.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.acti3.democrud.DTO.MateriaDTO;
import com.acti3.democrud.service.MateriaService;

@RestController
@RequestMapping("/api/materias")
public class MateriaController {
    @Autowired
    private MateriaService service;

    @GetMapping
    public ResponseEntity<List<MateriaDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MateriaDTO> buscar(@PathVariable Integer id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @PostMapping
    public ResponseEntity<MateriaDTO> guardar(@RequestBody MateriaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MateriaDTO> actualizar(
            @PathVariable Integer id,
            @RequestBody MateriaDTO dto) {

        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {

        service.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}
