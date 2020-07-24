package it.univaq.disim.oop.pharmathome.business.services;



import java.util.List;

import it.univaq.disim.oop.pharmathome.business.exceptions.BusinessException;
import it.univaq.disim.oop.pharmathome.business.exceptions.CancellaFarmacoException;
import it.univaq.disim.oop.pharmathome.business.exceptions.FarmacoNotFoundException;
import it.univaq.disim.oop.pharmathome.domain.Farmaco;

public interface FarmacoServices {

	List<Farmaco> findAllFarmaci() throws BusinessException;
	
	List<Farmaco> findFarmaciInEsaurimento() throws BusinessException;

	void aggiungiFarmaco(Farmaco f) throws BusinessException;
	
	void cancellaFarmaco(Farmaco f) throws CancellaFarmacoException, BusinessException;
	
	void modificaFarmaco(Farmaco f, String codice, String nome, String produttore, double prezzo,
						 int disponibilita, int quantitaMin) throws BusinessException;
	
	Farmaco cercaFarmaco(String nome) throws FarmacoNotFoundException, BusinessException;
	
	Farmaco findFarmacoById(int id) throws FarmacoNotFoundException, BusinessException;
	
	int findIdFarmacoByNome(String nome) throws FarmacoNotFoundException, BusinessException;
	
}
