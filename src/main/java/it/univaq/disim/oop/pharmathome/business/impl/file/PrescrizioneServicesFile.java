package it.univaq.disim.oop.pharmathome.business.impl.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import it.univaq.disim.oop.pharmathome.business.exceptions.BusinessException;
import it.univaq.disim.oop.pharmathome.business.exceptions.FarmacoNotFoundException;
import it.univaq.disim.oop.pharmathome.business.exceptions.UserNotFoundException;
import it.univaq.disim.oop.pharmathome.business.services.FarmacoServices;
import it.univaq.disim.oop.pharmathome.business.services.PrescrizioneServices;
import it.univaq.disim.oop.pharmathome.business.services.UserServices;
import it.univaq.disim.oop.pharmathome.domain.Farmaco;
import it.univaq.disim.oop.pharmathome.domain.Medico;
import it.univaq.disim.oop.pharmathome.domain.Paziente;
import it.univaq.disim.oop.pharmathome.domain.Prescrizione;

public class PrescrizioneServicesFile implements PrescrizioneServices{
	
	private String fileName;

	private FarmacoServices farmacoServices;
	private UserServices userServices;
	
	
	public PrescrizioneServicesFile(String fileName, FarmacoServices farmServ, UserServices userServ) {
		
		farmacoServices = farmServ;
		userServices = userServ;
		this.fileName = fileName;
		
	}

