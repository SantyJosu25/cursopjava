package ec.tda.curso.dobleautenticacionejb;

import ec.tda.curso.dobleautenticacion.ejb.dao.UsuarioDao;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

@ManagedBean
public class LoginController {

    @EJB
    private UsuarioDao usuarioDao;
    /**
     * Variable de sesion login*
     */
    private String nombreUsuario;
    private String clave;

    public String getCodigoEmail() {
        return codigoEmail;
    }

    public void setCodigoEmail(String codigoEmail) {
        this.codigoEmail = codigoEmail;
    }
    private String codigoEmail;

    /**
     * Metodos*
     */
    public void login() {
        
        boolean login = false;
        
        login = usuarioDao.varificarUsuario1fa(nombreUsuario, clave);
        if (login) {
            addMessage("Logeo Exitoso");
            PrimeFaces.current().executeScript("PF('pnlgSegundoFactor').show()");
        } else {
            addMessage("Logeo Fallido");
        }
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
