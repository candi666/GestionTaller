package gestiontaller.logic.bean;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="cliente")
public class ClienteBean {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty dni; 
    private final SimpleStringProperty nombre;
    private final SimpleStringProperty apellidos;
    private final SimpleStringProperty email;
    private final SimpleStringProperty telefono;
    
    /**
     * Contructor
     * 
     * @param id
     * @param dni
     * @param nombre
     * @param apellidos
     * @param email
     * @param telefono 
     */
    public ClienteBean(Integer id, String dni, String nombre, String apellidos, String email, String telefono){
        this.id=new SimpleIntegerProperty(id);
        this.dni=new SimpleStringProperty(dni);
        this.nombre=new SimpleStringProperty(nombre);
        this.apellidos=new SimpleStringProperty(apellidos);
        this.email=new SimpleStringProperty(email);
        this.telefono=new SimpleStringProperty(telefono);
    }
    
    public ClienteBean(){
        this.id=new SimpleIntegerProperty(0);
        this.dni=new SimpleStringProperty(null);
        this.nombre=new SimpleStringProperty(null);
        this.apellidos=new SimpleStringProperty(null);
        this.email=new SimpleStringProperty(null);
        this.telefono=new SimpleStringProperty(null);
    }

    public Integer getId(){
        return this.id.get();
    }
    public void setId(Integer id){
        this.id.set(id);
    }
    public String getDni(){
        return this.dni.get();
    }
    public void setDni(String dni){
        this.dni.set(dni);
    }
    public String getNombre(){
        return this.nombre.get();
    }
    public void setNombre(String nombre){
        this.nombre.set(nombre);
    }
    public String getApellidos(){
        return this.apellidos.get();
    }
    public void setApellidos(String apellidos){
        this.apellidos.set(apellidos);
    }
    public String getEmail(){
        return this.email.get();
    }
    public void setEmail(String email){
        this.email.set(email);
    }
    public String getTelefono(){
        return this.telefono.get();
    }
    public void setTelefono(String telefono){
        this.telefono.set(telefono);
    }
    
    @Override
    public String toString(){
        return this.getId().toString();
    }
}
