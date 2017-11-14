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
        String letraMayuscula = ""; //Guardaremos la letra introducida en formato mayúscula
        
        // Aquí excluimos cadenas distintas a 9 caracteres que debe tener un dni y también si el último caracter no es una letra
        if(testvalue.length() != 9 || Character.isLetter(testvalue.charAt(8)) == false ) {
            return false;
        }
        // Al superar la primera restricción, la letra la pasamos a mayúscula
        letraMayuscula = (testvalue.substring(8)).toUpperCase();
        // Por último validamos que sólo tengo 8 dígitos entre los 8 primeros caracteres y que la letra introducida es igual a la de la ecuación
        // Llamamos a los métodos privados de la clase soloNumeros() y letraDNI()
        if(soloNumeros(testvalue) != true && letraDNI(testvalue).equals(letraMayuscula)) {
            return false;
        }
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
    
    private static boolean soloNumeros(String testvalue) {

        int i, j = 0;
        String numero = ""; // Es el número que se comprueba uno a uno por si hay alguna letra entre los 8 primeros dígitos
        String miDNI = ""; // Guardamos en una cadena los números para después calcular la letra
        String[] unoNueve = {"0","1","2","3","4","5","6","7","8","9"};

        for(i = 0; i < testvalue.length() - 1; i++) {
            numero = testvalue.substring(i, i+1);

            for(j = 0; j < unoNueve.length; j++) {
                if(numero.equals(unoNueve[j])) {
                    miDNI += unoNueve[j];
                }
            }
        }

        if(miDNI.length() != 8) {
            return false;
        }
        else {
            return true;
        }
    }
 
    private static String letraDNI(String testvalue) {
        // El método es privado porque lo voy a usar internamente en esta clase, no se necesita fuera de ella

        // pasar miNumero a integer
        int miDNI = Integer.parseInt(testvalue.substring(0,8));
        int resto = 0;
        String miLetra = "";
        String[] asignacionLetra = {"T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E"};

        resto = miDNI % 23;

        miLetra = asignacionLetra[resto];

        return miLetra;
    }
}
