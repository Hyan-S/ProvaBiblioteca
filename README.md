# MyLibrary — Sistema de Biblioteca Pessoal

Sistema fullstack para gerenciamento de uma biblioteca pessoal, desenvolvido em
**Spring Boot** (backend) e **Angular** (frontend). Este repositório aplica os
**4 Pilares da Gerência de Configuração de Software (GCS)** — Identificação,
Controle de Mudanças, Registro de Status e Auditoria — usando GitHub Issues como
Change Requests e GitHub Actions como pipeline de auditoria.

## Stack

| Camada    | Tecnologia                          |
|-----------|-------------------------------------|
| Backend   | Java 21, Spring Boot 3.3, Spring Data JPA, H2 |
| Frontend  | Angular 21, TypeScript              |
| Build     | Maven (backend), npm/Angular CLI (frontend) |
| CI        | GitHub Actions                      |

## Estrutura do repositório

```
.
├── Back-end/Biblioteca/Biblioteca/   # API Spring Boot
│   └── src/main/java/senai/hyan/Biblioteca/
│       ├── entity/        # Categoria, Livro, LivroStatus, Emprestimo
│       ├── repository/    # Repositórios JPA
│       ├── dto/           # Objetos de transferência
│       ├── service/       # Regras de negócio
│       └── controller/    # Endpoints REST
├── Front-end/Sistema-Biblioteca/     # SPA Angular
│   └── src/app/           # Componentes: dashboard, categorias, livros, emprestimos
└── .github/workflows/ci.yml          # Pipeline de CI (Auditoria)
```

## Requisitos Funcionais

- **RF01 — CRUD de Categorias:** listar (com contagem de livros), criar e excluir
  categorias. Regras: nome único (RN01) e bloqueio de exclusão de categoria com
  livros vinculados (RN02).
- **RF02 — CRUD de Livros:** cadastrar livro vinculado a uma categoria, filtrar por
  categoria/status, buscar por título ou autor. Só permite excluir livro `DISPONIVEL`.
- **RF03 — Empréstimos:** emprestar (status do livro → `EMPRESTADO`) e devolver
  (status → `DISPONIVEL`), com validações de status.

## Como executar

### Backend (porta 8081)

```bash
cd "Back-end/Biblioteca/Biblioteca"
mvn spring-boot:run
```

A API sobe em `http://localhost:8081`. Console H2 em `http://localhost:8081/h2-console`.

### Frontend (porta 4200)

```bash
cd "Front-end/Sistema-Biblioteca"
npm install
npm start
```

A aplicação abre em `http://localhost:4200`.

## GCS — Fluxo de Controle de Mudanças

1. **Identificação:** branches `master` (produção) e `develop` (integração); tags
   versionadas (`v0.1.0`, `v1.0.0`, `v1.0.1`); CHANGELOG mantido.
2. **Controle de Mudanças:** cada requisito é uma Issue (Change Request) implementada
   em uma `feature/*` e integrada via Pull Request com `Closes #N`.
3. **Registro de Status:** Issues, PRs e o CHANGELOG registram o andamento de cada mudança.
4. **Auditoria:** o pipeline de CI (`.github/workflows/ci.yml`) valida backend e
   frontend a cada push/PR antes do merge.

### Mapa de Rastreabilidade

| Requisito                  | Issue | Branch                     | Release  |
|----------------------------|-------|----------------------------|----------|
| RF01 — CRUD Categorias     | #1    | `feature/crud-categorias`  | v1.0.0   |
| RF02 — CRUD Livros         | #2    | `feature/crud-livros`      | v1.0.0   |
| RF03 — Empréstimos         | #3    | `feature/emprestimos`      | v1.0.0   |
| Bug: validação de exclusão | #4    | `hotfix/validacao-exclusao`| v1.0.1   |

> Consulte `GCS-CHECKLIST.md` para o passo a passo das ações no GitHub
> (Branch Protection, Issues, Pull Requests e Releases).
