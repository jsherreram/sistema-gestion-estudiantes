/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.ols.sge.controller;

import co.com.ols.sge.entity.Estudiante;
import co.com.ols.sge.service.IEstudianteService;
import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author jsherreram
 */
@RestController
@RequestMapping("/estudiantes")
public class EstudianteRestController {

    @Autowired
    private IEstudianteService estudianteService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER','GUEST')")
    public ResponseEntity<List<Estudiante>> findAll(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {

        Sort sortByApellido = Sort.by("apellido");
        ResponseEntity<List<Estudiante>> responseEntity = null;
        List<Estudiante> estudiantes = null;

        if (page != null && size != null) {
            // Con paginación
            Pageable pageable = PageRequest.of(page, size, sortByApellido);
            estudiantes = estudianteService.findAll(pageable).getContent();
        } else {
            // Sin paginación
            estudiantes = estudianteService.findAll(sortByApellido);
        }
        if (estudiantes.size() > 0) {
            responseEntity = new ResponseEntity<List<Estudiante>>(estudiantes, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<List<Estudiante>>(HttpStatus.NO_CONTENT);
        }
        return responseEntity;
    }

//    @GetMapping
//    public ResponseEntity<List<Estudiante>> findAll() {
//
//        ResponseEntity<List<Estudiante>> responseEntity;
//
//        List<Estudiante> estudiantes = estudianteService.findAll();
//        if (estudiantes.size() > 0) {
//            responseEntity = new ResponseEntity<List<Estudiante>>(estudiantes, HttpStatus.OK);
//        } else {
//            responseEntity = new ResponseEntity<List<Estudiante>>(HttpStatus.NO_CONTENT);
//        }
//        return responseEntity;
//    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER','GUEST')")
    public ResponseEntity<Estudiante> findById(@PathVariable long id) {

        Estudiante estudiante = estudianteService.findById(id);
        ResponseEntity<Estudiante> responseEntity = null;

        if (estudiante != null) {
            responseEntity = new ResponseEntity<Estudiante>(estudiante, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<Estudiante>(HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Map<String, Object>> insert(@Valid @RequestBody Estudiante estudiante, BindingResult result) {

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
            Estudiante estudianteFromDB = estudianteService.save(estudiante);
            if (estudianteFromDB != null) {
                responseAsMap.put("estudiante", estudiante);
                responseAsMap.put("mensaje", "El estudiante con id: " + estudiante.getIdEstudiante() + ", se ha creado exitosamente!");
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.CREATED);
            } else {
                responseAsMap.put("mensaje", "El estudiante no se ha creado exitosamente!");
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DataAccessException e) {
            responseAsMap.put("mensaje", "El estudiante no se ha creado exitosamente: " + e.getMostSpecificCause().toString());
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Map<String, Object>> update(@PathVariable long id, @Valid @RequestBody Estudiante estudiante, BindingResult result) {

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
            estudiante.setIdEstudiante(id);
            Estudiante estudianteFromDB = estudianteService.save(estudiante);
            if (estudianteFromDB != null) {
                responseAsMap.put("estudiante", estudiante);
                responseAsMap.put("mensaje", "El estudiante con id: " + estudiante.getIdEstudiante() + ", se ha actualizado exitosamente!");
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.ACCEPTED);
            } else {
                responseAsMap.put("mensaje", "El estudiante no se ha actualizado exitosamente!");
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DataAccessException e) {
            responseAsMap.put("mensaje", "El estudiante no se ha actualizado exitosamente: " + e.getMostSpecificCause().toString());
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteById(@PathVariable long id) {

        Estudiante estudiante = estudianteService.findById(id);
        ResponseEntity<Estudiante> responseEntity;

        if (estudiante != null) {
            estudianteService.deleteById(id);
            responseEntity = new ResponseEntity("El estudiante con id: " + estudiante.getIdEstudiante() + ", se eliminó exitosamente!", HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

}
