package it.univaq.disim.oop.pharmathome.business.impl.file;

import java.io.File;

import it.univaq.disim.oop.pharmathome.business.BusinessFactory;
import it.univaq.disim.oop.pharmathome.business.services.FarmacoServices;
import it.univaq.disim.oop.pharmathome.business.services.PrescrizioneServices;
import it.univaq.disim.oop.pharmathome.business.services.UserServices;

public class FileBusinessFactory extends BusinessFactory{
	
	private UserServices userServices;
	private FarmacoServices farmacoServices;
	private PrescrizioneServices prescrizioneServices;
	
	private static final String REPOSITORY_BASE = "src" + File.separator + "main" + File.separator + "resources"
			+ File.separator + "dati";
	private static final String UTENTI_FILE_NAME = REPOSITORY_BASE + File.separator + "Utenti";
	private static final String FARMACI_FILE_NAME = REPOSITORY_BASE + File.separator + "Farmaci";
	private static final String PRESCRIZIONI_FILE_NAME = REPOSITORY_BASE + File.separator + "Prescrizioni";
	

	public FileBusinessFactory() {
		
		this.userServices = new UserServicesFile(UTENTI_FILE_NAME);
		this.farmacoServices = new FarmacoServicesFile(FARMACI_FILE_NAME, PRESCRIZIONI_FILE_NAME);
		this.prescrizioneServices = new PrescrizioneServicesFile(PRESCRIZIONI_FILE_NAME, farmacoServices, userServices);
		
	}

	@Override
	public UserServices getUserServices() {
		
		return userServices;
	}

	@Override
	public FarmacoServices getFarmacoServices() {
		
		return farmacoServices;
	}

	@Override
	public PrescrizioneServices getPrescrizioneServices() {
	
		return prescrizioneServices;
	}

}
