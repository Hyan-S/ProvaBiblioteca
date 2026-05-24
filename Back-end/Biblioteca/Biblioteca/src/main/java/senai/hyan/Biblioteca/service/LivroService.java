package senai.hyan.Biblioteca.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import senai.hyan.Biblioteca.dto.EmprestimoDTO;
import senai.hyan.Biblioteca.dto.LivroDTO;
import senai.hyan.Biblioteca.entity.Categoria;
import senai.hyan.Biblioteca.entity.Emprestimo;
import senai.hyan.Biblioteca.entity.Livro;
import senai.hyan.Biblioteca.entity.LivroStatus;
import senai.hyan.Biblioteca.repository.CategoriaRepository;
import senai.hyan.Biblioteca.repository.EmprestimoRepository;
import senai.hyan.Biblioteca.repository.LivroRepository;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    public List<LivroDTO> listar(Long categoriaId, LivroStatus status, String busca) {
        String buscaFinal = (busca == null || busca.isBlank()) ? null : busca;
        return livroRepository.buscarComFiltros(categoriaId, status, buscaFinal).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public LivroDTO buscarPorId(Long id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));
        return toDTO(livro);
    }

    public Livro criar(LivroDTO dto) {
        Livro livro = new Livro();
        livro.setTitulo(dto.getTitulo());
        livro.setAutor(dto.getAutor());
        livro.setIsbn(dto.getIsbn());
        livro.setAno(dto.getAno());
        livro.setStatus(LivroStatus.DISPONIVEL);

        if (dto.getCategoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não encontrada"));
            livro.setCategoria(categoria);
        }

        return livroRepository.save(livro);
    }

    public void deletar(Long id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));

        if (livro.getStatus() != LivroStatus.DISPONIVEL) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Não é possível excluir livro emprestado");
        }
        livroRepository.delete(livro);
    }

    public List<EmprestimoDTO> historicoEmprestimos(Long livroId) {
        livroRepository.findById(livroId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));

        return emprestimoRepository.findByLivroIdOrderByDataEmprestimoDesc(livroId).stream()
                .map(this::toEmprestimoDTO)
                .collect(Collectors.toList());
    }

    public LivroDTO toDTO(Livro livro) {
        LivroDTO dto = new LivroDTO();
        dto.setId(livro.getId());
        dto.setTitulo(livro.getTitulo());
        dto.setAutor(livro.getAutor());
        dto.setIsbn(livro.getIsbn());
        dto.setAno(livro.getAno());
        dto.setStatus(livro.getStatus());
        if (livro.getCategoria() != null) {
            dto.setCategoriaId(livro.getCategoria().getId());
            dto.setCategoriaNome(livro.getCategoria().getNome());
        }
        return dto;
    }

    private EmprestimoDTO toEmprestimoDTO(Emprestimo e) {
        EmprestimoDTO dto = new EmprestimoDTO();
        dto.setId(e.getId());
        dto.setLivroId(e.getLivro().getId());
        dto.setLivroTitulo(e.getLivro().getTitulo());
        dto.setNomePessoa(e.getNomePessoa());
        dto.setTelefone(e.getTelefone());
        dto.setDataEmprestimo(e.getDataEmprestimo());
        dto.setDataDevolucaoPrevista(e.getDataDevolucaoPrevista());
        dto.setDataDevolucaoEfetiva(e.getDataDevolucaoEfetiva());
        return dto;
    }
}
