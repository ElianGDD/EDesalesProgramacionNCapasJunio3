package com.risosu.EDesalesProgramacionNCapasJunio3.DAO;

import com.risosu.EDesalesProgramacionNCapasJunio3.ML.Usuario;
import com.risosu.EDesalesProgramacionNCapasJunio3.ML.UsuarioDireccion;
import com.risosu.EDesalesProgramacionNCapasJunio3.ML.Result;


public interface UsuarioDAO  {
    
     Result GetAll();

     Result Add(UsuarioDireccion alumnoDireccion);
     
     Result GetDatosAlumnoPDByIdAlumno(int IdUsuario);
     
     Result GetDatosBasicosUsuarioByIdUsuario(int IdUsuario);
     
}
