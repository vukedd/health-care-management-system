package model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DomZdravlja {

	private static final String DATE_TIME_FORMAT = "dd.MM.yyyy. HH:mm";
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
	
	private static final String DATA_FOLDER = "data";
	private static final Path KORISNICI_PUTANJA = Paths.get(DATA_FOLDER, "korisnici.csv");
	private static final Path ZDRAVSTVENI_KARTONI_PUTANJA = Paths.get(DATA_FOLDER, "zdravstveniKartoni.csv");
	private static final Path TERMINI_PUTANJA = Paths.get(DATA_FOLDER, "zdravstveniKartoniTermini.csv");
	private static final Path TERAPIJE_PUTANJA = Paths.get(DATA_FOLDER, "terapije.csv");
	
	private static final List<Korisnik> korisnici = new ArrayList<Korisnik>();
	private static final List<ZdravstveniKarton> zdravstveniKartoni = new ArrayList<ZdravstveniKarton>();
	private static final List<Termin> termini = new ArrayList<Termin>();
	private static final List<Terapija> terapije = new ArrayList<Terapija>();
	
	public static void ucitaj() {
		//Ucitavamo prvo korisnike, a zatim zdravstveneKartone
		//zato sto zKarton ne moze da postoji bez korisnika, te ako nema korisnik nema ni zKarton
		try {
			ucitajKorisnike();
			ucitajZdravstveneKartone();
			ucitajTermine();
			ucitajTerapije();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//Ucitavamo korisnika iz text File-a u Listu
	public static ArrayList<String> procitajKorisnike() throws IOException {
		ArrayList<String> korisnici = new ArrayList();
		
		for (String linija : Files.readAllLines(KORISNICI_PUTANJA)) {
			korisnici.add(linija);
		}
		return korisnici;
	}
	
	public static ArrayList<String> procitajTermine() throws IOException {
		ArrayList<String> termini = new ArrayList();
		
		for (String linija : Files.readAllLines(TERMINI_PUTANJA)) {
			termini.add(linija);
		}
		return termini;
	}
	
	public static ArrayList<String> procitajTerapije() throws IOException {
		ArrayList<String> terapije = new ArrayList();
		
		for (String linija : Files.readAllLines(TERAPIJE_PUTANJA)) {
			terapije.add(linija);
		}
		return terapije;
	}
	
	public static ArrayList<String> procitajKartone() throws IOException {
		ArrayList<String> kartoni = new ArrayList();
		
		for (String linija : Files.readAllLines(ZDRAVSTVENI_KARTONI_PUTANJA)) {
			kartoni.add(linija);
		}
		return kartoni;
	}
	 
	public static void ucitajKorisnike() throws IOException {
		for (String linija : Files.readAllLines(KORISNICI_PUTANJA)) {
				String[] info = linija.split(",");
				int id = Integer.parseInt(info[0]);
				String korisnickoIme = info[1];
				String lozinka = info[2];
				String ime = info[3];
				String prezime = info[4];
				String jMBG = info[5];
				Pol pol = Pol.values()[Integer.parseInt(info[6])];
				String adresa = info[7];
				String brojTelefona = info[8];
				Uloga uloga = Uloga.values()[Integer.parseInt(info[9])];
				LocalDateTime datumRegistracije = LocalDateTime.parse(info[10], DATE_TIME_FORMATTER);
				boolean aktivan = false;
				if (info[11].compareTo("0") == 0) {
					aktivan = true;
				} else {
					aktivan = false;
				}
				
					if (uloga.compareTo(Uloga.ADMIN) == 0) {
						Admin admin = new Admin(id, korisnickoIme, lozinka, ime, prezime, jMBG, pol, adresa, brojTelefona, uloga, datumRegistracije,aktivan);
						korisnici.add(admin);		
					}
					else if (uloga.compareTo(Uloga.PACIJENT) == 0) {
						Pacijent pacijent = new Pacijent(id, korisnickoIme, lozinka, ime, prezime, jMBG, pol, adresa, brojTelefona, uloga, datumRegistracije, null,aktivan);
						korisnici.add(pacijent);
					}	
					else if (uloga.compareTo(Uloga.LEKAR) == 0) {
						Lekar lekar = new Lekar(id, korisnickoIme, lozinka, ime, prezime, jMBG, pol, adresa, brojTelefona, uloga, datumRegistracije,aktivan);
						korisnici.add(lekar);
					}
			}
		
	}
	
	// Ucitavamo zdravstvene kartone iz text File-a u Listu
	public static void ucitajZdravstveneKartone() throws IOException{
		for (String linija : Files.readAllLines(ZDRAVSTVENI_KARTONI_PUTANJA)) {
				String info[] = linija.split(",");
				
				int idKartona = Integer.parseInt(info[0]);
				int idPacijenta = Integer.parseInt(info[1]);
				boolean aktivan = false;
				if (info[2].compareTo("0") == 0) {
					aktivan = true;
				} else {
					aktivan = false;
				}
			
				for (Korisnik k: korisnici) {
					if (k.getId() == idPacijenta) {
						Pacijent p = (Pacijent) k;
						ZdravstveniKarton zKarton = new ZdravstveniKarton(idKartona, p, aktivan);
						zdravstveniKartoni.add(zKarton);
					}
				}
			
		}
	}
	
	// Termine razlikujemo po id-ju lekara i po statusu
	// Ucitavamo termine iz txt file-a u aplikaciju
	public static void ucitajTermine() throws IOException{
		for (String linija : Files.readAllLines(TERMINI_PUTANJA)) {
			String[] info = linija.split(",");
			
			int idTermina = Integer.parseInt(info[0]);
			int idPacijenta = -1;
			if (info[1].compareTo(" ") != 0) {
				idPacijenta = Integer.parseInt(info[1]);
			}
			
			int idLekara = Integer.parseInt(info[2]);
			Status statusTermina = Status.values()[Integer.parseInt(info[3])];
			LocalDateTime datumPregleda = LocalDateTime.parse(info[4], DATE_TIME_FORMATTER);
			
			Lekar lekar = null;
			Pacijent pacijent = null;
			
			if (idPacijenta == -1) {
				for (Korisnik k : korisnici) {
					if (k.getId() == idLekara) {
						lekar = (Lekar)k;
						Termin termin = new Termin(idTermina, lekar, statusTermina, datumPregleda);
						termini.add(termin);
						
						lekar = null;
						pacijent = null;
						break;
					} 
				}
			}
			else {
				for (Korisnik k : korisnici) {
					if (k.getId() == idLekara) {
						lekar = (Lekar)k;
					}
					if (k.getId() == idPacijenta) {
						pacijent = (Pacijent)k;
					}
					if (lekar != null && pacijent != null) {
						Termin termin = new Termin(idTermina, pacijent, lekar, statusTermina, datumPregleda);
						termini.add(termin);
						pacijent.getzKarton().addTermin(termin);
						
						lekar = null;
						pacijent = null;
						break;
					}
				}
			}
				
		}
			
	}
	
	public static void ucitajTerapije() throws IOException{
		for (String linija : Files.readAllLines(TERAPIJE_PUTANJA)) {
			String info[] = linija.split(",");
			
			int idTerapije = Integer.parseInt(info[0]);
			int idLekara = Integer.parseInt(info[1]);
			int idKartona = Integer.parseInt(info[2]);
			String opisTerapije = info[3];
			
			Lekar l = null;
			for (Korisnik k: korisnici) {
				if (k.getId() == idLekara) {
					l = new Lekar((Lekar)k);
				}
			}
			
			ZdravstveniKarton zk = null;
			for (ZdravstveniKarton z: zdravstveniKartoni) {
				if (z.getId() == idKartona) {
					zk = z;
				}
			}
			
			Terapija t = new Terapija(idTerapije,opisTerapije, l, zk);
			terapije.add(t);
		}
	}
	
	public static void sacuvaj() {
		try {
			sacuvajKorisnike();
			sacuvajZdravstveneKartone();
			sacuvajTermine();
			sacuvajTerapije();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	// metoda koja ce svakog korisnika iz aplikacije sacuvati u text file
	private static void sacuvajKorisnike() throws IOException {
		List<String> linije = new ArrayList<String>();
		for (Korisnik k: korisnici) {
			String aktivan = "";
			if (k.getAktivan() == true) {
				aktivan = "0";
			} else {
				aktivan = "1";
			}
			String linija = String.join(",", String.valueOf(k.getId()),	
									  k.getKorisnickoIme(),
									  k.getLozinka(),
									  k.getIme(),
									  k.getPrezime(),
									  k.getJMBG(),
									  String.valueOf(k.getPol().ordinal()),
									  k.getAdresa(),
									  k.getBrojTelefona(),
									  String.valueOf(k.getUloga().ordinal()),
									  DATE_TIME_FORMATTER.format(k.getDatumRegistracije()),
									  aktivan);
			linije.add(linija);
		}
		Files.write(KORISNICI_PUTANJA, linije);
	}

	// metoda koja ce svaki Zdravstveni karton iz aplikacije sacuvati u text file
	private static void sacuvajZdravstveneKartone() throws IOException{
		List<String> linije = new ArrayList<String>();
		for (ZdravstveniKarton zKarton : zdravstveniKartoni) {
			String aktivan = "";
			if (zKarton.getAktivan() == true) {
				aktivan = "0";
			} else {
				aktivan = "1";
			}
			String linija = String.join(",", String.valueOf(zKarton.getId()),
											 String.valueOf(zKarton.getPacijent().getId()),
											 aktivan);
			linije.add(linija);
		}
		Files.write(ZDRAVSTVENI_KARTONI_PUTANJA, linije);
	}
	
	// metoda koja ce svaki Termin iz aplikacije sacuvati u text file
	private static void sacuvajTermine() throws IOException {
		List<String> linije = new ArrayList<String>();
		for (Termin t : termini) {
			String linija = null;
			if (t.getPacijent() == null) {
				linija = String.join(",", String.valueOf(t.getId()),
						 " ",
						 String.valueOf(t.getLekar().getId()),
						 String.valueOf(t.getStatus().ordinal()),
						 DATE_TIME_FORMATTER.format(t.getDatumTermina()));
						 linije.add(linija);
			} else {
				linija = String.join(",", String.valueOf(t.getId()),
						 String.valueOf(String.valueOf(t.getPacijent().getId())),
						 String.valueOf(t.getLekar().getId()),
						 String.valueOf(t.getStatus().ordinal()),
						 DATE_TIME_FORMATTER.format(t.getDatumTermina()));
						 linije.add(linija);
			}
		}
		Files.write(TERMINI_PUTANJA, linije);
	}
	
	
	private static void sacuvajTerapije() throws IOException {
		List<String> linije = new ArrayList<String>();
		for (Terapija t : terapije) {
			String linija = String.join(",", String.valueOf(t.getId()),
											 String.valueOf(t.getLekar().getId()),
											 String.valueOf(t.getzKarton().getId()),
											 t.getOpisTerapije());
			linije.add(linija);
		}
		Files.write(TERAPIJE_PUTANJA, linije);
	}
	
	public static List<Korisnik> getKorisnici() {
		return korisnici;
	}
	
	public static List<ZdravstveniKarton> getZdravstveniKartoni() {
		return zdravstveniKartoni;
	}
	
	public static List<Termin> getTermini() {
		return termini;
	}
	
	public static List<Terapija> getTerapije() {
		return terapije;
	}	
	
	public static Korisnik getKorisnik(String korisnickoIme) {
		for (Korisnik k: korisnici) {
			if (k.getKorisnickoIme().compareTo(korisnickoIme) == 0) {
				return k;
			}
		}
		return null;
	}
	
	public static boolean korisnickoImeCheck(String korisnickoIme) {
		for (Korisnik k: korisnici) {
			if (k.getKorisnickoIme().compareTo(korisnickoIme) == 0) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean validirajLogIn(String kIme, String lozinka) {
		for (Korisnik k: korisnici) {
			if (k.getKorisnickoIme().equals(kIme) && k.getLozinka().equals(lozinka) && k.getAktivan() == true) {
				ulogovanKao(k);
				return true;
			}
		}
		return false;
	}

	private static void ulogovanKao(Korisnik k) {
		
	}
	
	
}
