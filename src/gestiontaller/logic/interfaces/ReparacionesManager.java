package gestiontaller.logic.interfaces;

import gestiontaller.logic.bean.PiezaBean;
import gestiontaller.logic.bean.ReparacionBean;
import java.util.Collection;

/**
 * Piezas management interface.
 * @author Carlos
 */
public interface ReparacionesManager {

    /**
     *
     * @return
     */
    public Collection getAllReparaciones();

    /**
     *
     * @param id
     * @return
     */
    public ReparacionBean getReparacionById(int id);

    /**
     *
     * @param reparacion
     * @return
     */
    public boolean createReparacion(ReparacionBean reparacion);

    /**
     *
     * @param reparacion
     * @return
     */
    public boolean updateReparacion(ReparacionBean reparacion);

    /**
     *
     * @param reparacion
     * @return
     */
    public boolean deleteReparacion(ReparacionBean reparacion);
    
}
