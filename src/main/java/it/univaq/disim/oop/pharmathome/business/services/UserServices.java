package it.univaq.disim.oop.pharmathome.business.services;

import it.univaq.disim.oop.pharmathome.business.exceptions.BusinessException;
import it.univaq.disim.oop.pharmathome.business.exceptions.ConnectionException;
import it.univaq.disim.oop.pharmathome.business.exceptions.UserNotFoundException;
import it.univaq.disim.oop.pharmathome.domain.Medico;
import it.univaq.disim.oop.pharmathome.domain.Paziente;
import it.univaq.disim.oop.pharmathome.domain.Utente;

public interface UserServices {

	Utente authenticate(String username, String password) throws ConnectionException, UserNotFoundException, BusinessException;
	
	Utente register(String nome, String cognome, String codiceFiscale, String email, String password, String tipo) throws BusinessException;
	
	Medico findMedicoById(int id) throws UserNotFoundException, BusinessException;
	
	Paziente findPazienteById(int id) throws UserNotFoundException, BusinessException;
		
	int findIdPazienteByNominativo(String nome, String cognome) throws UserNotFoundException, BusinessException;
}
