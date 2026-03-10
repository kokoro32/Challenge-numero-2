package com.aluracursos.Challenge_numero_2.principal;

import com.aluracursos.Challenge_numero_2.service.LogicaEleccion;
import com.aluracursos.Challenge_numero_2.service.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Principal {

    @Autowired
    private LogicaEleccion logicaEleccion;

    public void principal(){
        Scanner teclado = new Scanner(System.in);

        var opcion = -1;
        while(opcion != 0){
            Menu.mostrarMenu();
            try{
                opcion = teclado.nextInt();
                teclado.nextLine();
                if (opcion < 0 || opcion > 5) {
                    System.out.println("Debes ingresar un número válido.");
                    continue;
                }else if (opcion == 0){
                    System.out.println("Muchas gracias por haber utilizado nuestro sistema de conversión");
                    break;
                }
                logicaEleccion.logicaSeleccion(opcion);
            } catch (java.util.InputMismatchException e) {
                System.out.println("Opción no válida. Intenta nuevamente.");
                teclado.nextLine();
                opcion = -1;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Ocurrió un error al ejecutar la opción.");
                teclado.nextLine();
                opcion = -1;
            }
        }
    }
}
