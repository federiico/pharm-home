package it.univaq.disim.oop.pharmathome.business.impl.db;

import it.univaq.disim.oop.pharmathome.business.BusinessFactory;
import it.univaq.disim.oop.pharmathome.business.exceptions.ConnectionException;
import it.univaq.disim.oop.pharmathome.business.services.FarmacoServices;
import it.univaq.disim.oop.pharmathome.business.services.PrescrizioneServices;
import it.univaq.disim.oop.pharmathome.business.services.UserServices;

public class DBBusinessFactory extends BusinessFactory{
	
	private UserServices userServices;
	private FarmacoServices farmaciServices;
	private PrescrizioneServices prescrizioneServices;
	
	public DBBusinessFactory() {
		
		try {
			userServices = new UserServiceDB();
			farmaciServices = new FarmacoServicesDB();
			prescrizioneServices = new PrescrizioneServicesDB(farmaciServices, userServices);
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
	} 
	
	@Override
	public UserServices getUserServices() {
		return userServices;
	}
	
	@Override
	public FarmacoServices getFarmacoServices() {
		return farmaciServices;
	}

	@Override
	public PrescrizioneServices getPrescrizioneServices() {
		return prescrizioneServices;
	}
	
}
