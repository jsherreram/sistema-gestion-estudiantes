/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.ols.sge.dao;

import co.com.ols.sge.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author jsherreram
 */
public interface ICursoDao extends JpaRepository<Curso, Long> {

    @Query(value = "SELECT c FROM Curso c LEFT JOIN FETCH c.asignaciones WHERE c.idCurso = :id")
    public Curso findById(long id);

}
