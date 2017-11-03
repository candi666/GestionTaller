package gestiontaller.gui.controller.clientes;

import gestiontaller.logic.interfaces.ClientesManager;
import gestiontaller.logic.javaBean.ClienteBean;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Carlos
 */
public class ClientesController implements Initializable {

    private Stage stage;
    private Stage ownerStage;
    private ClientesManager businessLogicController;

    // <editor-fold defaultstate="collapsed" desc="@FXML NODES">
    @FXML
    private TableView<ClienteBean> tvClientes;
    @FXML
    private TableColumn id;
    @FXML
    private TableColumn DNI;
    @FXML
    private TableColumn nombre;
    @FXML
    private TableColumn apellidos;
    @FXML
    private TableColumn email;
    @FXML
    private TableColumn telefono;
    @FXML
    private ImageView btnPrimero;
    @FXML
    private ImageView btnAnterior;
    @FXML
    private ImageView btnSiguiente;
    @FXML
    private ImageView btnUltimo;
    @FXML
    private ImageView btnHistorial;
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
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        DNI.setCellValueFactory(new PropertyValueFactory<>("DNI"));
        nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        apellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        telefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        
        ObservableList<ClienteBean> clientesData = FXCollections.observableArrayList(businessLogicController.getAllClientes());
        
        tvClientes.setItems(clientesData);
    }

    public void setClientesManager(ClientesManager businessLogicController) {
        this.businessLogicController=businessLogicController;
    }
}
