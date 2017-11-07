package gestiontaller.logic.controller;

import gestiontaller.logic.interfaces.FacturasManager;
import gestiontaller.logic.javaBean.FacturaBean;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

public class FacturasManagerTestDataGenerator implements FacturasManager {

    private ArrayList<FacturaBean> facturas;
    private Random rn = new Random();
    private int maxitems = 20;

    /**
     * Genera una cantidad "maxitems" de objetos FacturaBean para pruebas.
     *
     */
    public FacturasManagerTestDataGenerator() {
        facturas = new ArrayList();
        LocalDate fecha;
        LocalDate fechaVenc;
        Double total;

        for (int i = 0; i < maxitems; i++) {
            fecha = getRandomDate();
            fechaVenc = fecha.plusMonths(1);

            
            facturas.add(new FacturaBean(+i, fecha.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                    fechaVenc.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                    round((10 + (double) (Math.random() * 2000)),2), rn.nextBoolean(),
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
        for(FacturaBean factura:facturas){
            System.out.println(factura.getPagada());
        }
        
        return facturas;
    }

    /**
     * Metodo auxiliar para obtener una fecha aleatoria entre 2010 y 
     * la fecha actual.
     * @return fecha aleatoria.
     */
    public LocalDate getRandomDate() {
        int minDay = (int) LocalDate.of(2010, 1, 1).toEpochDay();
        int maxDay = (int) LocalDate.now().toEpochDay();
        long randomDay = minDay + rn.nextInt(maxDay - minDay);

        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);

        return randomDate;
    }

    /**
     * Redondea un double a los decimales deseados
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

}
