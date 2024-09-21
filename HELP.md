# Backend - Gestão de Biblioteca

### Backend utilizado para realizar os processos de crud para:

    - Usuarios (criação, atualização, busca de todos, busca por id e exclusão)
    - Livros (criação, atualização, busca de todos, busca por id e exclusão)
    - Empréstimos (emprestimos, devoluções, buscas e sugestões)

### Para rodar o projeto é necessário:

    - [IMPORTANTE] Clonar o projeto disponível em: https://github.com/BREstrai/brestrai-utils  
        - Compilar o projeto através da instrução do READ-ME
        - O brestrai-utils é um utilitário desenvolvido por mim para abstrair funcionalidades entre projetos
        
    - Ter o Postgres instalado ou rodar através do docker pelo arquivo [docker-compose.yaml] da raiz.
        - Caso utilize uma versão instalada em sua máquina, é necessário configurar no application.properties a conexão

### Para realizar testes sem a utilização de um frontend, é possível utilizar dentro dos resources/postman o arquivo:

[gestao biblioteca.postman_collection.json](gestao-biblioteca-app%2Fsrc%2Fmain%2Fresources%2Fpostman%2Fgestao%20biblioteca.postman_collection.json)

