package it.univaq.disim.oop.pharmathome.business.impl.ram;

import it.univaq.disim.oop.pharmathome.business.BusinessFactory;
import it.univaq.disim.oop.pharmathome.business.exceptions.BusinessException;
import it.univaq.disim.oop.pharmathome.business.services.FarmacoServices;
import it.univaq.disim.oop.pharmathome.business.services.PrescrizioneServices;
import it.univaq.disim.oop.pharmathome.business.services.UserServices;

public class RAMBusinessFactory extends BusinessFactory{
	
	private UserServices userServices;
	private FarmacoServices farmacoServices;
	private PrescrizioneServices prescrizioneServices;
	
	public RAMBusinessFactory() {
		userServices = new UserServiceRAM();
		farmacoServices = new FarmacoServicesRAM();
		prescrizioneServices = new PrescrizioneServicesRAM(userServices, farmacoServices);
	} 
	
	public UserServices getUserServices() {
		return userServices;
	}

	@Override
	public FarmacoServices getFarmacoServices() {
		// TODO Auto-generated method stub
		return farmacoServices;
	}

	@Override
	public PrescrizioneServices getPrescrizioneServices() {
		return prescrizioneServices;
	}
	
	
}
