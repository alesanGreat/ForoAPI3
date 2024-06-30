package com.alura.foroapi.repository;

import com.alura.foroapi.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {
    // Métodos adicionales personalizados si son necesarios
}
