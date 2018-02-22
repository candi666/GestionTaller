package gestiontaller.logic.bean;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos
 */
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
    
    /**
     *
     */
    public ClienteBean(){
        this.id=new SimpleIntegerProperty(0);
        this.dni=new SimpleStringProperty(null);
        this.nombre=new SimpleStringProperty(null);
        this.apellidos=new SimpleStringProperty(null);
        this.email=new SimpleStringProperty(null);
        this.telefono=new SimpleStringProperty(null);
    }

    /**
     *
     * @return
     */
    public Integer getId(){
        return this.id.get();
    }

    /**
     *
     * @param id
     */
    public void setId(Integer id){
        this.id.set(id);
    }

    /**
     *
     * @return
     */
    public String getDni(){
        return this.dni.get();
    }

    /**
     *
     * @param dni
     */
    public void setDni(String dni){
        this.dni.set(dni);
    }

    /**
     *
     * @return
     */
    public String getNombre(){
        return this.nombre.get();
    }

    /**
     *
     * @param nombre
     */
    public void setNombre(String nombre){
        this.nombre.set(nombre);
    }

    /**
     *
     * @return
     */
    public String getApellidos(){
        return this.apellidos.get();
    }

    /**
     *
     * @param apellidos
     */
    public void setApellidos(String apellidos){
        this.apellidos.set(apellidos);
    }

    /**
     *
     * @return
     */
    public String getEmail(){
        return this.email.get();
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email){
        this.email.set(email);
    }

    /**
     *
     * @return
     */
    public String getTelefono(){
        return this.telefono.get();
    }

    /**
     *
     * @param telefono
     */
    public void setTelefono(String telefono){
        this.telefono.set(telefono);
    }
    
    @Override
    public String toString(){
        return this.getId().toString();
    }
}
