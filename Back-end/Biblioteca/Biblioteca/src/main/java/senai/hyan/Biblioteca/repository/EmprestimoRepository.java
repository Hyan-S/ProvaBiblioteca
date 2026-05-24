package senai.hyan.Biblioteca.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import senai.hyan.Biblioteca.entity.Emprestimo;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    List<Emprestimo> findByDataDevolucaoEfetivaIsNull();

    List<Emprestimo> findByDataDevolucaoEfetivaIsNullAndDataDevolucaoPrevistaBefore(LocalDate data);

    List<Emprestimo> findByLivroIdOrderByDataEmprestimoDesc(Long livroId);

    boolean existsByLivroIdAndDataDevolucaoEfetivaIsNull(Long livroId);

    List<Emprestimo> findTop5ByOrderByDataEmprestimoDesc();
}
