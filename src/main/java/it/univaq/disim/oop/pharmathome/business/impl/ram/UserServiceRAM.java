package it.univaq.disim.oop.pharmathome.business.impl.ram;

import java.util.HashMap;
import java.util.Map;
import it.univaq.disim.oop.pharmathome.business.exceptions.BusinessException;
import it.univaq.disim.oop.pharmathome.business.exceptions.UserNotFoundException;
import it.univaq.disim.oop.pharmathome.business.services.UserServices;
import it.univaq.disim.oop.pharmathome.domain.Amministratore;
import it.univaq.disim.oop.pharmathome.domain.Farmacista;
import it.univaq.disim.oop.pharmathome.domain.Medico;
import it.univaq.disim.oop.pharmathome.domain.Paziente;
import it.univaq.disim.oop.pharmathome.domain.Utente;

public class UserServiceRAM implements UserServices{

	static int id = 5;
	
	private Map<Integer, Utente> iscritti = new HashMap<>();

	@Override
	public Utente authenticate(String username, String password) throws BusinessException {
		
		Utente utente;
		
		if("paziente".equalsIgnoreCase(username)) {
			utente = new Paziente();
			utente.setId(1);
			utente.setNome("Federico");
			utente.setCognome("Cantoro");
			utente.setEmail("federico@gmail.com");
			utente.setCodiceFiscale("CNTFRC99L21E058I");
			utente.setPassword(password);
			iscritti.put(utente.getId(),utente);
			return utente;
		}
		if("medico".equalsIgnoreCase(username)) {
			utente = new Medico();
			utente.setId(2);
			utente.setNome("Davide");
			utente.setCognome("Palombaro");
			utente.setEmail("davide@gmail.com");
			utente.setCodiceFiscale("DVDPLB99L21E058I");
			utente.setPassword(password);
			iscritti.put(utente.getId(),utente);
			return utente;
		}
		if("farmacista".equalsIgnoreCase(username)) {
			utente = new Farmacista();
			utente.setId(3);
			utente.setNome("Francesco");
			utente.setCognome("Santamaria");
			utente.setEmail("francesco@gmail.com");
			utente.setCodiceFiscale("FRCSNT99L21E058I");
			utente.setPassword(password);
			iscritti.put(utente.getId(),utente);
			return utente;
		}
		if("admin".equalsIgnoreCase(username)) {
			utente  = new Amministratore();
			utente.setId(4);
			utente.setNome("Giordano");
			utente.setCognome("Tinella");
			utente.setEmail("giordano@gmail.com");
			utente.setCodiceFiscale("GRDTNL99L21E058I");
			utente.setPassword(password);
			iscritti.put(utente.getId(),utente);
			return utente;
		}
		throw new UserNotFoundException();
	}

	@Override
	public Utente register(String nome, String cognome, String codiceFiscale, String email, String password, String tipo) throws BusinessException{
		
		Utente utente;
		
		if(tipo.equals("Paziente")){
			utente = new Paziente();
			utente.setId(id++);
			utente.setNome(nome);
			utente.setCognome(cognome);
			utente.setEmail(email);
			utente.setCodiceFiscale(codiceFiscale);
			utente.setPassword(password);
			iscritti.put(utente.getId(),utente);
			return utente;
		}
		if(tipo.equals("Medico")){
			utente = new Medico();
			utente.setId(id++);
			utente.setNome(nome);
			utente.setCognome(cognome);
			utente.setEmail(email);
			utente.setCodiceFiscale(codiceFiscale);
			utente.setPassword(password);
			iscritti.put(utente.getId(),utente);
			return utente;
		}
		if(tipo.equals("Farmacista")){
			utente = new Farmacista();
			utente.setId(id++);
			utente.setNome(nome);
			utente.setCognome(cognome);
			utente.setEmail(email);
			utente.setCodiceFiscale(codiceFiscale);
			utente.setPassword(password);
			iscritti.put(utente.getId(),utente);
			return utente;
		}
		throw new BusinessException();
	}

	@Override
	public Medico findMedicoById(int id) throws UserNotFoundException, BusinessException {
		
		Utente result = iscritti.get(id);
		if(result != null)
			return (Medico)result;
		throw new UserNotFoundException();
	}

	@Override
	public Paziente findPazienteById(int id) throws UserNotFoundException, BusinessException {
		
		Utente result = iscritti.get(id);
		if(result != null)
			return (Paziente)result;
		throw new UserNotFoundException();
	}

	@Override
	public int findIdPazienteByNominativo(String nome, String cognome) throws UserNotFoundException, BusinessException {
		
		for(Utente utente : iscritti.values()) {
			if(utente.getNome().equalsIgnoreCase(nome) && utente.getCognome().equals(cognome))
				return utente.getId();
		}
		throw new UserNotFoundException();
	}
	
}
