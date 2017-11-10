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
    private FacturasController facturasController;
    private int maxid = 0;

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
    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.setTitle("Gestión de Taller");
        if(factura!=null){
             lblTitulo.setText("Modificar factura");
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
    }

    public void setFacturasController(FacturasController facturasController) {
        this.facturasController = facturasController;
    }

    /**
     * Handle on window showing
     *
     * @param event
     */
    private void handleWindowShowing(WindowEvent event) {
        populateForm();
    }

    private void populateForm() {
        initComboBoxes();
        if (factura != null) {
            logger.info("Abierta ventana modificar factura.");
            tfFecha.setText(factura.getFecha());
            tfFechaVenc.setText(factura.getFechavenc());
            cbReparacion.setValue(factura.getIdreparacion());
            cbCliente.setValue(factura.getIdcliente());
            // TODO FORMAT DOUBLE DOS DECIMALES
            tfTotal.setText(factura.getTotal().toString());
            cbEstado.setValue(factura.getPagada());
        } else {
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
    private void actionCrearMod() {

        if (factura != null) {
            factura.setFecha(tfFecha.getText());
            factura.setFechavenc(tfFechaVenc.getText());
            factura.setTotal(Double.parseDouble(tfTotal.getText()));
            factura.setIdreparacion(cbReparacion.getValue());
            factura.setIdcliente(cbCliente.getValue());
            factura.setPagada(cbEstado.getValue());
        } else {
            factura = new FacturaBean(maxid + 1, tfFecha.getText(), tfFechaVenc.getText(), 10 + (Double) (Math.random() * 2000), cbEstado.getValue(), cbReparacion.getValue(), cbCliente.getValue());
            facturasController.getTableView().getItems().add(factura);
        }

        
        stage.close();
        ownerStage.requestFocus();
        facturasController.getTableView().refresh();
    }

    /**
     *
     */
    public void initComboBoxes() {
        ObservableList<FacturaBean> obList = FXCollections.observableArrayList(facturasLogicController.getAllFacturas());
        cbReparacion.getItems().clear();
        cbCliente.getItems().clear();
        cbEstado.getItems().add(Boolean.TRUE);

        for (FacturaBean factura : obList) {
            /* TODO Al implementar la base de datos se deberan cargar solo los
             * idreparacion que no esten asociados a ninguna factura.
             */
            cbReparacion.getItems().add(factura.getIdreparacion());
            cbReparacion.getItems().sorted();

            /* TODO Carga los clientes registrados en la aplicación.
             * Al implementar la base de datos debera buscar en la tabla clientes.
             */
            if (!cbCliente.getItems().contains(factura.getIdcliente())) {
                cbCliente.getItems().add(factura.getIdcliente());
                cbCliente.getItems().sorted();
            }

            /* Aprovechamos este recorrido para determinar el ultimo id de factura,
             * de este modo ya tenemos un id definido en caso de factura nueva.
             * TODO Al implementar la base de datos analizar otra solución sincronizada.
             */
            if (factura.getId() > maxid) {
                maxid = factura.getId();
            }
        }
    }

    public void setFacturasManager(FacturasManager facturasLogicController) {
        this.facturasLogicController = facturasLogicController;
    }
}
