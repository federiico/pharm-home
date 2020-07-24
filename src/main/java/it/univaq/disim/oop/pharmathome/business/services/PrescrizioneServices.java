package it.univaq.disim.oop.pharmathome.business.services;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import it.univaq.disim.oop.pharmathome.business.exceptions.BusinessException;
import it.univaq.disim.oop.pharmathome.business.exceptions.FarmacoNotFoundException;
import it.univaq.disim.oop.pharmathome.business.exceptions.UserNotFoundException;
import it.univaq.disim.oop.pharmathome.domain.Medico;
import it.univaq.disim.oop.pharmathome.domain.Paziente;
import it.univaq.disim.oop.pharmathome.domain.Prescrizione;

public interface PrescrizioneServices {

	List<Prescrizione> findPrescrizioniDaEvadere() throws BusinessException;

	void evadiPrescrizione(Prescrizione prescrizione) throws BusinessException;
	
	List<Prescrizione> findPrescrizioniFromPaziente(Paziente paziente) throws BusinessException;
	
	List<Prescrizione> cercaPrescrizioni(Paziente paziente, String medico, String farmaco, LocalDate data) throws BusinessException;
	
	List<Prescrizione> findPrescrizioniFromMedico(Medico medico) throws BusinessException;
	
	void modificaPrescrizione(Prescrizione prescrizione, String nome, String cognome, Collection<String> farmaci) throws UserNotFoundException, FarmacoNotFoundException, BusinessException;
	
	void cancellaPrescrizione(Prescrizione prescrizione) throws BusinessException;
	
	void creaPrescrizione(int idMedico, String nome, String cognome, List<String> farmaci) throws UserNotFoundException, FarmacoNotFoundException, BusinessException;
	
}
