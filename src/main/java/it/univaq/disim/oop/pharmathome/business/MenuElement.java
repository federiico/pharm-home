package it.univaq.disim.oop.pharmathome.business;

public class MenuElement {

	private String nome;
	private String view;
	
	public MenuElement(String nome, String view) {
		super();
		this.nome = nome;
		this.view = view;
	}
	
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}
}
