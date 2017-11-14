/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiontaller.gui.controller.facturas;

import gestiontaller.App;
import gestiontaller.logic.interfaces.FacturasManager;
import gestiontaller.logic.bean.FacturaBean;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Carlos
 */
public class FacturasController implements Initializable {

    private static final Logger logger = Logger.getLogger(FacturasController.class.getName());
    private Stage stage;
    private Stage ownerStage;
    private FacturasManager facturasLogicController;
    private ObservableList<FacturaBean> facturasData;
    private ObservableList<FacturaBean> facturasDataPendientes;
    private static final int maxrows = 18;
    private int pageindex;
    private int totalpages;
    

    // <editor-fold defaultstate="collapsed" desc="@FXML NODES">
    @FXML
    private Button btnPrimero;
    @FXML
    private Button btnAnterior;
    @FXML
    private Button btnSiguiente;
    @FXML
    private Button btnUltimo;
    @FXML
    private Label lblPagina;
    @FXML
    private Button btnPagado;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnAnadir;
    @FXML
    private ComboBox<String> cbFiltro;
    @FXML
    private TextField tfBuscar;
    @FXML
    private Button btnBuscar;
    @FXML
    private CheckBox chbPendientes;
    @FXML
    private Button btnSalir;
    @FXML
    private TableView<FacturaBean> tvFacturas;
    @FXML
    private TableColumn<FacturaBean, SimpleIntegerProperty> tcId;
    @FXML
    private TableColumn<FacturaBean, SimpleStringProperty> tcFecha;
    @FXML
    private TableColumn<FacturaBean, SimpleStringProperty> tcFechaVenc;
    @FXML
    private TableColumn<FacturaBean, SimpleIntegerProperty> tcIdReparacion;
    @FXML
    private TableColumn<FacturaBean, SimpleIntegerProperty> tcIdCliente;
    @FXML
    private TableColumn<FacturaBean, SimpleDoubleProperty> tcTotal;
    @FXML
    private TableColumn tcPagada;

