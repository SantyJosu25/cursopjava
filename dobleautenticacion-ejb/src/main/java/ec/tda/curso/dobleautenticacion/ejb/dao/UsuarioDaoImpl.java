package ec.tda.curso.dobleautenticacion.ejb.dao;

import ec.tda.curso.dobleautenticacion_ejb.dao.generic.DaoGenericoImplements;
import ec.tda.curso.dobleautenticacion_ejb.entidades.Usuario;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import org.apache.commons.codec.digest.DigestUtils;

public class UsuarioDaoImpl extends DaoGenericoImplements<Usuario> {

    public List<Usuario> findAllUser() {
        Query consulta = getEntityManager().createNamedQuery("Usuario.findAll");
        List<Usuario> listusuario = consulta.getResultList();
        return listusuario;
    }

    public boolean varificarUsuario1fa(String nickName, String password) {
        try {
            StringBuilder query = new StringBuilder("select u from Usuario u where" + "u.usUsername = :nick u.usPass= 2?");
            Query consulta = getEntityManager().createQuery(query.toString());
            consulta.setParameter("nick", nickName);
            consulta.setParameter(2, DigestUtils.md5Hex(password));
            Usuario user = (Usuario) consulta.getSingleResult();
            boolean valor = false;
            if (user != null) {
                valor = true;
            }

            return valor;
        } catch (EntityNotFoundException e) {
            return false;
        }
    }
}
