package it.univaq.disim.oop.pharmathome.business.impl.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import it.univaq.disim.oop.pharmathome.business.exceptions.BusinessException;
import it.univaq.disim.oop.pharmathome.business.exceptions.CancellaFarmacoException;
import it.univaq.disim.oop.pharmathome.business.exceptions.FarmacoNotFoundException;
import it.univaq.disim.oop.pharmathome.business.services.FarmacoServices;
import it.univaq.disim.oop.pharmathome.domain.Farmaco;

public class FarmacoServicesFile implements FarmacoServices{
	
	String farmacoFileName;
	String prescrizoneFileName; 
	
	public FarmacoServicesFile(String farmacoFileName, String prescrizioneFileName) {
		this.farmacoFileName = farmacoFileName;
		this.prescrizoneFileName = prescrizioneFileName;
	}

	@Override
	public List<Farmaco> findAllFarmaci() throws BusinessException {
		
		List<Farmaco> inventario = new ArrayList<Farmaco>();
		
		try {
			
			FileData fileData = Utility.readAllRows(farmacoFileName);
			
			for (String[] riga : fileData.getRighe()) {
				
				Farmaco farmaco = new Farmaco();
				
				farmaco.setId(Integer.parseInt(riga[0]));
				farmaco.setCodiceMinisteriale(riga[1]);
				farmaco.setNome(riga[2]);
				farmaco.setCasaFarmaceutica(riga[3]);
				farmaco.setPrezzo(Double.parseDouble(riga[4]));
				farmaco.setDisponibilita(Integer.parseInt(riga[5]));
				farmaco.setQuantitaMinima(Integer.parseInt(riga[6]));
				
				inventario.add(farmaco);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}

		return inventario;
	}

	@Override
	public List<Farmaco> findFarmaciInEsaurimento() throws BusinessException {
		
		List<Farmaco> inventario = new ArrayList<Farmaco>();
		
		try {
			
			FileData fileData = Utility.readAllRows(farmacoFileName);
			
			int disponibilita, quantitaMin;
			
			for (String[] riga : fileData.getRighe()) {
				
				disponibilita = Integer.parseInt(riga[5]);
				quantitaMin = Integer.parseInt(riga[6]);
				
				if(disponibilita < quantitaMin) {
				
					Farmaco farmaco = new Farmaco();
					
					farmaco.setCodiceMinisteriale(riga[1]);
					farmaco.setNome(riga[2]);
					farmaco.setCasaFarmaceutica(riga[3]);
					farmaco.setPrezzo((Double.parseDouble(riga[4])));
					farmaco.setDisponibilita(Integer.parseInt(riga[5]));
					farmaco.setQuantitaMinima(Integer.parseInt(riga[6]));
					
					inventario.add(farmaco);
				}	
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}

		return inventario;
	}

	@Override
	public void aggiungiFarmaco(Farmaco f) throws BusinessException {
			
			try {
			
				FileData fileData = Utility.readAllRows(farmacoFileName);
				
				try (PrintWriter writer = new PrintWriter(new File(farmacoFileName))) {
					
					long contatore = fileData.getContatore();
					writer.println((contatore + 1));
					
					for (String[] riga : fileData.getRighe()) {
						writer.println(String.join(Utility.SEPARATORE_COLONNA, riga));
					}
					
					StringBuilder row = new StringBuilder();
					
					row.append(contatore);
					row.append(Utility.SEPARATORE_COLONNA);
					row.append(f.getCodiceMinisteriale());
					row.append(Utility.SEPARATORE_COLONNA);
					row.append(f.getNome());
					row.append(Utility.SEPARATORE_COLONNA);
					row.append(f.getCasaFarmaceutica());
					row.append(Utility.SEPARATORE_COLONNA);
					row.append(f.getPrezzo().toString());
					row.append(Utility.SEPARATORE_COLONNA);
					row.append(f.getDisponibilita());
					row.append(Utility.SEPARATORE_COLONNA);
					row.append(f.getQuantitaMinima());
					row.append(Utility.SEPARATORE_COLONNA);
					
					writer.println(row.toString());
				
				}
			}catch (IOException e) {
				e.printStackTrace();
				throw new BusinessException(e);
			}
	}
	
			
	@Override
	public void cancellaFarmaco(Farmaco f) throws BusinessException {
		
		try {
			
			FileData fileData1 = Utility.readAllRows(prescrizoneFileName);
			boolean CancellaIsOk = true;
			int i;
				
			for (String[] riga : fileData1.getRighe()) {
				i = 4;
				while(!riga[i].equals("Evasa") && !riga[i].equals("Non Evasa")) {
					if(f.getId() == Integer.parseInt(riga[i]))
						CancellaIsOk = false;
					i++;
				}
			}
			
			if(CancellaIsOk) {
			
				FileData fileData = Utility.readAllRows(farmacoFileName);
				
				try (PrintWriter writer = new PrintWriter(new File(farmacoFileName))) {
					
					
					long contatore = fileData.getContatore();
					writer.println((contatore - 1));
					
					for (String[] riga : fileData.getRighe()) {
						
						if(f.getId() != Integer.parseInt(riga[0])){
							writer.println(String.join(Utility.SEPARATORE_COLONNA, riga));
						}
					}
				}
			}
			else throw new CancellaFarmacoException();
		}catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
		
	}

	@Override
	public void modificaFarmaco(Farmaco f, String codice, String nome, String produttore, double prezzo,
			int disponibilita, int quantitaMin) throws BusinessException {
	
		try {
			
			FileData fileData = Utility.readAllRows(farmacoFileName);
			
			try (PrintWriter writer = new PrintWriter(new File(farmacoFileName))) {
				
				writer.println((fileData.getContatore()));
				
				for (String[] riga : fileData.getRighe()) {
					
					if(Integer.toString(f.getId()).equals(riga[0])) {
						
						StringBuilder row = new StringBuilder();
						
						row.append(f.getId());
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(codice);
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(nome);
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(produttore);
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(prezzo);
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(disponibilita);
						row.append(Utility.SEPARATORE_COLONNA);
						row.append(quantitaMin);
						row.append(Utility.SEPARATORE_COLONNA);
						
						writer.println(row.toString());
					}
					else {
						writer.println(String.join(Utility.SEPARATORE_COLONNA, riga));
					}
				}
			}
		}catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}	
	}
	
	@Override
	public Farmaco cercaFarmaco(String nome) throws FarmacoNotFoundException, BusinessException {
	
		Farmaco result = null;
		
		try {
			
			FileData fileData = Utility.readAllRows(farmacoFileName);
			
			for (String[] riga : fileData.getRighe()) {
				
				if(riga[2].equalsIgnoreCase(nome)) {
					
					result = new Farmaco();
					
					result.setCodiceMinisteriale(riga[1]);
					result.setNome(riga[2]);
					result.setCasaFarmaceutica(riga[3]);
					result.setPrezzo((Double.parseDouble(riga[4])));
					result.setDisponibilita(Integer.parseInt(riga[5]));
					result.setQuantitaMinima(Integer.parseInt(riga[6]));
					
					break;
				}
			}
			if(result == null)
				throw new FarmacoNotFoundException();
			return result;
	
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}

	@Override
	public Farmaco findFarmacoById(int id) throws FarmacoNotFoundException, BusinessException {
		
		Farmaco result;
		
		try {
			
			FileData fileData = Utility.readAllRows(farmacoFileName);
			
			for (String[] riga : fileData.getRighe()) {
				
				if(Integer.parseInt(riga[0]) == id) {
					
					result = new Farmaco();
					
					result.setId(Integer.parseInt(riga[0]));
					result.setCodiceMinisteriale(riga[1]);
					result.setNome(riga[2]);
					result.setCasaFarmaceutica(riga[3]);
					result.setPrezzo((Double.parseDouble(riga[4])));
					result.setDisponibilita(Integer.parseInt(riga[5]));
					result.setQuantitaMinima(Integer.parseInt(riga[6]));
					
					return result;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
		throw new FarmacoNotFoundException();
	}

	@Override
	public int findIdFarmacoByNome(String nome) throws FarmacoNotFoundException, BusinessException {
		
		try {
			FileData fileData = Utility.readAllRows(farmacoFileName);
			
			for (String[] riga : fileData.getRighe()) {
				
				if (riga[2].equalsIgnoreCase(nome)) {
					
					return Integer.parseInt(riga[0]);
				}
			}
			throw new FarmacoNotFoundException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}
	}
}
