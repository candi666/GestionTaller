package gestiontaller.logic.interfaces;

import gestiontaller.logic.bean.FacturaBean;
import java.time.LocalDate;
import java.util.Collection;

/**
 * Interfaz para gestión de facturas.
 * @author Carlos
 */
public interface FacturasManager {
    public Collection getAllFacturas();
    public FacturaBean getFacturaById(Integer id);
    public FacturaBean getFacturaByReparacion(Integer idreparacion);
    public Collection getFacturasByDate(LocalDate fromDate, LocalDate toDate);
    public Collection getFacturasByCliente(Integer idcliente, LocalDate fromDate, LocalDate toDate);
    public boolean createFactura(FacturaBean factura);
    public boolean updateFactura(FacturaBean factura);
    public boolean deleteFactura(FacturaBean factura);
    
}
