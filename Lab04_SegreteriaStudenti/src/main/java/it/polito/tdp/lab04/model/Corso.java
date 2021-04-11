package it.polito.tdp.lab04.model;

import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Corso {
	private String codis;
	private Integer crediti;
	private String nome;
	private Integer pd;
	
	
	public Corso(String codis, Integer crediti, String nome, Integer pd) {
		super();
		this.codis = codis;
		this.crediti = crediti;
		this.nome = nome;
		this.pd = pd;
	}
	
	public String getCodis() {
		return codis;
	}
	public void setCodis(String codis) {
		this.codis = codis;
	}
	public Integer getCrediti() {
		return crediti;
	}
	public void setCrediti(Integer crediti) {
		this.crediti = crediti;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getPd() {
		return pd;
	}
	public void setPd(Integer pd) {
		this.pd = pd;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codis == null) ? 0 : codis.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Corso other = (Corso) obj;
		if (codis == null) {
			if (other.codis != null)
				return false;
		} else if (!codis.equals(other.codis))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "" + codis + " " + crediti + " "+ nome + " "+ pd;
	}
}
