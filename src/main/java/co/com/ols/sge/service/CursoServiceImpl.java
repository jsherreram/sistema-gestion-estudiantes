/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.ols.sge.service;

import co.com.ols.sge.dao.ICursoDao;
import co.com.ols.sge.entity.Curso;
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
public class CursoServiceImpl implements ICursoService {

    @Autowired
    private ICursoDao cursoDao;

    @Override
    @Transactional(readOnly = true)
    public List<Curso> findAll(Sort sort) {
        return cursoDao.findAll(sort);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Curso> findAll(Pageable pageable) {
        return cursoDao.findAll(pageable);
    }

//    @Override
//    @Transactional(readOnly = true)
//    public List<Curso> findAll() {
//        return (List<Curso>) cursoDao.findAll();
//    }

    @Override
    @Transactional(readOnly = true)
    public Curso findById(long id) {
        return cursoDao.findById(id);
    }

    @Override
    @Transactional
    public Curso save(Curso curso) {
        return cursoDao.save(curso);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        cursoDao.deleteById(id);
    }

}
