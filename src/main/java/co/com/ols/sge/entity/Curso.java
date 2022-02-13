/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.ols.sge.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 *
 * @author jsherreram
 */
@Data
@Entity
@Table(name = "curso")
public class Curso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curso")
    private Long idCurso;

    @Column(name = "nombre_curso")
    @NotEmpty(message = "El campo nombre curso no puede estar vacío")
    @Size(min = 4, max = 45, message = "El campo nombre curso debe tener entre 4 y 45 caracteres")
    private String nombreCurso;

    @Column(name = "estado_curso")
    @NotEmpty(message = "El campo estado curso no puede estar vacío")
    @Size(min = 4, max = 45, message = "El campo estado curso debe tener entre 6 y 8 caracteres")
    @Pattern(regexp = "Activo|Inactivo", message = "El campo estado curso debe estar únicamente Activo o Inactivo")
    private String estadoCurso;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, mappedBy = "curso")
    private List<Asignacion> asignaciones;

}
