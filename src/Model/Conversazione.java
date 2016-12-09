package Model;

import java.util.ArrayList;
import java.util.Date;

//non serve tutta la classe, si potrà levare
public class Conversazione {

	private ArrayList<Frase> frasi;
	private Date dataInizio;
	private Date dataFine;
	
	

	public Conversazione(ArrayList<Frase> frasi, Date dataInizio) {
		super();
		this.frasi = frasi;
		this.dataInizio = dataInizio;
	}

	public void addFrase(Frase frase) {
		this.frasi.add(frase);

	}

	// getter and setter
	public ArrayList<Frase> getFrasi() {
		return frasi;
	}

	public void setFrasi(ArrayList<Frase> frasi) {
		this.frasi = frasi;
	}

	public Date getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}

	public Date getDataFine() {
		return dataFine;
	}

	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}

}
