package gestiontaller.logic.bean;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos
 */
@XmlRootElement(name = "factura")
public class FacturaBean implements Serializable{

    private final SimpleIntegerProperty id;
    private SimpleObjectProperty<Date> fecha;
    private SimpleObjectProperty<Date> fechavenc;
    private final SimpleDoubleProperty total;
    private final SimpleBooleanProperty pagada;
    private final SimpleObjectProperty<ReparacionBean> reparacion;
    private final SimpleObjectProperty<ClienteBean> cliente;

    /**
     *
     */
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
     * @param reparacion
     * @param cliente
     */
    public FacturaBean(Integer id, Date fecha, Date fechavenc, Double total,
            Boolean pagada, ReparacionBean reparacion, ClienteBean cliente) {
        this.id = id==0 ? new SimpleIntegerProperty() : new SimpleIntegerProperty(id);
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

    /**
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id.set(id);
    }

    /**
     *
     * @return
     */
    public Date getFecha() {
        return fecha.get();
    }

    /**
     *
     * @param fecha
     */
    public void setFecha(Date fecha) {
        this.fecha.set(fecha);
    }

    /**
     *
     * @return
     */
    public Date getFechavenc() {
        return fechavenc.get();
    }

    /**
     *
     * @param fechavenc
     */
    public void setFechavenc(Date fechavenc) {
        this.fechavenc.set(fechavenc);
    }

    /**
     *
     * @return
     */
    public Double getTotal() {
        return total.get();
    }

    /**
     *
     * @param total
     */
    public void setTotal(Double total) {
        this.total.set(total);
    }

    /**
     *
     * @return
     */
    public Boolean getPagada() {
        return pagada.get();
    }

    /**
     *
     * @param pagada
     */
    public void setPagada(Boolean pagada) {
        this.pagada.set(pagada);
    }

    /**
     *
     * @return
     */
    @XmlElement(name="reparacion")
    public ReparacionBean getReparacion() {
        return reparacion.get();
    }

    /**
     *
     * @param reparacion
     */
    public void setReparacion(ReparacionBean reparacion) {
        this.reparacion.set(reparacion);
    }

    /**
     *
     * @return
     */
    @XmlElement(name="cliente")
    public ClienteBean getCliente() {
        return cliente.get();
    }

    /**
     *
     * @param cliente
     */
    public void setCliente(ClienteBean cliente) {
        this.cliente.set(cliente);
    }
    

}
