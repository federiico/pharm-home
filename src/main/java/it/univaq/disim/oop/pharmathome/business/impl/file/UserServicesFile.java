package it.univaq.disim.oop.pharmathome.business.impl.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import it.univaq.disim.oop.pharmathome.business.exceptions.BusinessException;
import it.univaq.disim.oop.pharmathome.business.exceptions.UserNotFoundException;
import it.univaq.disim.oop.pharmathome.business.services.UserServices;
import it.univaq.disim.oop.pharmathome.domain.Amministratore;
import it.univaq.disim.oop.pharmathome.domain.Farmacista;
import it.univaq.disim.oop.pharmathome.domain.Medico;
import it.univaq.disim.oop.pharmathome.domain.Paziente;
import it.univaq.disim.oop.pharmathome.domain.Utente;

public class UserServicesFile implements UserServices{
	
	String fileName;

	public UserServicesFile(String fileName) {
		super();
		this.fileName = fileName;
	}

	@Override
	public Utente authenticate(String username, String password) throws UserNotFoundException, BusinessException {
		
		try {
			FileData fileData = Utility.readAllRows(fileName);
			
			for (String[] riga : fileData.getRighe()) {
				
				if (riga[4].equals(username) && riga[5].equals(password)) {
					
					Utente utente = null;
					
					switch (riga[6]) {
					case "Amministratore":
						utente = new Amministratore();
						break;
					case "Farmacista":
						utente = new Farmacista();
						break;
					case "Medico":
						utente = new Medico();
						break;
					case "Paziente":
						utente = new Paziente();
						break;
					default:
						break;
					}
					if (utente != null) {
						utente.setId(Integer.parseInt(riga[0]));
						utente.setNome(riga[1]);
						utente.setCognome(riga[2]);
						utente.setEmail(riga[3]);
						utente.setCodiceFiscale(username);
						utente.setPassword(password);
					
					} else {
						throw new BusinessException("errore nella lettura del file");
					}
					return utente;
				}
			}
			throw new UserNotFoundException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	@Override
	public Utente register(String nome, String cognome, String codiceFiscale, String email, String password,
			String tipo) throws BusinessException{
		
		try {
			
			Utente utente = null;
			
			FileData fileData = Utility.readAllRows(fileName);
			
			try (PrintWriter writer = new PrintWriter(new File(fileName))) {
				
				long contatore = fileData.getContatore();
				writer.println((contatore + 1));
				for (String[] righe : fileData.getRighe()) {
					writer.println(String.join(Utility.SEPARATORE_COLONNA, righe));
				}
				
				StringBuilder row = new StringBuilder();
				row.append(contatore);
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(nome);
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(cognome);
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(email);
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(codiceFiscale);
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(password);
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(tipo);
				row.append(Utility.SEPARATORE_COLONNA);
				
				writer.println(row.toString());
				
				if(tipo.equals("Paziente")) {
					utente = new Paziente();	
				}
				if(tipo.equals("Medico")) {
					utente = new Medico();	
				}
				if(tipo.equals("Farmacista")) {
					utente = new Farmacista();	
				}
				
				if (utente != null) {
					utente.setId((int) contatore);
					utente.setNome(nome);
					utente.setCognome(cognome);
					utente.setEmail(email);
					utente.setCodiceFiscale(codiceFiscale);
					utente.setPassword(password);
					
					return utente;
				
				} else {
					throw new BusinessException();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	@Override
	public Medico findMedicoById(int id) throws UserNotFoundException, BusinessException {
		
		Medico result;
		
		try {
			
			FileData fileData = Utility.readAllRows(fileName);
			
			for (String[] riga : fileData.getRighe()) {
				
				if(Integer.parseInt(riga[0]) == id) {
					
					result = new Medico();
					
					result.setId(Integer.parseInt(riga[0]));
					result.setNome(riga[1]);
					result.setCognome(riga[2]);
					result.setEmail(riga[3]);
					result.setCodiceFiscale(riga[4]);
					result.setPassword(riga[5]);
					
					return result;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
		throw new UserNotFoundException();
	}

	@Override
	public Paziente findPazienteById(int id) throws UserNotFoundException, BusinessException {
		
		Paziente result;
		
		try {
			
			FileData fileData = Utility.readAllRows(fileName);
			
			for (String[] riga : fileData.getRighe()) {
				
				if(Integer.parseInt(riga[0]) == id) {
					
					result = new Paziente();
					
					result.setId(Integer.parseInt(riga[0]));
					result.setNome(riga[1]);
					result.setCognome(riga[2]);
					result.setEmail(riga[3]);
					result.setCodiceFiscale(riga[4]);
					result.setPassword(riga[5]);
					
					return result;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
		throw new UserNotFoundException();
	}

	@Override
	public int findIdPazienteByNominativo(String nome, String cognome) throws UserNotFoundException, BusinessException {
	
		try {
			FileData fileData = Utility.readAllRows(fileName);
			
			for (String[] riga : fileData.getRighe()) {
				
				if (riga[1].equals(nome) && riga[2].equals(cognome)) {
					
					return Integer.parseInt(riga[0]);
				}
			}
			throw new UserNotFoundException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

}
