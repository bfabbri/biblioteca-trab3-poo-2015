//package br.usp.icmc.biblioteca;

import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;


public class Usuario {
	private String nome;
	private String cpf;
	private int qte_livros_emprestados;
	private ArrayList < Livro > livros; 	//arraylist do tipo Livro chamada livros
	private boolean suspenso; 				//true se o usuario ficar suspenso
	private int num_maximo_de_emprestimos;
	private int dias_maximo_de_emprestimo;
	private int tipo;			 		// aluno -> 1	professor -> 2	comunidade -> 3
	private Date suspenso_ate;

	public Usuario() {
		livros = new ArrayList < Livro > ();
	}


	//tem que criar um construtor pro ArrayList
	public Usuario(String csv) {
		String[] values = csv.split(",");
		setNome(values[0]);
		setCPF(values[1]);
		setTipo(Integer.parseInt(values[2]));
		setQte_Livros_Emprestados(Integer.parseInt(values[3]));
		setSuspenso(Boolean.parseBoolean(values[4]));
		setNum_Max_De_Emprestimos(Integer.parseInt(values[5]));
		setDias_Max_De_Emprestimo(Integer.parseInt(values[6]));

		if (!values[7].equals("0")) {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
   			Date dt;

    		try{ 
    			dt = new SimpleDateFormat("dd/MM/yyyy").parse(values[7].replaceAll("\\r|\\n", ""));
	    		setSuspenso_Ate(dt);
	    		setSuspenso(true);
    		}
    		catch (Exception ex) { ex.printStackTrace();}
		}
		else
			setSuspenso(false);
	}


	public String toString() {
		return getNome() + "\t\t" + getCPF() + "\t\t" + getTipoStr();
	}


	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getCPF() {
		return this.cpf;
	}
	public void setCPF(String cpf) {
		this.cpf = cpf;
	}


	public int getQte_Livros_Emprestados() {
		return this.qte_livros_emprestados;
	}
	public void setQte_Livros_Emprestados(int qte_livros_emprestados) {
		this.qte_livros_emprestados = qte_livros_emprestados;
	}


	public boolean getSuspenso() {
		return this.suspenso;
	}
	public void setSuspenso(boolean suspenso) {
		this.suspenso = suspenso;
	}


	public int getNum_Max_De_Emprestimos() {
		return this.num_maximo_de_emprestimos;
	}
	public void setNum_Max_De_Emprestimos(int num_maximo_de_emprestimos) {
		this.num_maximo_de_emprestimos = num_maximo_de_emprestimos;
	}


	public int getDias_Max_De_Emprestimo() {
		return this.dias_maximo_de_emprestimo;
	}
	public void setDias_Max_De_Emprestimo(int dias_maximo_de_emprestimo) {
		this.dias_maximo_de_emprestimo = dias_maximo_de_emprestimo;
	}


	public int getTipo() {
		return this.tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getTipoStr() {
		String str = new String();
		if (this.tipo == 1) str = "Aluno";
		if (this.tipo == 2) str = "Professor";
		if (this.tipo == 3) str = "Comunidade";
		return str;
	}


	public Date getSuspenso_Ate() {
		return this.suspenso_ate;
	}
	public void setSuspenso_Ate(Date suspenso_ate) {
		this.suspenso_ate = suspenso_ate;
	}


	//insere livro na ArrayList livros
	public void insereLivro(Livro l) {
		livros.add(l);
		this.qte_livros_emprestados++;
	}


	//remove livro da ArrayList livros
	public void removeLivro(Livro l) {
		livros.remove(l);
		this.qte_livros_emprestados--;
	}


	//checa pra ver se o usuario possui o livro l
	public boolean temLivro(Livro l) {
		if (livros.contains(l) == true) //funcao da ArrayList que ve se existe l na arraylist
		return true;
		else return false;
	}
}