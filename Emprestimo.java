//package br.usp.icmc.biblioteca;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Emprestimo {

	private Livro livro;
	private Usuario usuario;
	private Date data_emprestimo;
	private Date data_devolucao;

	public String toString() {
		return getLivro().getTitulo() + "\t\t" + 
		getUsuario().getNome() + 
		"\t\t" + 
		new SimpleDateFormat("dd/MM/yyyy").format(getData_Emprestimo()) + 
		"\t\t" + 
		new SimpleDateFormat("dd/MM/yyyy").format(getData_Devolucao());
	}

	public Livro getLivro() {
		return this.livro;
	}
	public void setLivro(Livro livro) {
		this.livro = livro;
	}


	public Usuario getUsuario() {
		return this.usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public Date getData_Emprestimo() {
		return this.data_emprestimo;
	}
	public void setData_Emprestimo(Date data_emprestimo) {
		this.data_emprestimo = data_emprestimo;
	}


	public Date getData_Devolucao() {
		return this.data_devolucao;
	}
	public void setData_Devolucao(Date data_devolucao) {
		this.data_devolucao = data_devolucao;
	}

}