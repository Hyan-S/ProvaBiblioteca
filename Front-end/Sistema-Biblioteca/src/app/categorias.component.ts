import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { ApiService } from './api.service';
import { Categoria } from './models';

@Component({
  selector: 'app-categorias',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  template: `
    <h2>Categorias</h2>
    <form [formGroup]="form" (ngSubmit)="salvar()" class="form">
      <input formControlName="nome" placeholder="Nome" />
      <input formControlName="descricao" placeholder="Descrição" />
      <button type="submit" [disabled]="form.invalid">Adicionar</button>
    </form>

    @if (erro) { <p class="erro">{{ erro }}</p> }

    <table>
      <thead><tr><th>Nome</th><th>Descrição</th><th>Livros</th><th></th></tr></thead>
      <tbody>
        @for (c of categorias; track c.id) {
          <tr>
            <td>{{ c.nome }}</td>
            <td>{{ c.descricao }}</td>
            <td>{{ c.totalLivros }}</td>
            <td><button (click)="deletar(c.id!)">Excluir</button></td>
          </tr>
        }
      </tbody>
    </table>
  `,
  styles: [`
    .form { display: flex; gap: 8px; margin-bottom: 20px; }
    .form input { padding: 6px; }
    .form button { padding: 6px 12px; }
    table { width: 100%; border-collapse: collapse; }
    th, td { padding: 8px; border-bottom: 1px solid #ddd; text-align: left; }
    .erro { color: #c00; }
    button { cursor: pointer; }
  `]
})
export class CategoriasComponent implements OnInit {
  private api = inject(ApiService);
  private fb = inject(FormBuilder);

  categorias: Categoria[] = [];
  erro = '';
  form = this.fb.group({
    nome: ['', Validators.required],
    descricao: ['']
  });

  ngOnInit() {
    this.carregar();
  }

  carregar() {
    this.api.listarCategorias().subscribe(c => this.categorias = c);
  }

  salvar() {
    this.erro = '';
    this.api.criarCategoria(this.form.value as Categoria).subscribe({
      next: () => { this.form.reset({ nome: '', descricao: '' }); this.carregar(); },
      error: (e) => this.erro = e.error?.message || 'Erro ao salvar'
    });
  }

  deletar(id: number) {
    this.erro = '';
    if (!confirm('Excluir categoria?')) return;
    this.api.deletarCategoria(id).subscribe({
      next: () => this.carregar(),
      error: (e) => this.erro = e.error?.message || 'Erro ao excluir'
    });
  }
}
