package gestiontaller.logic.controller;
import gestiontaller.logic.interfaces.ClientesManager;
import gestiontaller.logic.javaBean.ClienteBean;
import java.util.ArrayList;
import java.util.Collection;


public class ClientesManagerTestDataGenerator implements ClientesManager{
    
    private ArrayList<ClienteBean> clientes;

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

}
