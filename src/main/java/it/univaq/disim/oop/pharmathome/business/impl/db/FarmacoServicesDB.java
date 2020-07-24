package it.univaq.disim.oop.pharmathome.business.impl.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.univaq.disim.oop.pharmathome.business.exceptions.BusinessException;
import it.univaq.disim.oop.pharmathome.business.exceptions.CancellaFarmacoException;
import it.univaq.disim.oop.pharmathome.business.exceptions.ConnectionException;
import it.univaq.disim.oop.pharmathome.business.exceptions.FarmacoNotFoundException;
import it.univaq.disim.oop.pharmathome.business.services.FarmacoServices;
import it.univaq.disim.oop.pharmathome.domain.Farmaco;

public class FarmacoServicesDB extends ConnectionDB implements FarmacoServices{
	
	private static Connection con;
	
	public FarmacoServicesDB() throws ConnectionException {
		
		try {
			con = DriverManager.getConnection(CONNECTION_STRING,DB_USER,DB_PASSWORD);
		} catch (SQLException e) {
			throw new ConnectionException("Errore di connessione",e);
		}
	}

	@Override
	public List<Farmaco> findAllFarmaci() throws BusinessException{
		
		List<Farmaco> inventario = new ArrayList<Farmaco>();
		Farmaco farmaco;
		
		
		try(PreparedStatement s = con.prepareStatement("select * from farmaco");){
			
			try(ResultSet resultSet = s.executeQuery()){
				
	        	while (resultSet.next()) {
	        			 
	        		farmaco = new Farmaco();
	        		farmaco.setId(resultSet.getInt("Id"));
	        		farmaco.setCodiceMinisteriale(resultSet.getString("Codice"));
	        		farmaco.setNome(resultSet.getString("Nome"));
	        		farmaco.setCasaFarmaceutica(resultSet.getString("CasaFarmaceutica"));
	        		farmaco.setPrezzo(resultSet.getDouble("Prezzo"));
	        		farmaco.setDisponibilita(resultSet.getInt("Disponibilita"));
	        		farmaco.setQuantitaMinima(resultSet.getInt("QuantitaMinima"));
	        		
	        		inventario.add(farmaco);
	        	}
			}
	     } catch (SQLException e) {
	    	 throw new ConnectionException("Errore esecuzione query", e);
	        }
		
		return inventario;
	}

	@Override
	public List<Farmaco> findFarmaciInEsaurimento() throws BusinessException{
		
		List<Farmaco> inventario = new ArrayList<Farmaco>();
		Farmaco farmaco;
		
		
		try(PreparedStatement s = con.prepareStatement("select * from farmaco where Disponibilita < QuantitaMinima")){
			
			try(ResultSet resultSet = s.executeQuery()){
        		
				while (resultSet.next()) {
        			 
	        		farmaco = new Farmaco();
	        		farmaco.setId(resultSet.getInt("Id"));
	        		farmaco.setCodiceMinisteriale(resultSet.getString("Codice"));
	        		farmaco.setNome(resultSet.getString("Nome"));
	        		farmaco.setCasaFarmaceutica(resultSet.getString("CasaFarmaceutica"));
	        		farmaco.setPrezzo(resultSet.getDouble("Prezzo"));
	        		farmaco.setDisponibilita(resultSet.getInt("Disponibilita"));
	        		farmaco.setQuantitaMinima(resultSet.getInt("QuantitaMinima"));
	        		
	        		inventario.add(farmaco);
				}
			}
        } catch (SQLException e) {
        	 throw new ConnectionException("Errore esecuzione query", e);
        }
		
		return inventario;
	}
	
	@Override
	public void aggiungiFarmaco(Farmaco farmaco) throws BusinessException {
		
		try(PreparedStatement s = con.prepareStatement("insert into farmaco(Codice,Nome,CasaFarmaceutica,Prezzo,Disponibilita,QuantitaMinima) values(?,?,?,?,?,?)")){
			
			s.setString(1, farmaco.getCodiceMinisteriale());
			s.setString(2, farmaco.getNome());
			s.setString(3, farmaco.getCasaFarmaceutica());
			s.setDouble(4, farmaco.getPrezzo());
			s.setInt(5, farmaco.getDisponibilita());
			s.setInt(6, farmaco.getQuantitaMinima());
			
			int resultSet = s.executeUpdate();
	    			
	    } catch (SQLException e) {
	    	 throw new ConnectionException("Errore esecuzione query", e);
	    }
	}
	
