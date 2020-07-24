package it.univaq.disim.oop.pharmathome.business.impl.ram;

import java.time.LocalDate;
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

public class PrescrizioneServicesRAM implements PrescrizioneServices{
	
	private static List<Prescrizione> prescrizioni = new ArrayList<Prescrizione>();
	
	UserServices userServices;
	FarmacoServices farmacoServices;
	
	
	public PrescrizioneServicesRAM(UserServices userServices, FarmacoServices farmacoServices) {
		
		this.userServices = userServices;
		this.farmacoServices = farmacoServices;
		creaPrescrizioniIniziali();
	
	}
	
	@Override
	public List<Prescrizione> findPrescrizioniDaEvadere() throws BusinessException{
		
		ArrayList<Prescrizione> result = new ArrayList<Prescrizione>();
		
		for(Prescrizione prescrizione : prescrizioni)
			if(prescrizione.getStato().equals("Non Evasa"))
				result.add(prescrizione);
		
		
		
		return result;
	}

	@Override
	public void evadiPrescrizione(Prescrizione prescrizione) throws BusinessException {
		
		prescrizione.setStato("Evasa");
	}

	@Override
	public List<Prescrizione> findPrescrizioniFromPaziente(Paziente paziente) throws BusinessException {
		
		List<Prescrizione> result = new ArrayList<Prescrizione>();
			
		for(Prescrizione prescrizione : prescrizioni) {
			if(prescrizione.getPaziente().getId() == paziente.getId())
				result.add(prescrizione);
		}
		return result;
	}
	
	@Override
	public List<Prescrizione> findPrescrizioniFromMedico(Medico medico) throws BusinessException {
		
		List<Prescrizione> result = new ArrayList<Prescrizione>();
		
		for(Prescrizione prescrizione : prescrizioni) {
			if(prescrizione.getMedico().getId() == medico.getId())
				result.add(prescrizione);
		}
		return result;
	}
	
	@Override
	public List<Prescrizione> cercaPrescrizioni(Paziente paziente, String medico, String farmaco, LocalDate data) throws BusinessException {
		
		List<Prescrizione> prescrizioniPaziente = findPrescrizioniFromPaziente(paziente);
		List<Prescrizione> result = new ArrayList<Prescrizione>();

		boolean boolMedico = false, boolData = false, boolFarmaco = false;
		
		if(medico.isEmpty()) boolMedico = true;
		if(farmaco.isEmpty()) boolFarmaco = true; 
		
		for(Prescrizione prescr : prescrizioniPaziente) {
		
			if(boolMedico == false && (medico.equalsIgnoreCase(prescr.getMedico().getNome()) || (medico.equalsIgnoreCase(prescr.getMedico().getCognome()))) )
				boolMedico = true;
			
			if(data.equals(prescr.getData()))
				boolData = true;
		
			for(Farmaco farm : prescr.getFarmaco()) {
				if(farm.getNome().equalsIgnoreCase(farmaco))
					boolFarmaco = true;
			}
			
			if(boolMedico && boolData && boolFarmaco)
				result.add(prescr);
		}
		return result;
	}
	
	@Override
	public void modificaPrescrizione(Prescrizione prescrizione, String nome, String cognome, Collection<String> farmaci) throws BusinessException {
		
		try {
			int idNuovoPaziente = userServices.findIdPazienteByNominativo(nome, cognome);
			Paziente nuovoPaziente = userServices.findPazienteById(idNuovoPaziente);
			
			List<Farmaco> nuoviFarmaci = new ArrayList<Farmaco>();
			Farmaco farmaco;
			int idFarmaco;
			for(String NomeFarmaco : farmaci) {
				idFarmaco = farmacoServices.findIdFarmacoByNome(NomeFarmaco);
				farmaco = farmacoServices.findFarmacoById(idFarmaco);
				nuoviFarmaci.add(farmaco);
			}
			
			prescrizione.setPaziente(nuovoPaziente);
			prescrizione.setFarmaco(nuoviFarmaci);
			
		}
		catch(UserNotFoundException e) {
			throw new UserNotFoundException();
		}
		catch(FarmacoNotFoundException e2) {
			throw new FarmacoNotFoundException();
		}

	}

	@Override
	public void cancellaPrescrizione(Prescrizione prescrizione) throws BusinessException {
		
		prescrizioni.remove(prescrizione);
	}

	@Override
	public void creaPrescrizione(int idMedico, String nome, String cognome, List<String> farmaci) throws BusinessException {

		
		try {
			Prescrizione result = new Prescrizione();
			
			int idPaziente = userServices.findIdPazienteByNominativo(nome, cognome);
			Paziente paziente = userServices.findPazienteById(idPaziente);
			
			List<Farmaco> listaFarmaci = new ArrayList<Farmaco>();
			Farmaco farmaco;
			int idFarmaco;
			for(String NomeFarmaco : farmaci) {
				idFarmaco = farmacoServices.findIdFarmacoByNome(NomeFarmaco);
				farmaco = farmacoServices.findFarmacoById(idFarmaco);
				listaFarmaci.add(farmaco);
			}
			
			result.setNumero(prescrizioni.size()+1);
			result.setMedico(userServices.findMedicoById(idMedico));
			result.setPaziente(paziente);
			result.setFarmaco(listaFarmaci);
			result.setData(LocalDate.now());
			result.setStato("Non Evasa");
			
			prescrizioni.add(result);
			
		}
		catch(UserNotFoundException e) {
			throw new UserNotFoundException();
		}
		catch(FarmacoNotFoundException e2) {
			throw new FarmacoNotFoundException();
		}
	}

	private void creaPrescrizioniIniziali() {
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
		
		Farmaco f3 = new Farmaco();
		f3.setId(3);
		f3.setCodiceMinisteriale("87402");
		f3.setNome("Rinazina");
		f3.setCasaFarmaceutica("Santa Croce");
		f3.setPrezzo(4.99);
		f3.setDisponibilita(8);
		f3.setQuantitaMinima(3);
		
		List<Farmaco> lista1 = new ArrayList<Farmaco>();
		lista1.add(f1);
		lista1.add(f2);
		
		List<Farmaco> lista2 = new ArrayList<Farmaco>();
		lista2.add(f3);
		
		Medico medico = new Medico();
		medico.setId(2);
		medico.setNome("Davide");
		medico.setCognome("Palombaro");
		medico.setEmail("davide@gmail.com");
		medico.setCodiceFiscale("DVDPLB99L21E058I");
		medico.setPassword("davide");
		
		Paziente paziente = new Paziente();
		paziente.setId(1);
		paziente.setNome("Federico");
		paziente.setCognome("Cantoro");
		paziente.setEmail("federico@gmail.com");
		paziente.setCodiceFiscale("CNTFRC");
		paziente.setPassword("federiico");
		
		LocalDate date = LocalDate.now();
		
		Prescrizione p1 = new Prescrizione();
		p1.setNumero(1);
		p1.setMedico(medico);
		p1.setPaziente(paziente);
		p1.setFarmaco(lista1);
		p1.setData(date);
		p1.setStato("Non Evasa");
		
		Prescrizione p2 = new Prescrizione();
		p2.setNumero(2);
		p2.setMedico(medico);
		p2.setPaziente(paziente);
		p2.setFarmaco(lista2);
		p2.setData(date);
		p2.setStato("Non Evasa");
		
		
		prescrizioni.add(p1);
		prescrizioni.add(p2);
		paziente.setPrescrizioni(prescrizioni);
		paziente.setPrescrizioni(prescrizioni);
		
		
	}
}
