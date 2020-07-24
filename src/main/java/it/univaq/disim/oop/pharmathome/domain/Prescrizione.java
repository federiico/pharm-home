package it.univaq.disim.oop.pharmathome.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class Prescrizione {

	private int id;
	
	private int numero;
	
	private Medico medico;
	
	private Paziente paziente;
	
	private Collection<Farmaco> farmaco = new ArrayList<Farmaco>();
	
	private LocalDate data;
	
	private String stato;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public Paziente getPaziente() {
		return paziente;
	}

	public void setPaziente(Paziente paziente) {
		this.paziente = paziente;
	}

	public Collection<Farmaco> getFarmaco() {
		return farmaco;
	}

	public void setFarmaco(Collection<Farmaco> farmaco) {
		this.farmaco = farmaco;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}
	
	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}
	
}
