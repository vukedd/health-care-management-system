package model;

import java.time.LocalDateTime;

public class Termin {
	
	protected int id;
	protected Pacijent pacijent;
	protected Lekar lekar;
	protected Status status;
	protected LocalDateTime datumTermina;

	public Termin(int id, Pacijent pacijent, Lekar lekar, Status status, LocalDateTime datumTermina) {
		this.id = id;
		this.pacijent = pacijent;
		this.lekar = lekar;
		
		if (lekar != null) {
			lekar.zakazaniTermini.add(this);
		}
		this.status = status;
		this.datumTermina = datumTermina;
	}
	
	public Termin(int id, Lekar lekar, Status status, LocalDateTime datumTermina) {
		this.id = id;
		this.lekar = lekar;
		this.pacijent = null;
		
		if (lekar != null) {
			lekar.zakazaniTermini.add(this);
		}
		this.status = status;
		this.datumTermina = datumTermina;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}

	public Lekar getLekar() {
		return lekar;
	}

	public void setLekar(Lekar lekar) {
		this.lekar = lekar;
	}


	public Pacijent getPacijent() {
		return pacijent;
	}

	public void setPacijent(Pacijent pacijent) {
		if (pacijent != null) {
			this.pacijent = pacijent;
			pacijent.zKarton.getTermini().add(this);
		}
		else {
			if (this.pacijent != null) {
				this.pacijent.zKarton.getTermini().remove(this);
				this.pacijent = null;
			} else {
			this.pacijent = null;
			}
		}
	}

	public void setDatumTermina(LocalDateTime datumTermina) {
		this.datumTermina = datumTermina;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public LocalDateTime getDatumTermina() {
		return datumTermina;
	}

	public void setF(LocalDateTime datumTermina) {
		this.datumTermina = datumTermina;
	}
	
	public boolean equals(Termin t) {
		if (t.getId() == this.getId()) {
			return true;
		}
		
		return false;
		
	}

	@Override
	public String toString() {
		return "Termin [id=" + id + ", lekar=" + lekar + ", status=" + status
				+ ", datumTermina=" + datumTermina + "]";
	}

	
	
	
}
