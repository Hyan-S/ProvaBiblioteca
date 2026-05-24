package senai.hyan.Biblioteca.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import senai.hyan.Biblioteca.entity.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, UUID>{
    
}
