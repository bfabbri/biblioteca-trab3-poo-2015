Este trabalho foi desenvolvido pelos alunos:

Bruno Pinto Ferraz Fabbri - 4154844
Frederico de Oliveira Sampaio - 8922100
Thiago Akio Tanaka - 8922114


	.: Sistema de biblioteca :.

Para rodar o trabalho basta rodar o arquivo Dedalus.jar.

Inicialmente, o programa pede para que seja setada a data atual para que as simulações possam ser feitas. Feito isso, o programa apresenta um menu auto-explicativo para acessar cada uma de suas funções, que são:

(1) Menu Livro:
(1.1) - Cadastrar livro
(1.2) - Editar livro
(1.3) - Excluir livro

(2) Menu usuário
(2.1) - Cadastrar usuário
(2.2) - Editar usuário
(2.3) - Excluir usuário

(3) - Alterar data 
(4) - Registrar empréstimo
(5) - Registrar devolução
(6) - Listar todos os livros
(7) - Listar todos os usuários
(8) - Listar todos os empréstimos
(9) - Listar os atrasos
(0) - Sair do programa


Os usuários são identificados através do CPF (string) como chave primária.
No caso dos livros, a chave primária é o ISBN (int).

A base de dados é salva quando o programa é fechado. Ela se divide em 3 tabelas, basicamente, usuários.csv, livros.csv e emprestimos.csv. Além disso, há um arquivo "historico.log" que guarda todo o histórico do programa.


-----------

Bugs conhecidos:

- O programa não limpa a tela a cada iteração com o usuário.

- Caso ocorra devolução com atraso e, em seguida, o programa for fechado e aberto, o usuário não estará mais como suspenso, mesmo que aberto em uma data na qual ele deveria estar.
