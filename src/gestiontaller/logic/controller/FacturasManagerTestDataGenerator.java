package gestiontaller.logic.controller;

import gestiontaller.logic.interfaces.FacturasManager;
import gestiontaller.logic.javaBean.FacturaBean;
import java.util.ArrayList;
import java.util.Collection;

public class FacturasManagerTestDataGenerator implements FacturasManager {

    private ArrayList<FacturaBean> facturas;

    public FacturasManagerTestDataGenerator() {
        facturas = new ArrayList();
        for (int i = 0; i < 20; i++) {
            facturas.add(new FacturaBean(+i, "06-11-2017", "06-12-2017", 10 + (Double) (Math.random() * 2000), false, 0,0));
        }
        
    }

    @Override
    public Collection getAllFacturas() {
        return facturas;
    }

}
