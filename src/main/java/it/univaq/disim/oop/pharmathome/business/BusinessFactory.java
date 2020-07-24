package it.univaq.disim.oop.pharmathome.business;

import it.univaq.disim.oop.pharmathome.business.impl.db.DBBusinessFactory;
import it.univaq.disim.oop.pharmathome.business.impl.file.FileBusinessFactory;
import it.univaq.disim.oop.pharmathome.business.services.FarmacoServices;
import it.univaq.disim.oop.pharmathome.business.services.PrescrizioneServices;
import it.univaq.disim.oop.pharmathome.business.services.UserServices;

public abstract class BusinessFactory {

//	private static BusinessFactory factory = new FileBusinessFactory();
//	private static BusinessFactory factory = new RAMBusinessFactory();
	private static BusinessFactory factory = new DBBusinessFactory();
	
	public static BusinessFactory getInstance() {
		return factory;
	}
	
	public abstract UserServices getUserServices();
	
	public abstract FarmacoServices getFarmacoServices();
	
	public abstract PrescrizioneServices getPrescrizioneServices();

}

