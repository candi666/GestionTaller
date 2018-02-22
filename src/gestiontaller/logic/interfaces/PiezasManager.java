package gestiontaller.logic.interfaces;

import gestiontaller.logic.bean.PiezaBean;
import java.util.Collection;

/**
 * Piezas management interface.
 * @author Carlos
 */
public interface PiezasManager {

    /**
     *
     * @return
     */
    public Collection getAllPiezas();

    /**
     *
     * @param id
     * @return
     */
    public PiezaBean getPiezaById(int id);

    /**
     *
     * @param pieza
     * @return
     */
    public boolean createPieza(PiezaBean pieza);

    /**
     *
     * @param pieza
     * @return
     */
    public boolean updatePieza(PiezaBean pieza);

    /**
     *
     * @param pieza
     * @return
     */
    public boolean deletePieza(PiezaBean pieza);
    
}
