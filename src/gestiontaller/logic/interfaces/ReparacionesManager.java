package gestiontaller.logic.interfaces;

import gestiontaller.logic.bean.PiezaBean;
import gestiontaller.logic.bean.ReparacionBean;
import java.util.Collection;

/**
 * Piezas management interface.
 * @author Carlos
 */
public interface ReparacionesManager {
    public Collection getAllReparaciones();
    public ReparacionBean getReparacionById(int id);
    public boolean createReparacion(ReparacionBean reparacion);
    public boolean updateReparacion(ReparacionBean reparacion);
    public boolean deleteReparacion(ReparacionBean reparacion);
    
}
