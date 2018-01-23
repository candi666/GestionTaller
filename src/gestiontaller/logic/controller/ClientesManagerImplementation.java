package gestiontaller.logic.controller;

import gestiontaller.logic.bean.ClienteBean;
import gestiontaller.logic.interfaces.ClientesManager;
import gestiontaller.rest.ClienteREST;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author 2dam
 */
public class ClientesManagerImplementation implements ClientesManager{
    private ClienteREST webClient;
    private static final Logger LOGGER=Logger.getLogger("GestionTaler");
    
    public ClientesManagerImplementation(){
        webClient = new ClienteREST();
    }
    
    @Override
    public Collection getAllClientes() {
        LOGGER.info("ClientesManager: Finding all clientes from REST service (XML).");
        List<ClienteBean> clientes = webClient.findAll_XML(new GenericType<List<ClienteBean>>() {});
        return clientes;
    }

    @Override
    public ClienteBean getClientesById(Integer id) {
        LOGGER.info("ClientesManager: Finding cliente by ID from REST service (XML).");
        return webClient.find_XML(ClienteBean.class, id);
    }

    @Override
    public Collection getClientesByDni(String dni) {
        LOGGER.info("ClientesManager: Finding cliente by DNI from REST service (XML).");
        List<ClienteBean> clientes = webClient.findByDni_XML(new GenericType<List<ClienteBean>>() {}, dni);
        return clientes;
    }

    @Override
    public Collection getClienteByNombre(String nombre) {
        LOGGER.info("ClientesManager: Finding cliente by Nombre from REST service (XML).");
        List<ClienteBean> clientes = webClient.findByName_XML(new GenericType<List<ClienteBean>>() {}, nombre);
        return clientes;
    }
    
    @Override
    public Collection getClientesByCriteria(String criteria) {
        LOGGER.info("ClientesManager: Finding cliente by Nombre from REST service (XML).");
        return null;
    }

    @Override
    public void deleteCliente(ClienteBean cliente) {
        LOGGER.info("ClientesManager: Deleting cliente {0}.");
        webClient.delete(cliente.getId());
    }

    @Override
    public void createCliente(ClienteBean cliente) {
        LOGGER.info("ClientesManager: Creating cliente {0}.");
        webClient.create_XML(cliente);
    }

    @Override
    public void updateCliente(ClienteBean cliente) {
        LOGGER.info("ClientesManager: Updating cliente {0}.");
        webClient.update_XML(cliente);
    }
    
}
