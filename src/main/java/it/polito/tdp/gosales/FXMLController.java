package it.polito.tdp.gosales;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.gosales.model.CoppiaA;
import it.polito.tdp.gosales.model.Model;
import it.polito.tdp.gosales.model.Products;
import it.polito.tdp.gosales.model.Retailers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAnalizzaComponente;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnSimula;

    @FXML
    private ComboBox<Integer> cmbAnno;

    @FXML
    private ComboBox<String> cmbNazione;

    @FXML
    private ComboBox<String> cmbProdotto;

    @FXML
    private ComboBox<String> cmbRivenditore;

    @FXML
    private TextArea txtArchi;

    @FXML
    private TextField txtN;

    @FXML
    private TextField txtNProdotti;

    @FXML
    private TextField txtQ;

    @FXML
    private TextArea txtResult;

    @FXML
    private TextArea txtVertici;

    @FXML
    void doAnalizzaComponente(ActionEvent event) {
    	String retailer = this.cmbRivenditore.getValue();
    	String retailerID = retailer.substring(0, retailer.indexOf("-"));
    	Integer rID = Integer.parseInt(retailerID);
    	Integer anno = this.cmbAnno.getValue();

    	if(retailer == null){
    		this.txtResult.setText("Inserire un anno nella boxRivenditore!");
    		return;
    	}
    	if(anno == null){
    		this.txtResult.setText("Inserire un anno nella boxAnno!");
    		return;
    	}
    	Retailers r = model.getIdMapRetailers().get(rID);
    	
    	int n = model.getNumberOfConnectedComponents(r);
    	int peso = model.getWeightOfConnectedComponents(r);
    	this.txtResult.appendText("\nComponente connessa composta da "+n+" vertici e avente peso "+peso);
    	
    	for(Products x : model.getAllProducts(r)) {
    		this.cmbProdotto.getItems().add(x.getNumber()+"-"+x.getProduct());
    	}
    	this.cmbProdotto.setDisable(false);
    	this.btnSimula.setDisable(false);
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	Integer anno = this.cmbAnno.getValue();
    	String nazione = this.cmbNazione.getValue();
    	String nProd = this.txtNProdotti.getText();
    	Integer nProdotti;
    	if(anno == null){
    		this.txtResult.setText("Inserire un anno nella boxAnno!");
    		return;
    	}
    	if(nazione == null){
    		this.txtResult.setText("Inserire una nazione nella boxNazione!");
    		return;
    	}
    	try {
    		nProdotti = Integer.parseInt(nProd);
    		
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("Inserire un valore numerico nel campo n");
    		return;
    	}
    	String s = model.creaGrafo(anno, nazione, nProdotti);
    	this.txtResult.setText(s);
    	
    	
    	String vertici = "";
    	String archi = "";
    	for(Retailers x : model.getGrafo().vertexSet()) {
    		vertici += x.getName() + "\n";
    	}
    	for(CoppiaA x : model.listArchi()) {
    		archi += x.getR1() +" <---> "+ x.getR2()+" ("+x.getPeso()+")\n";
    	}
    	this.txtVertici.setText(vertici);
    	this.txtArchi.setText(archi);
    	
    	this.cmbRivenditore.setDisable(false);
    	for(Retailers x : model.getGrafo().vertexSet()) {
    		this.cmbRivenditore.getItems().add(x.getCode()+"-"+x.getName());
    	}
    	this.btnAnalizzaComponente.setDisable(false);
    	this.txtN.setDisable(false);
    	this.txtQ.setDisable(false);
    	
    }

    @FXML
    void doSimulazione(ActionEvent event) {
    	String prod = this.cmbProdotto.getValue();
    	String id = prod.substring(0, prod.indexOf("-"));
    	Integer pID = Integer.parseInt(id);
    }

    @FXML
    void initialize() {
        assert btnAnalizzaComponente != null : "fx:id=\"btnAnalizzaComponente\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbAnno != null : "fx:id=\"cmbAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbNazione != null : "fx:id=\"cmbNazione\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbProdotto != null : "fx:id=\"cmbProdotto\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbRivenditore != null : "fx:id=\"cmbRivenditore\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtArchi != null : "fx:id=\"txtArchi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNProdotti != null : "fx:id=\"txtNProdotti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtQ != null : "fx:id=\"txtQ\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtVertici != null : "fx:id=\"txtVertici\" was not injected: check your FXML file 'Scene.fxml'.";

        for(int anno = 2015 ; anno <= 2018 ; anno ++) {
        	this.cmbAnno.getItems().add(anno);
        }
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	for(String x : model.getAllCountries()) {
    		this.cmbNazione.getItems().add(x);
    	}
    }

}
