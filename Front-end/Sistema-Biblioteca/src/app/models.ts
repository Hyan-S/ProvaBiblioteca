export type LivroStatus = 'DISPONIVEL' | 'EMPRESTADO';

export interface Categoria {
  id?: number;
  nome: string;
  descricao?: string;
  totalLivros?: number;
}

export interface Livro {
  id?: number;
  titulo: string;
  autor: string;
  isbn?: string;
  ano?: number;
  status?: LivroStatus;
  categoriaId?: number;
  categoriaNome?: string;
}

export interface Emprestimo {
  id?: number;
  livroId: number;
  livroTitulo?: string;
  nomePessoa: string;
  telefone?: string;
  dataEmprestimo?: string;
  dataDevolucaoPrevista: string;
  dataDevolucaoEfetiva?: string;
  diasAtraso?: number;
}

export interface Dashboard {
  totalLivros: number;
  totalDisponiveis: number;
  totalEmprestados: number;
  totalEmprestimosAtivos: number;
  ultimosEmprestimos: Emprestimo[];
}
