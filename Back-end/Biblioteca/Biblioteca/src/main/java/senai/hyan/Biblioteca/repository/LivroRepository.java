package senai.hyan.Biblioteca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import senai.hyan.Biblioteca.entity.Livro;
import senai.hyan.Biblioteca.entity.LivroStatus;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    long countByCategoriaId(Long categoriaId);

    long countByStatus(LivroStatus status);

    @Query("SELECT l FROM Livro l WHERE "
            + "(:categoriaId IS NULL OR l.categoria.id = :categoriaId) AND "
            + "(:status IS NULL OR l.status = :status) AND "
            + "(:busca IS NULL OR LOWER(l.titulo) LIKE LOWER(CONCAT('%', :busca, '%')) "
            + "OR LOWER(l.autor) LIKE LOWER(CONCAT('%', :busca, '%')))")
    List<Livro> buscarComFiltros(@Param("categoriaId") Long categoriaId,
                                 @Param("status") LivroStatus status,
                                 @Param("busca") String busca);
}
