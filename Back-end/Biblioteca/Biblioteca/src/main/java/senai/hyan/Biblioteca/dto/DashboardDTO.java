package senai.hyan.Biblioteca.dto;

import java.util.List;

public class DashboardDTO {
    private long totalLivros;
    private long totalDisponiveis;
    private long totalEmprestados;
    private long totalEmprestimosAtivos;
    private List<EmprestimoDTO> ultimosEmprestimos;

    public long getTotalLivros() {
        return totalLivros;
    }

    public void setTotalLivros(long totalLivros) {
        this.totalLivros = totalLivros;
    }

    public long getTotalDisponiveis() {
        return totalDisponiveis;
    }

    public void setTotalDisponiveis(long totalDisponiveis) {
        this.totalDisponiveis = totalDisponiveis;
    }

    public long getTotalEmprestados() {
        return totalEmprestados;
    }

    public void setTotalEmprestados(long totalEmprestados) {
        this.totalEmprestados = totalEmprestados;
    }

    public long getTotalEmprestimosAtivos() {
        return totalEmprestimosAtivos;
    }

    public void setTotalEmprestimosAtivos(long totalEmprestimosAtivos) {
        this.totalEmprestimosAtivos = totalEmprestimosAtivos;
    }

    public List<EmprestimoDTO> getUltimosEmprestimos() {
        return ultimosEmprestimos;
    }

    public void setUltimosEmprestimos(List<EmprestimoDTO> ultimosEmprestimos) {
        this.ultimosEmprestimos = ultimosEmprestimos;
    }
}
