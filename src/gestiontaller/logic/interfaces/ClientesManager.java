package gestiontaller.logic.interfaces;

import gestiontaller.logic.bean.ClienteBean;
import java.util.Collection;

/**
 * Interfaz
 * 
 * @author ionut188
 */
public interface ClientesManager {
    public Collection getAllClientes();
    public Collection getClientesByCriteria(String criteria);
    public ClienteBean getClienteByNombre(String tcNombre);
    public boolean deleteCliente(ClienteBean cliente);
    public boolean createCliente(ClienteBean cliente);
    public boolean updateCliente(ClienteBean cliente);
}
