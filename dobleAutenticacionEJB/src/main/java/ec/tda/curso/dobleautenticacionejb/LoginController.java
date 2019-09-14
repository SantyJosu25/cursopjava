package ec.tda.curso.dobleautenticacionejb;

import ec.tda.curso.dobleautenticacion.ejb.dao.UsuarioDao;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean
public class LoginController {

    @EJB
    private UsuarioDao usuarioDao;
    /**
     * Variable de sesion login*
     */
    private String nombreUsuario;
    private String clave;

    /**
     * Metodos*
     */
    public void login() {
        boolean login = false;
        login = usuarioDao.varificarUsuario1fa(nombreUsuario, clave);
        if (login) {
            addMessage("Logeo Exitoso");
        } else {
            addMessage("credenciales Incorrectas");
        }
        System.out.println("Datos Usuario: " + nombreUsuario);
        System.out.println("Clave usuario: " + clave);
    }

    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

}
