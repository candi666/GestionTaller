/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiontaller.gui.controller.clientes;

import gestiontaller.logic.interfaces.ClientesManager;
import gestiontaller.logic.javaBean.ClienteBean;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author 2dam
 */
public class ClientesCuController implements Initializable {
    private static final Logger logger = Logger.getLogger(ClientesCuController.class.getName());
    private Stage stage;
    private Stage ownerStage;
    private ClienteBean cliente;
    private ClientesManager clientesLogicController;
    private ClientesController clientesController;
    private int maxid = 0;

    @FXML
    private TextField tfDNI;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfApellido;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfTelefono;
    @FXML
    private Button btnCrear;
    @FXML
    private Button btnVolver;
    @FXML
    private Label lblTitulo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.setTitle("Gestión de Taller");
        if(cliente!=null){
             lblTitulo.setText("Modificar cliente");
             btnCrear.setText("Modificar");
        }
        
        stage.setResizable(false);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(ownerStage);
        stage.setOnShowing(this::handleWindowShowing);
        stage.setMaxWidth(340);
        stage.setMinWidth(340);
        stage.setMaxHeight(420);
        stage.setMinHeight(420);
        stage.show();

    }
    
    public void setOwnerStage(Stage ownerStage) {
        this.ownerStage = ownerStage;
    }

    /**
     * Establece cliente a modificar.
     *
     * @param cliente
     */
    public void setCliente(ClienteBean cliente) {
        this.cliente = cliente;
    }

    public void setClientesController(ClientesController clientesController) {
        this.clientesController = clientesController;
    }
    
    private void handleWindowShowing(WindowEvent event) {
        populateForm();
    }
    
    private void populateForm() {
        if (cliente != null) {
            logger.info("Abierta ventana modificar cliente.");
            tfDNI.setText(cliente.getDni());
            tfNombre.setText(cliente.getNombre());
            tfApellido.setText(cliente.getApellidos());
            tfEmail.setText(cliente.getEmail());
            tfTelefono.setText(cliente.getTelefono());
        } else {
            logger.info("Abierta ventana crear cliente.");
        }

    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    @FXML
    private void actionVolver() {
        stage.close();
        ownerStage.requestFocus();
    }
    
    @FXML
    private void actionCrearMod() {

        if (cliente != null) {
            cliente.setDni(tfDNI.getText());
            cliente.setNombre(tfNombre.getText());
            cliente.setApellidos(tfApellido.getText());
            cliente.setEmail(tfEmail.getText());
            cliente.setTelefono(tfTelefono.getText());
        } else {
            cliente = new ClienteBean(maxid + 1, tfDNI.getText(), tfNombre.getText(), tfApellido.getText(), tfEmail.getText(), tfTelefono.getText());
            clientesController.getTableView().getItems().add(cliente);
        }

        
        stage.close();
        ownerStage.requestFocus();
        clientesController.getTableView().refresh();
    }
    
    public void setClientesManager(ClientesManager clientesLogicController) {
        this.clientesLogicController = clientesLogicController;
    }

    
}

