import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { ApiService } from './api.service';
import { Emprestimo, Livro } from './models';

@Component({
  selector: 'app-emprestimos',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  template: `
    <h2>Empréstimos</h2>

    <h3>Novo empréstimo</h3>
    <form [formGroup]="form" (ngSubmit)="emprestar()" class="form">
      <select formControlName="livroId">
        <option [ngValue]="null">-- Livro disponível --</option>
        @for (l of livrosDisponiveis; track l.id) {
          <option [ngValue]="l.id">{{ l.titulo }} — {{ l.autor }}</option>
        }
      </select>
      <input formControlName="nomePessoa" placeholder="Nome da pessoa" />
      <input formControlName="telefone" placeholder="Telefone" />
      <input formControlName="dataDevolucaoPrevista" type="date" />
      <button type="submit" [disabled]="form.invalid">Emprestar</button>
    </form>

    @if (erro) { <p class="erro">{{ erro }}</p> }

    <h3>Empréstimos ativos</h3>
    @if (ativos.length === 0) {
      <p>Nenhum empréstimo ativo.</p>
    } @else {
      <table>
        <thead><tr><th>Livro</th><th>Pessoa</th><th>Telefone</th><th>Empréstimo</th><th>Devolução prevista</th><th></th></tr></thead>
        <tbody>
          @for (e of ativos; track e.id) {
            <tr>
              <td>{{ e.livroTitulo }}</td>
              <td>{{ e.nomePessoa }}</td>
              <td>{{ e.telefone }}</td>
              <td>{{ e.dataEmprestimo }}</td>
              <td>{{ e.dataDevolucaoPrevista }}</td>
              <td><button (click)="devolver(e.id!)">Devolver</button></td>
            </tr>
          }
        </tbody>
      </table>
    }

    <h3>Todos os empréstimos</h3>
    <table>
      <thead><tr><th>Livro</th><th>Pessoa</th><th>Empréstimo</th><th>Prevista</th><th>Devolvido em</th></tr></thead>
      <tbody>
        @for (e of todos; track e.id) {
          <tr>
            <td>{{ e.livroTitulo }}</td>
            <td>{{ e.nomePessoa }}</td>
            <td>{{ e.dataEmprestimo }}</td>
            <td>{{ e.dataDevolucaoPrevista }}</td>
            <td>{{ e.dataDevolucaoEfetiva || '—' }}</td>
          </tr>
        }
      </tbody>
    </table>
  `,
  styles: [`
    .form { display: flex; gap: 8px; margin-bottom: 20px; flex-wrap: wrap; }
    .form input, .form select { padding: 6px; }
    .form button { padding: 6px 12px; }
    table { width: 100%; border-collapse: collapse; margin-bottom: 20px; }
    th, td { padding: 8px; border-bottom: 1px solid #ddd; text-align: left; }
    .erro { color: #c00; }
    button { cursor: pointer; }
  `]
})
export class EmprestimosComponent implements OnInit {
  private api = inject(ApiService);
  private fb = inject(FormBuilder);

  livrosDisponiveis: Livro[] = [];
  ativos: Emprestimo[] = [];
  todos: Emprestimo[] = [];
  erro = '';

  form = this.fb.group({
    livroId: [null as number | null, Validators.required],
    nomePessoa: ['', Validators.required],
    telefone: [''],
    dataDevolucaoPrevista: ['', Validators.required]
  });

  ngOnInit() {
    this.carregar();
  }

  carregar() {
    this.api.listarLivros(null, 'DISPONIVEL', null).subscribe(l => this.livrosDisponiveis = l);
    this.api.listarAtivos().subscribe(a => this.ativos = a);
    this.api.listarEmprestimos().subscribe(t => this.todos = t);
  }

  emprestar() {
    this.erro = '';
    this.api.emprestar(this.form.value as any).subscribe({
      next: () => {
        this.form.reset({ livroId: null, nomePessoa: '', telefone: '', dataDevolucaoPrevista: '' });
        this.carregar();
      },
      error: (e) => this.erro = e.error?.message || 'Erro ao emprestar'
    });
  }

  devolver(id: number) {
    this.erro = '';
    this.api.devolver(id).subscribe({
      next: () => this.carregar(),
      error: (e) => this.erro = e.error?.message || 'Erro ao devolver'
    });
  }
}
