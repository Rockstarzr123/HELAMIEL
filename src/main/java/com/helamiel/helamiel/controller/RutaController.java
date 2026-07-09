package com.helamiel.helamiel.controller;

import com.helamiel.helamiel.model.Ruta;
import com.helamiel.helamiel.service.RutaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RutaController {

    private final RutaService service;

    public RutaController(RutaService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String inicio(Model model) {

        model.addAttribute("rutas", service.listar());
        model.addAttribute("ruta", new Ruta());

        return "index";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Ruta ruta){

        service.guardar(ruta);

        return "redirect:/";
    }

}