	@Override
	public List<Prescrizione> findPrescrizioniDaEvadere() throws BusinessException {
		
		List<Prescrizione> prescrizioniDaEvadere = new ArrayList<Prescrizione>();
		
		List<Farmaco> farmaciPrescrizione;
		
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate data;
		
		Prescrizione prescrizione;
		
		int i;
		
		try {
			
			FileData fileData = Utility.readAllRows(fileName);
			
			for (String[] riga : fileData.getRighe()) {
				
					i = 4;
					
					prescrizione = new Prescrizione();
					
					prescrizione.setNumero(Integer.parseInt(riga[0]));
					data = LocalDate.parse(riga[1], dateTimeFormatter);
					prescrizione.setData(data);
					prescrizione.setMedico(userServices.findMedicoById(Integer.parseInt(riga[2])));
					prescrizione.setPaziente(userServices.findPazienteById(Integer.parseInt(riga[3])));
					
					farmaciPrescrizione = new ArrayList<Farmaco>();
					while(!riga[i].equals("Evasa") && !riga[i].equals("Non Evasa")) {
						farmaciPrescrizione.add(farmacoServices.findFarmacoById(Integer.parseInt(riga[i])));
						i++;
					}
					prescrizione.setFarmaco(farmaciPrescrizione);
					
					if(riga[i].equals("Non Evasa"))
						prescrizioniDaEvadere.add(prescrizione);		
			}
			
			return prescrizioniDaEvadere;
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	@Override
	public void evadiPrescrizione(Prescrizione prescrizione) throws BusinessException {
		
		try {
			
			FileData fileData = Utility.readAllRows(fileName);
			
			try (PrintWriter writer = new PrintWriter(new File(fileName))) {
				
				long contatore = fileData.getContatore();
				writer.println((contatore));
				
				for (String[] riga : fileData.getRighe()) {
					
					if(Integer.parseInt(riga[0]) == prescrizione.getNumero()) {
						
						StringBuilder row = new StringBuilder();
						
						row.append(prescrizione.getNumero());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(prescrizione.getData().toString());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(prescrizione.getMedico().getId());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(prescrizione.getPaziente().getId());
						row.append(Utility.SEPARATORE_COLONNA);
						
						for(Farmaco f : prescrizione.getFarmaco()) {
							row.append(f.getId());
							row.append(Utility.SEPARATORE_COLONNA);
								
						}
						row.append("Evasa");
						writer.println(row.toString());
					}
					else
						writer.println(String.join(Utility.SEPARATORE_COLONNA, riga));
					}
			}
		}catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	@Override
	public List<Prescrizione> findPrescrizioniFromPaziente(Paziente paziente) throws BusinessException {
			
		List<Prescrizione> prescrizioni = new ArrayList<Prescrizione>();
		List<Farmaco> farmaciPrescrizione;
		
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate data;
		
		Prescrizione prescrizione;
		
		int i;
		
		try {
			
			FileData fileData = Utility.readAllRows(fileName);
			
			for (String[] riga : fileData.getRighe()) {
				
				if(Integer.parseInt(riga[3]) == paziente.getId()) {
				
					i = 4;
					
					prescrizione = new Prescrizione();
					
					prescrizione.setNumero(Integer.parseInt(riga[0]));
					data = LocalDate.parse(riga[1], dateTimeFormatter);
					prescrizione.setData(data);
					prescrizione.setMedico(userServices.findMedicoById(Integer.parseInt(riga[2])));
					prescrizione.setPaziente(userServices.findPazienteById(Integer.parseInt(riga[3])));
					
					farmaciPrescrizione = new ArrayList<Farmaco>();
					while(!riga[i].equals("Evasa") && !riga[i].equals("Non Evasa")) {
						farmaciPrescrizione.add(farmacoServices.findFarmacoById(Integer.parseInt(riga[i])));
						i++;
					}
					prescrizione.setFarmaco(farmaciPrescrizione);
					prescrizione.setStato(riga[i]);
					
					prescrizioni.add(prescrizione);
				}
				else continue;		
			}
			return prescrizioni;
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	@Override
	public List<Prescrizione> cercaPrescrizioni(Paziente paziente, String medico, String farmaco, LocalDate data)
			throws BusinessException {
		
		List<Prescrizione> prescrizioni = new ArrayList<Prescrizione>();
		Prescrizione prescrizione;
		List<Farmaco> farmaciPrescrizione = null;
		
		boolean boolMedico, boolData, boolFarmaco;
		
		Medico med;
		Farmaco farm;
		
		int i;
		
		try {
			
			FileData fileData = Utility.readAllRows(fileName);
			
			for (String[] riga : fileData.getRighe()) {
				
				boolMedico = boolData = boolFarmaco = false;
				if(farmaco.isEmpty()) boolFarmaco = true; 
				if(medico.isEmpty()) boolMedico = true;
				
				if(Integer.parseInt(riga[3]) == paziente.getId()) {
					
					farmaciPrescrizione = new ArrayList<Farmaco>();
					
					i = 4;
					
					med = userServices.findMedicoById(Integer.parseInt(riga[2]));
					
					if(boolMedico == false && (medico.equalsIgnoreCase(med.getNome()) || (medico.equalsIgnoreCase(med.getCognome()))))
						boolMedico = true;
					
					if(data.toString().equals(riga[1]))
						boolData = true;
				
					while(!riga[i].equals("Evasa") && !riga[i].equals("Non Evasa")) {
						farm = farmacoServices.findFarmacoById(Integer.parseInt(riga[i]));
						if(farm.getNome().equalsIgnoreCase(farmaco)) {
							boolFarmaco = true;
						}
						farmaciPrescrizione.add(farm);
						i++;
					}
					
					
					if(boolMedico && boolData && boolFarmaco) {
						prescrizione = new Prescrizione();
						prescrizione.setNumero(Integer.parseInt(riga[0]));
						prescrizione.setData(data);
						prescrizione.setMedico(med);
						prescrizione.setPaziente(paziente);
						prescrizione.setFarmaco(farmaciPrescrizione);
						
						prescrizioni.add(prescrizione);
					}
				}
				else continue;
			}
			return prescrizioni;
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	@Override
	public List<Prescrizione> findPrescrizioniFromMedico(Medico medico) throws BusinessException {
		
		List<Prescrizione> prescrizioni = new ArrayList<Prescrizione>();
		List<Farmaco> farmaciPrescrizione;
		
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate data;
		
		Prescrizione prescrizione;
		
		int i;
		
		try {
			
			FileData fileData = Utility.readAllRows(fileName);
			
			for (String[] riga : fileData.getRighe()) {
				
				if(Integer.parseInt(riga[2]) == medico.getId()) {
				
					i = 4;
					
					prescrizione = new Prescrizione();
					
					prescrizione.setNumero(Integer.parseInt(riga[0]));
					data = LocalDate.parse(riga[1], dateTimeFormatter);
					prescrizione.setData(data);
					prescrizione.setMedico(userServices.findMedicoById(Integer.parseInt(riga[2])));
					prescrizione.setPaziente(userServices.findPazienteById(Integer.parseInt(riga[3])));
					
					farmaciPrescrizione = new ArrayList<Farmaco>();
					while(!riga[i].equals("Evasa") && !riga[i].equals("Non Evasa")) {
						farmaciPrescrizione.add(farmacoServices.findFarmacoById(Integer.parseInt(riga[i])));
						i++;
					}
					prescrizione.setFarmaco(farmaciPrescrizione);
					prescrizione.setStato(riga[i]);
					
					prescrizioni.add(prescrizione);
				}
				else continue;
			}
			return prescrizioni;
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	@Override
	public void modificaPrescrizione(Prescrizione prescrizione, String nome, String cognome,
			Collection<String> farmaci) throws BusinessException {
	
		FileData fileData = null;
		
		try {
			
			fileData = Utility.readAllRows(fileName);
			
			try (PrintWriter writer = new PrintWriter(new File(fileName))) {
				
				writer.println((fileData.getContatore()));
				
				for (String[] riga : fileData.getRighe()) {
					
					if(prescrizione.getNumero() == Integer.parseInt(riga[0])) {
						
						StringBuilder row = new StringBuilder();
						
						row.append(prescrizione.getNumero());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(prescrizione.getData().toString());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(prescrizione.getMedico().getId());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(userServices.findIdPazienteByNominativo(nome, cognome));
						row.append(Utility.SEPARATORE_COLONNA);
						
						for(String nomeFarmaco : farmaci) {
							
							row.append(farmacoServices.findIdFarmacoByNome(nomeFarmaco));
							row.append(Utility.SEPARATORE_COLONNA);
						}
						
						row.append(prescrizione.getStato());
						writer.println(row.toString());
					}
					else {
						writer.println(String.join(Utility.SEPARATORE_COLONNA, riga));
					}
				}
			}
		}
		catch(FarmacoNotFoundException | UserNotFoundException e) {
			try (PrintWriter writer = new PrintWriter(new File(fileName))) {
				writer.println((fileData.getContatore()));
				for (String[] riga : fileData.getRighe()) {
					writer.println(String.join(Utility.SEPARATORE_COLONNA, riga));
				}
				throw new FarmacoNotFoundException();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}			
	}

	@Override
	public void cancellaPrescrizione(Prescrizione prescrizione) throws BusinessException {
		
		try {
			
			FileData fileData = Utility.readAllRows(fileName);
			
			try (PrintWriter writer = new PrintWriter(new File(fileName))) {
				
				writer.println((fileData.getContatore()));
				
				for (String[] riga : fileData.getRighe()) {
					
					if(prescrizione.getNumero() == Integer.parseInt(riga[0])) 
						continue;
					else 
						writer.println(String.join(Utility.SEPARATORE_COLONNA, riga));
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}	
	}

	@Override
	public void creaPrescrizione(int idMedico, String nome, String cognome, List<String> farmaci) throws BusinessException {
		
		FileData fileData = null;
		
		try {
			
			fileData = Utility.readAllRows(fileName);
			
			try (PrintWriter writer = new PrintWriter(new File(fileName))) {
				
				long contatore = fileData.getContatore();
				writer.println((contatore + 1));
				
				for (String[] riga : fileData.getRighe()) 
					writer.println(String.join(Utility.SEPARATORE_COLONNA, riga));
				
				StringBuilder row = new StringBuilder();
				
				row.append(contatore);
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(LocalDate.now().toString());
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(idMedico);
				row.append(Utility.SEPARATORE_COLONNA);
				row.append(userServices.findIdPazienteByNominativo(nome, cognome));
				row.append(Utility.SEPARATORE_COLONNA);
				
				for(String nomeFarmaco : farmaci) {
					
					row.append(farmacoServices.findIdFarmacoByNome(nomeFarmaco));
					row.append(Utility.SEPARATORE_COLONNA);
				}
				
				row.append("Non Evasa");
				writer.println(row.toString());
			}
		}
		catch(FarmacoNotFoundException | UserNotFoundException e) {
			try (PrintWriter writer = new PrintWriter(new File(fileName))) {
				writer.println((fileData.getContatore()));
				for (String[] riga : fileData.getRighe()) {
					writer.println(String.join(Utility.SEPARATORE_COLONNA, riga));
				}
				throw new FarmacoNotFoundException();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}
}
