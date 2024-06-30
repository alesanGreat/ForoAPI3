package com.alura.foroapi.controller;

import com.alura.foroapi.dto.TopicoDTO;
import com.alura.foroapi.service.TopicoService;
import com.alura.foroapi.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/topicos")
@Validated
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @GetMapping
    public ResponseEntity<Page<TopicoDTO>> listar(Pageable pageable) {
        Page<TopicoDTO> topicos = topicoService.listar(pageable);
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoDTO> obtenerPorId(@PathVariable Long id) {
        TopicoDTO topico = topicoService.obtener(id);
        return ResponseEntity.ok(topico);
    }

    @PostMapping
    public ResponseEntity<TopicoDTO> crear(@Valid @RequestBody TopicoDTO topicoDTO) {
        TopicoDTO topicoCreado = topicoService.crear(topicoDTO);
        return ResponseEntity.status(201).body(topicoCreado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicoDTO> actualizar(@PathVariable Long id, @Valid @RequestBody TopicoDTO topicoDTO) {
        TopicoDTO topicoActualizado = topicoService.actualizar(id, topicoDTO);
        return ResponseEntity.ok(topicoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        topicoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}