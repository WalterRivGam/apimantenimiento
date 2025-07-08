package pe.edu.uni.apiordentrabajo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(OrdenTrabajoCorrelativoId.class)
public class OrdenTrabajoCorrelativo {
	@Id
	private int anio;

	@Id
	private String tallerCodigo;

	private int ultimoNumero;

	public OrdenTrabajoCorrelativo() {
	}

	public OrdenTrabajoCorrelativo(int anio, String tallerCodigo, int ultimoNumero) {
		this.anio = anio;
		this.tallerCodigo = tallerCodigo;
		this.ultimoNumero = ultimoNumero;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public String getTallerCodigo() {
		return tallerCodigo;
	}

	public void setTallerCodigo(String tallerCodigo) {
		this.tallerCodigo = tallerCodigo;
	}

	public int getUltimoNumero() {
		return ultimoNumero;
	}

	public void setUltimoNumero(int ultimoNumero) {
		this.ultimoNumero = ultimoNumero;
	}

	@Override
	public String toString() {
		return "OrdenTrabajoCorrelativo [anio=" + anio + ", tallerCodigo=" + tallerCodigo + ", ultimoNumero="
				+ ultimoNumero + "]";
	}
}
