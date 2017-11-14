/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestiontaller.logic.util;

/**
 * Clase util para validación de campos
 * @author Carlos
 */
public final class FieldValidator {
    
    private FieldValidator(){
        
    }
    
    /**
     * Varifica si un string es un dni.
     * @param testvalue string a probar.
     * @return True -> Es dni.
     */
    public static boolean isDni(String testvalue){
        // TODO validacion dni
        return true;
    }
    
    /**
     * Verifica que el length  de un string este entre dos valores.
     * Ejemplo: Password debe tener al menos 8 caracteres y no mas de 16.
     * FieldValidator.lengthBetween(testvalue,8,16);
     * @param minLength length minimo del string.
     * @param maxLength length max del string.
     * @return 
     */
    public static boolean lengthBetween(String testvalue, int minLength, int maxLength){
        // TODO
        // -> Se puede forzar al campo a que solo acepte numeros ->
        return true;
    }
    
    /**
     * Verifica si un string es un numero.
     * @return 
     */
    public static boolean isNumber(){
        // TODO
        return true;
    }
}
