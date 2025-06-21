package com.risosu.EDesalesProgramacionNCapasJunio3.Controller;

import com.risosu.EDesalesProgramacionNCapasJunio3.DAO.ColoniaDAOImplementation;
import com.risosu.EDesalesProgramacionNCapasJunio3.DAO.DireccionDAOImplementation;
import com.risosu.EDesalesProgramacionNCapasJunio3.DAO.EstadoDAO;
import com.risosu.EDesalesProgramacionNCapasJunio3.DAO.EstadoDAOImplementation;
import com.risosu.EDesalesProgramacionNCapasJunio3.DAO.MunicipioDAOImplementation;
import com.risosu.EDesalesProgramacionNCapasJunio3.DAO.PaisDAOImplementation;
import com.risosu.EDesalesProgramacionNCapasJunio3.DAO.UsuarioDAOImplementation;
import com.risosu.EDesalesProgramacionNCapasJunio3.ML.Usuario;
import com.risosu.EDesalesProgramacionNCapasJunio3.ML.UsuarioDireccion;
import com.risosu.EDesalesProgramacionNCapasJunio3.ML.Colonia;
import com.risosu.EDesalesProgramacionNCapasJunio3.ML.Direccion;
import com.risosu.EDesalesProgramacionNCapasJunio3.ML.Pais;
import com.risosu.EDesalesProgramacionNCapasJunio3.ML.Result;
import jakarta.validation.Valid;
import java.util.Base64;
import java.security.AlgorithmConstraints;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller //controlador
@RequestMapping("/Presentacion")
public class PresentacionController {

    @Autowired
    private UsuarioDAOImplementation usuarioDAOImplementation;
    @Autowired
    private PaisDAOImplementation paisDAOImplementation;
    @Autowired
    private EstadoDAOImplementation estadoDAOImplementation;
    @Autowired
    private MunicipioDAOImplementation municipioDAOImplementation;
    @Autowired
    private ColoniaDAOImplementation coloniaDAOImplementation;
    @Autowired
    private DireccionDAOImplementation direccionDAOImplementation;

    @GetMapping
    public String Index(Model model) {
        Result result = usuarioDAOImplementation.GetAll();

        if (result.correct) {

            model.addAttribute("usuarioDireccion", result.objects);
        }
        return "Presentacion";

    }

    @GetMapping("UsuarioForm/{idAlumno}") // este prepara la vista de formualrio
    public String Accion(Model model, @PathVariable int idAlumno) {

        if (idAlumno < 1) {
            model.addAttribute("usuarioDireccion", new UsuarioDireccion());
            model.addAttribute("pais", paisDAOImplementation.GetAllPais().objects);

            UsuarioDireccion alumnoDireccion = new UsuarioDireccion();
            alumnoDireccion.Usuario = new Usuario();
            alumnoDireccion.Direccion = new Direccion();
            model.addAttribute("usuarioDireccion", alumnoDireccion);

            return "UsuarioForm";
        } else {

            model.addAttribute("usuarioDireccion", usuarioDAOImplementation.GetDatosAlumnoPDByIdAlumno(idAlumno).object);
            return "UsuarioEditable";
        }

    }

    @GetMapping("/formeditable")
    public String accionEditable(
            @RequestParam("idUsuario") int idUsuario,
            @RequestParam(required = false) Integer idDireccion,
            Model model) {
        //Editar usuario n y -1
        if (idDireccion == null) {
            
            Usuario usuario = (Usuario) usuarioDAOImplementation.GetDatosBasicosUsuarioByIdUsuario(idUsuario).object;

            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            usuarioDireccion.Usuario = usuario;
            usuarioDireccion.Usuario.setIdUsuario(idUsuario);
            usuarioDireccion.Direccion = new Direccion();
            usuarioDireccion.Direccion.setIdDireccion(-1);
          

            model.addAttribute("usuarioDireccion", usuarioDireccion);
            
            //Agregar direccion n y 0
        } else if (idDireccion == 0) {
            
          //Direccion direccion = (Direccion) direccionDAOImplementation.GetDireccionCMEPByIdUsuario(idUsuario).object;

            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            usuarioDireccion.Usuario = new Usuario();
            usuarioDireccion.Usuario.setIdUsuario(idUsuario);
            usuarioDireccion.Direccion = new Direccion();
            
            
            model.addAttribute("usuarioDireccion", usuarioDireccion); 
            model.addAttribute("pais", paisDAOImplementation.GetAllPais().objects);
            //Editar direccion n y m
        } else {
           // model.addAttribute("usuarioDireccion", new UsuarioDireccion());
            model.addAttribute("pais", paisDAOImplementation.GetAllPais().objects);

            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
//            alumnoDireccion.Usuario = new Usuario();
//            alumnoDireccion.Direccion = new Direccion();
            
            
            Result result =  direccionDAOImplementation.GetDireccionCMEPByIdUsuario(idDireccion);
            
            usuarioDireccion = (UsuarioDireccion) result.object;
            usuarioDireccion.Usuario = new Usuario();
            usuarioDireccion.Usuario.setIdUsuario(idUsuario);
            
            model.addAttribute("usuarioDireccion", usuarioDireccion);

        }

        return "UsuarioForm";
    }

//    @GetMapping("UsuarioEditable")//Este prepara la vista para editar usuario
//    public String 
    @GetMapping("/GetEstadosBYIdPais/{IdPais}")
    @ResponseBody
    public Result GetEstadoBYIdPais(@PathVariable("IdPais") int IdPais) {

        return estadoDAOImplementation.GetEstadoByIdPais(IdPais);
    }

    @GetMapping("/GetMunicipiosByIdEstado/{IdEstado}")
    @ResponseBody
    public Result GetMunicipioByIdEstados(@PathVariable("IdEstado") int IdEstado) {

        return municipioDAOImplementation.GetMunicipioByIdEstado(IdEstado);

    }

    @GetMapping("/GetColoniaByIdMunicipio/{IdMunicipio}")
    @ResponseBody
    public Result GetColoniaByIdMunicipio(@PathVariable("IdMunicipio") int IdMunicipio) {

        return coloniaDAOImplementation.GetColoniaByMunicipio(IdMunicipio);

    }

    @PostMapping("form") // este recupera los datos del formulario
    public String Accion(@Valid @ModelAttribute UsuarioDireccion alumnoDireccion,
            BindingResult bindingResult,
            @RequestParam MultipartFile imagenFile,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("usuarioDireccion", alumnoDireccion);
            return "UsuarioForm";
        }
        try {

            if (!imagenFile.isEmpty()) {

                byte[] bytes = imagenFile.getBytes();
                String imgBase64 = Base64.getEncoder().encodeToString(bytes);
                alumnoDireccion.Usuario.setImagenPerfil(imgBase64);

            }
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());

        }

        Result result = usuarioDAOImplementation.Add(alumnoDireccion);

        return "Presentacion"; // redireccionen a la vista de GetAll

    }
    @PostMapping("AgregarNuevaDireccion")
    public String AgregarNuevaDireccionUsuario(){
    
        return "mfmf" ;
    }

}
