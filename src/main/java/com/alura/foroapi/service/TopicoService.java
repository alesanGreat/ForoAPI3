package com.alura.foroapi.service;

import com.alura.foroapi.dto.TopicoDTO;
import com.alura.foroapi.model.Topico;
import com.alura.foroapi.repository.TopicoRepository;
import com.alura.foroapi.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    public Page<TopicoDTO> listar(Pageable pageable) {
        return topicoRepository.findAll(pageable).map(this::convertirADTO);
    }

    public TopicoDTO obtener(Long id) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topico no encontrado con id: " + id));
        return convertirADTO(topico);
    }

    @Transactional
    public TopicoDTO crear(TopicoDTO topicoDTO) {
        Topico topico = new Topico();
        topico.setTitulo(topicoDTO.getTitulo());
        topico.setMensaje(topicoDTO.getMensaje());
        topico.setAutor(topicoDTO.getAutor());
        Topico nuevoTopico = topicoRepository.save(topico);
        return convertirADTO(nuevoTopico);
    }

    @Transactional
    public TopicoDTO actualizar(Long id, TopicoDTO topicoDTO) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topico no encontrado con id: " + id));
        topico.setTitulo(topicoDTO.getTitulo());
        topico.setMensaje(topicoDTO.getMensaje());
        topico.setAutor(topicoDTO.getAutor());
        Topico topicoActualizado = topicoRepository.save(topico);
        return convertirADTO(topicoActualizado);
    }

    @Transactional
    public void eliminar(Long id) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topico no encontrado con id: " + id));
        topicoRepository.delete(topico);
    }

    private TopicoDTO convertirADTO(Topico topico) {
        TopicoDTO topicoDTO = new TopicoDTO();
        topicoDTO.setId(topico.getId());
        topicoDTO.setTitulo(topico.getTitulo());
        topicoDTO.setMensaje(topico.getMensaje());
        topicoDTO.setAutor(topico.getAutor());
        return topicoDTO;
    }
}