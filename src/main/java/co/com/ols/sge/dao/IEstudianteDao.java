/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.ols.sge.dao;

import co.com.ols.sge.entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author jsherreram
 */
public interface IEstudianteDao extends JpaRepository<Estudiante, Long> {

    @Query(value = "SELECT e FROM Estudiante e LEFT JOIN FETCH e.asignaciones WHERE e.idEstudiante = :id")
    public Estudiante findById(long id);

}
