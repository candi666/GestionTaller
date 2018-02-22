package gestiontaller.logic.interfaces;

import gestiontaller.logic.bean.FacturaBean;
import java.time.LocalDate;
import java.util.Collection;

/**
 * Interfaz para gestión de facturas.
 * @author Carlos
 */
public interface FacturasManager {

    /**
     *
     * @return
     */
    public Collection getAllFacturas();

    /**
     *
     * @param id
     * @return
     */
    public FacturaBean getFacturaById(Integer id);

    /**
     *
     * @param idreparacion
     * @return
     */
    public FacturaBean getFacturaByReparacion(Integer idreparacion);

    /**
     *
     * @param fromDate
     * @param toDate
     * @return
     */
    public Collection getFacturasByDate(LocalDate fromDate, LocalDate toDate);

    /**
     *
     * @param idcliente
     * @param fromDate
     * @param toDate
     * @return
     */
    public Collection getFacturasByCliente(Integer idcliente, LocalDate fromDate, LocalDate toDate);

    /**
     *
     * @param factura
     * @return
     */
    public boolean createFactura(FacturaBean factura);

    /**
     *
     * @param factura
     * @return
     */
    public boolean updateFactura(FacturaBean factura);

    /**
     *
     * @param factura
     * @return
     */
    public boolean deleteFactura(FacturaBean factura);
    
}
