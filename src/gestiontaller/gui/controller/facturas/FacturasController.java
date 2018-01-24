package gestiontaller.gui.controller.facturas;

import gestiontaller.App;
import gestiontaller.config.GTConstants;
import gestiontaller.gui.controller.HomeController;
import gestiontaller.logic.interfaces.FacturasManager;
import gestiontaller.logic.bean.FacturaBean;
import gestiontaller.logic.util.FieldValidator;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import static javafx.scene.input.KeyCode.S;
import static javafx.scene.input.KeyCode.T;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

/**
 * FXML Controller class Controlador para ventana de gestión de facturas.
 *
 * @author Carlos
 */
public class FacturasController implements Initializable {

    private static final Logger logger = Logger.getLogger(FacturasController.class.getName());
    private Stage stage;
    private Stage ownerStage;
    private FacturasManager facturasLogicController;
    private ObservableList<FacturaBean> facturasData;
    private int rowsPerPage = GTConstants.MAX_ROWS_TABLE_FACTURAS;
    DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

    // <editor-fold defaultstate="collapsed" desc="@FXML NODES">
    @FXML
    private Button btnPagado;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnAnadir;
    @FXML
    private ComboBox<String> cbCriteria;
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
    private TableColumn<FacturaBean, Date> tcFecha;
    @FXML
    private TableColumn<FacturaBean, Date> tcFechaVenc;
    @FXML
    private TableColumn<FacturaBean, SimpleObjectProperty> tcIdReparacion;
    @FXML
    private TableColumn<FacturaBean, SimpleObjectProperty> tcIdCliente;
    @FXML
    private TableColumn<FacturaBean, SimpleDoubleProperty> tcTotal;
    @FXML
    private TableColumn<FacturaBean, SimpleBooleanProperty> tcPagada;
    @FXML
    private Pagination pgFacturas;
    @FXML
    private DatePicker dpFromDate;
    @FXML
    private DatePicker dpToDate;
    @FXML
    private AnchorPane rootPane;

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
     * Inicializa stage
     *
     * @param root Elemento Parent del fxml
     */
    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.setTitle(HomeController.bundle.getString("app.gui.facturas.stage.title"));
        stage.setResizable(true);
        stage.getIcons().add(new Image(GTConstants.PATH_LOGO));

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(ownerStage);
        stage.setOnShowing(this::handleWindowShowing);
        //stage.setMaxWidth(1024);
        stage.setMinWidth(1024);
        //stage.setMaxHeight(748);
        stage.setMinHeight(748);
        stage.show();

    }

    /**
     * Handle on window showing
     *
     * @param event
     */
    private void handleWindowShowing(WindowEvent event) {
        handleActionEvents();
        handleKeysOnTable();
        initActionPanel();
        initTable();
        initPagination();
        initContextMenu();
        handleTvFacturasHeightChanged();
        initTooltips();
    }

    /**
     * Actualiza el numero de paginas
     */
    private void initPagination() {

        pgFacturas.setPageCount(((facturasData == null ? 0 : facturasData.size()) / rowsPerPage) + 1);
        pgFacturas.setPageFactory(this::createPage);
    }

    /**
     * Inicializa estado inicial del panel superior
     */
    private void initActionPanel() {
        btnEliminar.setDisable(true);
        btnModificar.setDisable(true);
        btnPagado.setDisable(true);
        btnAnadir.setDisable(false);

        // Inicializa combobox criteria
        cbCriteria.getItems().add(0, HomeController.bundle.getString("app.gui.facturas.cbfiltro.todas"));
        cbCriteria.getItems().add(1, HomeController.bundle.getString("app.gui.facturas.cbfiltro.id"));
        cbCriteria.getItems().add(2, HomeController.bundle.getString("app.gui.facturas.cbfiltro.cliente"));
        cbCriteria.getItems().add(3, HomeController.bundle.getString("app.gui.facturas.cbfiltro.reparacion"));
        cbCriteria.setValue(HomeController.bundle.getString("app.gui.facturas.cbfiltro.todas"));
        cbCriteria.setOnAction(this::handleCbCriteriaValueChange);
        tfBuscar.setDisable(true);
    }

    private void initTooltips() {
        // Tooltip fecha
        Tooltip tipFecha = new Tooltip("Ej: 01/01/2017 ó 01-01-2017");
        tipFecha.setAutoFix(true);
        tipFecha.setWrapText(true);
        tipFecha.setMaxSize(200, 60);
        Tooltip.install(dpFromDate, tipFecha);
        Tooltip.install(dpToDate, tipFecha);

        // Tooltip crear
        Tooltip tipAdd = new Tooltip("Crear");
        tipFecha.setAutoFix(true);
        tipFecha.setWrapText(true);
        tipFecha.setMaxSize(200, 60);
        Tooltip.install(btnAnadir, tipAdd);

        // Tooltip modificar
        Tooltip tipUpdate = new Tooltip("Modificar");
        tipFecha.setAutoFix(true);
        tipFecha.setWrapText(true);
        tipFecha.setMaxSize(200, 60);
        Tooltip.install(btnModificar, tipUpdate);

        // Tooltip eliminar
        Tooltip tipDelete = new Tooltip("Eliminar");
        tipFecha.setAutoFix(true);
        tipFecha.setWrapText(true);
        tipFecha.setMaxSize(200, 60);
        Tooltip.install(btnEliminar, tipDelete);

        // Tooltip pagar
        Tooltip tipPay = new Tooltip("Pagar");
        tipFecha.setAutoFix(true);
        tipFecha.setWrapText(true);
        tipFecha.setMaxSize(200, 60);
        Tooltip.install(btnPagado, tipPay);

    }

    /**
     * Formato y carga de datos a tabla.
     */
    private void initTable() {
        // Add binds
        tableColumnResizeBinds();

        // Add Listeners
        tvFacturas.getSelectionModel().selectedItemProperty().addListener(this::handleFacturasTableSelectionChanged);

        InitRowDoubleClickEvent();

        // Obtener Collection de Facturas
        facturasData = FXCollections.observableArrayList(facturasLogicController.getAllFacturas());

        // Placeholder en caso de no tener datos
        tvFacturas.setPlaceholder(new Label(HomeController.bundle.getString("app.gui.facturas.tableview.noresult")));

        // TableCellFactory
        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        // Custom rendering of the table cell.
        tcFecha.setCellFactory(column -> {
            return new TableCell<FacturaBean, Date>() {
                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        // Format date.
                        setText(dateFormatter.format(item));
                    }
                }
            };
        });

        tcFechaVenc.setCellValueFactory(new PropertyValueFactory<>("fechavenc"));
        // Custom rendering of the table cell.
        tcFechaVenc.setCellFactory(column -> {
            return new TableCell<FacturaBean, Date>() {
                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        // Format date.
                        setText(dateFormatter.format(item));
                    }
                }
            };
        });

        tcIdReparacion.setCellValueFactory(new PropertyValueFactory<>("reparacion"));
        tcIdCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));

        tcTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        /* Definición de columna Pagado.
         * Definir un comportamiento cuando sea true y otro para false.
         * De momento true o false, por implementar cambio de color o iconos.
         */
        tcPagada.setSortable(false);
        tcPagada.setCellValueFactory(new PropertyValueFactory<>("pagada"));

        // Añadir datos iniciales a la tabla
        tvFacturas.setItems(FXCollections.observableArrayList(facturasData.subList(0, rowsPerPage)));
    }

    /**
     * Inicializa menu contextual
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
        tvFacturas.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (e.getButton() == MouseButton.SECONDARY) {
                    cm.show(tvFacturas, e.getScreenX(), e.getScreenY());
                }
            }
        });
        //ContextMenu Modificar
        cmItem2.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                loadCrearMod(tvFacturas.getSelectionModel().getSelectedItem());
            }
        });
        cm.getItems().add(cmItem2);
        tvFacturas.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (e.getButton() == MouseButton.SECONDARY) {
                    cm.show(tvFacturas, e.getScreenX(), e.getScreenY());
                }
            }
        });

    }

    /**
     * Binds para cambiar el tamaño de las columnas cuando cambie el tamaño de
     * la tabla.
     */
    public void tableColumnResizeBinds() {

        tvFacturas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tcId.setResizable(false);
        tcFecha.setResizable(false);
        tcFechaVenc.setResizable(false);
        tcIdReparacion.setResizable(false);
        tcIdCliente.setResizable(false);
        tcTotal.setResizable(false);
        tcPagada.setResizable(false);

        tcId.setMaxWidth(10000);
        tcFecha.setMaxWidth(20000);
        tcFechaVenc.setMaxWidth(20000);
        tcIdReparacion.setMaxWidth(10000);
        tcIdCliente.setMaxWidth(10000);
        tcTotal.setMaxWidth(40000);
        tcPagada.setMaxWidth(10000);

        tcId.prefWidthProperty().bind(tvFacturas.widthProperty().multiply(0.1));
        tcFecha.prefWidthProperty().bind(tvFacturas.widthProperty().multiply(0.2));
        tcFechaVenc.prefWidthProperty().bind(tvFacturas.widthProperty().multiply(0.2));
        tcIdReparacion.prefWidthProperty().bind(tvFacturas.widthProperty().multiply(0.1));
        tcIdCliente.prefWidthProperty().bind(tvFacturas.widthProperty().multiply(0.1));
        tcTotal.prefWidthProperty().bind(tvFacturas.widthProperty().multiply(0.2));
        tcPagada.prefWidthProperty().bind(tvFacturas.widthProperty().subtract(20).multiply(0.1));
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

        // FILTER 
        chbPendientes.setOnAction((event) -> {
            if (chbPendientes.isSelected()) {
                FilteredList<FacturaBean> filteredData = new FilteredList<>(facturasData, p -> !p.getPagada());
                tvFacturas.setItems(filteredData);
                pgFacturas.setPageCount(filteredData.size() / rowsPerPage + 1);

            } else {
                tvFacturas.setItems(facturasData);
            }

        });

        // SEARCH
        btnBuscar.setOnAction(e -> actionBuscar());

    }

    /**
     * Acción borrar factura
     */
    @FXML
    private void actionEliminar() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("¿Desea eliminar la factura?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            // Index al comenzar acción eliminar
            int cpindex = pgFacturas.getCurrentPageIndex();

            if (facturasLogicController.deleteFactura(tvFacturas.getSelectionModel().getSelectedItem())) {
                reloadTable();
                int pcount = pgFacturas.getPageCount();
                int x = facturasData.size() + 1;
                int y = (pcount - 1) * (rowsPerPage);

                // Si es la ultima row de una pagina...
                if ((facturasData.size()) < (pcount - 1) * (rowsPerPage)) {
                    pgFacturas.setPageCount(pcount - 1);
                    if (cpindex == (pcount - 1)) {
                        cpindex--;
                    }
                }
            }

            // Si no es la primera pagina 
            if (cpindex > 0) {
                pgFacturas.setCurrentPageIndex(cpindex);
            }
        }
    }

    /**
     * Accion crear/modificar factura
     *
     * @param factura If null entonces crear factura
     */
    public void actionCrearMod(FacturaBean factura) {
//        /* facturasData: lista con todas las facturas
//        *  tvFacturas.getItems(): lista de facturas en la tabla actualmente.
//         */
//        int cpindex = pgFacturas.getCurrentPageIndex();
//
//        // Caso nueva factura
//        if (factura.getId() == 0) {
//            
//
//        } else { // Caso modificar factura
//
//            facturasLogicController.updateFactura(factura);
//            reloadTable();
//            tvFacturas.refresh();
//            pgFacturas.setCurrentPageIndex(cpindex);
//            tvFacturas.getSelectionModel().select(factura);
//        }

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
    private void actionBuscar() {
        // TODO Implementar busqueda en bases de datos.

        String criteria = tfBuscar.getText().trim();
        LocalDate fromDate = dpFromDate.getValue() == null ? LocalDate.now().minusYears(10) : dpFromDate.getValue();
        LocalDate toDate = dpToDate.getValue() == null ? LocalDate.now() : dpToDate.getValue();

        boolean res = false;

        /**
         * Busqueda TODAS (Todas las facturas en un rango de fechas)
         */
        if (cbCriteria.getSelectionModel().getSelectedIndex() == GTConstants.CRITERIA_INDEX_ALL) {

            ObservableList<FacturaBean> searchResults = FXCollections.observableArrayList(facturasLogicController.getFacturasByDate(fromDate, toDate));
            System.out.println(searchResults.size());
            if (!searchResults.isEmpty()) {
                facturasData.setAll(searchResults);
                res = true;
            }

        } else if (!criteria.isEmpty()) {
            switch (cbCriteria.getSelectionModel().getSelectedIndex()) {
                // Buscar factura por ID
                case GTConstants.CRITERIA_INDEX_ID: {
                    if (FieldValidator.isInteger(criteria)) {

                        FacturaBean factura = facturasLogicController.getFacturaById(Integer.parseInt(criteria));
                        if (factura != null) {
                            facturasData.setAll(factura);
                            res = true;
                        }
                    }
                    break;
                }

                // Buscar factura por id cliente + fecha
                case GTConstants.CRITERIA_INDEX_CLIENTE:

                    if (FieldValidator.isInteger(criteria)) {
                        ObservableList<FacturaBean> searchResults
                                = FXCollections.observableArrayList(
                                        facturasLogicController.getFacturasByCliente(Integer.parseInt(criteria),
                                                fromDate, toDate));

                        if (!searchResults.isEmpty()) {
                            facturasData.setAll(searchResults);
                            res = true;
                        }
                    }
                    break;

                // Buscar factura por id reparación    
                case GTConstants.CRITERIA_INDEX_REPARACION: {
                    if (FieldValidator.isInteger(criteria)) {
                        FacturaBean factura = facturasLogicController.getFacturaByReparacion(Integer.parseInt(criteria));
                        if (factura != null) {
                            facturasData.setAll(factura);
                            res = true;
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
        if (res) {
            tvFacturas.setItems(facturasData);
        } else {
            facturasData.clear();
            tvFacturas.getItems().clear();
        }
        initPagination();
        tvFacturas.refresh();

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
            loader.setResources(HomeController.bundle);
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
     * Acciones con teclado cuando tableview is focused 1. ENTER -> modificar.
     * 2. DELETE -> eliminar.
     */
    private void handleKeysOnTable() {
        tvFacturas.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case ENTER:
                        if (tvFacturas.getSelectionModel().getSelectedItem() != null) {
                            loadCrearMod(tvFacturas.getSelectionModel().getSelectedItem());
                        }
                        break;

                    case DELETE:
                        if (tvFacturas.getSelectionModel().getSelectedItem() != null) {
                            actionEliminar();
                        }
                        break;

                    default:
                        break;
                }
            }
        });
    }

    /* -----------------------------------------------------------------------*/
 /*                                  EVENTOS                                */
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
            } else {
                btnPagado.setDisable(true);
            }

        } else {
            btnModificar.setDisable(true);
            btnEliminar.setDisable(true);
            btnPagado.setDisable(true);
        }
    }

    /**
     * Cuando cambia el alto de la tabla se recalcula el nro de filas posibles y
     * se actualiza la paginación acorde al nro de items por página.
     */
    public void handleTvFacturasHeightChanged() {
        tvFacturas.heightProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue.doubleValue() > 0 && oldValue.doubleValue() != 0) {

                // Calcular index del item seleccionado actualmente
                int currentItemIndex = 0;
                FacturaBean selectedFactura = tvFacturas.getSelectionModel().getSelectedItem();

                if (selectedFactura != null) {
                    currentItemIndex = (pgFacturas.getCurrentPageIndex() * rowsPerPage) + tvFacturas.getSelectionModel().getSelectedIndex();
                }

                // Obtener cuantas rows entran en la table segun su heightProperty.
                Double rpp = (newValue.doubleValue() - GTConstants.DEFAULT_ROW_HEIGHT) / GTConstants.DEFAULT_ROW_HEIGHT;
                rowsPerPage = rpp.intValue();

                // Reiniciar paginación con el nuevo valor asignado a rowsPerPage
                pgFacturas.setPageCount((facturasData.size() / rowsPerPage) + 1);

                // Si es el caso, ir a la página donde se encuentra el ultimo item seleccionado en la tabla.
                if (currentItemIndex > rowsPerPage) {
                    int pageIndex = (int) (currentItemIndex / rowsPerPage);
                    pgFacturas.setCurrentPageIndex(pageIndex);
                    tvFacturas.getSelectionModel().select(selectedFactura);
                } else {
                    pgFacturas.setCurrentPageIndex(-1);
                }
                //pgFacturas.setCurrentPageIndex(-1);
                tvFacturas.refresh();

                /**
                 * Fix temporal para problema de actualización de tabla en
                 * primera página al hacer resize. ** No actualiza hasta que se
                 * haga focus, y si esta seleccionada ya hasta que se haga focus
                 * en otro nodo. TODO Buscar mejor solución. Investigar posible
                 * bug de javafx en este tema.
                 */
                if (tvFacturas.isFocused()) {
                    cbCriteria.requestFocus();
                } else {
                    tvFacturas.requestFocus();
                }
            }
        });
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
                        logger.info("Error al cargar ventana modificar factura. (Double click event)");
                    }

                }
            });
            return row;
        });
    }

    /**
     * Obtiene datos del modelo y los carga en la tabla.
     */
    public void reloadTable() {
        facturasData = FXCollections.observableArrayList(facturasLogicController.getAllFacturas());
        tvFacturas.setItems(facturasData);

        initPagination();
        tvFacturas.refresh();
    }

    /**
     * Modelo de creación de pagina para paginación.
     *
     * @param pageIndex
     * @return
     */
    private Node createPage(int pageIndex) {

        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, facturasData.size());
        tvFacturas.setItems(FXCollections.observableArrayList(facturasData.subList(fromIndex, toIndex)));

        return new BorderPane(tvFacturas);
    }

    /**
     * Controla acciones cuando cambia el criterio de busqueda.
     *
     * @param e
     */
    private void handleCbCriteriaValueChange(Event e) {

        if (cbCriteria.getSelectionModel().getSelectedIndex() == GTConstants.CRITERIA_INDEX_ID
                || cbCriteria.getSelectionModel().getSelectedIndex() == GTConstants.CRITERIA_INDEX_REPARACION) {
            dpFromDate.setValue(null);
            dpFromDate.setDisable(true);
            dpToDate.setValue(null);
            dpToDate.setDisable(true);
            tfBuscar.setDisable(false);
        } else {
            dpFromDate.setDisable(false);
            dpToDate.setDisable(false);
            if (cbCriteria.getSelectionModel().getSelectedIndex() == GTConstants.CRITERIA_INDEX_ALL) {
                tfBuscar.clear();
                tfBuscar.setDisable(true);
            } else {
                tfBuscar.setDisable(false);
            }
        }
        tfBuscar.clear();
        dpFromDate.setValue(null);
        dpToDate.setValue(null);
        reloadTable();
    }

    /* -----------------------------------------------------------------------*/
 /*                         GETTERS AND SETTERS                            */
 /* -----------------------------------------------------------------------*/
    /**
     * Obtener tabla de facturas
     *
     * @return tabla de facturas
     */
    public TableView<FacturaBean> getTvFacturas() {
        return tvFacturas;
    }

    /**
     * Conecta Stage a controlador
     *
     * @param stage stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Establece owner stage
     *
     * @param ownerStage parent stage
     */
    public void setOwnerStage(Stage ownerStage) {
        this.ownerStage = ownerStage;
    }

    /**
     * Establece objeto interfaz de facturas.
     *
     * @param facturasLogicController controlador de lógica
     */
    public void setFacturasManager(FacturasManager facturasLogicController) {
        this.facturasLogicController = facturasLogicController;
    }

    public Pagination getPgFacturas() {
        return pgFacturas;
    }

    public void recalcPage() {
        int pcount = pgFacturas.getPageCount();
        // Si esta llena la pagina actual...
        if ((facturasData.size() - 2) > (pcount) * rowsPerPage) {
            pgFacturas.setPageCount(pcount + 1);

        }
        pgFacturas.setCurrentPageIndex(pcount + 1);
    }

}
