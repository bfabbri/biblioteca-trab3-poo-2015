//package br.usp.icmc.biblioteca;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.*;

public class Biblioteca {

	ArrayList < Usuario > usuarios_cadastrados;
	ArrayList < Livro > livros_cadastrados;
	ArrayList < Emprestimo > emprestimos;


	public Biblioteca() {
		usuarios_cadastrados = new ArrayList < Usuario > ();
		livros_cadastrados = new ArrayList < Livro > ();
		emprestimos = new ArrayList < Emprestimo > ();
	}

	public boolean existe_algum_usuario() {
		if (usuarios_cadastrados.size() > 0)
			return true;
		return false;
	}

	public boolean existe_algum_livro() {
		if (livros_cadastrados.size() > 0)
			return true;
		return false;
	}

	public boolean existe_algum_emprestimo() {
		if (emprestimos.size() > 0)
			return true;
		return false;
	}

	public boolean existe_algum_atraso(Date dia_de_hoje) {
		for(Emprestimo e : emprestimos)
			if(dia_de_hoje.after(e.getData_Devolucao()))
				return true;
		return false;	
	}

	public boolean usuarioExiste(String cpf) {
		for (Usuario u : usuarios_cadastrados)
			if (u.getCPF().equals(cpf)) 
				return true;

		return false;
	}

	public Usuario getObjUsuario(String cpf) {
		Usuario u = new Usuario();
		for (Usuario u_aux : usuarios_cadastrados)
			if (u_aux.getCPF().equals(cpf)) 
				return u_aux;
		return u;
	}

	public boolean livroExiste(int isbn) {
		for (Livro l : livros_cadastrados)
			if (l.getCodigo() == isbn) 
			return true;
		return false;
	}

	public Livro getObjLivro(int isbn) {
		int i;
		Livro l = new Livro();
		for (Livro l_aux : livros_cadastrados)
			if (l_aux.getCodigo() == isbn) 
				return l_aux;
		return l;
	}


	public int getIndiceUsuario(String cpf) {
		int i;
		for (i = 0; i < usuarios_cadastrados.size(); i++)
		if (usuarios_cadastrados.get(i).getCPF().equals(cpf)) return i;
		return i;
	}

	public int getIndiceLivro(int isbn) {
		int i;
		for (i = 0; i < livros_cadastrados.size(); i++)
		if (livros_cadastrados.get(i).getCodigo() == isbn) return i;
		return i;
	}

	public boolean usuarioPossuiEmprestimo(String cpf) {
		int i;
		for (i = 0; i < emprestimos.size(); i++)
		if (emprestimos.get(i).getUsuario().getCPF().equals(cpf)) return true;
		return false;
	}


	public boolean adiciona_usuario(String nome, String cpf, int tipo_usuario) {
		Usuario u = new Usuario();
		int i;

		u.setNome(nome);
		u.setCPF(cpf);
		u.setSuspenso(false);
		u.setQte_Livros_Emprestados(0);
		u.setTipo(tipo_usuario);


		//percorre a arraylist de usuarios_cadastrados pra ver se o usuario ja existe nos cadastros.
		for (i = 0; i < usuarios_cadastrados.size(); i++) {
			if (usuarios_cadastrados.get(i).getCPF().equals(cpf)) {
				return false;
			}
		}


		//caso nao existir, o for inteiro sera percorrido e no final i sera igual ao tamanho da arraylist. Assim, saberemos que o cadastro de um novo usuario devera ser realizado
		if (i == usuarios_cadastrados.size()) {
			//caso seja um usuario do tipo aluno
			if (u.getTipo() == 1) {
				u.setNum_Max_De_Emprestimos(4);
				u.setDias_Max_De_Emprestimo(15);
			}

			//caso seja um usuario do tipo professor
			if (u.getTipo() == 2) {
				u.setNum_Max_De_Emprestimos(6);
				u.setDias_Max_De_Emprestimo(60);
			}

			//caso seja um usuario do tipo comunidade
			if (u.getTipo() == 3) {
				u.setNum_Max_De_Emprestimos(2);
				u.setDias_Max_De_Emprestimo(15);
			}

			usuarios_cadastrados.add(u);
		}

		return true;
	}


