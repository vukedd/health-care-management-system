package model;

import java.time.LocalDateTime;

public class Admin extends Korisnik{

	public Admin(int id, String Korisnickoime, String lozinka, String ime, String prezime, String jMBG, Pol pol,
			String adresa, String brojTelefona, Uloga uloga, LocalDateTime datumRegistracije, boolean aktivan) {
		super(id, Korisnickoime, lozinka, ime, prezime, jMBG, pol, adresa, brojTelefona, uloga, datumRegistracije, aktivan);	
		
	}

	
	
}
