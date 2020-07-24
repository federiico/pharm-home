package it.univaq.disim.oop.pharmathome.domain;

public class Farmaco {

	private int id;
	
	private String codiceMinisteriale;
	
	private String nome;
	
	private String casaFarmaceutica;
	
	private double prezzo;
	
	private int disponibilita;
	
	private int quantitaMinima;

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getCodiceMinisteriale() {
		return codiceMinisteriale;
	}

	public void setCodiceMinisteriale(String codiceMinisteriale) {
		this.codiceMinisteriale = codiceMinisteriale;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCasaFarmaceutica() {
		return casaFarmaceutica;
	}

	public void setCasaFarmaceutica(String casaFarmaceutica) {
		this.casaFarmaceutica = casaFarmaceutica;
	}

	public Double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	public int getDisponibilita() {
		return disponibilita;
	}

	public void setDisponibilita(int disponibilita) {
		this.disponibilita = disponibilita;
	}

	public int getQuantitaMinima() {
		return quantitaMinima;
	}

	public void setQuantitaMinima(int quantitaMinima) {
		this.quantitaMinima = quantitaMinima;
	}
	
}
