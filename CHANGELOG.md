# Changelog

Todas as mudanças relevantes deste projeto são documentadas neste arquivo.
O formato segue [Keep a Changelog](https://keepachangelog.com/pt-BR/1.0.0/)
e o projeto adota [Versionamento Semântico](https://semver.org/lang/pt-BR/).

## [1.0.1] - 2026-05-24

### Fixed
- Validação de exclusão de livro: agora bloqueia a exclusão quando existe
  empréstimo ativo em aberto e retorna HTTP 409 (Conflict) (#4)

## [1.0.0] - 2026-05-24

### Added
- RF01: CRUD de Categorias com validação de nome único e de exclusão (#1)
- RF02: CRUD de Livros com filtros, busca e controle de status (#2)
- RF03: Sistema de empréstimos (emprestar/devolver) (#3)
- Pipeline de CI: build do backend (Maven) e do frontend (Angular)
- Status automático do livro: DISPONIVEL <-> EMPRESTADO

### Technical
- 3 entidades: Categoria, Livro, Emprestimo
- Camada de serviço com a lógica de emprestar/devolver
- Branch protection configurada no `master`

## [0.1.0] - 2026-05-24

### Added
- Configuração inicial do repositório e estrutura base do projeto
