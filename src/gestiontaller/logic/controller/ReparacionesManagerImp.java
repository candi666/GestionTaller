package gestiontaller.logic.controller;

import gestiontaller.logic.bean.ReparacionBean;
import gestiontaller.logic.interfaces.ReparacionesManager;
import gestiontaller.rest.ReparacionRESTClient;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;

/**
 * Reparaciones management implementation
 * @author Carlos
 */
public class ReparacionesManagerImp implements ReparacionesManager{
    private static final Logger logger = Logger.getLogger(FacturasManagerImp.class.getName());
    private ReparacionRESTClient webClient;
    
    public ReparacionesManagerImp(){
        webClient = new ReparacionRESTClient();
    }
    
    @Override
    public Collection getAllReparaciones() {
        logger.info("ReparacionesManager: Finding all reparaciones from REST service (XML).");
        List<ReparacionBean> reparaciones = webClient.findAll_XML(new GenericType<List<ReparacionBean>>() {});
        return reparaciones;
    }

    @Override
    public ReparacionBean getReparacionById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean createReparacion(ReparacionBean reparacion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateReparacion(ReparacionBean reparacion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteReparacion(ReparacionBean reparacion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
