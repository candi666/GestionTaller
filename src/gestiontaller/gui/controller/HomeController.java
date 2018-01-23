package gestiontaller.gui.controller;

import gestiontaller.App;
import gestiontaller.config.GTConstants;
import gestiontaller.gui.controller.clientes.ClientesController;
import gestiontaller.gui.controller.facturas.FacturasController;
import gestiontaller.gui.controller.piezas.PiezasController;
import gestiontaller.gui.controller.reparaciones.ReparacionesController;

import gestiontaller.logic.controller.ClientesManagerTestDataGenerator;
import gestiontaller.logic.controller.FacturasManagerImp;

import gestiontaller.logic.controller.ClientesManagerImplementation;
import gestiontaller.logic.controller.FacturasManagerTestDataGenerator;

import gestiontaller.logic.interfaces.ClientesManager;
import gestiontaller.logic.interfaces.FacturasManager;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Carlos
 */
public class HomeController implements Initializable {

    private static final Logger logger = Logger.getLogger(HomeController.class.getName());
    public static ResourceBundle bundle;
    public static Locale locale;

    private Stage stage;

    // Para pruebas
    private static final int nfacturas = 140;
    private static final int nclientes = 140;
    @FXML
    private Button btnClientes;
    @FXML
    private Button btnFacturas;
    @FXML
    private Button btnReparaciones;
    @FXML
    private Button btnPiezas;
    @FXML
    private Menu mArchivo;
    @FXML
    private Menu mPreferencias;
    @FXML
    private Menu mAyuda;
    @FXML
    private MenuItem mISalir;
    @FXML
    private MenuItem mniClientes;
    @FXML
    private MenuItem mniFacturas;

        /* -----------------------------------------------------------------------*/
 /*                                     INIT                                    */
 /* -----------------------------------------------------------------------*/
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO get actual local language
        loadLang("es");
    }

    /**
     * Conecta Stage a controlador
     *
     * @param stage stage actual
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

        stage.setTitle("Gestión de taller");
        stage.setResizable(false);
        stage.getIcons().add(new Image(GTConstants.PATH_LOGO));

        stage.setOnShowing(this::handleWindowShowing);
        stage.setMaxWidth(500);
        stage.setMinWidth(500);
        stage.setMaxHeight(400);
        stage.setMinHeight(400);
        stage.show();
        logger.info("Módulo clientes abierto.");
    }

    /**
     * Handle on window showing
     *
     * @param event
     */
    private void handleWindowShowing(WindowEvent event) {
        handleActionEvents();
        initAccelerators();
        
    }
    
    /**
     * Carga lenguaje para la aplicación
     * @param lang 
     */
    private void loadLang(String lang) {
        locale = new Locale(lang);
        bundle = ResourceBundle.getBundle("resources.properties.MessageStrings", locale);
    }

    /**
     * Definición de aceleradores
     */
    private void initAccelerators(){
        mniClientes.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));
        mniFacturas.setAccelerator(new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));
        mISalir.setAccelerator(KeyCombination.keyCombination("shortcut+Q"));
    }
        /* -----------------------------------------------------------------------*/
 /*                                ACTIONS                         */
 /* -----------------------------------------------------------------------*/
    /**
     * Iniciar y mostrar ventana de clientes
     */
    @FXML
    private void loadClientes() {
        try {
            ClientesManager clientesLogicController = new ClientesManagerImplementation();
            FXMLLoader loader = new FXMLLoader(App.class.getResource("gui/view/clientes/modulo_clientes.fxml"));
            loader.setResources(bundle);
            AnchorPane root = (AnchorPane) loader.load();

            ClientesController ctr = ((ClientesController) loader.getController());

            ctr.setClientesManager(clientesLogicController);

            ctr.setOwnerStage(stage);
            ctr.setStage(new Stage());
            ctr.initStage(root);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error al cargar modulo clientes.", ex);
        }
    }

    /**
     * Iniciar y mostrar ventana de facturas
     */
    @FXML
    private void loadFacturas() {
        try {
            FacturasManager facturasLogicController = new FacturasManagerImp();
            FXMLLoader loader = new FXMLLoader(App.class.getResource("gui/view/facturas/modulo_facturas.fxml"));
            loader.setResources(bundle);

            AnchorPane root = (AnchorPane) loader.load();

            FacturasController ctr = ((FacturasController) loader.getController());
            ctr.setFacturasManager(facturasLogicController);
            ctr.setOwnerStage(stage);
            ctr.setStage(new Stage());
            ctr.initStage(root);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error al cargar modulo facturas.", ex);
        }
    }
    
    /**
     * Iniciar y mostrar ventana de piezas
     */
    @FXML
    private void loadPiezas() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("gui/view/piezas/modulo_piezas.fxml"));
            loader.setResources(bundle);
            AnchorPane root = (AnchorPane) loader.load();

            PiezasController ctr = ((PiezasController) loader.getController());

            ctr.setOwnerStage(stage);
            ctr.setStage(new Stage());
            ctr.initStage(root);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error al cargar modulo piezas.", ex);
        }
    }
    
    /**
     * Iniciar y mostrar ventana de reparaciones
     */
    @FXML
    private void loadReparaciones() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("gui/view/reparaciones/modulo_reparaciones.fxml"));
            loader.setResources(bundle);
            AnchorPane root = (AnchorPane) loader.load();

            ReparacionesController ctr = ((ReparacionesController) loader.getController());

            ctr.setOwnerStage(stage);
            ctr.setStage(new Stage());
            ctr.initStage(root);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error al cargar modulo reparaciones.", ex);
        }
    }

        /* -----------------------------------------------------------------------*/
 /*                                HANDLERS                            */
 /* -----------------------------------------------------------------------*/
    /**
     * Handle onAction para menu item salir.
     *
     * @param event
     */
    @FXML
    private void handleMiClose(ActionEvent event) {
        logger.info("Fin de ejecución.");
        Platform.exit();
    }
    
    /**
     * Especifica comportamiento onAction de botones.
     */
    private void handleActionEvents() {
        btnClientes.setOnAction(e -> loadClientes());
        btnFacturas.setOnAction(e -> loadFacturas());
        btnReparaciones.setOnAction(e -> loadReparaciones());
        btnPiezas.setOnAction(e -> loadPiezas());

    }
    
}
