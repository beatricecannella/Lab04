package it.polito.tdp.lab04.model;

import java.util.List;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {
private CorsoDAO corsoDao = new CorsoDAO();
private StudenteDAO studenteDao = new StudenteDAO();

public List<Corso> getAllCourses(){

	return corsoDao.getTuttiICorsi();
}

public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
	return corsoDao.getStudentiIscrittiAlCorso(corso);
}

public List<Studente> getTuttiGliStudenti() {
	return studenteDao.getTuttiGliStudenti();
}
public Studente cercaStudente(int matricola) {
	return 	 studenteDao.cercaStudente(matricola);
}

public List<Corso> getStudentiIscrittiAlCorso(Studente s) {
	return studenteDao.getStudentiIscrittiAlCorso(s);
}
public boolean isStudenteIscrittoACorso(Studente s, Corso c){
	return this.studenteDao.isStudenteIscrittoACorso(s, c);
}
public boolean inscriviStudenteACorso(Studente studente, Corso corso) {
	return this.corsoDao.inscriviStudenteACorso(studente, corso);
}
}