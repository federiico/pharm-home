package it.univaq.disim.oop.pharmathome.business.impl.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import it.univaq.disim.oop.pharmathome.business.exceptions.BusinessException;
import it.univaq.disim.oop.pharmathome.business.exceptions.ConnectionException;
import it.univaq.disim.oop.pharmathome.business.services.FarmacoServices;
import it.univaq.disim.oop.pharmathome.business.services.PrescrizioneServices;
import it.univaq.disim.oop.pharmathome.business.services.UserServices;
import it.univaq.disim.oop.pharmathome.domain.Farmaco;
import it.univaq.disim.oop.pharmathome.domain.Medico;
import it.univaq.disim.oop.pharmathome.domain.Paziente;
import it.univaq.disim.oop.pharmathome.domain.Prescrizione;

public class PrescrizioneServicesDB extends ConnectionDB implements PrescrizioneServices{
	
	private FarmacoServices farmacoServices;
	private UserServices userServices;
	private static Connection con;
	
	
	
	public PrescrizioneServicesDB( FarmacoServices farmServ, UserServices userServ) throws ConnectionException{
	
		try {
			con = DriverManager.getConnection(CONNECTION_STRING,DB_USER,DB_PASSWORD);
			
		} catch (SQLException e) {
			throw new ConnectionException("Errore di connessione",e);
		}
		farmacoServices = farmServ;
		userServices = userServ;;
		
	}

	@Override
	public List<Prescrizione> findPrescrizioniDaEvadere() throws BusinessException{
		
		List<Prescrizione> result = new ArrayList<Prescrizione>();
		List<Farmaco> listaFarmaci = new ArrayList<Farmaco>();
		Prescrizione prescrizione;
		Farmaco farmaco;
		
		try( PreparedStatement s = con.prepareStatement("Select * from prescrizione where Stato = 'Non Evasa' group by NRE");
			 PreparedStatement f = con.prepareStatement("Select Id_farmaco from prescrizione where NRE = ?")){
			
			try(ResultSet rs = s.executeQuery()){
			
        		 while (rs.next()) {
        			 
        			prescrizione = new Prescrizione();
        			
        			prescrizione.setNumero(rs.getInt("NRE"));
        			prescrizione.setMedico(userServices.findMedicoById(rs.getInt("Id_medico")));
        			prescrizione.setPaziente(userServices.findPazienteById(rs.getInt("Id_paziente")));
        			
        			f.setInt(1, rs.getInt("NRE"));
        			listaFarmaci = new ArrayList<Farmaco>();
        			try(ResultSet rs2 = f.executeQuery()){
        				
        				while(rs2.next()) {
        					
        					farmaco = farmacoServices.findFarmacoById(rs2.getInt("Id_farmaco"));
        					listaFarmaci.add(farmaco);
        				}
        			}
        			
        			prescrizione.setFarmaco(listaFarmaci);
        			prescrizione.setData(rs.getDate("Data").toLocalDate());
        			 
        			result.add(prescrizione);
        		 
        		 }
			}	 	
        } catch (SQLException e) {
        	 throw new ConnectionException("Errore esecuzione query", e);
        }
		return result;
	}

	@Override
	public void evadiPrescrizione(Prescrizione prescrizione) throws BusinessException {
		
			try( PreparedStatement s = con.prepareStatement("UPDATE `pharmathome`.`prescrizione`\r\n" + 
				"SET" +  "`Stato` = \"Evasa\" " + 
				"WHERE `NRE` = "+ prescrizione.getNumero() +"");){
				
				int res = s.executeUpdate();
				
			}catch (SQLException e) {
				 throw new ConnectionException("Errore esecuzione query", e);
	    	}
	}

