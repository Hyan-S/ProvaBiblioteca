import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators, FormsModule } from '@angular/forms';
import { ApiService } from './api.service';
import { Categoria, Emprestimo, Livro, LivroStatus } from './models';

@Component({
  selector: 'app-livros',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  template: `
    <h2>Livros</h2>

    <form [formGroup]="form" (ngSubmit)="salvar()" class="form">
      <input formControlName="titulo" placeholder="Título" />
      <input formControlName="autor" placeholder="Autor" />
      <input formControlName="isbn" placeholder="ISBN" />
      <input formControlName="ano" type="number" placeholder="Ano" />
      <select formControlName="categoriaId">
        <option [ngValue]="null">-- Categoria --</option>
        @for (c of categorias; track c.id) {
          <option [ngValue]="c.id">{{ c.nome }}</option>
        }
      </select>
      <button type="submit" [disabled]="form.invalid">Adicionar</button>
    </form>

    <div class="filtros">
      <select [(ngModel)]="filtroCategoria" (change)="carregar()">
        <option [ngValue]="null">Todas categorias</option>
        @for (c of categorias; track c.id) {
          <option [ngValue]="c.id">{{ c.nome }}</option>
        }
      </select>
      <select [(ngModel)]="filtroStatus" (change)="carregar()">
        <option [ngValue]="null">Todos status</option>
        <option value="DISPONIVEL">Disponível</option>
        <option value="EMPRESTADO">Emprestado</option>
      </select>
      <input [(ngModel)]="busca" (input)="carregar()" placeholder="Buscar por título/autor" />
    </div>

    @if (erro) { <p class="erro">{{ erro }}</p> }

    <table>
      <thead><tr><th>Título</th><th>Autor</th><th>ISBN</th><th>Ano</th><th>Categoria</th><th>Status</th><th></th></tr></thead>
      <tbody>
        @for (l of livros; track l.id) {
          <tr>
            <td>{{ l.titulo }}</td>
            <td>{{ l.autor }}</td>
            <td>{{ l.isbn }}</td>
            <td>{{ l.ano }}</td>
            <td>{{ l.categoriaNome }}</td>
            <td><span class="badge" [class.emp]="l.status === 'EMPRESTADO'">{{ l.status }}</span></td>
            <td>
              <button (click)="historico(l.id!)">Histórico</button>
              <button (click)="deletar(l.id!)">Excluir</button>
            </td>
          </tr>
        }
      </tbody>
    </table>

    @if (historicoLivro.length > 0 || historicoTitulo) {
      <h3>Histórico de "{{ historicoTitulo }}"</h3>
      @if (historicoLivro.length === 0) {
        <p>Sem empréstimos registrados.</p>
      } @else {
        <table>
          <thead><tr><th>Pessoa</th><th>Empréstimo</th><th>Devolução prevista</th><th>Devolução efetiva</th></tr></thead>
          <tbody>
            @for (e of historicoLivro; track e.id) {
              <tr>
                <td>{{ e.nomePessoa }}</td>
                <td>{{ e.dataEmprestimo }}</td>
                <td>{{ e.dataDevolucaoPrevista }}</td>
                <td>{{ e.dataDevolucaoEfetiva || '—' }}</td>
              </tr>
            }
          </tbody>
        </table>
      }
    }
  `,
  styles: [`
    .form, .filtros { display: flex; gap: 8px; margin-bottom: 20px; flex-wrap: wrap; }
    .form input, .form select, .filtros input, .filtros select { padding: 6px; }
    .form button { padding: 6px 12px; }
    table { width: 100%; border-collapse: collapse; margin-bottom: 20px; }
    th, td { padding: 8px; border-bottom: 1px solid #ddd; text-align: left; }
    .badge { background: #c8e6c9; padding: 3px 8px; border-radius: 4px; font-size: 12px; }
    .badge.emp { background: #ffcdd2; }
    .erro { color: #c00; }
    button { cursor: pointer; margin-right: 4px; }
  `]
})
export class LivrosComponent implements OnInit {
  private api = inject(ApiService);
  private fb = inject(FormBuilder);

  livros: Livro[] = [];
  categorias: Categoria[] = [];
  historicoLivro: Emprestimo[] = [];
  historicoTitulo = '';
  erro = '';

  filtroCategoria: number | null = null;
  filtroStatus: LivroStatus | null = null;
  busca = '';

  form = this.fb.group({
    titulo: ['', Validators.required],
    autor: ['', Validators.required],
    isbn: [''],
    ano: [null as number | null],
    categoriaId: [null as number | null]
  });

  ngOnInit() {
    this.api.listarCategorias().subscribe(c => this.categorias = c);
    this.carregar();
  }

  carregar() {
    this.api.listarLivros(this.filtroCategoria, this.filtroStatus, this.busca).subscribe(l => this.livros = l);
  }

  salvar() {
    this.erro = '';
    this.api.criarLivro(this.form.value as Livro).subscribe({
      next: () => {
        this.form.reset({ titulo: '', autor: '', isbn: '', ano: null, categoriaId: null });
        this.carregar();
      },
      error: (e) => this.erro = e.error?.message || 'Erro ao salvar'
    });
  }

  deletar(id: number) {
    this.erro = '';
    if (!confirm('Excluir livro?')) return;
    this.api.deletarLivro(id).subscribe({
      next: () => this.carregar(),
      error: (e) => this.erro = e.error?.message || 'Erro ao excluir'
    });
  }

  historico(id: number) {
    const livro = this.livros.find(l => l.id === id);
    this.historicoTitulo = livro?.titulo || '';
    this.api.historicoLivro(id).subscribe(h => this.historicoLivro = h);
  }
}
