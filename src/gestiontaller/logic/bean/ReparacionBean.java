package gestiontaller.logic.bean;

import java.util.Collection;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;
import javafx.collections.ObservableList;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement(name="reparacion")
public class ReparacionBean {
    private final SimpleIntegerProperty id;
    private SimpleObjectProperty<Date> fechainicio; 
    private SimpleObjectProperty<Date> fechafin;
    private final SimpleStringProperty coche; 
    private final SimpleStringProperty descripcion;
    private final SimpleObjectProperty<ClienteBean> cliente;
    private final SimpleListProperty<PiezaBean> piezas;
    
    public ReparacionBean(){
        this.id=new SimpleIntegerProperty();
        this.fechainicio = new SimpleObjectProperty<Date>();
        this.fechafin = new SimpleObjectProperty<Date>();
        this.coche=new SimpleStringProperty();
        this.descripcion=new SimpleStringProperty();
        this.cliente = new SimpleObjectProperty<ClienteBean>();
        this.piezas = new SimpleListProperty<PiezaBean>();
    }
    
    public ReparacionBean(Integer id, Date fechainicio, Date fechafin, String coche, 
            String descripcion, ClienteBean cliente, ObservableList<PiezaBean> piezas){
        this.id=new SimpleIntegerProperty(id);
        this.fechainicio = new SimpleObjectProperty<Date>(fechainicio);
        this.fechafin = new SimpleObjectProperty<Date>(fechafin);
        this.coche=new SimpleStringProperty(coche);
        this.descripcion=new SimpleStringProperty(descripcion);
        this.cliente = new SimpleObjectProperty<ClienteBean>(cliente);
        this.piezas = new SimpleListProperty<PiezaBean>(piezas);
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
    public void setDescripcion(String descripcion){
        this.descripcion.set(descripcion);
    }
    public String getCoche(){
        return this.coche.get();
    }
    public void setCoche(String coche){
        this.coche.set(coche);
    }

    public Date getFechainicio() {
        return fechainicio.get();
    }
    
    public void setFechainicio(Date fechainicio){
        this.fechainicio.set(fechainicio);
    }

    public Date getFechafin() {
        return fechafin.get();
    }

    public void setFechafin(Date fechafin){
        this.fechafin.set(fechafin);
    }
    
    @XmlElement(name="cliente")
    public ClienteBean getCliente() {
        return cliente.get();
    }
    
    public void setCliente(ClienteBean cliente){
        this.cliente.set(cliente);
    }

    @XmlElement(name="piezas")
    public List<PiezaBean> getPiezas() {
        return piezas.get();
    }
    
    public void setPiezas(ObservableList<PiezaBean> piezas){
        this.piezas.set(piezas);
    }
    
    
    @Override
    public String toString(){
        return this.getId().toString();
    }
}
