
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.ols.sge.dao;

import co.com.ols.sge.entity.Asignacion;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author jsherreram
 */
public interface IAsignacionDao extends JpaRepository<Asignacion, Long> {

    @Query(value = "SELECT a FROM Asignacion a LEFT JOIN FETCH a.estudiante LEFT JOIN FETCH a.curso")
    public List<Asignacion> findAll(Sort sort);

    @Query(value = "SELECT a FROM Asignacion a LEFT JOIN FETCH a.estudiante LEFT JOIN FETCH a.curso", countQuery = "SELECT COUNT(a) FROM Asignacion a LEFT JOIN a.estudiante LEFT JOIN a.curso")
    public Page<Asignacion> findAll(Pageable pageable);

    @Query(value = "SELECT a FROM Asignacion a LEFT JOIN FETCH a.estudiante LEFT JOIN FETCH a.curso WHERE a.idAsignacion = :id")
    public Asignacion findById(long id);

}
