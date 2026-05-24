package senai.hyan.Biblioteca.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import senai.hyan.Biblioteca.entity.Livro;
import senai.hyan.Biblioteca.repository.LivroRepository;

@Service
public class LivroService {

    @Autowired
    private LivroRepository _livroRepository;

    public Livro AdicionarLivro(Livro livro){
        return _livroRepository.save(livro);
    }

    public void DeletarLivro(UUID id){
        _livroRepository.deleteById(id);
    }

    public Livro EditarLivro(Livro livro){
        Livro livroBuscado = BuscarLivroPorId(livro.getId());

        if(livroBuscado == null)
            throw new IllegalArgumentException("Id de livro não identificado ou inexistente"); 

        livro.setTitulo(livro.getTitulo());
        livro.setAutor(livro.getAutor());
        livro.setAnoPublicacao(livro.getAnoPublicacao());
        livro.setCategoria(livro.getCategoria());
        livro.setEditora(livro.getEditora());
        livro.setISBN(livro.getISBN());
        livro.setStatus(livro.getStatus());
        livro.setHistorico(livro.getHistorico());

        return _livroRepository.save(livro);
    }

    public Livro BuscarLivroPorId(UUID id){
        return _livroRepository.findById(id).orElse(null);
    }
}