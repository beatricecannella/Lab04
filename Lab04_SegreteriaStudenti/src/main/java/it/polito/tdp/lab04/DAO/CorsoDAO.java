package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {
	StudenteDAO studente = new StudenteDAO() ;
	/*
	 * Ottengo tutti i corsi salvati nel Db
	 */
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * "
				+ " FROM corso";
		List<Corso> corsi = new LinkedList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				//System.out.println(codins + " " + numeroCrediti + " " + nome + " " + periodoDidattico);
				
				// Crea un nuovo JAVA Bean Corso
				Corso c = new Corso(codins, numeroCrediti, nome, periodoDidattico);
				// Aggiungi il nuovo oggetto Corso alla lista corsi
				corsi.add(c);
			}

			conn.close();
			
			return corsi;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	
	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	public Corso getCorso(Corso corso) {
		for(Corso c: this.getTuttiICorsi()) {
			if(c.equals(corso)) {
				return c;
			}
		}
		return null;
	}

	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
		String codins = corso.getCodis();
		final String sql = "SELECT * "
				+ " FROM iscrizione "
				+ "WHERE codins = ?";
	
		List<Studente> iscritti = new LinkedList<Studente>();
		
	
	

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, codins);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				int matricolaStudente = rs.getInt("matricola");
				Studente s = studente.cercaStudente(matricolaStudente);
				iscritti.add(s);
				
			}
			rs.close();
			st.close();
			conn.close();
			
			
			return iscritti;
		
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}

	/*
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	 */
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {
		String sql = "INSERT IGNORE INTO `iscritticorsi`.`iscrizione` (`matricola`, `codins`) VALUES(?,?)";
			boolean returnValue = false;	
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, studente.getMatricola());
			st.setString(2, corso.getCodis());
			int rs  = st.executeUpdate();	

			if(rs == 1) {
				returnValue = true; 
				
			}
		

			conn.close();

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
		return returnValue;
	}
		
		
		// TODO
		// ritorna true se l'iscrizione e' avvenuta con successo
	

}
