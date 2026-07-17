package org.unisiga.main;

import org.unisiga.controller.InscripcionController;
import org.unisiga.model.*;
import org.unisiga.view.VentanaPrincipal;
import org.unisiga.view.ConsoleView;



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
        
        
        // MAIN DE CONSOLA
        
        /*
        ConsoleView vista = new ConsoleView();
        InscripcionController controller = new InscripcionController();
        vista.desplegarMenu();

        // 1. Sembrar catálogo de asignaturas.
        Asignatura progBasica = new Asignatura("PROG101", "Programación Básica", 5);
        Asignatura poo = new Asignatura("POO201", "Programación Orientada a Objetos", 6);
        Asignatura algebra = new Asignatura("MAT101", "Álgebra Lineal", 4);

        // 2. Regla recursiva: POO requiere Programación Básica.
        poo.agregarPrerrequisito(progBasica);

        // 3. Composición fuerte: secciones y evaluaciones creadas desde la asignatura.
        Seccion seccionPoo = poo.crearSeccion('1', 2, "Lunes 08:00");
        poo.crearEvaluacion(1, "Proyecto Final", 40.0f);
        progBasica.crearSeccion('1', 30, "Martes 10:00");

        controller.registrarAsignaturaEnDb(progBasica);
        controller.registrarAsignaturaEnDb(poo);
        controller.registrarAsignaturaEnDb(algebra);

        // 4. Estudiantes de prueba.
        Estudiante juan = new Estudiante("11.111.111-1", "Juan Pérez", "juan@uni.cl", "20230001", 2023, 5.5f);
        Estudiante maria = new Estudiante("22.222.222-2", "María Soto", "maria@uni.cl", "20230002", 2023, 6.0f);

        // Juan YA aprobó Programación Básica: se inscribe y se marca como Aprobado.
        Seccion seccionBasicaJuan = progBasica.getSecciones().get(0);
        juan.inscribirSeccion(seccionBasicaJuan);
        juan.getInscripciones().get(0).setEstadoInscripcion("Aprobado");

        controller.registrarEstudianteEnDb(juan);
        controller.registrarEstudianteEnDb(maria);

        // 5. Ejecutar casos de uso y mostrar resultados con la vista.
        vista.mostrarMensajeProcesamiento("Juan intenta inscribir POO (tiene el prerrequisito):");
        String r1 = controller.inscribirSeccionEstudiante("20230001", "POO201", '1');
        vista.mostrarMensajeProcesamiento(r1);

        vista.mostrarMensajeProcesamiento("María intenta inscribir POO (sin el prerrequisito):");
        String r2 = controller.inscribirSeccionEstudiante("20230002", "POO201", '1');
        vista.mostrarMensajeProcesamiento(r2);

        // Comprobante para la inscripción exitosa de Juan.
        vista.imprimirComprobante(juan.getNombre(), poo.getNombre(), '1');
        */
    }
}