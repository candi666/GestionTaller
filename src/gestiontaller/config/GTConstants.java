/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiontaller.config;

/**
 * Clase donde están definidas todas las constantes de la aplicacion
 *
 * @author Carlos
 */
public class GTConstants {
    
    public static final int MAX_MOCK_FACTURAS = 100;
    public static final int MAX_ROWS_TABLE_FACTURAS = 22;
    public static final int MAX_MOCK_CLIENTES = 127;
    public static final int MAX_ROWS_TABLE_CLIENTES = 20;
    
    public static final int CRITERIA_INDEX_ALL = 0;
    public static final int CRITERIA_INDEX_ID = 1;
    public static final int CRITERIA_INDEX_CLIENTE = 2;
    public static final int CRITERIA_INDEX_REPARACION = 3;
    
    // PATTERNS
    
    public static final String DATE_FORMAT_SPAIN = "dd-MM-yyyy";
    
    // DIMENSIONS
    /**
     * Default row height de javafx establecido en su archivo css.
     * TODO obtener row height dinamicamente desde el nodo tableview.
     */
    public static final int DEFAULT_ROW_HEIGHT = 24;
    
}
