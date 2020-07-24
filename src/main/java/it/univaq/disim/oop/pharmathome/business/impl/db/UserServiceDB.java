package it.univaq.disim.oop.pharmathome.business.impl.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import it.univaq.disim.oop.pharmathome.business.exceptions.BusinessException;
import it.univaq.disim.oop.pharmathome.business.exceptions.ConnectionException;
import it.univaq.disim.oop.pharmathome.business.exceptions.UserNotFoundException;
import it.univaq.disim.oop.pharmathome.business.services.UserServices;
import it.univaq.disim.oop.pharmathome.domain.Amministratore;
import it.univaq.disim.oop.pharmathome.domain.Farmacista;
import it.univaq.disim.oop.pharmathome.domain.Medico;
import it.univaq.disim.oop.pharmathome.domain.Paziente;
import it.univaq.disim.oop.pharmathome.domain.Utente;

public class UserServiceDB extends ConnectionDB implements UserServices {
	
	private static Connection con;
	
	public UserServiceDB() throws ConnectionException {
		
		try {
			con = DriverManager.getConnection(CONNECTION_STRING,DB_USER,DB_PASSWORD);
		} catch (SQLException e) {
			throw new ConnectionException("Errore di connessione",e);
		}
	}
	
	@Override
	public Utente authenticate(String username, String password) throws BusinessException{
		
		Utente user = null;
		
		try(PreparedStatement s = con.prepareStatement("select * from utente where CodiceFiscale = ? and Password = ?")){
			
			s.setString(1, username);
			s.setString(2, password);
			
			try(ResultSet rs = s.executeQuery()){
		
			    	
				while(rs.next()) {
					
					if(rs.getInt("Tipo") == 1)
				    	user = new Paziente();
					if(rs.getInt("Tipo") == 2)
				    	user = new Medico();
				    if(rs.getInt("Tipo") == 3)
				    	user = new Amministratore();
				    if(rs.getInt("Tipo") == 4)
				    	user = new Farmacista();


				    		
				    user.setId(Integer.parseInt(rs.getString("Id")));
				    user.setNome(rs.getString("Nome"));
				    user.setCognome(rs.getString("Cognome"));
				    user.setEmail(rs.getString("Email"));
				    user.setCodiceFiscale(username);
				    user.setPassword(password);	    
			    
			}
			}
		  } catch (SQLException e) {
		    	throw new ConnectionException("Errore esecuzione query", e);
		  }
		
		if(user != null)
			return user;
		throw new UserNotFoundException();
	}

	@Override
	public Utente register(String nome, String cognome, String codiceFiscale, String email, String password, String tipo) throws BusinessException {
		
		int tipoInt = 0;
		
		
		switch(tipo) {
			case("Paziente"):
				tipoInt = 1;
				break;
			case("Medico"):
				tipoInt = 2;
				break;
			case("Farmacista"):
				tipoInt = 4;
				break;
		}
		
		Utente result = null;
	
		
		try(PreparedStatement s = con.prepareStatement("insert into utente (Nome,Cognome,Email,CodiceFiscale,Password,Tipo)"
								+ " values (?,?,?,?,?,?)");){
			
			s.setString(1, nome);
			s.setString(2, cognome);
			s.setString(3, email);
			s.setString(4, codiceFiscale);
			s.setString(5, password);
			s.setInt(6, tipoInt);
			
			int res = s.executeUpdate();
				
			try(PreparedStatement s1 = con.prepareStatement("select Id from utente where Nome = ? and Cognome = ?")){
	         
				s1.setString(1, nome);
				s1.setString(2, cognome);
				
				try(ResultSet resultSet = s1.executeQuery()){
	           
		            while(resultSet.next()) {
		            	
			            if(tipo.equals("Paziente")) {
							result = new Paziente();	
						}
						if(tipo.equals("Medico")) {
							result = new Medico();	
						}
						if(tipo.equals("Farmacista")) {
							result = new Farmacista();	
						}
						
						result.setId(resultSet.getInt("Id"));
						result.setNome(nome);
						result.setCognome(cognome);
						result.setEmail(email);
						result.setCodiceFiscale(codiceFiscale);
						result.setPassword(password);
		            }
				}
			}	
		} catch (SQLException e) {
			  e.printStackTrace();
	          throw new ConnectionException("Errore esecuzione query", e);
	        }
		
		return result;
	}

	@Override
	public Medico findMedicoById(int id) throws BusinessException {
		
		Medico result = null;
		
		try(PreparedStatement s = con.prepareStatement("select * from utente where Id = ?")){
			
			s.setInt(1, id);
			
			try(ResultSet resultSet = s.executeQuery()){
				
				while(resultSet.next()) {
						
				    result = new Medico();
				    		
				    result.setId(id);
				    result.setNome(resultSet.getString("Nome"));
				    result.setCognome(resultSet.getString("Cognome"));
				    result.setEmail(resultSet.getString("Email"));
				    result.setCodiceFiscale(resultSet.getString("CodiceFiscale"));
				    result.setPassword(resultSet.getString("Password"));	    
				    
				}
			}
		  } catch (SQLException e) {
			  throw new ConnectionException("Errore esecuzione query", e);
		  }
		
		if(result != null)
			return result;
		throw new UserNotFoundException();
	}

	@Override
	public Paziente findPazienteById(int id) throws BusinessException {
		
		Paziente result = null;
		
		try(PreparedStatement s = con.prepareStatement("select * from utente where Id = ?")){
			
			s.setInt(1, id);
			
			try(ResultSet resultSet = s.executeQuery()){
				
				while(resultSet.next()) {
						
				    result = new Paziente();
				    		
				    result.setId(id);
				    result.setNome(resultSet.getString("Nome"));
				    result.setCognome(resultSet.getString("Cognome"));
				    result.setEmail(resultSet.getString("Email"));
				    result.setCodiceFiscale(resultSet.getString("CodiceFiscale"));
				    result.setPassword(resultSet.getString("Password"));	    
				    
				}
			}
		  } catch (SQLException e) {
			  throw new ConnectionException("Errore esecuzione query", e);
		  }
		
		if(result != null)
			return result;
		throw new UserNotFoundException();	
		
	}

	@Override
	public int findIdPazienteByNominativo(String nome, String cognome) throws BusinessException {
		
		int result = -1;
	
		try(PreparedStatement s = con.prepareStatement("select Id from utente where Nome = ? and Cognome = ?")){
			
			s.setString(1, nome);
			s.setString(2, cognome);
			
			try(ResultSet resultSet = s.executeQuery()){
				
				while(resultSet.next()) {
						
					result = resultSet.getInt("Id");
				}
			}
		  } catch (SQLException e) {
			  throw new ConnectionException("Errore esecuzione query", e);
		  }
		
		if(result >= 0)
			return result;
		throw new UserNotFoundException();
		
	}

}
