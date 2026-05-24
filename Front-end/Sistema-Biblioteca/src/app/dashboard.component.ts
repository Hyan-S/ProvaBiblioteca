import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApiService } from './api.service';
import { Dashboard } from './models';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  template: `
    <h2>Dashboard</h2>
    @if (data) {
      <div class="cards">
        <div class="card"><div class="num">{{ data.totalLivros }}</div><div>Total de livros</div></div>
        <div class="card"><div class="num">{{ data.totalDisponiveis }}</div><div>Disponíveis</div></div>
        <div class="card"><div class="num">{{ data.totalEmprestados }}</div><div>Emprestados</div></div>
        <div class="card"><div class="num">{{ data.totalEmprestimosAtivos }}</div><div>Empréstimos ativos</div></div>
      </div>

      <h3>Últimos 5 empréstimos</h3>
      @if (data.ultimosEmprestimos.length === 0) {
        <p>Nenhum empréstimo registrado.</p>
      } @else {
        <table>
          <thead><tr><th>Livro</th><th>Pessoa</th><th>Data</th><th>Devolução prevista</th></tr></thead>
          <tbody>
            @for (e of data.ultimosEmprestimos; track e.id) {
              <tr>
                <td>{{ e.livroTitulo }}</td>
                <td>{{ e.nomePessoa }}</td>
                <td>{{ e.dataEmprestimo }}</td>
                <td>{{ e.dataDevolucaoPrevista }}</td>
              </tr>
            }
          </tbody>
        </table>
      }
    }
  `,
  styles: [`
    .cards { display: flex; gap: 16px; flex-wrap: wrap; margin-bottom: 20px; }
    .card { background: #f5f5f5; padding: 20px; border-radius: 8px; min-width: 150px; text-align: center; }
    .num { font-size: 32px; font-weight: bold; color: #2c5aa0; }
    table { width: 100%; border-collapse: collapse; }
    th, td { padding: 8px; border-bottom: 1px solid #ddd; text-align: left; }
  `]
})
export class DashboardComponent implements OnInit {
  private api = inject(ApiService);
  data?: Dashboard;

  ngOnInit() {
    this.api.dashboard().subscribe(d => this.data = d);
  }
}