	public void adiciona_livro(String titulo, String autor, int codigo, int quantidade, int tipo_livro) {
		Livro l = new Livro();
		int i;

		l.setTitulo(titulo);
		l.setAutor(autor);
		l.setCodigo(codigo);
		l.setQuantidade(quantidade);
		l.setTipo(tipo_livro);

		//percorre a arraylist de livros_cadastrados pra ver se o livro ja existe nos cadastros. Se ja existir, ele apenas incrementa a qte de livros
		for (i = 0; i < livros_cadastrados.size(); i++) {
			if (livros_cadastrados.get(i).getCodigo() == codigo) {
				livros_cadastrados.get(i).setQuantidade(livros_cadastrados.get(i).getQuantidade() + quantidade);
				break;
			}
		}

		//caso nao existir, o for inteiro sera percorrido e no final i sera igual ao tamanho da arraylist. Assim, saberemos que o cadastro de um novo livro devera ser realizado
		if (i == livros_cadastrados.size()) {
			livros_cadastrados.add(l);
		}

	}


	public void listar_usuarios() {
		if (existe_algum_usuario()) {
			System.out.println("=======================================================");
			System.out.format(
				"|%-25s |%-15s |%-10s",
			 	"Nome", 
			 	"CPF",
			 	"Tipo"
		 	);
			System.out.println("\n=======================================================");
			for (Usuario u : usuarios_cadastrados)
				//System.out.println(usuarios_cadastrados.get(i).toString());	
				System.out.format(
					"|%-25s |%-15s |%-10s%n",
				 	u.getNome(), 
				 	u.getCPF(),
				 	u.getTipoStr()
			 	);
			System.out.println("-------------------------------------------------------");	
		}
		else
			System.out.println("\n\nAinda não existem usuários cadastrados...");
	}


	public void listar_livros() {
		if (existe_algum_livro()) {
			System.out.println("==========================================================================================");
			System.out.format(
				"|%-25s |%-25s |%-10s |%-10s |%-5s",
			 	"Titulo", 
			 	"Autor",
			 	"ISBN",
			 	"Quantidade",
			 	"Tipo"
		 	);
			System.out.println("\n==========================================================================================");
			for (Livro l : livros_cadastrados)
				System.out.format(
					"|%-25s |%-25s |%-10s |%-10s |%-5s%n",
				 	l.getTitulo(), 
				 	l.getAutor(),
				 	l.getCodigo(),
				 	l.getQuantidade(),
				 	l.getTipoStr()
			 	);
			System.out.println("------------------------------------------------------------------------------------------");
		}
		else
			System.out.println("\n\nAinda não existem livros cadastrados...");
	}


	public void listar_emprestimos() {
		if(existe_algum_emprestimo()) {
			System.out.println("=======================================================================================");
			System.out.format(
				"|%-25s |%-25s |%-20s |%-9s",
				"Livro",
				"Usuario",
				"Emprestimo",
				"Devolucao"
			);
			System.out.println("\n=======================================================================================");
			for (Emprestimo e: emprestimos)
				System.out.format(
				"|%-25s |%-25s |%-20s |%-9s%n",
				e.getLivro().getTitulo(),
				e.getUsuario().getNome(),
				new SimpleDateFormat("dd/MM/yyyy").format(e.getData_Emprestimo()),
				new SimpleDateFormat("dd/MM/yyyy").format(e.getData_Devolucao())
			);	
			System.out.println("---------------------------------------------------------------------------------------");
		}
		else
			System.out.println("\n\nAinda não foram feitos emprestimos...");
	}




	//retorna 1 se fez o emprestimo com sucesso
	//retorna 2 se o usuario u ja emprestou o livro l
	//retorna 3 se o usuario u ja atigiu o num_max de livro que ele pode emprestar
	//retorna 4 se o tipo do usuario eh incompativel com o tipode livro
	//retorna 5 se o aluno u estiver suspenso
	//retorna 6 se nao houver mais o livro l na biblioteca
	public int empresta(Usuario u, Livro l, Date dia_de_hoje) {
		//checar para ver se ja nao houve um emprestimo do usuario u com o livro l
		List < Emprestimo > lsr = emprestimos.stream()
			.filter(e -> e.getUsuario().equals(u))
			.filter(e -> e.getLivro().equals(l))
			.collect(Collectors.toList());
		if (lsr.size() > 0) {
			return 2;
		}


		//checa se o usuario ja atingiu o num max de livros que ele pode emprestar
		if (u.getQte_Livros_Emprestados() >= u.getNum_Max_De_Emprestimos()) {
			return 3;
		}

		//checa se o tipo de usuario eh compativel com o tipo de livro
		if (u.getTipo() == 3 && l.getTipo() == 1) {
			return 4;
		}

		//checa para ver se ainda existe o livro l (ve se ele ainda esta em "estoque")
		if (l.getQuantidade() == 0) {
			return 6;
		}

		//caso todas as as checagens estejam ok, o livro eh emprestado
		u.insereLivro(l);
		l.setQuantidade(l.getQuantidade() - 1);


		Emprestimo e = new Emprestimo();

		e.setLivro(l);
		e.setUsuario(u);
		e.setData_Emprestimo(dia_de_hoje); //data que estiver setada no programa

		Date d1 = e.getData_Emprestimo();
		Date d2 = new Date();

		d2.setTime(d1.getTime() + (u.getDias_Max_De_Emprestimo() * 24 * 60 * 60 * 1000)); //mseg.
		e.setData_Devolucao(d2);

		emprestimos.add(e);

		return 1;
	}


