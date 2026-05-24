import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Categoria, Livro, Emprestimo, Dashboard, LivroStatus } from './models';

@Injectable({ providedIn: 'root' })
export class ApiService {
  private http = inject(HttpClient);
  private base = 'http://localhost:8081/api';

  listarCategorias(): Observable<Categoria[]> {
    return this.http.get<Categoria[]>(`${this.base}/categorias`);
  }
  criarCategoria(c: Categoria): Observable<Categoria> {
    return this.http.post<Categoria>(`${this.base}/categorias`, c);
  }
  deletarCategoria(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/categorias/${id}`);
  }

  listarLivros(categoriaId?: number | null, status?: LivroStatus | null, busca?: string | null): Observable<Livro[]> {
    let params = new HttpParams();
    if (categoriaId) params = params.set('categoriaId', categoriaId);
    if (status) params = params.set('status', status);
    if (busca) params = params.set('busca', busca);
    return this.http.get<Livro[]>(`${this.base}/livros`, { params });
  }
  buscarLivro(id: number): Observable<Livro> {
    return this.http.get<Livro>(`${this.base}/livros/${id}`);
  }
  criarLivro(l: Livro): Observable<Livro> {
    return this.http.post<Livro>(`${this.base}/livros`, l);
  }
  deletarLivro(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/livros/${id}`);
  }
  historicoLivro(id: number): Observable<Emprestimo[]> {
    return this.http.get<Emprestimo[]>(`${this.base}/livros/${id}/emprestimos`);
  }

  listarEmprestimos(): Observable<Emprestimo[]> {
    return this.http.get<Emprestimo[]>(`${this.base}/emprestimos`);
  }
  listarAtivos(): Observable<Emprestimo[]> {
    return this.http.get<Emprestimo[]>(`${this.base}/emprestimos/ativos`);
  }
  listarAtrasados(): Observable<Emprestimo[]> {
    return this.http.get<Emprestimo[]>(`${this.base}/emprestimos/atrasados`);
  }
  emprestar(payload: { livroId: number; nomePessoa: string; telefone?: string; dataDevolucaoPrevista: string }): Observable<Emprestimo> {
    return this.http.post<Emprestimo>(`${this.base}/emprestimos/emprestar`, payload);
  }
  devolver(id: number): Observable<Emprestimo> {
    return this.http.post<Emprestimo>(`${this.base}/emprestimos/${id}/devolver`, {});
  }

  dashboard(): Observable<Dashboard> {
    return this.http.get<Dashboard>(`${this.base}/dashboard`);
  }
}
