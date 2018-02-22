package gestiontaller.logic.interfaces;

import gestiontaller.logic.bean.ClienteBean;
import java.util.Collection;

/**
 * Interfaz
 * 
 * @author ionut188
 */
public interface ClientesManager {
    
    /**
     *
     * @param id
     * @return
     */
    public ClienteBean getClientesById(Integer id);

    /**
     *
     * @return
     */
    public Collection getAllClientes();

    /**
     *
     * @param dni
     * @return
     */
    public Collection getClientesByDni(String dni);

    /**
     *
     * @param nombre
     * @return
     */
    public Collection getClienteByNombre(String nombre);

    /**
     *
     * @param criteria
     * @return
     */
    public Collection getClientesByCriteria(String criteria);

    /**
     *
     * @param cliente
     */
    public void deleteCliente(ClienteBean cliente);

    /**
     *
     * @param cliente
     */
    public void createCliente(ClienteBean cliente);

    /**
     *
     * @param cliente
     */
    public void updateCliente(ClienteBean cliente);
}
