# Backend - Gestão de Biblioteca

Este projeto oferece uma API de backend para a gestão de uma biblioteca, permitindo operações de CRUD para usuários,
livros e empréstimos.

## Funcionalidades

- **Usuários**:
    - Criar, atualizar, buscar todos, buscar por ID e excluir.

- **Livros**:
    - Criar, atualizar, buscar todos, buscar por ID e excluir.

- **Empréstimos**:
    - Registrar empréstimos, realizar devoluções, buscar empréstimos e sugerir livros.

## Como Rodar o Projeto

### Pré-requisitos

1. Clonar e Compilar o Projeto `brestrai-utils`:
    - Clone o repositório: [https://github.com/BREstrai/brestrai-utils](https://github.com/BREstrai/brestrai-utils).
    - Compile o projeto seguindo as instruções do README.
    - O `brestrai-utils` é uma biblioteca de utilitários desenvolvida para facilitar a abstração de funcionalidades
      comuns entre projetos, ele contém algumas abstrações e configurações necessárias para rodar esse projeto.

2. Configuração do Banco de Dados (PostgreSQL):
    - **Usando Docker**:
        - Utilize o arquivo `docker-compose.yaml` localizado na raiz do projeto para rodar o PostgreSQL via Docker.

    - **Instalação Local**:
        - Se o PostgreSQL estiver instalado localmente, configure a conexão no arquivo `application.properties` de
          acordo com suas credenciais locais.

### Testando a API

1. Você pode realizar testes na API sem a necessidade de um frontend, utilizando o Postman.
    - Importe o arquivo de coleção localizado em:
        - src/main/resources/postman/gestao-biblioteca.postman_collection.json para testar as rotas disponíveis.

### Tecnologias Utilizadas

    - Java 17+

    - Spring Boot

    - PostgreSQL

    - Docker (opcional)