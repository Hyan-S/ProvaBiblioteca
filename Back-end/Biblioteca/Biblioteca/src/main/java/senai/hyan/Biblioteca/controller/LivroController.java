package senai.hyan.Biblioteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import senai.hyan.Biblioteca.dto.EmprestimoDTO;
import senai.hyan.Biblioteca.dto.LivroDTO;
import senai.hyan.Biblioteca.entity.LivroStatus;
import senai.hyan.Biblioteca.service.LivroService;

@RestController
@RequestMapping("/api/livros")
@CrossOrigin(origins = "*")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @GetMapping
    public List<LivroDTO> listar(
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(required = false) LivroStatus status,
            @RequestParam(required = false) String busca) {
        return livroService.listar(categoriaId, status, busca);
    }

    @GetMapping("/{id}")
    public LivroDTO buscarPorId(@PathVariable Long id) {
        return livroService.buscarPorId(id);
    }

    @PostMapping
    public LivroDTO criar(@RequestBody LivroDTO dto) {
        return livroService.toDTO(livroService.criar(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        livroService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/emprestimos")
    public List<EmprestimoDTO> historicoEmprestimos(@PathVariable Long id) {
        return livroService.historicoEmprestimos(id);
    }
}
