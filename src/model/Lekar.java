package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Lekar extends Korisnik{
	protected ArrayList<Termin> zakazaniTermini;
	
	public Lekar(int id, String Korisnickoime, String lozinka, String ime, String prezime, String jMBG, Pol pol, String adresa, String brojTelefona, Uloga uloga, LocalDateTime datumRegistracije, boolean aktivan) {
		super(id, Korisnickoime, lozinka, ime, prezime, jMBG, pol, adresa, brojTelefona, uloga, datumRegistracije,aktivan);
		// TODO Auto-generated constructor stub
		zakazaniTermini = new ArrayList<Termin>();
		
	}
	
	public Lekar(Lekar l) {
		super(l.getId(), l.getKorisnickoIme(), l.getLozinka(), l.getIme(), l.getPrezime(), l.getJMBG(), l.getPol(), l.getAdresa(), l.getBrojTelefona(), l.getUloga(), l.getDatumRegistracije(), l.getAktivan());
		this.zakazaniTermini = l.getZakazaniTermini();
		this.aktivan = l.getAktivan();
	}

	public ArrayList<Termin> getZakazaniTermini() {
		return zakazaniTermini;
	}

	public void setZakazaniTermini(ArrayList<Termin> zakazaniTermini) {
		this.zakazaniTermini = zakazaniTermini;
	}

	@Override
	public String toString() {
		return "Lekar [zakazaniTermini=" + zakazaniTermini + "]";
	}
	
	public boolean addTermin(Termin t) {
		if (t != null) {
			zakazaniTermini.add(t);
			t.lekar = this;
			return true;
		}
		return false;
	}
	
	public boolean removeTermin(Termin t) {
		for (Termin t1: zakazaniTermini) {
			if (t1.equals(t)) {
				t1.lekar = null;
				zakazaniTermini.remove(t1);
				return true;
			}
		}
		return false;
	}
	
	
}
