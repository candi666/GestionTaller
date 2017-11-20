package gestiontaller.gui.controller.clientes;

import gestiontaller.App;
import gestiontaller.config.GTConstants;
import gestiontaller.gui.controller.HomeController;
import static gestiontaller.gui.controller.HomeController.bundle;
import gestiontaller.logic.controller.ClientesManagerTestDataGenerator;
import gestiontaller.logic.interfaces.ClientesManager;
import gestiontaller.logic.bean.ClienteBean;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


/**
 * FXML Controller class
 *
 * @author Carlos
 */
public class ClientesController implements Initializable {
    private static final Logger logger = Logger.getLogger(ClientesController.class.getName());
    private Stage stage;
    private Stage ownerStage;
    private ClientesManager clientesLogicController;
    private ObservableList<ClienteBean> clientesData;
    private static final int rowsPerPage = GTConstants.MAX_ROWS_TABLE_CLIENTES;

    // <editor-fold defaultstate="collapsed" desc="@FXML NODES">
    @FXML
    private TableView<ClienteBean> tvClientes;
    @FXML
    private TableColumn tcId;
    @FXML
    private TableColumn<ClienteBean, SimpleStringProperty> tcDni;
    @FXML
    private TableColumn tcNombre;
    @FXML
    private TableColumn tcApellidos;
    @FXML
    private TableColumn tcEmail;
    @FXML
    private TableColumn tcTelefono;
    @FXML
    private Button btnHistorial;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnAnadir;
    @FXML
    private TextField tfBuscar;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnSalir;
    @FXML
    private Pagination pgClientes;
    

