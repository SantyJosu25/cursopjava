package ec.tda.curso.dobleautenticacion.ejb.dao;

import ec.tda.curso.dobleautenticacion_ejb.dao.generic.DaoGenerico;
import ec.tda.curso.dobleautenticacion_ejb.entidades.Usuario;
import java.util.List;
import javax.ejb.Local;

@Local
public interface UsuarioDao extends DaoGenerico<Usuario> {

    public List<Usuario> findAllUser();

    public boolean varificarUsuario1fa(String usUsername, String usPass);

    public Usuario findByNicknamePass(String usUsername, String usPass);
}
