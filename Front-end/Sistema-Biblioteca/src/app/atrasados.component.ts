import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApiService } from './api.service';
import { Emprestimo } from './models';

@Component({
  selector: 'app-atrasados',
  standalone: true,
  imports: [CommonModule],
  template: `
    <h2>Empréstimos atrasados</h2>
    @if (atrasados.length === 0) {
      <p>Nenhum empréstimo atrasado.</p>
    } @else {
      <table>
        <thead><tr><th>Livro</th><th>Pessoa</th><th>Telefone</th><th>Devolução prevista</th><th>Dias de atraso</th></tr></thead>
        <tbody>
          @for (e of atrasados; track e.id) {
            <tr>
              <td>{{ e.livroTitulo }}</td>
              <td>{{ e.nomePessoa }}</td>
              <td>{{ e.telefone }}</td>
              <td>{{ e.dataDevolucaoPrevista }}</td>
              <td><strong>{{ e.diasAtraso }}</strong></td>
            </tr>
          }
        </tbody>
      </table>
    }
  `,
  styles: [`
    table { width: 100%; border-collapse: collapse; }
    th, td { padding: 8px; border-bottom: 1px solid #ddd; text-align: left; }
    strong { color: #c00; }
  `]
})
export class AtrasadosComponent implements OnInit {
  private api = inject(ApiService);
  atrasados: Emprestimo[] = [];

  ngOnInit() {
    this.api.listarAtrasados().subscribe(a => this.atrasados = a);
  }
}
