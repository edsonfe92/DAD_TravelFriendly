package com.example.demo;
import java.util.Date;

//@Entity
public class Travel{
	//@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String origen;
	private String destino;
	private String conductor;
	private String[] pasajeros;
	private Date fecha;
	
	public Travel() {
		
	}
	
	public Travel(String o, String d, String c, String[] p, Date f) {
		super();
		origen=o;
		destino=d;
		for(int i=0;i<p.length;i++) {
			pasajeros[i]=p[i];
		}
		fecha=f;
	}
	
	//@RestController
	
	
	public Long getId() {
		return id;
	}
	public String getOrigen() {
		return origen;
	}
	public String getDestino() {
		return destino;
	}
	public String getConductor() {
		return conductor;
	}
	public String[] getPasajeros() {
		return pasajeros;
	}
	public Date getFecha() {
		return fecha;
	}
	
	public void setId(Long id) {
		this.id=id;
	}
	public void setOrigen(String origen) {
		this.origen=origen;
	}
	public void setDestino(String destino) {
		this.destino=destino;
	}
	public void setConductor(String conductor) {
		this.conductor=conductor;
	}
	public void setPasajeros(String[] pasajeros) {
		this.pasajeros=pasajeros;
	}
	public void setFecha(Date fecha) {
		this.fecha=fecha;
	}
	
	
	
	
}