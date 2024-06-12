package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Pacijent extends Korisnik{

	protected ZdravstveniKarton zKarton;
	
	public Pacijent(int id, String Korisnickoime, String lozinka, String ime, String prezime, String jMBG, Pol pol, String adresa, String brojTelefona, Uloga uloga, LocalDateTime datumRegistracije, ZdravstveniKarton zKarton, boolean aktivan) {
		super(id, Korisnickoime, lozinka, ime, prezime, jMBG, pol, adresa, brojTelefona, uloga, datumRegistracije, aktivan);
		
		
		 /* izbacivalo je gresko zato sto sam zKartonu stavio da ne prima vrednost null,
		 * iako moze postojati pacijent bez zKartona, resio sam tako sto sam if
		 * statement, stavio kada pacijenta povezujem sa kartonom, jer ne mozemo kartonu
		 * dodeliti pacijenta, ako taj karton ne postoji*/
		this.zKarton = zKarton;
		if (zKarton != null) {
			zKarton.pacijent = this;
		}
	}


	public ZdravstveniKarton getzKarton() {
		return zKarton;
	}


	public void setzKarton(ZdravstveniKarton zKarton) {
		this.zKarton = zKarton;
	}
	
//	private ArrayList<String> ucitajTerapije(){
//		ArrayList<String> terapije = new ArrayList<String>();
//		for (Termin t: zKarton.getTermini()) {
//			String tr = t.getOpisTerapije();
//			terapije.add(tr);
//		}
//		return terapije;
//	}
//	
//	public ArrayList<String> getTerapije() {
//		return ucitajTerapije();
//	}
//	
	public String toString() {
		return "Pacijent [id=" + id + ", korisnickoIme=" + korisnickoIme
				+ ", lozinka=" + lozinka + ", ime=" + ime + ", prezime=" + prezime + ", JMBG=" + JMBG + ", pol=" + pol
				+ ", adresa=" + adresa + ", broj=" + brojTelefona + ", uloga=" + uloga + ", datumRegistracije="
				+ datumRegistracije + "]";
	}
	

}
