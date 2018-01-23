/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiontaller.logic.controller;

import gestiontaller.logic.bean.FacturaBean;
import gestiontaller.logic.interfaces.FacturasManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;
import rest.FacturaRESTClient;

/**
 * Facturas manager implementation
 * @author Carlos
 */
public class FacturasManagerImp implements FacturasManager{
    private FacturaRESTClient webClient;
    private static final Logger logger = Logger.getLogger(FacturasManagerImp.class.getName());
    
    @Override
    public Collection getAllFacturas() {
        List<FacturaBean> facturas = new ArrayList<>();
        
        try{
            facturas = webClient.findAll_XML(new GenericType<List<FacturaBean>>() {});
        }catch(Exception ex){
            logger.severe("Couldn't retrieve facturas data.");
            ex.printStackTrace();
        }
        
        return facturas;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FacturaBean getFacturaById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FacturaBean getFacturaByReparacion(int idreparacion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection getFacturasByDate(LocalDate fromDate, LocalDate toDate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection getFacturasByCliente(int idcliente, LocalDate fromDate, LocalDate toDate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean createFactura(FacturaBean factura) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateFactura(FacturaBean factura) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteFactura(FacturaBean factura) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