    // </editor-fold>
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnHistorial.setDisable(true);
        btnModificar.setDisable(true);
        btnEliminar.setDisable(true);
        
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
//        stage.setMaxWidth(1024);
        stage.setMinWidth(1024);
//        stage.setMaxHeight(1024);
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
        initContextMenu();
        initPagination();
        handleActionEvents();
    }
    
    /**
     * Modelo de creación de pagina para paginación.
     *
     * @param pageIndex
     * @return
     */
    private void initPagination() {

        pgClientes.setPageCount((clientesData.size() / rowsPerPage) + 1);
        pgClientes.setPageFactory(this::createPage);

    }

    public void setClientesManager(ClientesManager businessLogicController) {
        this.clientesLogicController=businessLogicController;
    }
    
    public void initTable(){
        // Obtener Collection de Facturas
        clientesData = FXCollections.observableArrayList(clientesLogicController.getAllClientes());
        
        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tcApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tcTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        tvClientes.getSelectionModel().selectedItemProperty().addListener(this::handleClientesTableSelectionChanged);
       
        
        tvClientes.setItems(clientesData);
    }
    
    public void initContextMenu(){
        final ContextMenu cm = new ContextMenu();
        MenuItem cmItem1 = new MenuItem("Eliminar");
        MenuItem cmItem2 = new MenuItem("Modificar");
        
        //ContextMenu Eliminar
        cmItem1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                actionEliminar();
            }
        });
        cm.getItems().add(cmItem1);
        tvClientes.addEventHandler(MouseEvent.MOUSE_CLICKED,
            new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent e) {
                    if (e.getButton() == MouseButton.SECONDARY)  
                        cm.show(tvClientes, e.getScreenX(), e.getScreenY());
                }
        });
        //ContextMenu Modificar
        cmItem2.setOnAction(e -> loadCrearMod(tvClientes.getSelectionModel().getSelectedItem()));
        
        cm.getItems().add(cmItem2);
        tvClientes.addEventHandler(MouseEvent.MOUSE_CLICKED,
            new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent e) {
                    if (e.getButton() == MouseButton.SECONDARY)  
                        cm.show(tvClientes, e.getScreenX(), e.getScreenY());
                }
        });
        
    }
    
    private void handleClientesTableSelectionChanged(ObservableValue observable, Object oldValue, Object newValue){
        if(newValue!=null){
            btnHistorial.setDisable(false);
            btnModificar.setDisable(false);
            btnEliminar.setDisable(false);
        }else{
            btnHistorial.setDisable(true);
            btnModificar.setDisable(true);
            btnEliminar.setDisable(true);
        }
        
    }
    
    /**
     * Obtiene datos del modelo y los carga en la tabla.
     */
    public void reloadTable() {
        clientesData = FXCollections.observableArrayList(clientesLogicController.getAllClientes());
        tvClientes.setItems(clientesData);

        initPagination();
        tvClientes.refresh();
    }
    
    private void handleActionEvents(){
        // CRUD
        btnAnadir.setOnAction(e -> loadCrearMod(null));
        btnModificar.setOnAction(e -> loadCrearMod(tvClientes.getSelectionModel().getSelectedItem()));
        btnBuscar.setOnAction(e -> actionBuscar());
    }
    
    /**
     * Acción Crear/Modificar Cliente
     */
    public void actionCrearMod(ClienteBean cliente) {
        /* facturasData: lista con todas las facturas
        *  tvFacturas.getItems(): lista de facturas en la tabla actualmente.
         */
        int cpindex = pgClientes.getCurrentPageIndex();

        if (cliente.getId() == 0) {
            if (clientesLogicController.createCliente(cliente)) {
                reloadTable();
                int pcount = pgClientes.getPageCount();

                // Si esta llena la pagina actual...
                if ((clientesData.size() - 1) > (pcount) * rowsPerPage) {
                    pgClientes.setPageCount(pcount + 1);
                    pgClientes.setCurrentPageIndex(pcount + 1);
                }
            }

        } else {

            clientesLogicController.updateCliente(cliente);
            reloadTable();
            tvClientes.refresh();
            pgClientes.setCurrentPageIndex(cpindex);
        }

    }
    
    @FXML
    private void actionEliminar() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("¿Desea eliminar el cliente?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            int cpindex = pgClientes.getCurrentPageIndex();

            if (clientesLogicController.deleteCliente(tvClientes.getSelectionModel().getSelectedItem())) {
                reloadTable();
                int pcount = pgClientes.getPageCount();

                // Si es la ultima row de una pagina...
                if ((clientesData.size()) < (pcount - 1) * (rowsPerPage)) {
                    pgClientes.setPageCount(pcount - 1);
                    if (cpindex == (pcount - 1)) {
                        cpindex--;
                    }
                }
            }

            // Si no es la primera pagina 
            if (cpindex > 0) {
                pgClientes.setCurrentPageIndex(cpindex);
            }
        } 
        
    }
    
    private void actionBuscar() {
        String criteria = tfBuscar.getText().trim();
        boolean res = false;
        
        if (!criteria.isEmpty()) {
            tvClientes.getItems().stream()
            .filter(item -> Objects.equals(item.getDni(), criteria))
            .findAny()
            .ifPresent((ClienteBean item) -> {
                tvClientes.getSelectionModel().select(item);
                tvClientes.scrollTo(item);
            });
            res = true;
        }
//        ObservableList<ClienteBean> searchResults = FXCollections.observableArrayList(clientesLogicController.getClienteByDni(criteria));
//        
//        if (!criteria.isEmpty()) {
//            clientesData.setAll(searchResults);
//            res = true;
//        }
        
        if (res) {
            tvClientes.setItems(clientesData);
            logger.log(Level.SEVERE, "entra.");
        } else {
            clientesData.clear();
            tvClientes.getItems().clear();
            logger.log(Level.SEVERE, ".........");
        }
        initPagination();
        tvClientes.refresh();

    }
    @FXML
    private void actionVolver() {
        stage.close();
        ownerStage.requestFocus();
    }
    
    private void loadCrearMod(ClienteBean cliente) {
        try {
            ClientesManager clientesLogicController = new ClientesManagerTestDataGenerator();
            FXMLLoader loader = new FXMLLoader(App.class.getResource("gui/view/clientes/nuevo_cliente.fxml"));
            loader.setResources(HomeController.bundle);
            AnchorPane root = (AnchorPane) loader.load();
            ClientesCuController ctr = ((ClientesCuController) loader.getController());
            ctr.setStage(new Stage());
            ctr.setClientesManager(clientesLogicController);
            ctr.setClientesController(this);
            
            // En caso de opción Modificar
            if (cliente != null) {
                ctr.setCliente(cliente);
            }

            ctr.setOwnerStage(stage);
            ctr.initStage(root);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error al cargar ventana nuevo_cliente.fxml.", ex);
        }
    }
    
    /**
     * Modelo de creación de pagina para paginación.
     *
     * @param pageIndex
     * @return
     */
    private Node createPage(int pageIndex) {

        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, clientesData.size());
        tvClientes.setItems(FXCollections.observableArrayList(clientesData.subList(fromIndex, toIndex)));

        return new BorderPane(tvClientes);
    }
    
    public TableView getTableView(){
        return this.tvClientes;
    }
}
