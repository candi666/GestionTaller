package gestiontaller.logic.bean;

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
    
    /**
     * 
     * @param id id=0 reservado para modificaciones.
     * @param fecha
     * @param fechavenc
     * @param total
     * @param pagada
     * @param idreparacion
     * @param idcliente 
     */
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
    
    public FacturaBean(String fecha, String fechavenc, Double total,
            Boolean pagada, Integer idreparacion, Integer idcliente){
        this.id=null;
        this.fecha=new SimpleStringProperty(fecha);
        this.fechavenc=new SimpleStringProperty(fechavenc);
        this.total=new SimpleDoubleProperty(total);
        this.pagada=new SimpleBooleanProperty(pagada);
        this.idreparacion=new SimpleIntegerProperty(idreparacion);
        this.idcliente=new SimpleIntegerProperty(idcliente); 
    }
    

    /* **** GETTERS & SETTERS **** */

    public Integer getId() {
        return id.get();
    }

    public void setId(Integer id) {
        this.id.set(id);
    }

    public String getFecha() {
        return fecha.get();
    }

    public void setFecha(String fecha) {
        this.fecha.set(fecha);
    }

    public String getFechavenc() {
        return fechavenc.get();
    }

    public void setFechavenc(String fechavenc) {
        this.fechavenc.set(fechavenc);
    }

    public Double getTotal() {
        return total.get();
    }

    public void setTotal(Double total) {
        this.total.set(total);
    }

    public Boolean getPagada() {
        return pagada.get();
    }

    public void setPagada(Boolean pagada) {
        this.pagada.set(pagada);
    }

    public Integer getIdreparacion() {
        return idreparacion.get();
    }

    public void setIdreparacion(Integer idreparacion) {
        this.idreparacion.set(idreparacion);
    }

    public Integer getIdcliente() {
        return idcliente.get();
    }

    public void setIdcliente(Integer idcliente) {
        this.idcliente.set(idcliente);
    }
    
    

    
}
