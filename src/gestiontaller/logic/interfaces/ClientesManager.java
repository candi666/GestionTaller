package gestiontaller.logic.interfaces;

import gestiontaller.logic.bean.ClienteBean;
import java.util.Collection;

/**
 * Interfaz
 * 
 * @author ionut188
 */
public interface ClientesManager {
    
    public ClienteBean getClientesById(Integer id);
    public Collection getAllClientes();
    public ClienteBean getClientesByDni(String dni);
    public ClienteBean getClienteByNombre(String nombre);
    public Collection getClientesByCriteria(String criteria);
    public void deleteCliente(ClienteBean cliente);
    public void createCliente(ClienteBean cliente);
    public void updateCliente(ClienteBean cliente);
}
