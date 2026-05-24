package senai.hyan.Biblioteca.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import senai.hyan.Biblioteca.entity.Categoria;
import senai.hyan.Biblioteca.repository.CategoriaRepository;

@Service
public class CategoriaService {
 
    @Autowired
    private CategoriaRepository _categoriaRepository;

    public Categoria AdicionarCategoria(Categoria categoria){
        return _categoriaRepository.save(categoria);
    }

    public void DeletarCategoria(UUID id){
        _categoriaRepository.deleteById(id);
    }

    public Categoria AtualizarCategoria(Categoria categoria){
        Categoria categoriaBuscada = BuscarCategoriaPorId(categoria.getId());

        if(categoriaBuscada == null)
            throw new IllegalArgumentException("Categoria não identificada ou inexistente");

        categoria.setCategoria(categoria.getCategoria());
        categoria.setDescricao(categoria.getDescricao());

        return _categoriaRepository.save(categoria);
    }

    public Categoria BuscarCategoriaPorId(UUID id){
        return _categoriaRepository.findById(id).orElse(null);
    }
}