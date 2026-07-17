package org.unisiga.controller;

import java.util.ArrayList;
import java.util.List;
import org.unisiga.model.*;

/**
 * Controlador de lógica de negocio transaccional. Simula llamadas e interacciones de base de datos.
 */
public class InscripcionController {
    private List<Estudiante> estudiantesDb;
    private List<Asignatura> asignaturasDb;

    public InscripcionController() {
        this.estudiantesDb = new ArrayList<>();
        this.asignaturasDb = new ArrayList<>();
    }

    // Métodos de sembrado (seeding) de bases de datos
    public void registrarEstudianteEnDb(Estudiante e) { estudiantesDb.add(e); }
    public void registrarAsignaturaEnDb(Asignatura a) { asignaturasDb.add(a); }

    /**
     * Procesa la solicitud de inscripción de asignaturas.
     */
    public String inscribirSeccionEstudiante(String matricula, String codigoAsignatura, char idGrupo) {
        // 1. Buscar estudiante y asignatura en la "base de datos".
        Estudiante estudiante = buscarEstudiante(matricula);
        if (estudiante == null) {
            return "ERROR: No existe un estudiante con matrícula " + matricula + ".";
        }
        Asignatura asignatura = buscarAsignatura(codigoAsignatura);
        if (asignatura == null) {
            return "ERROR: No existe una asignatura con código " + codigoAsignatura + ".";
        }

        // 2. Obtener el grupo (sección) solicitado por composición.
        Seccion seccion = buscarSeccion(asignatura, idGrupo);
        if (seccion == null) {
            return "ERROR: La asignatura " + asignatura.getNombre()
                    + " no tiene la sección '" + idGrupo + "'.";
        }

        // 3. Validar prerrequisitos: cada prerrequisito debe estar "Aprobado" en el historial del alumno.
        for (Asignatura prereq : asignatura.getPrerrequisitos()) {
            if (!tieneAprobada(estudiante, prereq)) {
                return "RECHAZADO: " + estudiante.getNombre()
                        + " no cumple el prerrequisito '" + prereq.getNombre() + "'.";
            }
        }

        // 4. Delegar la transacción al dominio del modelo.
        try {
            estudiante.inscribirSeccion(seccion);
        } catch (RuntimeException ex) {
            return "RECHAZADO: " + ex.getMessage();
        }

        return "OK: " + estudiante.getNombre() + " inscrito en "
                + asignatura.getNombre() + " - Sección " + idGrupo + ".";
    }

    // ---- Métodos auxiliares privados ----

    private Estudiante buscarEstudiante(String matricula) {
        for (Estudiante e : estudiantesDb) {
            if (e.getMatricula().equals(matricula)) {
                return e;
            }
        }
        return null;
    }

    private Asignatura buscarAsignatura(String codigo) {
        for (Asignatura a : asignaturasDb) {
            if (a.getCodigo().equals(codigo)) {
                return a;
            }
        }
        return null;
    }

    private Seccion buscarSeccion(Asignatura asignatura, char idGrupo) {
        for (Seccion s : asignatura.getSecciones()) {
            if (s.getIdGrupo() == idGrupo) {
                return s;
            }
        }
        return null;
    }

    /**
     * Revisa el historial de inscripciones del estudiante buscando la asignatura aprobada.
     */
    private boolean tieneAprobada(Estudiante estudiante, Asignatura asignatura) {
        for (Inscripcion insc : estudiante.getInscripciones()) {
            if (insc.getSeccion().getAsignatura() == asignatura
                    && "Aprobado".equalsIgnoreCase(insc.getEstadoInscripcion())) {
                return true;
            }
        }
        return false;
    }
}