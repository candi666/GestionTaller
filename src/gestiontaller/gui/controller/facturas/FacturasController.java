/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiontaller.gui.controller.facturas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Carlos
 */
public class FacturasController implements Initializable {
    Stage stage;
    Stage ownerStage;
    
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
    private ComboBox<?> cbFiltro;
    @FXML
    private TextField tfBuscar;
    @FXML
    private ImageView btnBuscar;
    @FXML
    private CheckBox chbPendientes;
    @FXML
    private ImageView btnSalir;
    // </editor-fold>
    
    /**
     * Initializes the controller class.
     */
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
     * @param ownerStage 
     */
    public void setOwnerStage(Stage ownerStage){
        this.ownerStage=ownerStage;
    }
    
    /**
     * Handle on window showing
     * @param event 
     */
    private void handleWindowShowing(WindowEvent event){
        // TODO estado inicial
    }
}
