package senai.hyan.Biblioteca.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import senai.hyan.Biblioteca.dto.EmprestarRequest;
import senai.hyan.Biblioteca.dto.EmprestimoDTO;
import senai.hyan.Biblioteca.entity.Emprestimo;
import senai.hyan.Biblioteca.entity.Livro;
import senai.hyan.Biblioteca.entity.LivroStatus;
import senai.hyan.Biblioteca.repository.EmprestimoRepository;
import senai.hyan.Biblioteca.repository.LivroRepository;

@Service
public class EmprestimoService {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private LivroRepository livroRepository;

    public List<EmprestimoDTO> listar() {
        return emprestimoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<EmprestimoDTO> listarAtivos() {
        return emprestimoRepository.findByDataDevolucaoEfetivaIsNull().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<EmprestimoDTO> listarAtrasados() {
        return emprestimoRepository
                .findByDataDevolucaoEfetivaIsNullAndDataDevolucaoPrevistaBefore(LocalDate.now()).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public EmprestimoDTO emprestar(EmprestarRequest req) {
        Livro livro = livroRepository.findById(req.getLivroId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));

        if (livro.getStatus() != LivroStatus.DISPONIVEL) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Livro já está emprestado");
        }

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setLivro(livro);
        emprestimo.setNomePessoa(req.getNomePessoa());
        emprestimo.setTelefone(req.getTelefone());
        emprestimo.setDataEmprestimo(LocalDate.now());
        emprestimo.setDataDevolucaoPrevista(req.getDataDevolucaoPrevista());

        livro.setStatus(LivroStatus.EMPRESTADO);
        livroRepository.save(livro);

        return toDTO(emprestimoRepository.save(emprestimo));
    }

    public EmprestimoDTO devolver(Long id) {
        Emprestimo emprestimo = emprestimoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empréstimo não encontrado"));

        if (emprestimo.getDataDevolucaoEfetiva() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empréstimo já foi devolvido");
        }

        Livro livro = emprestimo.getLivro();
        if (livro.getStatus() == LivroStatus.DISPONIVEL) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Livro já está disponível");
        }

        emprestimo.setDataDevolucaoEfetiva(LocalDate.now());
        livro.setStatus(LivroStatus.DISPONIVEL);
        livroRepository.save(livro);

        return toDTO(emprestimoRepository.save(emprestimo));
    }

    public EmprestimoDTO toDTO(Emprestimo e) {
        EmprestimoDTO dto = new EmprestimoDTO();
        dto.setId(e.getId());
        dto.setLivroId(e.getLivro().getId());
        dto.setLivroTitulo(e.getLivro().getTitulo());
        dto.setNomePessoa(e.getNomePessoa());
        dto.setTelefone(e.getTelefone());
        dto.setDataEmprestimo(e.getDataEmprestimo());
        dto.setDataDevolucaoPrevista(e.getDataDevolucaoPrevista());
        dto.setDataDevolucaoEfetiva(e.getDataDevolucaoEfetiva());

        if (e.getDataDevolucaoEfetiva() == null && e.getDataDevolucaoPrevista().isBefore(LocalDate.now())) {
            dto.setDiasAtraso(ChronoUnit.DAYS.between(e.getDataDevolucaoPrevista(), LocalDate.now()));
        }
        return dto;
    }
}
