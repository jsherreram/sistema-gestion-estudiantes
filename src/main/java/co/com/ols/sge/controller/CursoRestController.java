/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.ols.sge.controller;

import co.com.ols.sge.entity.Curso;
import co.com.ols.sge.service.ICursoService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author jsherreram
 */
@RestController
@RequestMapping("/cursos")
public class CursoRestController {

    @Autowired
    private ICursoService cursoService;

    @GetMapping
    public ResponseEntity<List<Curso>> findAll(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {

        Sort sortByNombreCurso = Sort.by("nombreCurso");
        ResponseEntity<List<Curso>> responseEntity = null;
        List<Curso> cursos = null;

        if (page != null && size != null) {
            // Con paginación
            Pageable pageable = PageRequest.of(page, size, sortByNombreCurso);
            cursos = cursoService.findAll(pageable).getContent();
        } else {
            // Sin paginación
            cursos = cursoService.findAll(sortByNombreCurso);
        }
        if (cursos.size() > 0) {
            responseEntity = new ResponseEntity<List<Curso>>(cursos, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<List<Curso>>(HttpStatus.NO_CONTENT);
        }
        return responseEntity;
    }

//    @GetMapping
//    public ResponseEntity<List<Curso>> findAll() {
//
//        ResponseEntity<List<Curso>> responseEntity;
//
//        List<Curso> cursos = cursoService.findAll();
//        if (cursos.size() > 0) {
//            responseEntity = new ResponseEntity<List<Curso>>(cursos, HttpStatus.OK);
//        } else {
//            responseEntity = new ResponseEntity<List<Curso>>(HttpStatus.NO_CONTENT);
//        }
//        return responseEntity;
//    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Curso> findById(@PathVariable long id) {

        Curso curso = cursoService.findById(id);
        ResponseEntity<Curso> responseEntity = null;

        if (curso != null) {
            responseEntity = new ResponseEntity<Curso>(curso, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<Curso>(HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }
    
    @PostMapping
    public ResponseEntity<Map<String, Object>> insert(@Valid @RequestBody Curso curso, BindingResult result) {

        Map<String, Object> responseAsMap = new HashMap<String, Object>();
        ResponseEntity<Map<String, Object>> responseEntity = null;
        List<String> errores = null;

        if (result.hasErrors()) {
            errores = new ArrayList<String>();
            for (ObjectError error : result.getAllErrors()) {
                errores.add(error.getDefaultMessage());
            }
            responseAsMap.put("errors", errores);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);
            return responseEntity;
        }
        try {
            Curso cursoFromDB = cursoService.save(curso);
            if (cursoFromDB != null) {
                responseAsMap.put("curso", curso);
                responseAsMap.put("mensaje", "El curso con id: " + curso.getIdCurso() + ", se ha creado exitosamente!");
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.CREATED);
            } else {
                responseAsMap.put("mensaje", "El curso no se ha creado exitosamente!");
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DataAccessException e) {
            responseAsMap.put("mensaje", "El curso no se ha creado exitosamente: " + e.getMostSpecificCause().toString());
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable long id, @Valid @RequestBody Curso curso, BindingResult result) {

        Map<String, Object> responseAsMap = new HashMap<String, Object>();
        ResponseEntity<Map<String, Object>> responseEntity = null;
        List<String> errores = null;

        if (result.hasErrors()) {
            errores = new ArrayList<String>();
            for (ObjectError error : result.getAllErrors()) {
                errores.add(error.getDefaultMessage());
            }
            responseAsMap.put("errors", errores);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);
            return responseEntity;
        }
        try {
            curso.setIdCurso(id);
            Curso cursoFromDB = cursoService.save(curso);
            if (cursoFromDB != null) {
                responseAsMap.put("curso", curso);
                responseAsMap.put("mensaje", "El curso con id: " + curso.getIdCurso() + ", se ha actualizado exitosamente!");
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.ACCEPTED);
            } else {
                responseAsMap.put("mensaje", "El curso no se ha actualizado exitosamente!");
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DataAccessException e) {
            responseAsMap.put("mensaje", "El curso no se ha actualizado exitosamente: " + e.getMostSpecificCause().toString());
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {

        Curso curso = cursoService.findById(id);
        ResponseEntity<Curso> responseEntity;

        if (curso != null) {
            cursoService.deleteById(id);
            responseEntity = new ResponseEntity("El curso con id: " + curso.getIdCurso() + ", se eliminó exitosamente!", HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

}
