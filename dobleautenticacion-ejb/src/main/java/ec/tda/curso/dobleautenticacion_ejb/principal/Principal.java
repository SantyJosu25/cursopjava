package ec.tda.curso.dobleautenticacion_ejb.principal;

import ec.tda.curso.dobleautenticacion.ejb.dao.UsuarioDao;
import ec.tda.curso.dobleautenticacion.ejb.dao.UsuarioDaoImpl;
import ec.tda.curso.dobleautenticacion_ejb.entidades.Usuario;
import java.util.List;
import javax.ejb.EJB;
import org.apache.commons.codec.digest.DigestUtils;

public class Principal extends UsuarioDaoImpl {

    @EJB
    public UsuarioDao userDAO;

    public static void main(String[] args) throws Exception {
        
        UsuarioDaoImpl userDaoImp = new UsuarioDaoImpl();
//
//        Usuario usuario = new Usuario();
//        usuario.setUsEmail("santiago-1235@hotmail.com");
//        usuario.setIdUser(2);
//        usuario.setUsUsername("san");
//        usuario.setUsUser("san");
//        usuario.setUsPass(DigestUtils.md5Hex("1234"));

//        userDaoImp.save(usuario);
//        userDaoImp.findAllUser();
        List<Usuario> usuarios = userDaoImp.findAllUser();
        System.out.println(usuarios.size());
        boolean valor = false;

        valor = userDaoImp.varificarUsuario1fa("santy", "zdqmko2516");
        System.out.println("Existe Usuario " + valor);
    }
}
