package gestiontaller.logic.bean;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos
 */
@XmlRootElement(name="pieza")
public class PiezaBean {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty descripcion; 
    private final SimpleStringProperty fabricante;
    
    /**
     *
     */
    public PiezaBean(){
        this.id=new SimpleIntegerProperty();
        this.descripcion=new SimpleStringProperty();
        this.fabricante=new SimpleStringProperty();
    }
    
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
    public String getDescripcion(){
        return this.descripcion.get();
    }

    /**
     *
     * @param dni
     */
    public void setDescripcion(String dni){
        this.descripcion.set(dni);
    }

    /**
     *
     * @return
     */
    public String getFabricante(){
        return this.descripcion.get();
    }

    /**
     *
     * @param dni
     */
    public void setFabricante(String dni){
        this.descripcion.set(dni);
    }
    
    @Override
    public String toString(){
        return this.getId().toString();
    }
}
