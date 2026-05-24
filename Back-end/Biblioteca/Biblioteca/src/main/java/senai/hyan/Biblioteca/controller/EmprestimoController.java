package senai.hyan.Biblioteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import senai.hyan.Biblioteca.dto.EmprestarRequest;
import senai.hyan.Biblioteca.dto.EmprestimoDTO;
import senai.hyan.Biblioteca.service.EmprestimoService;

@RestController
@RequestMapping("/api/emprestimos")
@CrossOrigin(origins = "*")
public class EmprestimoController {

    @Autowired
    private EmprestimoService emprestimoService;

    @GetMapping
    public List<EmprestimoDTO> listar() {
        return emprestimoService.listar();
    }

    @GetMapping("/ativos")
    public List<EmprestimoDTO> ativos() {
        return emprestimoService.listarAtivos();
    }

    @GetMapping("/atrasados")
    public List<EmprestimoDTO> atrasados() {
        return emprestimoService.listarAtrasados();
    }

    @PostMapping("/emprestar")
    public EmprestimoDTO emprestar(@RequestBody EmprestarRequest request) {
        return emprestimoService.emprestar(request);
    }

    @PostMapping("/{id}/devolver")
    public EmprestimoDTO devolver(@PathVariable Long id) {
        return emprestimoService.devolver(id);
    }
}
