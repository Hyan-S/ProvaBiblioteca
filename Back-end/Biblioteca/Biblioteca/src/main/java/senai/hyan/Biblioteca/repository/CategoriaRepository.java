package senai.hyan.Biblioteca.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import senai.hyan.Biblioteca.entity.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, UUID>{
    
}
