package com.risosu.EDesalesProgramacionNCapasJunio3.DAO;

import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Roll;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Usuario;
import com.risosu.EDesalesProgramacionNCapasJunio3.ML.Result;
import com.risosu.EDesalesProgramacionNCapasJunio3.ML.UsuarioDireccion;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class IUsuarioJPADAOImplementation implements IUsuarioJPADAO{
    
    @Autowired
    private EntityManager entityManager;
    
    
    @Transactional
    @Override
    public Result Add(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();
        try {

            Usuario usuarioJPA = new Usuario();
            usuarioJPA.setNombre(usuarioDireccion.Usuario.getNombre());
            usuarioJPA.setFechaNacimiento(usuarioDireccion.Usuario.getFechaNacimiento());
            usuarioJPA.setUserName(usuarioDireccion.Usuario.getUserName());
            usuarioJPA.setApellidoPaterno(usuarioDireccion.Usuario.getApellidoPaterno());
            usuarioJPA.setApellidoMaterno(usuarioDireccion.Usuario.getApellidoMaterno());
            usuarioJPA.setPassword(usuarioDireccion.Usuario.getPassword());
            usuarioJPA.setSexo(usuarioDireccion.Usuario.getSexo());
            usuarioJPA.setTelefono(usuarioDireccion.Usuario.getTelefono());
            usuarioJPA.setCelular(usuarioDireccion.Usuario.getCelular());
            usuarioJPA.setCurp(usuarioDireccion.Usuario.getCurp());
            usuarioJPA.setEmail(usuarioDireccion.Usuario.getEmail());
            usuarioJPA.Roll = new Roll();
            usuarioJPA.Roll.setIdRoll(usuarioDireccion.Usuario.Roll.getIdRoll());
            entityManager.persist(usuarioJPA);
            /*persistir direccion*/
            //direccionJPA = new DIreccionJPA
            //direccionjpa.alumno = alumnoJPA

            result.correct = true;

        } catch (Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;

    }

    @Override
    public Result UpdateDatosUsuario(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();
        
        try {
            Usuario usuarioJPA = new Usuario();
            usuarioJPA.setNombre(usuarioDireccion.Usuario.getNombre());
            usuarioJPA.setFechaNacimiento(usuarioDireccion.Usuario.getFechaNacimiento());
            usuarioJPA.setUserName(usuarioDireccion.Usuario.getUserName());
            usuarioJPA.setApellidoPaterno(usuarioDireccion.Usuario.getApellidoPaterno());
            usuarioJPA.setApellidoMaterno(usuarioDireccion.Usuario.getApellidoMaterno());
            usuarioJPA.setPassword(usuarioDireccion.Usuario.getPassword());
            usuarioJPA.setSexo(usuarioDireccion.Usuario.getSexo());
            usuarioJPA.setTelefono(usuarioDireccion.Usuario.getTelefono());
            usuarioJPA.setCelular(usuarioDireccion.Usuario.getCelular());
            usuarioJPA.setCurp(usuarioDireccion.Usuario.getCurp());
            usuarioJPA.setEmail(usuarioDireccion.Usuario.getEmail());
            usuarioJPA.Roll = new Roll();
            usuarioJPA.Roll.setIdRoll(usuarioDireccion.Usuario.Roll.getIdRoll());
            entityManager.merge(usuarioJPA);
            
            
        } catch (Exception ex) {
            result.errorMessage = ex.getLocalizedMessage();
            result.correct = false;
            result.ex = ex;
            
        }
        
        return result;
    }

}