	public void cancellaFarmaco(Farmaco farmaco) throws BusinessException{
		
		try(PreparedStatement s = con.prepareStatement("delete from farmaco where Id = ? ")){
			
			s.setInt(1, farmaco.getId());
			
			
			int res = s.executeUpdate();
			
	    } catch (SQLException e) {
	    	throw new CancellaFarmacoException();	   
	    }
	}
	
	public void modificaFarmaco(Farmaco farmaco, String codice, String nome, String produttore, double prezzo, 
								int disponibilita, int quantitaMin) throws BusinessException{
		
		try(PreparedStatement s = con.prepareStatement("update farmaco set Codice = ?, Nome = ?, CasaFarmaceutica = ?, "
				+ "Prezzo = ?, Disponibilita = ?, QuantitaMinima = ? where Id = ?")){
			
			s.setString(1, codice);
			s.setString(2, nome);
			s.setString(3, produttore);
			s.setDouble(4, prezzo);
			s.setInt(5, disponibilita);
			s.setInt(6, quantitaMin);
			s.setInt(7, farmaco.getId());
			
			int res = s.executeUpdate();
			
	    } catch (SQLException e) {
	    	throw new ConnectionException("Errore esecuzione query", e);	   
	    }
	}

	@Override
	public Farmaco cercaFarmaco(String nome) throws BusinessException {
		
		Farmaco result = null;
		
		try(PreparedStatement s = con.prepareStatement("select * from farmaco where Nome = ?")){
			
			s.setString(1, nome);
			
			try(ResultSet resultSet = s.executeQuery()){
			
        		 while (resultSet.next()) {
        			 
        			 result = new Farmaco();
        			 result.setId(resultSet.getInt("Id"));
        			 result.setCodiceMinisteriale(resultSet.getString("Codice"));
        			 result.setNome(resultSet.getString("Nome"));
        			 result.setCasaFarmaceutica(resultSet.getString("CasaFarmaceutica"));
        			 result.setPrezzo(resultSet.getDouble("Prezzo"));
        			 result.setDisponibilita(resultSet.getInt("Disponibilita"));
        			 result.setQuantitaMinima(resultSet.getInt("QuantitaMinima"));
        		 }	 
        	}
        } catch (SQLException e) {
        		throw new ConnectionException("Errore esecuzione query", e);	 
        }
		
		if(result != null)
			return result;
		throw new FarmacoNotFoundException();
	}

	@Override
	public Farmaco findFarmacoById(int id) throws BusinessException {
		
		Farmaco result = null;
		
		try(PreparedStatement s = con.prepareStatement("Select * from farmaco where Id = ?")){
			
			s.setInt(1, id);
			
			try(ResultSet resultSet = s.executeQuery()){
				
       		 while (resultSet.next()) {
       			 
       			 result = new Farmaco();
       			 result.setId(resultSet.getInt("Id"));
       			 result.setCodiceMinisteriale(resultSet.getString("Codice"));
       			 result.setNome(resultSet.getString("Nome"));
       			 result.setCasaFarmaceutica(resultSet.getString("CasaFarmaceutica"));
       			 result.setPrezzo(resultSet.getDouble("Prezzo"));
       			 result.setDisponibilita(resultSet.getInt("Disponibilita"));
       			 result.setQuantitaMinima(resultSet.getInt("QuantitaMinima"));
       		 }	 
       	}
       } catch (SQLException e) {
       		throw new ConnectionException("Errore esecuzione query", e);	 
       }
		
		if(result != null)
			return result;
		throw new FarmacoNotFoundException();
	}

	@Override
	public int findIdFarmacoByNome(String nome) throws BusinessException {
		
		int result = -1;
		
		try(PreparedStatement s = con.prepareStatement("Select Id from farmaco where Nome = ?")){
			
			s.setString(1, nome);
			
			try(ResultSet resultSet = s.executeQuery()){
				
       		 while (resultSet.next()) {
       			 result = resultSet.getInt("Id");
       		 }	 
       	}
       } catch (SQLException e) {
       		throw new ConnectionException("Errore esecuzione query", e);	 
       }
		
		if(result >= 0)
			return result;
		throw new FarmacoNotFoundException();	
	}

}
