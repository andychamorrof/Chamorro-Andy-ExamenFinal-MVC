package org.unisiga.main;

import org.unisiga.controller.InscripcionController;
import org.unisiga.model.*;
import org.unisiga.view.VentanaPrincipal;



/**
 * Orquestador principal de pruebas. Configura el escenario inicial de la pauta.
 */
public class Main {
    public static void main(String[] args) {
        InscripcionController controller = new InscripcionController();

        // === SEMBRADO DE DATOS ===
        Asignatura progBasica = new Asignatura("PROG101", "Programación Básica", 5);
        Asignatura poo = new Asignatura("POO201", "Programación Orientada a Objetos", 6);
        poo.agregarPrerrequisito(progBasica);
        poo.crearSeccion('1', 30, "Lunes 08:00");
        progBasica.crearSeccion('1', 30, "Martes 10:00");
        controller.registrarAsignaturaEnDb(progBasica);
        controller.registrarAsignaturaEnDb(poo);

        Estudiante juan = new Estudiante("11.111.111-1", "Juan Pérez", "juan@uni.cl", "20230001", 2023, 5.5f);
        Estudiante maria = new Estudiante("22.222.222-2", "María Soto", "maria@uni.cl", "20230002", 2023, 6.0f);
        Seccion seccionBasica = progBasica.getSecciones().get(0);
        juan.inscribirSeccion(seccionBasica);
        juan.getInscripciones().get(0).setEstadoInscripcion("Aprobado");
        controller.registrarEstudianteEnDb(juan);
        controller.registrarEstudianteEnDb(maria);

        // === ABRIR LA VENTANA PRINCIPAL ===
        final InscripcionController ctrl = controller;
        java.awt.EventQueue.invokeLater(() -> {
            new VentanaPrincipal(ctrl).setVisible(true);
        });
    }
}