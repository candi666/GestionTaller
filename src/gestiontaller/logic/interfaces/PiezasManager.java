package gestiontaller.logic.interfaces;

import gestiontaller.logic.bean.PiezaBean;
import java.util.Collection;

/**
 * Piezas management interface.
 * @author Carlos
 */
public interface PiezasManager {
    public Collection getAllPiezas();
    public PiezaBean getPiezaById(int id);
    public boolean createPieza(PiezaBean pieza);
    public boolean updatePieza(PiezaBean pieza);
    public boolean deletePieza(PiezaBean pieza);
    
}
