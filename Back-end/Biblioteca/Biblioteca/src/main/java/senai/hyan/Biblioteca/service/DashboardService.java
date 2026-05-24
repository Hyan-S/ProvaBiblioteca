package senai.hyan.Biblioteca.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import senai.hyan.Biblioteca.dto.DashboardDTO;
import senai.hyan.Biblioteca.entity.LivroStatus;
import senai.hyan.Biblioteca.repository.EmprestimoRepository;
import senai.hyan.Biblioteca.repository.LivroRepository;

@Service
public class DashboardService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private EmprestimoService emprestimoService;

    public DashboardDTO getDashboard() {
        DashboardDTO dto = new DashboardDTO();
        dto.setTotalLivros(livroRepository.count());
        dto.setTotalDisponiveis(livroRepository.countByStatus(LivroStatus.DISPONIVEL));
        dto.setTotalEmprestados(livroRepository.countByStatus(LivroStatus.EMPRESTADO));
        dto.setTotalEmprestimosAtivos(emprestimoRepository.findByDataDevolucaoEfetivaIsNull().size());
        dto.setUltimosEmprestimos(emprestimoRepository.findTop5ByOrderByDataEmprestimoDesc().stream()
                .map(emprestimoService::toDTO)
                .collect(Collectors.toList()));
        return dto;
    }
}
