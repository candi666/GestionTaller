package gestiontaller.logic.controller;
import gestiontaller.config.GTConstants;
import gestiontaller.logic.interfaces.ClientesManager;
import gestiontaller.logic.bean.ClienteBean;
import gestiontaller.logic.util.FieldValidator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Datos de prueba
 * 
 * @author Ionut
 */
public class ClientesManagerTestDataGenerator implements ClientesManager{

    private static final Logger logger = Logger.getLogger(ClientesManagerTestDataGenerator.class.getName());
    private ArrayList<ClienteBean> clientes;
    private int maxitems = GTConstants.MAX_MOCK_CLIENTES;
    private int maxid = 0;

    /**
     * Generar clientes para pruebas
     */
    public ClientesManagerTestDataGenerator(){
        clientes=new ArrayList();

        for(int i=1; i<maxitems;i++){
            clientes.add(new ClienteBean(+i,"dni"+i,"nombre"+i,"apellidos"+i,"email"+i,"600000000"+i));
        }
    }

    /**
     * Obtener los clientes
     *
     * @return
     */
    @Override
    public Collection getAllClientes() {
        return clientes;
    }
    
    /**
     * Crear cliente
     * @param cliente
     * @return 
     */
    public boolean createCliente(ClienteBean cliente) {
        try {
            cliente.setId(getMaxId() + 1);
            clientes.add(cliente);
            logger.info("Creado cliente id: " + cliente.getId());
            return true;
        } catch (Exception e) {
            logger.info("Ha ocurrido un error al crear cliente.");
            return false;
        }
    }
    
    /**
     * Modificar cliente
     * @param cliente
     * @return 
     */
    public boolean updateCliente(ClienteBean cliente) {
        boolean res = false;
        try {
            for (ClienteBean cli : clientes) {
                if (cli.getId() == cliente.getId()) {
                    cli.setDni(cliente.getDni());
                    cli.setNombre(cliente.getNombre());
                    cli.setApellidos(cliente.getApellidos());
                    cli.setEmail(cliente.getEmail());
                    cli.setTelefono(cliente.getTelefono());
                    logger.info("Modificado cliente id: " + cliente.getId());
                    res = true;
                    break;
                }
            }

            return res;
        } catch (Exception e) {
            logger.info("Ha ocurrido un error al modificar cliente id: " + cliente.getId());
            return res;
        }
    }
    
    /**
     * Obtener el id maximo de la tabla
     * 
     * @return 
     */
    private int getMaxId() {
        for (ClienteBean fact : clientes) {
            if (fact.getId() > maxid) {
                maxid = fact.getId();
            }
        }
        return maxid;

    }

    /**
     * Eliminar cliente
     * @param cliente
     * @return
     */
    @Override
    public boolean deleteCliente(ClienteBean cliente) {
       try {
            clientes.remove(cliente);
            logger.info("Factura id: " + cliente.getId() + " eliminada.");
            return true;
        } catch (Exception e) {
            logger.info("Ha ocurrido un error al intentar eliminar factura id: " + cliente.getId());
            return false;
        }
    }

    /**
     * Obtener cliente por DNI y Nombre
     * @param criteria
     * @return
     */
    @Override
    public Collection getClientesByCriteria(String criteria) {
        List<ClienteBean> filteredClientes = new ArrayList();
        try {
            filteredClientes = clientes.stream()
                    .filter(c -> (c.getDni().equals(criteria) || c.getNombre().equals(criteria)))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.info("Error en búsqueda de clientes.");
        }
        return filteredClientes;
    }

    /**
     *
     * @param tcNombre
     * @return
     */
    @Override
    public ClienteBean getClienteByNombre(String tcNombre) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
