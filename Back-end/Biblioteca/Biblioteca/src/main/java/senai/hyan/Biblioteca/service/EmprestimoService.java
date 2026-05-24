package senai.hyan.Biblioteca.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import senai.hyan.Biblioteca.entity.Emprestimo;
import senai.hyan.Biblioteca.repository.EmprestimoRepository;

@Service
public class EmprestimoService {
    
    private EmprestimoRepository _emprestimoRepository;

    //Colocar mais regras/validações aqui
    public Emprestimo CriarEmprestimo(Emprestimo emprestimo){
        return _emprestimoRepository.save(emprestimo);
    }

    public Emprestimo CriarDevolucao(UUID id){
        Emprestimo emprestimoBuscado = BuscarEmprestimoPorId(id);

        if(emprestimoBuscado == null)
            throw new IllegalArgumentException("Emprestimo não identificado ou inexistente");

        emprestimoBuscado.setAtivo(true);

        return _emprestimoRepository.save(emprestimoBuscado);
    }

    public Emprestimo BuscarEmprestimoPorId(UUID id){
        return _emprestimoRepository.findById(id).orElse(null);
    }
}
