/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiontaller.gui.controller.facturas;

import gestiontaller.logic.interfaces.FacturasManager;
import gestiontaller.logic.javaBean.FacturaBean;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Carlos
 */
public class FacturasCuController implements Initializable {

    private static final Logger logger = Logger.getLogger(FacturasCuController.class.getName());
    private Stage stage;
    private Stage ownerStage;
    private FacturaBean factura;
    private FacturasManager facturasLogicController;

    @FXML
    private Label lblTitulo;
    @FXML
    private TextField tfFecha;
    @FXML
    private TextField tfFechaVenc;
    @FXML
    private ComboBox<Integer> cbReparacion;
    @FXML
    private ComboBox<Integer> cbCliente;
    @FXML
    private TextField tfTotal;
    @FXML
    private ComboBox<Boolean> cbEstado;
    @FXML
    private Button btnCrear;
    @FXML
    private Button btnVolver;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    /**
     * Inicializa la stage
     *
     * @param root Elemento Parent del fxml
     */
    public void initStage(Parent root, String titulo) {
        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.setTitle("Gestión de Taller");
        lblTitulo.setText(titulo);
        stage.setResizable(false);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(ownerStage);
        //stage.setOnShowing(this::handleWindowShowing);
        stage.setMaxWidth(340);
        stage.setMinWidth(340);
        stage.setMaxHeight(420);
        stage.setMinHeight(420);
        stage.show();

    }

    /**
     * Establece owner stage
     *
     * @param ownerStage
     */
    public void setOwnerStage(Stage ownerStage) {
        this.ownerStage = ownerStage;
    }

    /**
     * Establece factura a modificar.
     *
     * @param factura
     */
    public void setFactura(FacturaBean factura) {
        this.factura = factura;
        populateForm();
        
    }

    /**
     * Handle on window showing
     *
     * @param event
     */
    private void handleWindowShowing(WindowEvent event) {
//        if(factura!=null){
//            populateForm();
//        }
    }

    private void populateForm() {
        initComboBox();
        if (factura != null) {
            logger.info("Abierta ventana modificar factura.");
            tfFecha.setText(factura.getFecha());
            tfFechaVenc.setText(factura.getFechavenc());
            cbReparacion.setValue(factura.getIdreparacion());
            cbCliente.setValue(factura.getIdcliente());
            // TODO FORMAT DOUBLE DOS DECIMALES
            tfTotal.setText(factura.getTotal().toString());
            cbEstado.setValue(factura.getPagada());
        }else{
            logger.info("Abierta ventana crear factura.");
        }

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    /**
     * Cierra stage actual y enfoca el stage home.
     */
    @FXML
    private void actionVolver() {
        stage.close();
        ownerStage.requestFocus();
    }
    
    /**
     * Añade una nueva factura a la tabla y luego cierra el stage actual.
     */
    @FXML
    private void actionCrear() {
        //TODO add factura
        stage.close();
        ownerStage.requestFocus();
    }
    
    public void initComboBox(){
        ObservableList<FacturaBean> obList = FXCollections.observableArrayList(facturasLogicController.getAllFacturas());
        cbReparacion.getItems().clear();
        cbCliente.getItems().clear();
        
        for(FacturaBean factura : obList){
            cbReparacion.getItems().add(factura.getIdreparacion());
            cbCliente.getItems().add(factura.getIdcliente());
        }
    }
    
    public void setFacturasManager(FacturasManager facturasLogicController) {
        this.facturasLogicController = facturasLogicController;
    }
}
