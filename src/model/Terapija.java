package model;

public class Terapija {
	protected int id;
	protected String opisTerapije;
	protected Lekar lekar;
	protected ZdravstveniKarton zKarton;
	
	public Terapija(int id, String opisTerapije, Lekar lekar, ZdravstveniKarton zKarton) {
		super();
		this.id = id;
		this.opisTerapije = opisTerapije;
		this.lekar = lekar;
		this.zKarton = zKarton;
		
		if (zKarton != null) {
			zKarton.getTerapije().add(this);
		}
	}
	
	public int getId() {
		return id;
	}
	
	public String getOpisTerapije() {
		return opisTerapije;
	}
	public void setOpisTerapije(String opisTerapije) {
		this.opisTerapije = opisTerapije;
	}
	public Lekar getLekar() {
		return lekar;
	}
	public void setLekar(Lekar lekar) {
		this.lekar = lekar;
	}
	public ZdravstveniKarton getzKarton() {
		return zKarton;
	}
	public void setzKarton(ZdravstveniKarton zKarton) {
		this.zKarton = zKarton;
	}
	
	
}
