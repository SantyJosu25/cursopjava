package ec.tda.curso.dobleautenticacion.ejb.dao;

import ec.tda.curso.dobleautenticacion_ejb.dao.generic.DaoGenericoImplements;
import ec.tda.curso.dobleautenticacion_ejb.entidades.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import org.apache.commons.codec.digest.DigestUtils;

@Stateless
public class UsuarioDaoImpl extends DaoGenericoImplements<Usuario> implements UsuarioDao{

    @Override
    public List<Usuario> findAllUser() {
        Query consulta = getEntityManager().createNamedQuery("Usuario.findAll");
        List<Usuario> listusuario = consulta.getResultList();
        return listusuario;
    }

    @Override
    public boolean varificarUsuario1fa(String usUsername, String usPass) {
        try {
            StringBuilder query = new StringBuilder("select u from Usuario u where u.usUsername = ?1 and u.usPass = ?2");
            Query consulta = getEntityManager().createQuery(query.toString());
            consulta.setParameter(1, usUsername);
            consulta.setParameter(2, usPass);
            Usuario user = (Usuario) consulta.getSingleResult();
            boolean valor = false;
            if (user != null) {
                valor = true;
            }

            return valor;
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
