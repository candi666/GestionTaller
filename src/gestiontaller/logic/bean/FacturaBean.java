package gestiontaller.logic.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "factura")
public class FacturaBean {

    private final SimpleIntegerProperty id;
    private SimpleObjectProperty<Date> fecha;
    private SimpleObjectProperty<Date> fechavenc;
    private final SimpleDoubleProperty total;
    private final SimpleBooleanProperty pagada;
    private final SimpleObjectProperty<ReparacionBean> reparacion;
    private final SimpleObjectProperty<ClienteBean> cliente;

    
    public FacturaBean(){
        this.id = new SimpleIntegerProperty();
        this.fecha = new SimpleObjectProperty<Date>();
        this.fechavenc = new SimpleObjectProperty<Date>();
        this.total = new SimpleDoubleProperty();
        this.pagada = new SimpleBooleanProperty();
        this.reparacion = new SimpleObjectProperty<ReparacionBean>();
        this.cliente = new SimpleObjectProperty<ClienteBean>();
    }
    /**
     * Constructor
     *
     * @param id id=0 reservado para modificaciones.
     * @param fecha fecha
     * @param fechavenc fecha de vencimiento
     * @param total total
     * @param pagada pagada
     * @param idreparacion id de reparación asociada
     * @param idcliente id de cliente asociado
     */
    public FacturaBean(Integer id, Date fecha, Date fechavenc, Double total,
            Boolean pagada, ReparacionBean reparacion, ClienteBean cliente) {
        this.id = new SimpleIntegerProperty(id);
        this.fecha = new SimpleObjectProperty<Date>(fecha);
        this.fechavenc = new SimpleObjectProperty<Date>(fechavenc);
        this.total = new SimpleDoubleProperty(total);
        this.pagada = new SimpleBooleanProperty(pagada);
        this.reparacion = new SimpleObjectProperty<ReparacionBean>(reparacion);
        this.cliente = new SimpleObjectProperty<ClienteBean>(cliente);
    }

    /* **** GETTERS & SETTERS **** */
    public Integer getId() {
        return id.get();
    }

    public void setId(Integer id) {
        this.id.set(id);
    }

    public Date getFecha() {
        return fecha.get();
    }

    public void setFecha(Date fecha) {
        this.fecha.set(fecha);
    }

    public Date getFechavenc() {
        return fechavenc.get();
    }

    public void setFechavenc(Date fechavenc) {
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

    @XmlElement(name="reparacion")
    public ReparacionBean getReparacion() {
        return reparacion.get();
    }

    public void setReparacion(ReparacionBean reparacion) {
        this.reparacion.set(reparacion);
    }

    @XmlElement(name="cliente")
    public ClienteBean getCliente() {
        return cliente.get();
    }

    public void setCliente(ClienteBean cliente) {
        this.cliente.set(cliente);
    }
    

}
