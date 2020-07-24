package it.univaq.disim.oop.pharmathome.business.impl.ram;

import java.util.ArrayList;
import java.util.List;

import it.univaq.disim.oop.pharmathome.business.exceptions.BusinessException;
import it.univaq.disim.oop.pharmathome.business.exceptions.FarmacoNotFoundException;
import it.univaq.disim.oop.pharmathome.business.services.FarmacoServices;
import it.univaq.disim.oop.pharmathome.domain.Farmaco;

public class FarmacoServicesRAM implements FarmacoServices{
	
	private static List<Farmaco> inventario = new ArrayList<Farmaco>();
	
	static {
		
		Farmaco f1 = new Farmaco();
		f1.setId(1);
		f1.setCodiceMinisteriale("36578");
		f1.setNome("Oki");
		f1.setCasaFarmaceutica("Bayern");
		f1.setPrezzo(4.99);
		f1.setDisponibilita(15);
		f1.setQuantitaMinima(5);
		
		Farmaco f2 = new Farmaco();
		f2.setId(2);
		f2.setCodiceMinisteriale("42974");
		f2.setNome("Tachipirina");
		f2.setCasaFarmaceutica("Melanini");
		f2.setPrezzo(8.50);
		f2.setDisponibilita(10);
		f2.setQuantitaMinima(3);
	
		inventario.add(f1);
		inventario.add(f2);
	}
	
	@Override
	public List<Farmaco> findAllFarmaci() throws BusinessException{
		
		return inventario;
	}

	@Override
	public List<Farmaco> findFarmaciInEsaurimento() {
		
		List<Farmaco> InEsaurimento = new ArrayList<Farmaco>();
		for(Farmaco farmaco : inventario) {
			if(farmaco.getDisponibilita() < farmaco.getQuantitaMinima())
				InEsaurimento.add(farmaco);
		}
		return InEsaurimento;
	}
	
	@Override
	public void aggiungiFarmaco(Farmaco farmaco) throws BusinessException {
		inventario.add(farmaco);
	}
	
	public void cancellaFarmaco(Farmaco farmaco) throws BusinessException{
		inventario.remove(farmaco);
	}
	
	public void modificaFarmaco(Farmaco farmaco, String codice, String nome, String produttore, double prezzo, 
								int disponibilita, int quantitaMin) throws BusinessException{

		farmaco.setCodiceMinisteriale(codice);
		farmaco.setNome(nome);
		farmaco.setCasaFarmaceutica(produttore);
		farmaco.setPrezzo(prezzo);
		farmaco.setDisponibilita(disponibilita);
		farmaco.setQuantitaMinima(quantitaMin);

	}

	@Override
	public Farmaco cercaFarmaco(String nome) throws BusinessException {
		
		Farmaco result = null;
		
		if(inventario.size() == 0) { //creo qualche farmaco da cercare nel caso l'inventario non sia stato creato da un amministratore
			
			Farmaco f1 = new Farmaco();
			f1.setId(1);
			f1.setCodiceMinisteriale("83657");
			f1.setNome("Oki");
			f1.setCasaFarmaceutica("Bayern");
			f1.setPrezzo(4.99);
			f1.setDisponibilita(15);
			f1.setQuantitaMinima(5);
			
			Farmaco f2 = new Farmaco();
			f2.setId(2);
			f2.setCodiceMinisteriale("42974");
			f2.setNome("Tachipirina");
			f2.setCasaFarmaceutica("Melanini");
			f2.setPrezzo(8.50);
			f2.setDisponibilita(10);
			f2.setQuantitaMinima(3);
		
			inventario.add(f1);
			inventario.add(f2); 
		}
		
		for(Farmaco farmaco : inventario) {
			if(farmaco.getNome().equalsIgnoreCase(nome))
				result = farmaco;
			
		}
		if(result == null) 
			throw new FarmacoNotFoundException();
		return result;
	}

	@Override
	public Farmaco findFarmacoById(int id) throws BusinessException {
		
		for(Farmaco farmaco : inventario)
			if(farmaco.getId() == id)
				return farmaco;
		throw new FarmacoNotFoundException();
	}

	@Override
	public int findIdFarmacoByNome(String nome) throws BusinessException {
		
		for(Farmaco farmaco : inventario)
			if(farmaco.getNome().equalsIgnoreCase(nome))
				return farmaco.getId();
		throw new FarmacoNotFoundException();
	}
}
