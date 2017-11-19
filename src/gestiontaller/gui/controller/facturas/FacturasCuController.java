package gestiontaller.gui.controller.facturas;

import gestiontaller.gui.controller.HomeController;
import gestiontaller.logic.interfaces.FacturasManager;
import gestiontaller.logic.bean.FacturaBean;
import gestiontaller.logic.util.FieldValidator;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
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
    private CheckBox chbPagada;
    @FXML
    private Button btnCrear;
    @FXML
    private Button btnVolver;
    @FXML
    private ImageView hintFecha;
    @FXML
    private ImageView hintFechaVenc;
    @FXML
    private ImageView hintReparacion;
    @FXML
    private ImageView hintCliente;
    @FXML
    private ImageView hintTotal;

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

    /**
     * Establece instancia del controlador de facturas
     * @param facturasController 
     */
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
        initTooltips();

    }

    /**
     * Carga datos en el formulario
     */
    private void populateForm() {
        initComboBoxes();
        if (factura != null) {
            logger.info("Abierta ventana modificar factura.");

            // Set titulo de ventana y texto de boton crear/modificar.
            lblTitulo.setText(HomeController.bundle.getString("app.gui.facturas.cu.title.update"));
            btnCrear.setText(HomeController.bundle.getString("generic.crud.update"));

            // Prepara fechas
            LocalDate fecha = LocalDate.parse(factura.getFecha(), dateFormatter);
            LocalDate fechaVenc = LocalDate.parse(factura.getFechavenc(), dateFormatter);

            // Asignar valores de objeto seleccionado a los campos
            dpFecha.setValue(fecha);
            dpFechaVenc.setValue(fechaVenc);
            cbReparacion.setValue(factura.getIdreparacion());
            cbCliente.setValue(factura.getIdcliente());
            chbPagada.setSelected(factura.getPagada());

            // TODO FORMAT DOUBLE DOS DECIMALES
            tfTotal.setText(factura.getTotal().toString());

            /* Deshabilitar modificar fecha, solo se puede modificar la fecha
            *  de vencimiento.
             */
            dpFecha.setDisable(true);

        } else {
            logger.info("Abierta ventana crear factura.");
            lblTitulo.setText(HomeController.bundle.getString("app.gui.facturas.cu.title.add"));
            dpFecha.setValue(LocalDate.now());
            dpFechaVenc.setValue(dpFecha.getValue().plusMonths(1));
        }

    }
    
    /**
     * Establece stage
     * @param stage 
     */
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
        if (formValid()) {
            // Preparar datos
            String fecha = dpFecha.getValue().format(dateFormatter);
            String fechavenc = dpFechaVenc.getValue().format(dateFormatter);
            Double total = Double.parseDouble(tfTotal.getText());

            // Caso modificar
            if (factura != null) {
                //factura.setFecha(fecha);
                factura.setFechavenc(fechavenc);
                factura.setTotal(total);
                factura.setIdreparacion(cbReparacion.getValue());
                factura.setIdcliente(cbCliente.getValue());
                factura.setPagada(chbPagada.isSelected());

                facturasController.actionCrearMod(factura);

            } else { // Caso Crear
                FacturaBean newFactura = new FacturaBean(0, fecha, fechavenc, total,
                        chbPagada.isSelected(), cbReparacion.getValue(), cbCliente.getValue());
                facturasController.actionCrearMod(newFactura);
            }

            stage.close();
            ownerStage.requestFocus();
        }
    }

    /**
     * Carga datos en comboboxes reparacion y cliente.
     */
    public void initComboBoxes() {
        ObservableList<FacturaBean> obList = FXCollections.observableArrayList(facturasLogicController.getAllFacturas());
        cbReparacion.getItems().clear();
        cbCliente.getItems().clear();

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
    
    /**
     * Crea tooltips con sugerencias para la validación del formulario.
     */
    public void initTooltips() {
        // Tooltip fecha
        Tooltip tipFecha = new Tooltip("Este campo es obligatorio, debe tener un formato de fecha dd/MM/aaaa. Ej: 01/01/2017");
        tipFecha.setAnchorX(hintFecha.getX()-150);
        tipFecha.setAnchorY(hintFecha.getY()-30);
        tipFecha.setAutoFix(true);
        tipFecha.setWrapText(true);
        tipFecha.setMaxSize(200, 60);
        Tooltip.install(hintFecha, tipFecha);
        
        // Tooltip fecha de vencimiento
        tipFecha.setAnchorX(hintFechaVenc.getX()-150);
        tipFecha.setAnchorY(hintFechaVenc.getY()-30);
        Tooltip.install(hintFechaVenc, tipFecha);
        
        // Tooltip cb reparaciones
        Tooltip tooltip = new Tooltip("Este campo es obligatorio");
        tooltip.setMaxSize(200, 30);
        tooltip.setAnchorX(hintReparacion.getX()-150);
        tooltip.setAnchorY(hintReparacion.getY()-30);
        Tooltip.install(hintReparacion, tooltip);
        
        // Tooltip cb clientes
        tooltip.setAnchorX(hintCliente.getX()-150);
        tooltip.setAnchorY(hintCliente.getY()-30);
        Tooltip.install(hintCliente, tooltip);
        
        // Tooltip total
        Tooltip tipTotal = new Tooltip("Este campo es obligatorio y debe ser un número.");
        tipTotal.setAnchorX(hintTotal.getX()-150);
        tipTotal.setAnchorY(hintTotal.getY()-30);
        Tooltip.install(hintTotal, tooltip);
        

    }

    /**
     * Establece instancia del controlador de lógica.
     * @param facturasLogicController 
     */
    public void setFacturasManager(FacturasManager facturasLogicController) {
        this.facturasLogicController = facturasLogicController;
    }

    /**
     * Establece acciones onAction
     */
    public void SetActionEvents() {
        // Show datepicker fecha
        dpFecha.setOnAction(event
                -> {
            LocalDate date = dpFecha.getValue();
        }
        );

        // Show datepicker fecha vencimiento
        dpFechaVenc.setOnAction(event
                -> {
            LocalDate date = dpFechaVenc.getValue();
        }
        );
    }

    /**
     * Verifica cada campo dle formulario, si alguno no es válido, entonces
     * el formulario no es válido.
     * @return validéz del formulario.
     */
    public boolean formValid() {
        boolean valid = true;

        // Validación fecha
        if (dpFecha.getValue() == null || !FieldValidator.isDate(dpFecha.getValue())) {
            dpFecha.getStyleClass().add("tf-invalid");
            hintFecha.setVisible(true);
            valid = false;
        }

        // Validación fecha de vencimiento
        if (dpFechaVenc.getValue() == null || !FieldValidator.isDate(dpFechaVenc.getValue())) {
            dpFechaVenc.getStyleClass().add("tf-invalid");
            hintFechaVenc.setVisible(true);
            valid = false;
        }

        // Validación cb reparación
        if (cbReparacion.getValue() == null) {
            cbReparacion.getStyleClass().add("tf-invalid");
            hintReparacion.setVisible(true);
            valid = false;
        }

        // Validación cb cliente
        if (cbCliente.getValue() == null) {
            cbCliente.getStyleClass().add("tf-invalid");
            hintCliente.setVisible(true);
            valid = false;
        }

        // Validación Total
        if (!tfTotal.getText().isEmpty()) {
            try {
                Double isDouble = Double.parseDouble(tfTotal.getText());
                if (isDouble < 0) {
                    tfTotal.getStyleClass().add("tf-invalid");
                    hintTotal.setVisible(true);
                    valid = false;
                }
            } catch (NumberFormatException e) {
                tfTotal.getStyleClass().add("tf-invalid");
                hintTotal.setVisible(true);
                valid = false;
                // TODO string literal
                logger.info("Error al parsear String a Double");
            }
        } else {
            tfTotal.getStyleClass().add("tf-invalid");
            hintTotal.setVisible(true);
            valid = false;
        }

        return valid;
    }
}
