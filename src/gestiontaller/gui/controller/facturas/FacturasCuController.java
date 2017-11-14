/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiontaller.gui.controller.facturas;

import gestiontaller.logic.interfaces.FacturasManager;
import gestiontaller.logic.bean.FacturaBean;
import gestiontaller.logic.util.FieldValidator;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @FXML
    private Label lblTitulo;
    @FXML
    private DatePicker dpFecha;
    @FXML
    private DatePicker dpFechaVenc;
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
        if (factura != null) {
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
        SetActionEvents();
        populateForm();
    }

    private void populateForm() {
        initComboBoxes();
        if (factura != null) {
            logger.info("Abierta ventana modificar factura.");
            // Prepara fechas
            LocalDate fecha = LocalDate.parse(factura.getFecha(), dateFormatter);
            LocalDate fechaVenc = LocalDate.parse(factura.getFechavenc(), dateFormatter);

            // Asignar valores de objeto seleccionado a los campos
            dpFecha.setValue(fecha);
            dpFechaVenc.setValue(fechaVenc);
            cbReparacion.setValue(factura.getIdreparacion());
            cbCliente.setValue(factura.getIdcliente());
            cbEstado.setValue(factura.getPagada());

            // TODO FORMAT DOUBLE DOS DECIMALES
            tfTotal.setText(factura.getTotal().toString());

            /* Deshabilitar modificar fecha, solo se puede modificar la fecha
            *  de vencimiento.
             */
            dpFecha.setDisable(true);

        } else {
            logger.info("Abierta ventana crear factura.");
            dpFecha.setValue(LocalDate.now());
            dpFechaVenc.setValue(dpFecha.getValue().plusMonths(1));
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
        // Preparar datos
        String fecha = dpFecha.getValue().format(dateFormatter);
        String fechavenc = dpFechaVenc.getValue().format(dateFormatter);
        Double total = Double.parseDouble(tfTotal.getText());

        // Caso modificar
        if (factura != null) {
            if (formValid()) {
                //factura.setFecha(fecha);
                factura.setFechavenc(fechavenc);
                factura.setTotal(total);
                factura.setIdreparacion(cbReparacion.getValue());
                factura.setIdcliente(cbCliente.getValue());
                factura.setPagada(cbEstado.getValue());
                
                facturasController.actionCrearMod(factura);
                stage.close();
                ownerStage.requestFocus();
            }

        } else { // Caso Crear

            if (formValid()) {
                FacturaBean newFactura = new FacturaBean(0, fecha, fechavenc, total,
                        cbEstado.getValue(), cbReparacion.getValue(), cbCliente.getValue());

                System.out.println(newFactura.getId() + " " + newFactura.getFecha() + " "
                        + newFactura.getFechavenc() + " " + newFactura.getTotal() + " "
                        + newFactura.getPagada() + " " + newFactura.getIdreparacion() + " " + newFactura.getIdcliente());

                facturasController.actionCrearMod(newFactura);
                stage.close();
                ownerStage.requestFocus();
            }
        }

    }

    /**
     *
     */
    public void initComboBoxes() {
        ObservableList<FacturaBean> obList = FXCollections.observableArrayList(facturasLogicController.getAllFacturas());
        cbReparacion.getItems().clear();
        cbCliente.getItems().clear();
        cbEstado.getItems().add(Boolean.TRUE);
        cbEstado.getItems().add(Boolean.FALSE);

        ArrayList<Integer> reparaciones = new ArrayList();
        ArrayList<Integer> clientes = new ArrayList();

        for (FacturaBean factura : obList) {
            /* TODO Al implementar la base de datos se deberan cargar solo los
             * idreparacion que no esten asociados a ninguna factura.
             */
            reparaciones.add(factura.getIdreparacion());

            /* TODO Carga los clientes registrados en la aplicación.
             * Al implementar la base de datos debera buscar en la tabla clientes.
             */
            if (!cbCliente.getItems().contains(factura.getIdcliente())) {
                clientes.add(factura.getIdcliente());
            }
        }
        reparaciones.sort(null);
        cbReparacion.getItems().addAll(reparaciones);
        clientes.sort(null);
        cbCliente.getItems().addAll(clientes);

    }

    public void setFacturasManager(FacturasManager facturasLogicController) {
        this.facturasLogicController = facturasLogicController;
    }

    public void SetActionEvents() {
        // Show datepicker fecha
        dpFecha.setOnAction(event
                -> {
            LocalDate date = dpFecha.getValue();
            System.out.println("Selected date: " + date);
        }
        );

        // Show datepicker fecha vencimiento
        dpFechaVenc.setOnAction(event
                -> {
            LocalDate date = dpFechaVenc.getValue();
            System.out.println("Selected dateV: " + date);
        }
        );
    }

    public boolean formValid() {
        boolean res = true;

        if (!FieldValidator.isDni(tfTotal.getText())) {
            // poner en rojo
            //res = false;
        }

        return res;
    }

}
