package com.acti3.democrud.controllers;

import com.acti3.democrud.models.Materia;
import com.acti3.democrud.repository.MateriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MateriaController {

    @Autowired
    private MateriaRepository repo;

    // LISTAR
    @GetMapping("/materias")
    public String listar(Model model) {
        model.addAttribute("materias", repo.findAll());
        return "materia/index";
    }

    // FORM NUEVO
    @GetMapping("/materias/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("materia", new Materia());
        return "materia/form";
    }

    // GUARDAR
    @PostMapping("/materias/guardar")
    public String guardar(@ModelAttribute Materia materia) {
        repo.save(materia);
        return "redirect:/materias";
    }

    // EDITAR
    @GetMapping("/materias/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {  
        Materia materia = repo.findById(id).orElse(null);
        model.addAttribute("materia", materia);
        return "materia/form";
    }

    // ELIMINAR
    @GetMapping("/materias/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        repo.deleteById(id);
        return "redirect:/materias";
    }
}