	@Override
	public List<Prescrizione> findPrescrizioniFromPaziente(Paziente paziente) throws BusinessException {
		
		List<Farmaco> listaFarmaci;
		List<Prescrizione> result = new ArrayList<Prescrizione>();
		Farmaco farmaco;
		Prescrizione prescrizione;
		
		try(PreparedStatement s = con.prepareStatement("select * from prescrizione where Id_paziente = " + paziente.getId() + " group by NRE");
			PreparedStatement f = con.prepareStatement("Select Id_farmaco from prescrizione where NRE = ?")){
			
			try(ResultSet rs = s.executeQuery()){
        		
				while (rs.next()) {
        			 
					prescrizione = new Prescrizione();
        			
        			prescrizione.setNumero(rs.getInt("NRE"));
        			prescrizione.setMedico(userServices.findMedicoById(rs.getInt("Id_medico")));
        			prescrizione.setPaziente(userServices.findPazienteById(rs.getInt("Id_paziente")));
        			
        			f.setInt(1, rs.getInt("NRE"));
        			listaFarmaci = new ArrayList<Farmaco>();
        			try(ResultSet rs2 = f.executeQuery()){
        				
        				while(rs2.next()) {
        					
        					farmaco = farmacoServices.findFarmacoById(rs2.getInt("Id_farmaco"));
        					listaFarmaci.add(farmaco);
        				}
        			}
        			
        			prescrizione.setFarmaco(listaFarmaci);
        			prescrizione.setData(rs.getDate("Data").toLocalDate());
        			 
        			result.add(prescrizione);
        		
        		 }
			}
        } catch (SQLException e) {
        	throw new ConnectionException("Errore esecuzione query", e);
        }
		
		return result;
	}
	
	@Override
	public List<Prescrizione> findPrescrizioniFromMedico(Medico medico) throws BusinessException {
		
		List<Farmaco> listaFarmaci;
		List<Prescrizione> result = new ArrayList<Prescrizione>();
		Prescrizione prescrizione;
		Farmaco farmaco;

		
		try(PreparedStatement s = con.prepareStatement("select * from prescrizione where Id_medico = " + medico.getId() + " group by NRE");
			PreparedStatement f = con.prepareStatement("Select Id_farmaco from prescrizione where NRE = ?")){
			
				try(ResultSet rs = s.executeQuery()){ 
	        		
					while (rs.next()) {
	        			
						prescrizione = new Prescrizione();
	        			prescrizione.setNumero(rs.getInt("NRE"));
	        			prescrizione.setMedico(medico);
	        			prescrizione.setPaziente(userServices.findPazienteById(rs.getInt("Id_paziente")));
	        			 
	        			f.setInt(1, rs.getInt("NRE"));
	        			listaFarmaci = new ArrayList<Farmaco>();
	        			try(ResultSet rs2 = f.executeQuery()){
	        				
	        				while(rs2.next()) {
	        					
	        					farmaco = farmacoServices.findFarmacoById(rs2.getInt("Id_farmaco"));
	        					listaFarmaci.add(farmaco);
	        				}
	        			}
	        			
	 					prescrizione.setData(rs.getDate("Data").toLocalDate());
	 					prescrizione.setFarmaco(listaFarmaci);
	 					prescrizione.setStato(rs.getString("Stato"));
	 					
	 					result.add(prescrizione);
	 					
	        		 }
				}
        	} catch (SQLException e) {
        		e.printStackTrace();
        		throw new ConnectionException("Errore esecuzione query", e);
    	}
		
		return result;		

	}
	
