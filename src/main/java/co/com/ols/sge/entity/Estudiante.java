/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.ols.sge.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 *
 * @author jsherreram
 */
@Data
@Entity
@Table(name = "estudiante")
public class Estudiante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estudiante")
    private Long idEstudiante;

    @NotEmpty(message = "El campo nombre estudiante no puede estar vacío")
    @Size(min = 4, max = 45, message = "El campo nombre estudiante debe tener entre 4 y 45 caracteres")
    private String nombre;

    @NotEmpty(message = "El campo apellido estudiante no puede estar vacío")
    @Size(min = 4, max = 45, message = "El campo apellido estudiante debe tener entre 4 y 45 caracteres")
    private String apellido;

    @NotEmpty(message = "El campo correo estudiante no puede estar vacío")
    @Size(min = 4, max = 45, message = "El campo correo estudiante debe tener entre 4 y 45 caracteres")
    @Email(message = "El campo correo estudiante no tiene un formato de una dirección de correo electrónico válida")
    private String correo;

    @NotEmpty(message = "El campo estado estudiante no puede estar vacío")
    @Size(min = 4, max = 45, message = "El campo estado estudiante debe tener entre 6 y 8 caracteres")
    @Pattern(regexp = "Activo|Inactivo", message = "El campo estado estudiante debe estar únicamente Activo o Inactivo")
    private String estado;

    @Column(name = "fecha_nacimiento")
    @NotNull(message = "El campo fecha de nacimiento estudiante no puede estar nulo")
    @Past(message = "El campo fecha de nacimiento estudiante debe ser una fecha en el pasado")
    private Date fechaNacimiento;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, mappedBy = "estudiante")
    private List<Asignacion> asignaciones;

}
