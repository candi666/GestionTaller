package gestiontaller.gui.controller.clientes;

import gestiontaller.config.GTConstants;
import gestiontaller.gui.controller.HomeController;
import gestiontaller.logic.interfaces.ClientesManager;
import gestiontaller.logic.bean.ClienteBean;
import gestiontaller.logic.util.FieldValidator;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Ionut
 */
public class ClientesCuController implements Initializable {

    private static final Logger logger = Logger.getLogger(ClientesCuController.class.getName());
    private Stage stage;
    private Stage ownerStage;
    private ClienteBean cliente;
    private ClientesManager clientesLogicController;
    private ClientesController clientesController;
    private int maxid = 0;
    
    //<editor-fold defaultstate="collapsed" desc="@FXML NODES">    
    @FXML
    private TextField tfDNI;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfApellido;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfTelefono;
    @FXML
    private Button btnCrear;
    @FXML
    private Button btnVolver;
    @FXML
    private Label lblTitulo;
    @FXML
    private ImageView hintDNI;
    @FXML
    private ImageView hintNombre;
    @FXML
    private ImageView hintApellido;
    @FXML
    private ImageView hintEmail;
    @FXML
    private ImageView hintTelefono;
    @FXML
    private AnchorPane rootPane;
    // </editor-fold>

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.setTitle("Gestión de Taller");
        if (cliente != null) {
            logger.info("Abierta ventana modificar factura.");
            lblTitulo.setText(HomeController.bundle.getString("app.gui.clientes.cu.title.update"));
            btnCrear.setText(HomeController.bundle.getString("generic.crud.update"));
        }
        
        stage.getIcons().add(new Image(GTConstants.PATH_LOGO));
        stage.setResizable(false);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(ownerStage);
        stage.setOnShowing(this::handleWindowShowing);
        stage.setMaxWidth(340);
        stage.setMinWidth(340);
        stage.setMaxHeight(460);
        stage.setMinHeight(460);
        stage.show();

    }

    public void setOwnerStage(Stage ownerStage) {
        this.ownerStage = ownerStage;
    }

    /**
     * Establece cliente a modificar.
     *
     * @param cliente
     */
    public void setCliente(ClienteBean cliente) {
        this.cliente = cliente;
    }

    public void setClientesController(ClientesController clientesController) {
        this.clientesController = clientesController;
    }

    private void handleWindowShowing(WindowEvent event) {
        populateForm();
        initTooltips();
        
        rootPane.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case ESCAPE:
                        actionVolver();
                        break;
                    case ENTER:
                        actionCrearMod();
                        break;
                    default:
                        break;
                }
            }
        });
    }
    
    private void populateForm() {
        if (cliente != null) {
            logger.info("Abierta ventana modificar cliente.");
            tfDNI.setText(cliente.getDni());
            tfNombre.setText(cliente.getNombre());
            tfApellido.setText(cliente.getApellidos());
            tfEmail.setText(cliente.getEmail());
            tfTelefono.setText(cliente.getTelefono());
        } else {
            logger.info("Abierta ventana crear cliente.");
        }

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void actionVolver() {
        stage.close();
        ownerStage.requestFocus();
    }

    @FXML
    private void actionCrearMod() {
        if (validar()) {
            // Caso modificar
            if (cliente != null) {
                cliente.setDni(tfDNI.getText());
                cliente.setNombre(tfNombre.getText());
                cliente.setApellidos(tfApellido.getText());
                cliente.setEmail(tfEmail.getText());
                cliente.setTelefono(tfTelefono.getText());
                
                clientesController.actionCrearMod(cliente);
            } else { // Caso crear
                ClienteBean newCliente = new ClienteBean(0, tfDNI.getText(), tfNombre.getText(), tfApellido.getText(), tfEmail.getText(), tfTelefono.getText());
                clientesController.actionCrearMod(newCliente);
            }
            stage.close();
            ownerStage.requestFocus();
        }
    }

    public void initTooltips() {
        // Tooltip DNI
        Tooltip tipToolDNI = new Tooltip("Este campo es obligatorio. Ej: 11111111H");
        tipToolDNI.setAnchorX(hintDNI.getX()-150);
        tipToolDNI.setAnchorY(hintDNI.getY()-30);
        Tooltip.install(hintDNI, tipToolDNI);
        // Tooltip Nombre
        Tooltip tipToolNombre = new Tooltip("Este campo es obligatorio. Solo letras.");
        tipToolNombre.setAnchorX(hintNombre.getX()-150);
        tipToolNombre.setAnchorY(hintNombre.getY()-30);
        Tooltip.install(hintNombre, tipToolNombre);
        // Tooltip Apellido
        Tooltip tipToolApellido = new Tooltip("Este campo es obligatorio. Solo letras.");
        tipToolApellido.setAnchorX(hintApellido.getX()-150);
        tipToolApellido.setAnchorY(hintApellido.getY()-30);
        Tooltip.install(hintApellido, tipToolApellido);
        // Tooltip Email
        Tooltip tipToolEmail = new Tooltip("Este campo es obligatorio. Ej: zzz@zzz.zzz");
        tipToolEmail.setAnchorX(hintEmail.getX()-150);
        tipToolEmail.setAnchorY(hintEmail.getY()-30);
        Tooltip.install(hintEmail, tipToolEmail);
        // Tooltip Telefono
        Tooltip tipToolTelefono = new Tooltip("Este campo es obligatorio. Solo números.");
        tipToolTelefono.setAnchorX(hintTelefono.getX()-150);
        tipToolTelefono.setAnchorY(hintTelefono.getY()-30);
        Tooltip.install(hintTelefono, tipToolTelefono);
    }
    
    public void setClientesManager(ClientesManager clientesLogicController) {
        this.clientesLogicController = clientesLogicController;
    }

    //Validacion
    public boolean validar() {
        boolean res = true;
        
        //Validación DNI
        if (!FieldValidator.isDni(tfDNI.getText())) {
            tfDNI.getStyleClass().add("tf-invalid");
            hintDNI.setVisible(true);
            res = false;
        }
        //Validación Nombre
        if (!tfNombre.getText().matches("[a-zA-Z_]+") || tfNombre.getText() == "" ) {
            tfNombre.getStyleClass().add("tf-invalid");
            hintNombre.setVisible(true);
            res = false;
        }
        //Validación Apellido
        if (!tfApellido.getText().matches("[a-zA-Z_]+") || tfApellido.getText() == "") {
            tfApellido.getStyleClass().add("tf-invalid");
            hintApellido.setVisible(true);
            res = false;
        }
        //Validación Email
        if (!FieldValidator.isEmail(tfEmail.getText())) {
            tfEmail.getStyleClass().add("tf-invalid");
            hintEmail.setVisible(true);
            res = false;
        }
        //Validación Telefono
        if (!tfTelefono.getText().matches("[0-9]+")) {
            tfTelefono.getStyleClass().add("tf-invalid");
            hintTelefono.setVisible(true);
            res = false;
        }
        
        return res;
    }
}
