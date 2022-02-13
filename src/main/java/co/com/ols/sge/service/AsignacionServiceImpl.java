/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.ols.sge.service;

import co.com.ols.sge.dao.IAsignacionDao;
import co.com.ols.sge.dao.ICursoDao;
import co.com.ols.sge.dao.IEstudianteDao;
import co.com.ols.sge.entity.Asignacion;
import co.com.ols.sge.entity.Curso;
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
public class AsignacionServiceImpl implements IAsignacionService {

    @Autowired
    private IAsignacionDao asignacionDao;

    @Autowired
    private IEstudianteDao estudianteDao;

    @Autowired
    private ICursoDao cursoDao;

    @Override
    @Transactional(readOnly = true)
    public List<Asignacion> findAll(Sort sort) {
        return asignacionDao.findAll(sort);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Asignacion> findAll(Pageable pageable) {
        return asignacionDao.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Asignacion findById(long id) {
        return asignacionDao.findById(id);
    }

    @Override
    @Transactional
    public Asignacion save(Asignacion asignacion) {

        Estudiante estudiante = estudianteDao.findById(asignacion.getEstudiante().getIdEstudiante()).orElse(new Estudiante());
        asignacion.setEstudiante(estudiante);

        Curso curso = cursoDao.findById(asignacion.getCurso().getIdCurso()).orElse(new Curso());
        asignacion.setCurso(curso);

        return asignacionDao.save(asignacion);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        asignacionDao.deleteById(id);
    }

}
