package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {

	public List<Studente> getTuttiGliStudenti() {

		final String sql = "SELECT * "
				+ " FROM studente";
		List<Studente> studenti = new LinkedList<Studente>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				int matricola = rs.getInt("matricola");
				String cognome = rs.getString("cognome");
				
				String nome = rs.getString("nome");
				
				String cds = rs.getString("CDS");

				System.out.println(matricola + " " + cognome + " " + nome + " " + cds);
				
				// Crea un nuovo JAVA Bean Studente
			Studente s  = new Studente(matricola, cognome, nome, cds);
			// Aggiungi il nuovo oggetto Studente alla lista studenti
				studenti.add(s);
			}

			conn.close();
			
			return studenti;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	public Studente cercaStudente(int matricola) {
		for(Studente s : this.getTuttiGliStudenti()) {
			if(s.getMatricola() == matricola) {
				return s;
			}
		}
		return null;
	}
	
	public List<Corso> getStudentiIscrittiAlCorso(Studente s) {
		List<Corso> corsi = new LinkedList<Corso>();
		Studente studenteCercato = this.cercaStudente(s.getMatricola());
		String sql = "SELECT * "
				+ "FROM corso c, iscrizione i "
				+ "WHERE c.codins=i.codins AND i.matricola=?";
		

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, studenteCercato.getMatricola());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");
				System.out.println(codins + " " + numeroCrediti + " " + nome + " " + periodoDidattico);
				
				// Crea un nuovo JAVA Bean Studente
			Corso c  = new Corso(codins, numeroCrediti, nome, periodoDidattico);
			// Aggiungi il nuovo oggetto Studente alla lista studenti
				corsi.add(c);
			}
		

			conn.close();

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
		return corsi;
	}
	
	public boolean isStudenteIscrittoACorso(Studente s, Corso c){
		boolean returnValue = false;

		String sql="SELECT * "
				+ "FROM iscrizione i "
				+ "WHERE matricola=? AND codins=?";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, s.getMatricola());
			st.setString(2, c.getCodis());
			ResultSet rs = st.executeQuery();

			if(rs.next()) {
				returnValue = true;
			}
		

			conn.close();

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
		return returnValue;
	}
	
}
