package gestiontaller.logic.javaBean;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ClienteBean {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty dni;
    private final SimpleStringProperty nombre;
    private final SimpleStringProperty apellidos;
    private final SimpleStringProperty email;
    private final SimpleIntegerProperty telefono;
    
    public ClienteBean(Integer id, String dni, String nombre, String apellidos, String email, Integer telefono){
        this.id=new SimpleIntegerProperty(id);
        this.dni=new SimpleStringProperty(dni);
        this.nombre=new SimpleStringProperty(nombre);
        this.apellidos=new SimpleStringProperty(apellidos);
        this.email=new SimpleStringProperty(email);
        this.telefono=new SimpleIntegerProperty(telefono);
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
    public Integer getTelefono(){
        return this.telefono.get();
    }
    public void setTelefono(Integer telefono){
        this.telefono.set(telefono);
    }
}
