/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    public FacturaBean getFacturaById(String id);
    public FacturaBean getFacturaByReparacion(String idreparacion);
    public Collection getFacturasByDate(LocalDate fromDate, LocalDate toDate);
    public Collection getFacturasByCliente(String idcliente, LocalDate fromDate, LocalDate toDate);
    public boolean createFactura(FacturaBean factura);
    public boolean updateFactura(FacturaBean factura);
    public boolean deleteFactura(FacturaBean factura);
    
}
