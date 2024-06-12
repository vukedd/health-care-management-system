package model;

import java.util.ArrayList;

public class ZdravstveniKarton {
	private int id;
	Pacijent pacijent;
	protected ArrayList<Termin> termini;
	protected ArrayList<Terapija> terapije;
	protected boolean aktivan;
	
	public ZdravstveniKarton(int id, Pacijent pacijent, boolean aktivan) {
		this.id = id;
		
		if (pacijent != null) {
			this.pacijent = pacijent;
		}
		pacijent.zKarton = this;
		this.termini = new ArrayList<Termin>();
		this.terapije = new ArrayList<Terapija>();
		this.aktivan = aktivan;
	}
	
	public boolean getAktivan() {
		return aktivan;
	}
	
	public void setAktivan(boolean aktivan) {
		this.aktivan = aktivan;
	}
	
	
	public Pacijent getPacijent() {
		return pacijent;
	}
	
	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}
	
	public ArrayList<Termin> getTermini() {
		return termini;
	}
	
	public void setTermini(Termin termin) {
		if (termin != null) {
			this.termini.add(termin);
		}
	}
	
	public int getId() {
		return id;
	}
	
	public boolean addTermin(Termin t) {
		if (t != null) {
			termini.add(t);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean removeTermin(Termin t) {
		for (Termin t1 : termini) {
			if (t1.equals(t)) {
				termini.remove(t);
				return true;
			}
		}
		
		return false;
	}
	
	public ArrayList<Terapija> getTerapije() {
		return terapije;
	}


	public void setTerapije(ArrayList<Terapija> terapije) {
		this.terapije = terapije;
	}


	public void setTermini(ArrayList<Termin> termini) {
		this.termini = termini;
	}


	public boolean addTerapija(Terapija t) {
		if (t != null) {
			terapije.add(t);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean removeTerapija(Terapija t) {
		for (Terapija t1 : terapije) {
			if (t1.equals(t)) {
				terapije.remove(t);
				return true;
			}
		}
		
		return false;
	}

}
