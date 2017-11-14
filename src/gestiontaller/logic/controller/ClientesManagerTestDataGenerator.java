package gestiontaller.logic.controller;
import gestiontaller.logic.interfaces.ClientesManager;
import gestiontaller.logic.bean.ClienteBean;
import java.util.ArrayList;
import java.util.Collection;


public class ClientesManagerTestDataGenerator implements ClientesManager{
    
    private ArrayList<ClienteBean> clientes;
    private int maxid = 0;

    public ClientesManagerTestDataGenerator(){
        clientes=new ArrayList();

        for(int i=0; i<20;i++){
            clientes.add(new ClienteBean(+i,"dni"+i,"nombre"+i,"apellidos"+i,"email"+i,"600000000"+i));
        }
    }

    @Override
    public Collection getAllClientes() {
        for (int i=0; i<clientes.size();i++){
            System.out.println("id: "+clientes.get(i).getId()+"   fecha: "+clientes.get(i).getDni());
        }
        return clientes;
    }
    
    private int getMaxId() {
        for (ClienteBean fact : clientes) {
            if (fact.getId() > maxid) {
                maxid = fact.getId();
            }
        }
        return maxid;

    }

}
