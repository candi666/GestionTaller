/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiontaller.logic.controller;

import gestiontaller.logic.bean.PiezaBean;
import gestiontaller.logic.interfaces.PiezasManager;
import gestiontaller.rest.PiezaRESTClient;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;

/**
 * PiezasManager impementation
 * @author Carlos
 */
public class PiezasManagerImp implements PiezasManager{
    private static final Logger logger = Logger.getLogger(FacturasManagerImp.class.getName());
    private PiezaRESTClient webClient;
    
    public PiezasManagerImp(){
        webClient = new PiezaRESTClient();
    }

    @Override
    public Collection getAllPiezas() {
        logger.info("PiezasManager: Finding all piezas from REST service (XML).");
        List<PiezaBean> piezas = webClient.findAll_XML(new GenericType<List<PiezaBean>>() {});
        return piezas;
    }

    @Override
    public PiezaBean getPiezaById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean createPieza(PiezaBean factura) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updatePieza(PiezaBean factura) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletePieza(PiezaBean factura) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
