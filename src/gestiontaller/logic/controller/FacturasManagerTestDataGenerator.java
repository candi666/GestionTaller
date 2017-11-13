package gestiontaller.logic.controller;

import gestiontaller.logic.interfaces.FacturasManager;
import gestiontaller.logic.bean.FacturaBean;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.logging.Logger;

public class FacturasManagerTestDataGenerator implements FacturasManager {

    private static final Logger logger = Logger.getLogger(FacturasManagerTestDataGenerator.class.getName());
    private ArrayList<FacturaBean> facturas;
    private Random rn = new Random();
    private int maxitems;
    private int maxid = 0;

    /**
     * Genera una cantidad "maxitems" de objetos FacturaBean para pruebas.
     *
     */
    public FacturasManagerTestDataGenerator(int maxitems) {
        facturas = new ArrayList();
        this.maxitems = maxitems;
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
    @Override
    public Collection getAllFacturas() {
//        for(FacturaBean factura:facturas){
//            System.out.println(factura.getId());
//        }

        return facturas;
    }

    public FacturaBean getFacturaById(int id) {
        FacturaBean factura = null;
        for (FacturaBean fact : facturas) {
            if (fact.getId() == id) {
                factura = fact;
            }
        }
        return factura;
    }

    public Collection getFacturasByDate(LocalDate fromDate, LocalDate toDate) {

        // TODO
        return null;
    }

    public Collection getFacturasByCliente(String cliente) {
        // TODO
        return null;
    }

    public boolean createFactura(FacturaBean factura) {
        try {
            factura.setId(getMaxId() + 1);
            facturas.add(factura);
            logger.info("Agregada nueva factura id: " + factura.getId());
            return true;
        } catch (Exception e) {
            logger.info("Ha ocurrido un error al crear nueva factura.");
            return false;
        }
    }

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
                    res=true;
                    break;
                }
            }

            
            return res;
        } catch (Exception e) {
            logger.info("Ha ocurrido un error al modificar factura, factura no encontrada.");
            return res;
        }
    }

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

    private int getMaxId() {
        for (FacturaBean fact : facturas) {
            if (fact.getId() > maxid) {
                maxid = fact.getId();
            }
        }
        return maxid;

    }
}
