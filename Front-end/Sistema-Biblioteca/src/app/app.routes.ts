import { Routes } from '@angular/router';
import { DashboardComponent } from './dashboard.component';
import { CategoriasComponent } from './categorias.component';
import { LivrosComponent } from './livros.component';
import { EmprestimosComponent } from './emprestimos.component';
import { AtrasadosComponent } from './atrasados.component';

export const routes: Routes = [
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'categorias', component: CategoriasComponent },
  { path: 'livros', component: LivrosComponent },
  { path: 'emprestimos', component: EmprestimosComponent },
  { path: 'atrasados', component: AtrasadosComponent }
];
