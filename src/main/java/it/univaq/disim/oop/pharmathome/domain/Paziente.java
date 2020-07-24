package it.univaq.disim.oop.pharmathome.domain;

import java.util.List;

public class Paziente extends Utente{

	private List<Prescrizione> prescrizioni;

	public List<Prescrizione> getPrescrizioni() {
		return prescrizioni;
	}

	public void setPrescrizioni(List<Prescrizione> prescrizioni) {
		this.prescrizioni = prescrizioni;
	}
	
}
