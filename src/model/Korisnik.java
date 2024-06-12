package model;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class Korisnik {
	protected int id; 
	protected String korisnickoIme; 
	protected String lozinka; 
	protected String ime;
	protected String prezime; 
	protected String JMBG; 
	protected Pol pol;
	protected String adresa; 
	protected String brojTelefona; 
	protected Uloga uloga;
	protected LocalDateTime datumRegistracije;
	protected boolean aktivan;
	
	
	public Korisnik(int id, String korisnickoIme, String lozinka, String ime, String prezime, String jMBG, Pol pol , String adresa, String brojTelefona, Uloga uloga, LocalDateTime datumRegistracije, boolean aktivan) {
		super();
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.korisnickoIme = korisnickoIme;
		this.uloga = uloga;
		this.pol = pol;
		JMBG = jMBG;
		this.adresa = adresa;
		this.brojTelefona = brojTelefona;
		this.lozinka = lozinka;
		this.datumRegistracije = datumRegistracije;
		this.aktivan = aktivan;
	}

	public boolean getAktivan() {
		return aktivan;
	}
	
	public void setAktivan(boolean aktivnost) {
		this.aktivan = aktivnost;
	}
	
	public String getIme() {
		return ime;
	}
	
	public void setIme(String ime) {
		this.ime = ime;
	}
	
	public String getPrezime() {
		return prezime;
	}
	
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	
	public String getKorisnickoIme() {
		return korisnickoIme;
	}
	
	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}
	
	public Pol getPol() {
		return pol;
	}
	
	public void setPol(Pol pol) {
		this.pol = pol;
	}
	
	public String getJMBG() {
		return JMBG;
	}
	
	public void setJMBG(String jMBG) {
		JMBG = jMBG;
	}
	
	public String getAdresa() {
		return adresa;
	}
	
	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}
	
	public String getBrojTelefona() {
		return brojTelefona;
	}
	
	public void setBrojTelefona(String brojTelefona) {
		this.brojTelefona = brojTelefona;
	}
	
	public String getLozinka() {
		return lozinka;
	}
	
	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Uloga getUloga() {
		return uloga;
	}

	public void setUloga(Uloga uloga) {
		this.uloga = uloga;
	}
	
	public LocalDateTime getDatumRegistracije() {
		return datumRegistracije;
	}


	public void setDatumRegistracije(LocalDateTime datumRegistracije) {
		this.datumRegistracije = datumRegistracije;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Korisnik other = (Korisnik) obj;
		return id == other.id;
	}
	
	
}
