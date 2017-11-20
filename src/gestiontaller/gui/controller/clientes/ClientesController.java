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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
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
 * @author Ionut
 */
public class ClientesController implements Initializable {

    private static final Logger logger = Logger.getLogger(ClientesController.class.getName());
    private Stage stage;
    private Stage ownerStage;
    private ClientesManager clientesLogicController;
    private ObservableList<ClienteBean> clientesData;
    private static int rowsPerPage = GTConstants.MAX_ROWS_TABLE_CLIENTES;

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
     * @param url ..
     * @param rb ..
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
        stage.getIcons().add(new Image(GTConstants.PATH_LOGO));
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
        handleKeysOnTable();
        handleTvClientesHeightChanged();
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

    /**
     *
     * @param clientesLogicController ..
     */
    public void setClientesManager(ClientesManager clientesLogicController) {
        this.clientesLogicController = clientesLogicController;
    }

    /**
     *
     */
    public void initTable() {
        tableColumnResizeBinds();
        
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

    /**
     *
     */
    public void initContextMenu() {
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
            @Override
            public void handle(MouseEvent e) {
                if (e.getButton() == MouseButton.SECONDARY) {
                    cm.show(tvClientes, e.getScreenX(), e.getScreenY());
                }
            }
        });
        //ContextMenu Modificar
        cmItem2.setOnAction(e -> loadCrearMod(tvClientes.getSelectionModel().getSelectedItem()));

        cm.getItems().add(cmItem2);
        tvClientes.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (e.getButton() == MouseButton.SECONDARY) {
                    cm.show(tvClientes, e.getScreenX(), e.getScreenY());
                }
            }
        });

    }
    
    /**
     * Redimensionado de las columnas al maximizar ventana
     */
    public void tableColumnResizeBinds() {

        tvClientes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tcId.setResizable(false);
        tcDni.setResizable(false);
        tcNombre.setResizable(false);
        tcApellidos.setResizable(false);
        tcEmail.setResizable(false);
        tcTelefono.setResizable(false);

        tcId.setMaxWidth(10000);
        tcDni.setMaxWidth(20000);
        tcNombre.setMaxWidth(20000);
        tcApellidos.setMaxWidth(10000);
        tcEmail.setMaxWidth(20000);
        tcTelefono.setMaxWidth(20000);

        tcId.prefWidthProperty().bind(tvClientes.widthProperty().multiply(0.1));
        tcDni.prefWidthProperty().bind(tvClientes.widthProperty().multiply(0.2));
        tcNombre.prefWidthProperty().bind(tvClientes.widthProperty().multiply(0.2));
        tcApellidos.prefWidthProperty().bind(tvClientes.widthProperty().multiply(0.2));
        tcEmail.prefWidthProperty().bind(tvClientes.widthProperty().multiply(0.2));
        tcTelefono.prefWidthProperty().bind(tvClientes.widthProperty().multiply(0.1));
    }

    private void handleClientesTableSelectionChanged(ObservableValue observable, Object oldValue, Object newValue) {
        if (newValue != null) {
            btnHistorial.setDisable(false);
            btnModificar.setDisable(false);
            btnEliminar.setDisable(false);
        } else {
            btnHistorial.setDisable(true);
            btnModificar.setDisable(true);
            btnEliminar.setDisable(true);
        }

    }
    
    /**
     * Redimensionado de los valores de la tabla al maximizar ventana
     */
    public void handleTvClientesHeightChanged() {
        tvClientes.heightProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue.doubleValue() > 0 && oldValue.doubleValue() != 0) {

                int currentItemIndex = 0;
                ClienteBean selectedFactura = tvClientes.getSelectionModel().getSelectedItem();

                if (selectedFactura != null) {
                    currentItemIndex = (pgClientes.getCurrentPageIndex() * rowsPerPage) + tvClientes.getSelectionModel().getSelectedIndex();
                }

                Double rpp = (newValue.doubleValue() - GTConstants.DEFAULT_ROW_HEIGHT) / GTConstants.DEFAULT_ROW_HEIGHT;
                rowsPerPage = rpp.intValue();

                pgClientes.setPageCount((clientesData.size() / rowsPerPage) + 1);

                if (currentItemIndex > rowsPerPage) {
                    int pageIndex = (int) (currentItemIndex / rowsPerPage);
                    pgClientes.setCurrentPageIndex(pageIndex);
                    tvClientes.getSelectionModel().select(selectedFactura);
                } else {
                    pgClientes.setCurrentPageIndex(-1);
                }
                tvClientes.refresh();

                if (tvClientes.isFocused()) {
                    tfBuscar.requestFocus();
                } else {
                    tvClientes.requestFocus();
                }
            }
        });
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

    private void handleActionEvents() {
        // CRUD
        btnAnadir.setOnAction(e -> loadCrearMod(null));
        btnModificar.setOnAction(e -> loadCrearMod(tvClientes.getSelectionModel().getSelectedItem()));
        btnBuscar.setOnAction(e -> actionBuscar());
    }

    /**
     * Acción Crear/Modificar Cliente
     * @param cliente
     */
    public void actionCrearMod(ClienteBean cliente) {
        int cpindex = pgClientes.getCurrentPageIndex();

        if (cliente.getId() == 0) {
            if (clientesLogicController.createCliente(cliente)) {
                reloadTable();
                int pcount = pgClientes.getPageCount();

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
        if (result.get() == ButtonType.OK) {
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
            
            ObservableList<ClienteBean> searchResults = FXCollections.observableArrayList(clientesLogicController.getClientesByCriteria(criteria));
            if (!searchResults.isEmpty()) {
                clientesData.setAll(searchResults);
                res = true;
            }
        }else{
            clientesData = FXCollections.observableArrayList(clientesLogicController.getAllClientes());
            res = true;
        }

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

    /**
     *
     * @return
     */
    public TableView getTableView() {
        return this.tvClientes;
    }
    
    private void handleKeysOnTable() {
        tvClientes.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case ENTER:
                        if (tvClientes.getSelectionModel().getSelectedItem() != null) {
                            loadCrearMod(tvClientes.getSelectionModel().getSelectedItem());
                        }
                        break;
                    
                    case DELETE:
                        if (tvClientes.getSelectionModel().getSelectedItem() != null) {
                            actionEliminar();
                        }
                        break;
                        
                    default:
                        break;
                }
            }
        });
    }
}
