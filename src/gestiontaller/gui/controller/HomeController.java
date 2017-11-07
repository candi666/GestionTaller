package gestiontaller.gui.controller;

import gestiontaller.App;
import gestiontaller.gui.controller.clientes.ClientesController;
import gestiontaller.gui.controller.facturas.FacturasController;
import gestiontaller.gui.controller.piezas.PiezasController;
import gestiontaller.gui.controller.reparaciones.ReparacionesController;
import gestiontaller.logic.controller.ClientesManagerTestDataGenerator;
import gestiontaller.logic.controller.FacturasManagerTestDataGenerator;
import gestiontaller.logic.interfaces.ClientesManager;
import gestiontaller.logic.interfaces.FacturasManager;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Carlos
 */
public class HomeController implements Initializable {
    private static final Logger logger = Logger.getLogger( HomeController.class.getName() );
    private Stage stage;
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    /**
     * Conecta Stage a controlador
     * @param stage
     */
    public void setStage(Stage stage){
        this.stage=stage;
    }
    
    /**
     * Inicializa la stage
     * @param root Elemento Parent del fxml
     */
    public void initStage(Parent root){
        Scene scene = new Scene(root);
        stage.setScene(scene);
        
        stage.setTitle("Gestión de taller");
        stage.setResizable(false);
        
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
     * @param event 
     */
    private void handleWindowShowing(WindowEvent event){
        // TODO estado inicial
    }

    // *************** ON ACTION HANDLERS ******************* //
    /**
     * Handle onAction para boton piezas.
     * @param event 
     */
    @FXML
    private void btnPiezasActionHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("gui/view/piezas/modulo_piezas.fxml"));
            AnchorPane root = (AnchorPane)loader.load();
            
            PiezasController ctr = ((PiezasController)loader.getController());
            
            ctr.setOwnerStage(stage);
            ctr.setStage(new Stage());
            ctr.initStage(root); 
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error al cargar modulo piezas.", ex);
        }
    }
    
    /**
     * Handle onAction para boton clientes.
     * @param event 
     */
    @FXML
    private void btnClientesActionHandler(ActionEvent event) {
        try {
            ClientesManager clientesLogicController=new ClientesManagerTestDataGenerator();
            FXMLLoader loader = new FXMLLoader(App.class.getResource("gui/view/clientes/modulo_clientes.fxml"));
            AnchorPane root = (AnchorPane)loader.load();
            
            ClientesController ctr = ((ClientesController)loader.getController());
            
            ctr.setClientesManager(clientesLogicController);
            
            ctr.setOwnerStage(stage);
            ctr.setStage(new Stage());
            ctr.initStage(root); 
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error al cargar modulo clientes.", ex);
        }
    }
    /**
     * Handle onAction para boton reparaciones.
     * @param event 
     */
    @FXML
    private void btnReparacionesActionHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("gui/view/reparaciones/modulo_reparaciones.fxml"));
            AnchorPane root = (AnchorPane)loader.load();
            
            ReparacionesController ctr = ((ReparacionesController)loader.getController());
            
            ctr.setOwnerStage(stage);
            ctr.setStage(new Stage());
            ctr.initStage(root); 
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error al cargar modulo reparaciones.", ex);
        }
    }
    /**
     * Handle onAction para boton facturas.
     * @param event 
     */
    @FXML
    private void btnFacturasActionHandler(ActionEvent event) {
        try {
            FacturasManager facturasLogicController=new FacturasManagerTestDataGenerator();
            FXMLLoader loader = new FXMLLoader(App.class.getResource("gui/view/facturas/modulo_facturas.fxml"));
            AnchorPane root = (AnchorPane)loader.load();
            
            FacturasController ctr = ((FacturasController)loader.getController());
            ctr.setFacturasManager(facturasLogicController);
            ctr.setOwnerStage(stage);
            ctr.setStage(new Stage());
            ctr.initStage(root); 
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error al cargar modulo facturas.", ex);
        }
    }
    /**
     * Handle onAction para menu item salir.
     * @param event 
     */
    @FXML
    private void handleMiClose(ActionEvent event) {
        logger.info("Fin de ejecución.");
        Platform.exit();
    }

}
