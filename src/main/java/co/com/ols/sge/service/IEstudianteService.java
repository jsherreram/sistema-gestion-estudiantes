/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.ols.sge.service;

import co.com.ols.sge.entity.Estudiante;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 *
 * @author jsherreram
 */
public interface IEstudianteService {
    
    public List<Estudiante> findAll(Sort sort);

    public Page<Estudiante> findAll(Pageable pageable);
    
//    public List<Estudiante> findAll();

    public Estudiante findById(long id);

    public Estudiante save(Estudiante estudiante);

    public void deleteById(long id);
    
}
