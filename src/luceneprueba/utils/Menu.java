/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package luceneprueba.utils;

/**
 *
 * @author Gonzalo
 */
public class Menu {
    
    static public void printMainMenu() {
        System.out.flush();
        System.out.println("Opciones:");
        System.out.println("1. Crear índice invertido");
        System.out.println("2. Buscar en el índice invertido");
        System.out.println("3. Obtener reviews por fecha desde un archivo");
        System.out.println("4. Obtener los top-60 reviews por cada día");
        System.out.println("5. Generar archivos con información de los reviews");
        System.out.println("6. Crear lista de reviews desde archivos de texto");
        System.out.println("7. Salir");
        System.out.print("Elija una opción: ");
    }
 
}
