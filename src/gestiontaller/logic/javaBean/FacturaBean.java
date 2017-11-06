package gestiontaller.logic.javaBean;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class FacturaBean {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty fecha;
    private final SimpleStringProperty fechavenc;
    private final SimpleDoubleProperty total;
    private final SimpleBooleanProperty pagada;
    private final SimpleIntegerProperty idreparacion;
    private final SimpleIntegerProperty idcliente;
    
    public FacturaBean(Integer id, String fecha, String fechavenc, Double total,
            Boolean pagada, Integer idreparacion, Integer idcliente){
        this.id=new SimpleIntegerProperty(id);
        this.fecha=new SimpleStringProperty(fecha);
        this.fechavenc=new SimpleStringProperty(fechavenc);
        this.total=new SimpleDoubleProperty(total);
        this.pagada=new SimpleBooleanProperty(pagada);
        this.idreparacion=new SimpleIntegerProperty(idreparacion);
        this.idcliente=new SimpleIntegerProperty(idcliente);
        
    }

    /* **** GETTERS & SETTERS **** */

    public SimpleIntegerProperty getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id.set(id);
    }

    public SimpleStringProperty getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha.set(fecha);
    }

    public SimpleStringProperty getFechavenc() {
        return fechavenc;
    }

    public void setFechavenc(String fechavenc) {
        this.fechavenc.set(fechavenc);
    }

    public SimpleDoubleProperty getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total.set(total);
    }

    public SimpleBooleanProperty getPagada() {
        return pagada;
    }

    public void setPagada(Boolean pagada) {
        this.pagada.set(pagada);
    }

    public SimpleIntegerProperty getIdreparacion() {
        return idreparacion;
    }

    public void setIdreparacion(Integer idreparacion) {
        this.idreparacion.set(idreparacion);
    }

    public SimpleIntegerProperty getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Integer idcliente) {
        this.idcliente.set(idcliente);
    }
    
    

    
}
