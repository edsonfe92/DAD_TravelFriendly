package model;
import java.util.List;
import javax.persistence.*;


@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id; 
	
	private String name;
	private String usuario;
	private String contraseña;
	private List<Integer> valoraciones;
	
	public User(String n, String u, String c) {
		name = n;
		usuario=u;
		contraseña=c;
	}
	public User(String n, String c){
		name = n;
		contraseña=c;
		
	}
	
	public void setName(String n) {
		name =n;
		
	}
	public String getName() {
		return name;
	}
	public void setContraseña(String n) {
		contraseña = n ;
		
	}
	public String getContraseña() {
		return contraseña;
	}
	
	
}
