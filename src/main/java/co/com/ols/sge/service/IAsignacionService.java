/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.ols.sge.service;

import co.com.ols.sge.entity.Asignacion;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 *
 * @author jsherreram
 */
public interface IAsignacionService {

    public List<Asignacion> findAll(Sort sort);

    public Page<Asignacion> findAll(Pageable pageable);

    public Asignacion findById(long id);

    public Asignacion save(Asignacion asignacion);

    public void deleteById(long id);

}
