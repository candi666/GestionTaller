package gestiontaller.logic.bean;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="pieza")
public class PiezaBean {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty descripcion; 
    private final SimpleStringProperty fabricante;
    
    /**
     * Contructor
     * 
     * @param id
     * @param descripcion
     * @param fabricante
     */
    public PiezaBean(Integer id, String descripcion, String fabricante){
        this.id=new SimpleIntegerProperty(id);
        this.descripcion=new SimpleStringProperty(descripcion);
        this.fabricante=new SimpleStringProperty(fabricante);
    }

    public Integer getId(){
        return this.id.get();
    }
    public void setId(Integer id){
        this.id.set(id);
    }
    public String getDescripcion(){
        return this.descripcion.get();
    }
    public void setDescripcion(String dni){
        this.descripcion.set(dni);
    }
    public String getFabricante(){
        return this.descripcion.get();
    }
    public void setFabricante(String dni){
        this.descripcion.set(dni);
    }
}