	@Override
	/*CONTROLLAREEE*/
	public List<Prescrizione> cercaPrescrizioni(Paziente paziente, String medico, String farmaco, LocalDate data) throws BusinessException {
		
		
		List<Prescrizione> result = new ArrayList<Prescrizione>();
		Prescrizione prescrizione;
		List<Farmaco> listaFarmaci;

		
		boolean boolMedico, boolData, boolFarmaco;
		
		Medico med;
		Farmaco farm;
		
		try(PreparedStatement s = con.prepareStatement("select * from prescrizione where Id_paziente =" + paziente.getId() + " group by NRE");
			PreparedStatement f = con.prepareStatement("select Id_farmaco, nome from prescrizione as p join farmaco as f on (p.Id_farmaco = f.Id) where NRE = ?")){
			
			try(ResultSet rs = s.executeQuery()){ 
        		
				while (rs.next()) {
					
					boolMedico = boolData = boolFarmaco = false;
					
        			 if(medico.isEmpty()) boolMedico = true;
        						
        			 boolFarmaco = false;
        			 if(farmaco.isEmpty()) boolFarmaco = true; 
        					      								
        			 med = userServices.findMedicoById(rs.getInt("Id_medico"));
        							
        			 if(boolMedico == false && (medico.equalsIgnoreCase(med.getNome()) || (medico.equalsIgnoreCase(med.getCognome()))))
        				boolMedico = true;
        							
        			 if(data.toString().equals(rs.getDate("data").toString()))
        				boolData = true;
        						
        			 f.setInt(1, rs.getInt("NRE"));
	        		 listaFarmaci = new ArrayList<Farmaco>();
	        		 
	        		 try(ResultSet rs2 = f.executeQuery()){
	        		      
	        			 while(rs2.next()) {
        		        					
        		        		if(farmaco.equalsIgnoreCase(rs2.getString("nome")))
        		        			boolFarmaco = true;
        		        		farm = farmacoServices.findFarmacoById(rs2.getInt("Id_farmaco"));
        		        		listaFarmaci.add(farm);
	        			 }
        		     }
        							
	        		 if(boolMedico && boolData && boolFarmaco) {
        								
	        			prescrizione = new Prescrizione();
	        			prescrizione.setNumero(rs.getInt("NRE"));
	        			prescrizione.setData(data);
	        			prescrizione.setMedico(med);
	        			prescrizione.setPaziente(paziente);
	        			prescrizione.setFarmaco(listaFarmaci);
        								
	        			result.add(prescrizione);
	        		 }
				}
			}
		}catch (SQLException e) {
        	throw new ConnectionException("Errore esecuzione query", e);
        }
		return result;
	}

	@Override
	public void modificaPrescrizione(Prescrizione prescrizione, String nome, String cognome, Collection<String> farmaci) throws BusinessException {
		

		
		try(PreparedStatement s = con.prepareStatement("delete from prescrizione where nre ="+ prescrizione.getNumero());
				PreparedStatement f = con.prepareStatement("Insert into prescrizione (NRE,Id_medico, Id_paziente,Id_farmaco,Data) values (?,?,?,?,?)")){
						
						int r = s.executeUpdate();
						Date data = Date.valueOf(prescrizione.getData());
						
						f.setInt(1, prescrizione.getNumero());
						f.setInt(2, prescrizione.getMedico().getId());
						f.setInt(3, userServices.findIdPazienteByNominativo(nome, cognome));
						f.setDate(5, data);
						
						for(String nomeFarmaco : farmaci) {
					
							f.setInt(4, farmacoServices.findIdFarmacoByNome(nomeFarmaco));
							r = f.executeUpdate();
						}
					} catch (SQLException e) {
						e.printStackTrace();
						throw new ConnectionException("Errore esecuzione query", e);
	    }
				
	}
	

	@Override
	public void cancellaPrescrizione(Prescrizione prescrizione) throws BusinessException {
		
		try(PreparedStatement s = con.prepareStatement("DELETE FROM `pharmathome`.`prescrizione` WHERE NRE = "+ prescrizione.getNumero())){
			
	    		int r = s.executeUpdate();
	    	
		} catch (SQLException ex) {
	    	}
	}

	@Override
	public void creaPrescrizione(int idMedico, String nome, String cognome, List<String> farmaci) throws BusinessException {
		try(PreparedStatement s = con.prepareStatement("Insert into prescrizione (NRE,Id_medico,Id_paziente,Id_farmaco, Data) values (?,"+ idMedico +","
														+ userServices.findIdPazienteByNominativo(nome,cognome) +", ?, ?)"); 
			PreparedStatement f = con.prepareStatement("select max(NRE)  from prescrizione")){
			
			ResultSet resultSet = f.executeQuery();
			int nre = 0;
			
			while(resultSet.next())
				 nre = resultSet.getInt("max(NRE)") +1;
			
			Date data = Date.valueOf(LocalDate.now());
			
			s.setInt(1, nre);
			s.setDate(3, data);
			
			for(String nomeFarmaco : farmaci) {
				s.setInt(2, farmacoServices.findIdFarmacoByNome(nomeFarmaco));
				int r = s.executeUpdate();
			}	
	    		
	    	} catch (SQLException e) {
	    		throw new ConnectionException("Errore esecuzione query", e);
	    	}
	}
}
