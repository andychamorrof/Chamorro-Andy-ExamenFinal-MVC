package org.unisiga.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Catálogo general de asignaturas. Controla las secciones y evaluaciones unificadas.
 * [EVALUACIÓN]: Control absoluto de ciclos de vida mediante Composición Fuerte y Auto-asociación.
 */
public class Asignatura {
    private String codigo;
    private String nombre;
    private int creditosSct;
    
    // Auto-asociación recursiva para prerrequisitos
    private List<Asignatura> prerrequisitos;
    
    // Composiciones fuertes (Sólo Asignatura puede instanciar estos objetos)
    private List<Seccion> secciones;
    private List<Evaluacion> evaluaciones;

    public Asignatura(String codigo, String nombre, int creditosSct) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.creditosSct = creditosSct;
        this.prerrequisitos = new ArrayList<>();
        this.secciones = new ArrayList<>();
        this.evaluaciones = new ArrayList<>();
    }

    public void agregarPrerrequisito(Asignatura asig) {
        // TODO: Agregar asignatura de prerrequisito evitando duplicados
        if (asig == null) {
            throw new IllegalArgumentException("El prerrequisito no puede ser nulo.");
        }
        if (asig == this) {
            throw new IllegalArgumentException("Una asignatura no puede ser prerrequisito de sí misma.");
        }
        // Evita duplicados.
        if (!this.prerrequisitos.contains(asig)) {
            this.prerrequisitos.add(asig);
        }
    }

    /**
     * LÓGICA DE COMPOSICIÓN: Instancia una sección semestral y la asocia.
     */
    public Seccion crearSeccion(char idGrupo, int cupoMaximo, String horario) {
        // TODO: Crear y retornar una nueva sección. Recuerda que el constructor de Sección es restringido.
        // Sólo esta clase puede invocar el constructor package-private de Seccion.
        Seccion seccion = new Seccion(idGrupo, cupoMaximo, horario, this);
        this.secciones.add(seccion);
        return seccion;
    }

    /**
     * LÓGICA DE COMPOSICIÓN: Instancia una evaluación unificada para la asignatura.
     */
    public Evaluacion crearEvaluacion(int id, String titulo, float ponderacion) {
        // TODO: Crear y retornar una nueva evaluación oficial de la cátedra.
        // Sólo esta clase puede invocar el constructor package-private de Evaluacion.
        Evaluacion evaluacion = new Evaluacion(id, titulo, ponderacion, this);
        this.evaluaciones.add(evaluacion);
        return evaluacion;
    }

    // Getters
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public List<Asignatura> getPrerrequisitos() { return prerrequisitos; }
    public List<Seccion> getSecciones() { return secciones; }
    public List<Evaluacion> getEvaluaciones() { return evaluaciones; }
}
