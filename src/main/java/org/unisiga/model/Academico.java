package org.unisiga.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa al profesor encargado de dictar cátedras.
 */
public class Academico extends MiembroUniversitario {
    private String idEmpleado;
    private String tipoContrato;
    private Departamento departamento; // Relación de agregación
    private List<Seccion> seccionesDictadas;

    public Academico(String rut, String nombre, String correo, String idEmpleado, String tipoContrato) {
        super(rut, nombre, correo);
        this.idEmpleado = idEmpleado;
        this.tipoContrato = tipoContrato;
        this.seccionesDictadas = new ArrayList<>();
    }

    @Override
    public boolean login(String password) {
        // TODO: Implementar validación simulada de MFA docente (requiere que el password contenga '@')
        if (password == null) {
            return false;
        }
        return password.contains("@");
    }

    /**
     * Registra la nota de un estudiante para una evaluación de la asignatura.
     * [REGLAS]: Validar parámetros, rango de notas [1.0, 7.0] y que la evaluación pertenezca a la asignatura.
     */
    public void registrarNota(Inscripcion inscripcion, Evaluacion evaluacion, float valorNota) {
        // TODO: Implementar la validación e inserción/actualización de la nota (Tres Vías)
        // 1. Validar parámetros no nulos.
        if (inscripcion == null || evaluacion == null) {
            throw new IllegalArgumentException("La inscripción y la evaluación no pueden ser nulas.");
        }
        // 2. Validar rango de la nota [1.0, 7.0].
        if (valorNota < 1.0f || valorNota > 7.0f) {
            throw new IllegalArgumentException("La nota debe estar en el rango [1.0, 7.0].");
        }
        // 3. Coherencia: la evaluación debe pertenecer a la misma asignatura de la sección inscrita.
        Asignatura asignaturaSeccion = inscripcion.getSeccion().getAsignatura();
        if (evaluacion.getAsignatura() != asignaturaSeccion) {
            throw new IllegalArgumentException(
                    "La evaluación no pertenece a la asignatura de la sección inscrita.");
        }
        // 4. Crear la calificación (estructura de tres vías) y vincularla a ambos extremos.
        Calificacion calificacion = new Calificacion(valorNota, inscripcion, evaluacion);
        inscripcion.getCalificaciones().add(calificacion);
        evaluacion.getCalificaciones().add(calificacion);
    }

    // Getters y Setters
    public String getIdEmpleado() { return idEmpleado; }
    public Departamento getDepartamento() { return departamento; }
    public void setDepartamento(Departamento depto) { this.departamento = depto; }
    public List<Seccion> getSeccionesDictadas() { return seccionesDictadas; }
}
