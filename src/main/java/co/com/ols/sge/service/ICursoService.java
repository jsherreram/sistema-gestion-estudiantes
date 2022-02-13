/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.ols.sge.service;

import co.com.ols.sge.entity.Curso;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 *
 * @author jsherreram
 */
public interface ICursoService {
    
    public List<Curso> findAll(Sort sort);

    public Page<Curso> findAll(Pageable pageable);
    
//    public List<Curso> findAll();

    public Curso findById(long id);

    public Curso save(Curso curso);

    public void deleteById(long id);
    
}
