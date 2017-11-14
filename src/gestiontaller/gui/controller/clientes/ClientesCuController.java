/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiontaller.gui.controller.clientes;

import gestiontaller.logic.interfaces.ClientesManager;
import gestiontaller.logic.bean.ClienteBean;
import gestiontaller.logic.util.FieldValidator;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
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
    private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
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
//            NO FUNCIONA ¿?¿?¿?¿?¿?
//            final Tooltip tooltip = new Tooltip();
//            tooltip.setText("GG");
//            btnCrear.setTooltip(tooltip);
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
    
    
    //Validacion
    @FXML
    private void actionValidacion(){
        if (validar()) {
            actionCrearMod();
        }
    }
    
    @FXML
    public boolean validar() {
        boolean res=true;
//        
        if(!FieldValidator.isDni(tfDNI.getText())){
            tfDNI.getStyleClass().add("tf-invalid");
            res=false;
        }
//        
        return res;
    }
//    private boolean soloNumeros() {
//
//        int i, j = 0;
//        String numero = ""; // Es el número que se comprueba uno a uno por si hay alguna letra entre los 8 primeros dígitos
//        String miDNI = ""; // Guardamos en una cadena los números para después calcular la letra
//        String[] unoNueve = {"0","1","2","3","4","5","6","7","8","9"};
//
//        for(i = 0; i < tfDNI.getText().length() - 1; i++) {
//            numero = tfDNI.getText().substring(i, i+1);
//
//            for(j = 0; j < unoNueve.length; j++) {
//                if(numero.equals(unoNueve[j])) {
//                    miDNI += unoNueve[j];
//                }
//            }
//        }
//
//        if(miDNI.length() != 8) {
//            return false;
//        }
//        else {
//            return true;
//        }
//    }
// 
//    private String letraDNI() {
//        // El método es privado porque lo voy a usar internamente en esta clase, no se necesita fuera de ella
//
//        // pasar miNumero a integer
//        int miDNI = Integer.parseInt(tfDNI.getText().substring(0,8));
//        int resto = 0;
//        String miLetra = "";
//        String[] asignacionLetra = {"T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E"};
//
//        resto = miDNI % 23;
//
//        miLetra = asignacionLetra[resto];
//
//        return miLetra;
//    }
    
    public boolean validarEmail() {
        // Compiles the given regular expression into a pattern.
        Pattern pattern = Pattern.compile(PATTERN_EMAIL);
 
        // Match the given input against this pattern
        Matcher matcher = pattern.matcher(tfEmail.getText());
        return matcher.matches();
    }
    
}
