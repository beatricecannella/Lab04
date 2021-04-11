/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
*/
package it.polito.tdp.lab04;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;
import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	Model model;
	List <String> elencoCorsi = new ArrayList<String>();
	CorsoDAO corsoDao= new CorsoDAO();
	StudenteDAO studenti = new StudenteDAO(); 
	

    @FXML
    private Button btnCercaIscrizione;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Corso> boxCorsi;

    @FXML
    private Button btnCercaIscrittICorso;

    @FXML
    private TextField txtMatricola;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCognome;

    @FXML
    private Button btnCorsi;

    @FXML
    private Button btnIscrivi;

    @FXML
    private TextArea txtResult;

    @FXML
    private Button btnReset;

    @FXML
    void controllaStudente(ActionEvent event) {
    	this.txtResult.clear();
    	boolean matricolaEsistente = false;
    int matricola = 0;
    try{
    	matricola = Integer.parseInt(this.txtMatricola.getText());
    } catch(NumberFormatException nfe) {
    	this.txtResult.setText("ERRORE: inserire una matricola valida!");
    }
    for(Studente s: this.studenti.getTuttiGliStudenti()) {
    	if(s.getMatricola() == matricola) {
    		this.txtCognome.setText(s.getCognome());
    		this.txtNome.setText(s.getNome());
    		matricolaEsistente = true;
    	}
    }
    if(matricolaEsistente == false) {
    	this.txtResult.setText("ERRORE: matricola inesistente!");
    }
    
    
    }
    @FXML
    void doReset(ActionEvent event) {
    	this.txtResult.setText("");
    	this.txtNome.clear();
    	this.txtMatricola.clear();
    	this.txtCognome.clear();
    	this.boxCorsi.setValue(null);
    }
    @FXML
    void scegliCorso(ActionEvent event) {

    }

    

    @FXML
    void searchCorsi(ActionEvent event) {
    	this.txtResult.clear();
    	int matricola = 0;
    	try {
    		matricola = Integer.parseInt(txtMatricola.getText());
    		Studente studenteSelezionato = (Studente)this.model.cercaStudente(matricola);
    		if(studenteSelezionato !=null) {
    			List<Corso> corsiDelloStudente = this.model.getStudentiIscrittiAlCorso(studenteSelezionato);
    			StringBuilder sb = new StringBuilder();
    			this.txtResult.setStyle("-fx-font-family: monospace");
    			for(Corso c: corsiDelloStudente) {
    				sb.append(String.format("%-8s ", c.getCodis()));
    				sb.append(String.format("%-4s ", c.getCrediti()));
    				sb.append(String.format("%-45s ", c.getNome()));
    				sb.append(String.format("%-4s ", c.getPd()));
    				sb.append("\n");
    			}
    		
    			this.txtResult.appendText(sb.toString());
    		}
    		else {
    			this.txtResult.setText("ERRORE: Matricola non esistente!");
    		}
    	}
    	catch(NumberFormatException nfe){
    		this.txtResult.setText("ERRORE: Devi inserire una matricola numerica!");
    	}
    		
    }
    @FXML
    void doCercaIscrizione(ActionEvent event) {
    	this.txtResult.clear();
    	Corso corsoSelezionato = this.boxCorsi.getValue();
    	
    	int matricola = 0;
    	try {
    		matricola = Integer.parseInt(txtMatricola.getText());
    		Studente studenteSelezionato = (Studente)this.model.cercaStudente(matricola);
    		if(studenteSelezionato !=null) {
    			boolean iscritto = model.isStudenteIscrittoACorso(studenteSelezionato, corsoSelezionato);
    			if(iscritto == true) {
    				this.txtResult.setText("Studente già iscritto al corso " + corsoSelezionato.getNome());
    			}
    			else {
    				this.txtResult.setText("Studente non iscritto al corso " + corsoSelezionato.getNome());
    			}
    		}

    }
    	catch(NumberFormatException nfe){
    		this.txtResult.setText("ERRORE: Devi inserire una matricola numerica!");
    	}
    }

    @FXML
    void searchIscrittiCorso(ActionEvent event) {
    this.txtResult.clear();
    
  
    	Corso corsoSelezionato = this.boxCorsi.getValue();
    //	List <Studente> studentiIscritti = model.getStudentiIscrittiAlCorso(corsoSelezionato);
    	this.txtResult.setStyle("-fx-font-family: monospace");
        if(corsoSelezionato == null) {
    		txtResult.setText("ERRORE: Devi selezionare un corso");
    	return;
    	}
    	String elenco = "";
    	for(Studente s : model.getStudentiIscrittiAlCorso(corsoSelezionato)) {
    		elenco += s.toString() + "\n"; 
    		
    	}
    	this.txtResult.setText("" + elenco);
   
    }
    
    @FXML
    void doIscrivi(ActionEvent event) {
    	Corso corsoSelezionato = this.boxCorsi.getValue();
    	int matricola = 0;
    	try {
    		matricola = Integer.parseInt(txtMatricola.getText());
    		Studente studenteSelezionato = (Studente)this.model.cercaStudente(matricola);
    		if(studenteSelezionato !=null) {
    			boolean iscritto = model.isStudenteIscrittoACorso(studenteSelezionato, corsoSelezionato);
    			if(iscritto == true) {
    				this.txtResult.setText("ERRORE: studente già iscritto al corso " + corsoSelezionato.getNome());
    				return;
    			}
    			if(iscritto == false) {
    				
    				if(model.inscriviStudenteACorso(studenteSelezionato, corsoSelezionato) == true) {
    					
    				this.txtResult.setText("Iscrizione avvenuta con successo!" + corsoSelezionato.getNome());
    				
    				}
    			}
    		
    		}
    		}catch(NumberFormatException nfe){
        		this.txtResult.setText("ERRORE: Devi inserire una matricola numerica!");
        	}
    	}	
    


    public void setModel(Model m) {
    this.model = m;
    
    this.boxCorsi.getItems().addAll(model.getAllCourses());
  //this.boxCorsi.getItems().add(null);
    }
    
    @FXML
    void initialize() {
        assert boxCorsi != null : "fx:id=\"boxCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaIscrittICorso != null : "fx:id=\"btnCercaIscrittICorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCorsi != null : "fx:id=\"btnCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaIscrizione != null : "fx:id=\"btnCercaIscrizione\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnIscrivi != null : "fx:id=\"btnIscrivi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'Scene.fxml'.";
        model = new Model();
    
    }
}