    // </editor-fold>
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    /* -----------------------------------------------------------------------*/
 /*                        METODOS DE INICIALIZACIÓN                       */
 /* -----------------------------------------------------------------------*/
    /**
     * Inicializa la stage
     *
     * @param root Elemento Parent del fxml
     */
    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.setTitle("Gestión de taller - Facturas");
        stage.setResizable(true);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(ownerStage);
        stage.setOnShowing(this::handleWindowShowing);
        stage.setMaxWidth(1024);
        stage.setMinWidth(1024);
        stage.setMaxHeight(680);
        stage.setMinHeight(680);
        stage.show();

    }

    /**
     * Handle on window showing
     *
     * @param event
     */
    private void handleWindowShowing(WindowEvent event) {
        handleActionEvents();
        initActionPanel();
        initTable();
        initPagination();

    }

    private void initPagination() {
        pageindex = 1;
        totalpages = (facturasData.size() - 1) / maxrows;
        
        btnPrimero.setDisable(true);
        btnAnterior.setDisable(true);
        
        lblPagina.setText(pageindex + " / " + totalpages);
    }

    private void initActionPanel() {
        btnEliminar.setDisable(true);
        btnModificar.setDisable(true);
        btnPagado.setDisable(true);
        btnAnadir.setDisable(false);
    }

    /**
     * Formato y carga de datos a tabla.
     */
    private void initTable() {
        // Obtener Collection de Facturas
        facturasData = FXCollections.observableArrayList(facturasLogicController.getAllFacturas());

        

        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        tcFechaVenc.setCellValueFactory(new PropertyValueFactory<>("fechavenc"));
        tcIdReparacion.setCellValueFactory(new PropertyValueFactory<>("idreparacion"));
        tcIdCliente.setCellValueFactory(new PropertyValueFactory<>("idcliente"));
        tcTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        /* Definición de columna Pagado.
         * Definir un comportamiento cuando sea true y otro para false.
         * De momento SI o NO, por implementar cambio de color o iconos.
         */
        tcPagada.setSortable(false);
        tcPagada.setCellValueFactory(new PropertyValueFactory<>("pagada"));
        
        // Add Listeners
        tvFacturas.getSelectionModel().selectedItemProperty().addListener(this::handleFacturasTableSelectionChanged);
        InitRowDoubleClickEvent();
        
        // Añadir datos iniciales a la tabla
        tvFacturas.setItems(FXCollections.observableArrayList(facturasData.subList(0, maxrows)));
        
        
    }

    /* -----------------------------------------------------------------------*/
 /*                                ACCIONES                            */
 /* -----------------------------------------------------------------------*/
    /**
     * Inicializa acciones para botones que requieren parametros.
     */
    private void handleActionEvents() {
        // CRUD
        btnAnadir.setOnAction(e -> loadCrearMod(null));
        btnModificar.setOnAction(e -> loadCrearMod(tvFacturas.getSelectionModel().getSelectedItem()));

        // NAV
        btnSiguiente.setOnAction(e -> goToPage(pageindex + 1));
        btnAnterior.setOnAction(e -> goToPage(pageindex - 1));
        btnPrimero.setOnAction(e -> goToPage(1));
        btnUltimo.setOnAction(e -> goToPage(totalpages));

        // FILTER 
        chbPendientes.setOnAction((event) -> {
            if (chbPendientes.isSelected()) {
                FilteredList<FacturaBean> filteredData = new FilteredList<>(facturasData, p -> !p.getPagada());
                //tvFacturas.getItems().removeAll();
                tvFacturas.setItems(filteredData);
                
            } else {
                //tvFacturas.getItems().removeAll();
                tvFacturas.setItems(facturasData);
            }

        });
        
        // SEARCH
        btnBuscar.setOnAction((event) -> {
            // TODO

        });

    }

    /**
     * Acción borrar factura
     */
    @FXML
    private void actionEliminar() {
        /* facturasData: lista con todas las facturas
        *  tvFacturas.getItems(): lista de facturas en la tabla actualmente.
         */
        
        // TODO DIALOG ELIMINAR
        facturasLogicController.deleteFactura(tvFacturas.getSelectionModel().getSelectedItem());
//        facturasData.remove(tvFacturas.getSelectionModel().getSelectedItem());
//        tvFacturas.getItems().remove(tvFacturas.getSelectionModel().getSelectedItem());

        reloadTable();
        totalpages = facturasData.size() / maxrows;

        if (pageindex > 1 && tvFacturas.getItems().size() < 1) {
            goToPage(pageindex - 1);
        }

    }

    /**
     * Acción Crear/Modificar factura
     */
    @FXML
    public void actionCrearMod(FacturaBean factura) {
        /* facturasData: lista con todas las facturas
        *  tvFacturas.getItems(): lista de facturas en la tabla actualmente.
         */
        if(factura.getId()==0){
            System.out.println("Test crear: "+factura.getFecha());
            if(facturasLogicController.createFactura(factura)){
                reloadTable();
                goToPage(totalpages);
            }
            
        }else{
            facturasLogicController.updateFactura(factura);
            reloadTable();
            goToPage(pageindex);
            tvFacturas.refresh();
        }
        
        totalpages = facturasData.size() / maxrows; 
        
    }

    /**
     * Cambiar estado de la factura
     */
    @FXML
    private void actionPagar() {
        FacturaBean factura = tvFacturas.getSelectionModel().getSelectedItem();
        if (factura != null) {
            if (factura.getPagada()) {
                factura.setPagada(false);
            } else {
                factura.setPagada(true);
                btnPagado.setDisable(true);
            }
            tvFacturas.refresh();
        }
    }

    /**
     * Buscar
     */
    @FXML
    private void actionBuscar() {
        // TODO Implementar busqueda en bases de datos.
        // +++ De momento utilizaremos filter para pruebas.
    }

    /**
     * Cierra stage actual y enfoca el owner stage
     */
    @FXML
    private void actionVolver() {
        stage.close();
        ownerStage.requestFocus();
    }

    /**
     * Carga ventana Crear/Modificar factura. Si pasamos null se abre una
     * ventana para nueva factura. Si le pasamos la factura seleccionada se abre
     * una venatana para modificar.
     *
     * @param factura factura seleccionada en la tabla. Para nueva factura
     * utiizar null.
     */
    private void loadCrearMod(FacturaBean factura) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("gui/view/facturas/nueva_factura.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            FacturasCuController ctr = ((FacturasCuController) loader.getController());
            ctr.setStage(new Stage());
            ctr.setFacturasManager(facturasLogicController);
            ctr.setFacturasController(this);

            // En caso de opción Modificar
            if (factura != null) {
                ctr.setFactura(factura);
            }

            ctr.setOwnerStage(stage);
            ctr.initStage(root);
        } catch (IOException ex) {
            ex.printStackTrace();
            logger.log(Level.SEVERE, "Error al cargar ventana nueva_factura.fxml.", ex);
        }
    }

    /**
     * Metodo de navegación entre paginas de la tabla.
     *
     * @param pageindex pagina objetivo.
     */
    private void goToPage(int pageindex) {
        this.pageindex = pageindex;
        int fromIndex;
        int toIndex;
        

        if (pageindex == 1) { // Si primera pagina
            fromIndex = 0;
            toIndex = maxrows;

            btnPrimero.setDisable(true);
            btnAnterior.setDisable(true);
            btnSiguiente.setDisable(false);
            btnUltimo.setDisable(false);
        } else if (pageindex == totalpages) { // Si ultima pagina
            fromIndex = pageindex * maxrows;
            toIndex = facturasData.size();

            btnPrimero.setDisable(false);
            btnAnterior.setDisable(false);
            btnSiguiente.setDisable(true);
            btnUltimo.setDisable(true);
        } else { // Resto de paginas
            fromIndex = ((pageindex - 1) * maxrows);
            toIndex = ((pageindex - 1) * maxrows) + maxrows;

            btnSiguiente.setDisable(false);
            btnUltimo.setDisable(false);
            btnPrimero.setDisable(false);
            btnAnterior.setDisable(false);
        }

        tvFacturas.setItems(FXCollections.observableArrayList(facturasData.subList(fromIndex, toIndex)));
        lblPagina.setText(pageindex + " / " + totalpages);

    }

    /* -----------------------------------------------------------------------*/
 /*                           EVENTOS DE TABLA                             */
 /* -----------------------------------------------------------------------*/
    /**
     * Listener para seleccion en la tabla. Escucha si se ha seleccionado algun
     * elemento
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    private void handleFacturasTableSelectionChanged(ObservableValue observable, Object oldValue, Object newValue) {
        if (newValue != null) {
            FacturaBean factura = (FacturaBean) newValue;
            btnModificar.setDisable(false);
            btnEliminar.setDisable(false);

            if (!factura.getPagada()) {
                btnPagado.setDisable(false);
            }

        } else {
            btnModificar.setDisable(true);
            btnEliminar.setDisable(true);
            btnPagado.setDisable(true);
        }
    }

    /**
     * Accion al hacer doble click sobre row de la tabla
     */
    public void InitRowDoubleClickEvent() {
        tvFacturas.setRowFactory(tv -> {
            TableRow<FacturaBean> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    try {

                        loadCrearMod(tvFacturas.getSelectionModel().getSelectedItem());
                    } catch (Exception ex) {
                        logger.info("Error al cargar ventana nueva factura.");
                    }

                }
            });
            return row;
        });
    }
    
    public void reloadTable(){
        facturasData = FXCollections.observableArrayList(facturasLogicController.getAllFacturas());
        tvFacturas.setItems(facturasData);
        tvFacturas.refresh();
    }

    /* -----------------------------------------------------------------------*/
 /*                         GETTERS AND SETTERS                            */
 /* -----------------------------------------------------------------------*/
    /**
     * Obtener tabla de facturas
     *
     * @return
     */
    public TableView<FacturaBean> getTvFacturas() {
        return tvFacturas;
    }

    /**
     * Conecta Stage a controlador
     *
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
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
     * Pasa objeto interfaz de facturas.
     *
     * @param facturasLogicController
     */
    public void setFacturasManager(FacturasManager facturasLogicController) {
        this.facturasLogicController = facturasLogicController;
    }
}
