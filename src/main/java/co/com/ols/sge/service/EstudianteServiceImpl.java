/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.ols.sge.service;

import co.com.ols.sge.dao.IEstudianteDao;
import co.com.ols.sge.entity.Estudiante;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jsherreram
 */
@Service
public class EstudianteServiceImpl implements IEstudianteService {

    @Autowired
    private IEstudianteDao estudianteDao;

    @Override
    @Transactional(readOnly = true)
    public List<Estudiante> findAll(Sort sort) {
        return estudianteDao.findAll(sort);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Estudiante> findAll(Pageable pageable) {
        return estudianteDao.findAll(pageable);
    }
    
//    @Override
//    @Transactional(readOnly = true)
//    public List<Estudiante> findAll() {
//        return estudianteDao.findAll();
//    }

    @Override
    @Transactional(readOnly = true)
    public Estudiante findById(long id) {
        return estudianteDao.findById(id);
    }

    @Override
    @Transactional
    public Estudiante save(Estudiante estudiante) {
        return estudianteDao.save(estudiante);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        estudianteDao.deleteById(id);
    }

}