	public int devolve(Usuario u, Livro l, Date dia_de_hoje) {

		//tem que achar na arraylist de "emprestimos" o emprestimo relacionado a devolucao que esta sendo feita, pra poder calcular se houve atraso e pra poder retirar ele da arraylist
		List < Emprestimo > lsr = emprestimos.stream()
			.filter(e -> e.getUsuario().equals(u))
			.filter(e -> e.getLivro().equals(l))
			.collect(Collectors.toList());

		if (lsr.size() == 0) {
			return 2;
		}

		//checa para ver se houve atraso na entrega ou nao
		//se o dia da entrega for maior que o dia que deveria ter entregado...
		if (dia_de_hoje.after(lsr.get(0).getData_Devolucao())) {
			int dias_de_suspensao;
			Date d_aux = new Date();

			//subtrai a data do dia com a data de suposta devolucao e tranforma para um int
			dias_de_suspensao = (int)(dia_de_hoje.getTime() - lsr.get(0).getData_Devolucao().getTime());
			//guarda em d_aux a data do dia somando o numero de dias que o ususario esta suspenso (que eh o int dias_de_suspensao)
			d_aux.setTime(dia_de_hoje.getTime() + (dias_de_suspensao));

			lsr.get(0).getUsuario().setSuspenso_Ate(d_aux);
			lsr.get(0).getUsuario().setSuspenso(true);

			System.out.println("\n\nSUSPENSO ATE: " + new SimpleDateFormat("dd/MM/yyyy").format(lsr.get(0).getUsuario().getSuspenso_Ate()));
		}

		u.removeLivro(l);
		l.setQuantidade(l.getQuantidade() + 1);

		//precisa remover o emprestimo da arraylist "emprestimos"
		emprestimos.remove(lsr.get(0));

		return 1;
	}


	//ao abrir o programa, apos setar data, essa funcao eh chamada para verificar se algum dos emprestimos esta em atraso
	public void verifica_atraso(Date dia_de_hoje) {
		int i;
		//percorre a arraylist de empretimos
		for (i = 0; i < emprestimos.size(); i++) {
			//se a dia_de_hoje for maior que a data_devolucao, quer dizer que o livro esta em atraso, e portanto o aluno deve ficar suspenso
			if (dia_de_hoje.after(emprestimos.get(i).getData_Devolucao())) {
				emprestimos.get(i).getUsuario().setSuspenso(true);
				//ele faz isso para qdo o usuario esta suspenso e nao devolveu o livro.
				//dai caso ele for tentar emprestar um livro, a funcao verifica_suspensao retornara true.
				emprestimos.get(i).getUsuario().setSuspenso_Ate(dia_de_hoje);
			}
		}
	}


	public boolean verifica_suspensao(Usuario u, Date dia_de_hoje) {

		//se o usuario estiver suspenso...
		if (u.getSuspenso() == true) {
			//checa para ver se a data do sistema eh maior que a data ate em que o aluno estava suspenso.
			//se a data de hoje da passou o prazo de suspensao, ele volta pra nao suspenso e retorna que ele nao esta suspenso
			if (dia_de_hoje.after(u.getSuspenso_Ate())) {
				u.setSuspenso(false);
				return false;
			}

			//se a data de hoje aind nao passou a suspensao, ele fala que o usuario ainda ate suspenso ate data_ate
			else return true;

		} else return false;

	}


	public void listar_atrasos(Date dia_de_hoje) {
		if(existe_algum_atraso(dia_de_hoje)) {
			System.out.println("=======================================================================================");
			System.out.format(
				"|%-25s |%-25s |%-20s |%-9s",
				"Livro",
				"Usuario",
				"Emprestimo",
				"Devolucao"
			);
			System.out.println("\n=======================================================================================");
			for (Emprestimo e: emprestimos)
				System.out.format(
				"|%-25s |%-25s |%-20s |%-9s%n",
				e.getLivro().getTitulo(),
				e.getUsuario().getNome(),
				new SimpleDateFormat("dd/MM/yyyy").format(e.getData_Emprestimo()),
				new SimpleDateFormat("dd/MM/yyyy").format(e.getData_Devolucao())
			);	
			System.out.println("---------------------------------------------------------------------------------------");
		}
		else
			System.out.println("\n\nNao existe nenhum atraso...");
		}
}
