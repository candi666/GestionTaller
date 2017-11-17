package gestiontaller.logic.controller;

import gestiontaller.config.GTConstants;
import gestiontaller.gui.controller.HomeController;
import gestiontaller.logic.interfaces.FacturasManager;
import gestiontaller.logic.bean.FacturaBean;
import gestiontaller.logic.util.FieldValidator;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class FacturasManagerTestDataGenerator implements FacturasManager {

    private static final Logger logger = Logger.getLogger(FacturasManagerTestDataGenerator.class.getName());
    private ArrayList<FacturaBean> facturas;
    private Random rn = new Random();
    private int maxitems = GTConstants.MAX_MOCK_FACTURAS;
    private int maxid = 0;

    /**
     * Genera una cantidad "maxitems" de objetos FacturaBean para pruebas.
     *
     */
    public FacturasManagerTestDataGenerator() {
        facturas = new ArrayList();
        LocalDate fecha;
        LocalDate fechaVenc;
        Double total;

        for (int i = 1; i < maxitems; i++) {
            fecha = getRandomDate();
            fechaVenc = fecha.plusMonths(1);

            createFactura(new FacturaBean(+i, fecha.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                    fechaVenc.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                    round((10 + (double) (Math.random() * 2000)), 2), rn.nextBoolean(),
                    0 + (int) (Math.random() * maxitems), 0 + (int) (Math.random() * maxitems)));

        }
    }

    /**
     * Obtener todas las facturas
     *
     * @return
     */
    public Collection getAllFacturas() {
//        for(FacturaBean factura:facturas){
//            System.out.println(factura.getId());
//        }

        return facturas;
    }

    /**
     * Obtener factura por id
     *
     * @param id id de factura a buscar.
     * @return FacturaBean resultado
     */
    public FacturaBean getFacturaById(String id) {
        FacturaBean factura = null;
        if (FieldValidator.isInteger(id)) {
            for (FacturaBean fact : facturas) {
                if (fact.getId() == Integer.valueOf(id)) {
                    factura = fact;
                }
            }
        }
        return factura;
    }

    /**
     * Obtener factura por id reparación
     *
     * @param id id de reparación a buscar.
     * @return FacturaBean resultado
     */
    public FacturaBean getFacturaByReparacion(String id) {
        FacturaBean factura = null;
        if (FieldValidator.isInteger(id)) {
            for (FacturaBean fact : facturas) {
                if (fact.getIdreparacion() == Integer.valueOf(id)) {
                    factura = fact;
                }
            }
        }
        return factura;
    }

    /**
     * Obtiene facturas en un rango de fechas
     *
     * @param fromDate
     * @param toDate
     * @return
     */
    public Collection getFacturasByDate(LocalDate fromDate, LocalDate toDate) {
        String fDate = fromDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        System.out.println(fDate);

        // Busqueda Java8 Lambda Aggregation (Revisar funcionamiento)
//        List<FacturaBean> filteredList = facturas.stream().filter(f -> dateIn(f.getFecha(),fromDate,toDate))
//                         .collect(Collectors.toList());

        // Busqueda recorriendo ArrayList
        ArrayList<FacturaBean> filteredList = new ArrayList();
        for (FacturaBean factura : facturas) {
            if(dateIn(factura.getFecha(),fromDate,toDate)){
                filteredList.add(factura);
            }
        }
        

        System.out.println(filteredList.size());


        // TODO
        return filteredList;
    }

    /**
     * Obtiene facturas para un cliente en un rango de fechas
     *
     * @param cliente
     * @param fromDate
     * @param toDate
     * @return
     */
    public Collection getFacturasByCliente(String cliente, LocalDate fromDate, LocalDate toDate) {
        // TODO
        return null;
    }

    /**
     * Crear factura
     *
     * @param factura
     * @return
     */
    public boolean createFactura(FacturaBean factura) {
        try {
            factura.setId(getMaxId() + 1);
            facturas.add(factura);
            logger.info("Creada factura id: " + factura.getId());
            return true;
        } catch (Exception e) {
            logger.info("Ha ocurrido un error al crear factura.");
            return false;
        }
    }

    /**
     * Modificar factura
     *
     * @param factura
     * @return
     */
    public boolean updateFactura(FacturaBean factura) {
        boolean res = false;
        try {
            for (FacturaBean fact : facturas) {
                if (fact.getId() == factura.getId()) {
                    fact.setFecha(factura.getFecha());
                    fact.setFechavenc(factura.getFechavenc());
                    fact.setIdreparacion(factura.getIdreparacion());
                    fact.setIdcliente(factura.getIdcliente());
                    fact.setTotal(factura.getTotal());
                    fact.setPagada(factura.getPagada());
                    logger.info("Modificada factura id: " + factura.getId());
                    res = true;
                    break;
                }
            }

            return res;
        } catch (Exception e) {
            logger.info("Ha ocurrido un error al modificar factura id: " + factura.getId());
            return res;
        }
    }

    /**
     * Eliminar factura
     *
     * @param factura
     * @return
     */
    public boolean deleteFactura(FacturaBean factura) {
        try {
            facturas.remove(factura);
            logger.info("Factura id: " + factura.getId() + " eliminada.");
            return true;
        } catch (Exception e) {
            logger.info("Ha ocurrido un error al intentar eliminar factura id: " + factura.getId());
            return false;
        }
    }

    /////////////// AUX ///////////////////
    /**
     * Metodo auxiliar para obtener una fecha aleatoria entre 2010 y la fecha
     * actual.
     *
     * @return fecha aleatoria.
     */
    private LocalDate getRandomDate() {
        int minDay = (int) LocalDate.of(2010, 1, 1).toEpochDay();
        int maxDay = (int) LocalDate.now().toEpochDay();
        long randomDay = minDay + rn.nextInt(maxDay - minDay);

        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);

        return randomDate;
    }

    /**
     * Redondea un double a los decimales deseados
     *
     * @param value double a redondear.
     * @param places decimales deseados.
     * @return double
     */
    private Double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return new Double(bd.doubleValue());
    }

    /**
     * Obtiene el maximo id con el fin de simular AUTOINCREMENT
     *
     * @return
     */
    private int getMaxId() {
        for (FacturaBean fact : facturas) {
            if (fact.getId() > maxid) {
                maxid = fact.getId();
            }
        }
        return maxid;

    }

    private boolean dateIn(String fechaComp, LocalDate fromDate, LocalDate toDate) {
        LocalDate fecha = LocalDate.parse(fechaComp, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        Boolean res = false;

        try {
            if (fecha.equals(fromDate) || fecha.equals(toDate) || (fecha.isAfter(fromDate) && fecha.isBefore(fecha))) {
                res = true;
                //logger.info("Fecha encontrada: "+fecha.toString());
            }else{
                //logger.info("Fecha fuera de rango: "+fecha.toString());
            }
        } catch (Exception ex) {
            logger.info(HomeController.bundle.getString("app.error.indate"));
        }

        return res;
    }

}
