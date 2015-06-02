//package br.usp.icmc.biblioteca;
import java.util.ArrayList;
import java.util.Date;

public class Livro {

	private String titulo;
	private String autor;
	private int codigo;
	private int qunatidade;
	private int tipo; //texto -> 1	geral -> 2

	public Livro() {

	}

	public Livro(String csv) {
		String[] values = csv.split(",");
		setTitulo(values[0]);
		setAutor(values[1]);
		setCodigo(Integer.parseInt(values[2]));
		setQuantidade(Integer.parseInt(values[3]));
		setTipo(Integer.parseInt(values[4]));
	}


	public String toString() {
		return getTitulo() + "\t\t" + getAutor() + "\t\t" + getCodigo() + "\t\t" + getQuantidade() + "\t\t" + getTipoStr();
	}

	
	public String getTipoStr() {
		String str = new String();
		if (this.tipo == 1) str = "Livro texto";
		if (this.tipo == 2) str = "Livro geral";
		return str;
	}


	public String getTitulo() {
		return this.titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public String getAutor() {
		return this.autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}


	public int getCodigo() {
		return this.codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}


	public int getQuantidade() {
		return this.qunatidade;
	}
	public void setQuantidade(int qunatidade) {
		this.qunatidade = qunatidade;
	}


	public int getTipo() {
		return this.tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
}