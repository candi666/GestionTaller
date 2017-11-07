/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiontaller.gui.controller.facturas;

import gestiontaller.App;
import gestiontaller.logic.interfaces.FacturasManager;
import gestiontaller.logic.javaBean.ClienteBean;
import gestiontaller.logic.javaBean.FacturaBean;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    private static final Logger logger = Logger.getLogger( FacturasController.class.getName() );
    private Stage stage;
    private Stage ownerStage;
    private FacturasManager facturasLogicController;
    ObservableList<FacturaBean> facturasData;

    // <editor-fold defaultstate="collapsed" desc="@FXML NODES">
    @FXML
    private ImageView btnPrimero;
    @FXML
    private ImageView btnAnterior;
    @FXML
    private ImageView btnSiguiente;
    @FXML
    private ImageView btnUltimo;
    @FXML
    private ImageView btnPagado;
    @FXML
    private ImageView btnModificar;
    @FXML
    private ImageView btnEliminar;
    @FXML
    private ImageView btnAnadir;
    @FXML
    private ComboBox<String> cbFiltro;
    @FXML
    private TextField tfBuscar;
    @FXML
    private ImageView btnBuscar;
    @FXML
    private CheckBox chbPendientes;
    @FXML
    private ImageView btnSalir;
    @FXML
    private TableView<FacturaBean> tvFacturas;
    @FXML
    private TableColumn tcId;
    @FXML
    private TableColumn tcFecha;
    @FXML
    private TableColumn tcFechaVenc;
    @FXML
    private TableColumn tcIdReparacion;
    @FXML
    private TableColumn tcIdCliente;
    @FXML
    private TableColumn tcTotal;
    @FXML
    private TableColumn tcPagado;

    // </editor-fold>
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initialStatus();
    }

    /* -----------------------------------------------------------------------*/
    /*                        METODOS DE INICIALIZACIÓN                       */
    /* -----------------------------------------------------------------------*/
    /**
     * Conecta Stage a controlador
     *
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Inicializa la stage
     *
     * @param root Elemento Parent del fxml
     */
    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.setTitle("Gestión de taller - Clientes");
        stage.setResizable(true);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(ownerStage);
        stage.setOnShowing(this::handleWindowShowing);
        stage.setMaxWidth(1024);
        stage.setMinWidth(748);
        stage.setMaxHeight(1024);
        stage.setMinHeight(748);
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
     * Handle on window showing
     *
     * @param event
     */
    private void handleWindowShowing(WindowEvent event) {
        initTable();
    }

    public void setFacturasManager(FacturasManager facturasLogicController) {
        this.facturasLogicController = facturasLogicController;
    }
    
    /**
     * Establece estado inicial para los elementos de la ventana.
     */
    private void initialStatus(){
        btnEliminar.setDisable(true);
        btnModificar.setDisable(true);
        btnAnadir.setDisable(false);
    }
    
    /**
     * Formato y carga de datos a tabla.
     */
    private void initTable() {
        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        tcFechaVenc.setCellValueFactory(new PropertyValueFactory<>("fechavenc"));
        tcIdReparacion.setCellValueFactory(new PropertyValueFactory<>("idreparacion"));
        tcIdCliente.setCellValueFactory(new PropertyValueFactory<>("idcliente"));
        tcTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        tcPagado.setCellValueFactory(new PropertyValueFactory<>("pagado"));

        facturasData = FXCollections.observableArrayList(facturasLogicController.getAllFacturas());
        tvFacturas.setItems(facturasData);
    }

    /* -----------------------------------------------------------------------*/
    /*                            ACCIONES BOTONES                            */
    /* -----------------------------------------------------------------------*/
    /**
     * Acción borrar factura
     */
    @FXML
    private void actionBorrar(){
        //TODO
    }
    /**
     * Acción modificar factura
     */
    @FXML
    private void actionModificar(){
        //TODO
    }
    /**
     * Acción crear factura
     */
    @FXML
    private void actionCrear(){
        //TODO
    }
    /**
     * Cambiar estado de la factura
     */
    @FXML
    private void actionPagar() {
        stage.close();
        ownerStage.requestFocus();
    }
    /**
     * Buscar
     */
    @FXML
    private void actionBuscar() {
        stage.close();
        ownerStage.requestFocus();
    }
    /**
     * Cierra stage actual y enfoca el stage home.
     */
    @FXML
    private void actionVolver() {
        stage.close();
        ownerStage.requestFocus();
    }
    
    private void loadCrearMod(FacturaBean factura) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("gui/view/facturas/nueva_factura.fxml"));
            AnchorPane root = (AnchorPane)loader.load();
            
            FacturasCuController ctr = ((FacturasCuController)loader.getController());
//            ctr.setFacturasManager(facturasLogicController);
            ctr.setOwnerStage(stage);
//            ctr.setStage(new Stage());
            ctr.initStage(root, "Modificar Factura"); 
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error al cargar ventana nueva_factura.fxml.", ex);
        }
    }
}
