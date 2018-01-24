/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiontaller.logic.controller;

import gestiontaller.config.GTConstants;
import gestiontaller.logic.bean.FacturaBean;
import gestiontaller.logic.interfaces.FacturasManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;
import gestiontaller.rest.FacturaRESTClient;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

/**
 * Facturas manager implementation
 *
 * @author Carlos
 */
public class FacturasManagerImp implements FacturasManager {

    private FacturaRESTClient webClient;
    private static final Logger logger = Logger.getLogger(FacturasManagerImp.class.getName());
    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public FacturasManagerImp() {
        webClient = new FacturaRESTClient();
    }

    @Override
    public Collection getAllFacturas() {
        logger.info("FacturasManager: Finding all facturas from REST service (XML).");
        List<FacturaBean> facturas = webClient.findAll_XML(new GenericType<List<FacturaBean>>() {
        });
        return facturas;
    }

    @Override
    public FacturaBean getFacturaById(Integer id) {
        logger.info("FacturasManager: Finding factura by id {" + id + "} from REST service (XML).");
        return webClient.find_XML(FacturaBean.class, id);
    }

    @Override
    public FacturaBean getFacturaByReparacion(Integer idreparacion) {
        logger.info("FacturasManager: Finding factura by reparacion {" + idreparacion + "} from REST service (XML).");
        return webClient.find_XML(FacturaBean.class, idreparacion);
    }

    /**
     * Get facturas between dates. Converts LocalDate to String in format
     * yyyy-MM-dd and then creates the request.
     *
     * @param fromDate LocalDate
     * @param toDate LocalDate
     * @return List of FacturaBean
     */
    @Override
    public Collection getFacturasByDate(LocalDate fromDate, LocalDate toDate) {
        logger.info("FacturasManager: Finding facturas between " + fromDate.format(df) + " and " + toDate.format(df) + " from REST service (XML).");
        List<FacturaBean> facturas = webClient.findByDate_XML(new GenericType<List<FacturaBean>>() {
        }, fromDate.format(df), toDate.format(df));
        return facturas;
    }

    @Override
    public Collection getFacturasByCliente(Integer idcliente, LocalDate fromDate, LocalDate toDate) {
        
        logger.info("FacturasManager: Finding facturas by Cliente {" + idcliente + "} and date range from REST service (XML).");
        List<FacturaBean> facturas = webClient.findByCliente_XML(new GenericType<List<FacturaBean>>() {
        }, idcliente);

        List<FacturaBean> filteredList;

        filteredList = facturas.stream().filter(f -> f.getFecha().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate().compareTo(fromDate) >= 0 )
                .filter(f -> f.getFecha().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate().compareTo(toDate) <= 0 )
                .collect(Collectors.toList());

        return filteredList == null ? facturas : filteredList;
    }

    @Override
    public boolean createFactura(FacturaBean factura) {
        boolean res = false;
        
        logger.info("FacturasManager: Create factura {"+factura.getId()+"}.");
        try{
            webClient.create_XML(factura);
            res = true;
        }catch(Exception ex){
            //TODO Add DeleteFacturaException
        }
        return res;
    }

    @Override
    public boolean updateFactura(FacturaBean factura) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteFactura(FacturaBean factura) {
        boolean res = false;
        
        logger.info("FacturasManager: Deleting factura id {"+factura.getId()+"}.");
        try{
            webClient.delete(factura.getId());
            res = true;
        }catch(Exception ex){
            //TODO Add DeleteFacturaException
        }
        return res;
    }

}
