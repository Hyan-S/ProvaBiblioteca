package senai.hyan.Biblioteca.dto;

public class CategoriaDTO {
    private Long id;
    private String nome;
    private String descricao;
    private long totalLivros;

    public CategoriaDTO() {
    }

    public CategoriaDTO(Long id, String nome, String descricao, long totalLivros) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.totalLivros = totalLivros;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public long getTotalLivros() {
        return totalLivros;
    }

    public void setTotalLivros(long totalLivros) {
        this.totalLivros = totalLivros;
    }
}
