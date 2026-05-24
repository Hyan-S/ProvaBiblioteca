# GCS — Passo a passo no GitHub (ações que exigem o navegador)

> O trabalho local (branches, tags, CI, README, CHANGELOG e o código de cada RF
> distribuído em `feature/*`) já está feito e enviado (push). Este arquivo lista
> apenas o que precisa ser feito pela interface web do GitHub, em ordem.
>
> Repositório: https://github.com/Hyan-S/ProvaBiblioteca

Branches já no remoto:
`master` (principal), `develop`, `feature/crud-categorias`, `feature/crud-livros`,
`feature/emprestimos`, `hotfix/validacao-exclusao`.
Tag já no remoto: `v0.1.0`.

---

## 1. Branch Protection no `master` (Pilar: Identificação/Controle)

`Settings` → `Branches` → `Add branch ruleset` (ou *Add rule* clássico) para `master`:
- [x] **Require a pull request before merging**
- [x] **Require status checks to pass before merging** → selecione
  `Build Backend (Maven)` e `Build Frontend (Angular)`
  (aparecem após a 1ª execução do CI).

---

## 2. Issues (Change Requests) — Pilar: Controle de Mudanças

Crie em `Issues` → `New issue`. Labels: `enhancement` e `approved`.

### Issue #1 — `[FEATURE] RF01 - CRUD Categorias`
```
## O que vai ser feito
- Backend: Entidade Categoria + Repository + Controller (GET, POST, DELETE)
- Frontend: categoria-list + categoria-form
- Validação: RN01 (nome único), RN02 (não excluir com livros)

## Critérios de Aceite
- CA01.1: Listar categorias com contagem de livros
- CA01.2: Criar nova categoria
- CA01.3: Excluir categoria SEM livros
- CA01.4: Bloquear exclusão se houver livros
```

### Issue #2 — `[FEATURE] RF02 - CRUD Livros`
```
## O que vai ser feito
- Backend: Entidade Livro + @ManyToOne Categoria + Repository + Controller
- Frontend: livro-list com filtros + livro-form com dropdown de categorias
- Status inicial: DISPONIVEL

## Critérios de Aceite
- CA02.1: Cadastrar livro vinculado a categoria
- CA02.3: Filtros por categoria e status
- CA02.4: Busca por título ou autor
- CA02.5: Só excluir se DISPONIVEL
```

### Issue #3 — `[FEATURE] RF03 - Sistema de Empréstimos`
```
## O que vai ser feito
- Backend: Entidade Emprestimo + Service Layer (emprestar/devolver)
- Lógica: emprestar muda status -> EMPRESTADO, devolver -> DISPONIVEL
- Frontend: emprestimo-form (emprestar) + botão devolver

## Critérios de Aceite
- CA03.1-3: Emprestar livro disponível
- CA03.4-6: Devolver livro emprestado
- CA03.7-8: Validações de status
```

### Issue #4 — `[BUG] Validação permite excluir livro emprestado`
> Crie esta na Fase 6 (hotfix). Label: `bug`.
```
## Problema
A exclusão de livro não bloqueia quando existe um empréstimo ativo em aberto,
permitindo remover um livro que está com alguém.

## Correção esperada
Validar o status/empréstimo ativo antes de excluir e retornar HTTP 409 (Conflict).
```

---

## 3. Pull Requests — Pilar: Controle de Mudanças + Auditoria

Para cada PR: `Pull requests` → `New pull request`, **base: `develop`**,
**compare: a branch da feature**. Aguarde o CI ficar verde e faça **Merge**.

### PR #1 — base `develop` ← `feature/crud-categorias`
- Título: `feat: implementar RF01 - CRUD Categorias`
- Descrição:
```
Closes #1

Adiciona a API REST do CRUD de Categorias (controller + service + DTO).

Critérios de aceite atendidos:
- [x] CA01.1 Listar categorias com contagem de livros
- [x] CA01.2 Criar nova categoria
- [x] CA01.3 Excluir categoria SEM livros
- [x] CA01.4 Bloquear exclusão se houver livros (RN02)
```

### PR #2 — base `develop` ← `feature/crud-livros`
- Título: `feat: implementar RF02 - CRUD Livros`
- Descrição:
```
Closes #2

Adiciona a API REST do CRUD de Livros (controller + service + DTO) com filtros,
busca e controle de status.

Critérios de aceite atendidos:
- [x] CA02.1 Cadastrar livro vinculado a categoria
- [x] CA02.3 Filtros por categoria e status
- [x] CA02.4 Busca por título ou autor
- [x] CA02.5 Só excluir se DISPONIVEL
```

### PR #3 — base `develop` ← `feature/emprestimos`
- Título: `feat: implementar RF03 - Sistema de Empréstimos`
- Descrição:
```
Closes #3

Adiciona o sistema de empréstimos (service emprestar/devolver, controllers e
dashboard). Emprestar -> EMPRESTADO, devolver -> DISPONIVEL.

Critérios de aceite atendidos:
- [x] CA03.1-3 Emprestar livro disponível
- [x] CA03.4-6 Devolver livro emprestado
- [x] CA03.7-8 Validações de status
```

> Faça o merge de um PR por vez, sempre com o CI verde.

---

## 4. Release v1.0.0 — Pilar: Todos os 4

Após os 3 PRs mergeados em `develop`:

1. Abra um PR **base `master` ← `develop`** (título `release: v1.0.0`) e faça o merge
   com o CI verde. *(Alternativa por linha de comando abaixo.)*
2. `Releases` → `Draft a new release`:
   - **Tag:** `v1.0.0` (target: `master`) — o GitHub cria a tag no merge.
   - **Title:** `v1.0.0 - RF01-03`
   - **Notes:** cole a seção `[1.0.0]` do `CHANGELOG.md`.
   - `Publish release`.

Alternativa local para o passo 1 (se preferir):
```bash
git checkout master && git merge --no-ff develop -m "release: v1.0.0"
git push origin master
```

---

## 5. Hotfix v1.0.1 — Pilar: Todos os 4

A branch `hotfix/validacao-exclusao` (corrige a validação de exclusão) já está no remoto.

1. Crie a **Issue #4** (texto na seção 2).
2. Abra um PR **base `master` ← `hotfix/validacao-exclusao`**:
   - Título: `fix: validar status antes de excluir livro`
   - Descrição: `Closes #4`
   - Merge com o CI verde.
3. `Releases` → `Draft a new release`:
   - **Tag:** `v1.0.1` (target: `master`)
   - **Notes:** cole a seção `[1.0.1]` do `CHANGELOG.md`.
4. Propague o hotfix para `develop` (PR `develop` ← `hotfix/validacao-exclusao`
   ou `git checkout develop && git merge --no-ff hotfix/validacao-exclusao && git push`).

---

## 6. Bônus — Screenshot do Network graph
`Insights` → `Network` e capture o grafo mostrando `develop`, as `feature/*`,
o `hotfix/*` e as merges em `master`.
