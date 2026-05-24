package senai.hyan.Biblioteca.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import senai.hyan.Biblioteca.dto.CategoriaDTO;
import senai.hyan.Biblioteca.entity.Categoria;
import senai.hyan.Biblioteca.repository.CategoriaRepository;
import senai.hyan.Biblioteca.repository.LivroRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private LivroRepository livroRepository;

    public List<CategoriaDTO> listar() {
        return categoriaRepository.findAll().stream()
                .map(c -> new CategoriaDTO(c.getId(), c.getNome(), c.getDescricao(),
                        livroRepository.countByCategoriaId(c.getId())))
                .collect(Collectors.toList());
    }

    public Categoria criar(Categoria categoria) {
        if (categoriaRepository.existsByNomeIgnoreCase(categoria.getNome())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria com este nome já existe");
        }
        return categoriaRepository.save(categoria);
    }

    public void deletar(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));

        long totalLivros = livroRepository.countByCategoriaId(id);
        if (totalLivros > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Não é possível excluir categoria com livros vinculados");
        }
        categoriaRepository.delete(categoria);
    }
}
