package gestiontaller.logic.bean;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ClienteBean {
    private final SimpleIntegerProperty tcId;
    private final SimpleStringProperty tcDni;
    private final SimpleStringProperty tcNombre;
    private final SimpleStringProperty tcApellidos;
    private final SimpleStringProperty tcEmail;
    private final SimpleStringProperty tcTelefono;
    
    public ClienteBean(Integer id, String dni, String nombre, String apellidos, String email, String telefono){
        this.tcId=new SimpleIntegerProperty(id);
        this.tcDni=new SimpleStringProperty(dni);
        this.tcNombre=new SimpleStringProperty(nombre);
        this.tcApellidos=new SimpleStringProperty(apellidos);
        this.tcEmail=new SimpleStringProperty(email);
        this.tcTelefono=new SimpleStringProperty(telefono);
    }

    public Integer getId(){
        return this.tcId.get();
    }
    public void setId(Integer id){
        this.tcId.set(id);
    }
    public String getDni(){
        return this.tcDni.get();
    }
    public void setDni(String dni){
        this.tcDni.set(dni);
    }
    public String getNombre(){
        return this.tcNombre.get();
    }
    public void setNombre(String nombre){
        this.tcNombre.set(nombre);
    }
    public String getApellidos(){
        return this.tcApellidos.get();
    }
    public void setApellidos(String apellidos){
        this.tcApellidos.set(apellidos);
    }
    public String getEmail(){
        return this.tcEmail.get();
    }
    public void setEmail(String email){
        this.tcEmail.set(email);
    }
    public String getTelefono(){
        return this.tcTelefono.get();
    }
    public void setTelefono(String telefono){
        this.tcTelefono.set(telefono);
    }
}
