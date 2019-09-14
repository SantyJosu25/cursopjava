package ec.tda.curso.dobleautenticacionejb;

import ec.tda.curso.dobleautenticacion.ejb.dao.GenaraacionCodeDao;
import ec.tda.curso.dobleautenticacion.ejb.dao.UsuarioDao;
import ec.tda.curso.dobleautenticacion_ejb.entidades.GeneratorCode;
import ec.tda.curso.dobleautenticacion_ejb.entidades.Usuario;
import ec.tda.curso.dobleautenticacion_ejb.utilitarios.AlgoritmoGeneradorCodigo;
import ec.tda.curso.dobleautenticacion_ejb.utilitarios.SendMailGmail;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.swing.JOptionPane;
import org.primefaces.PrimeFaces;

@ManagedBean
public class LoginController {

    @EJB
    private UsuarioDao usuarioDao;
    @EJB
    private GenaraacionCodeDao generadorcodigo;
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

        try {
            boolean login = false;

            login = usuarioDao.varificarUsuario1fa(nombreUsuario, clave);

            Usuario user = usuarioDao.findByNicknamePass(nombreUsuario, clave);

            if (login) {
                AlgoritmoGeneradorCodigo agc = new AlgoritmoGeneradorCodigo();
                agc.creaPass();

                GeneratorCode gc = new GeneratorCode();
                gc.setCodCode(agc.getPass());
                gc.setCodDataEnd(new Date());
                gc.setCodDataStart(new Date());
                gc.setIdCode(generadorcodigo.generarId(GeneratorCode.class.getName(), "idCode"));
                gc.setIdUser(user);

                //guardado codigo de email
                generadorcodigo.save(gc);

                //envio de codigo a email
                SendMailGmail sendEmail = new SendMailGmail();
                sendEmail.setUser(user);
                sendEmail.setFactorAutenticacion(agc.getPass());

                sendEmail.enviarMail();

                addMessage("Logeo Exitoso");

                PrimeFaces.current().executeScript("PF('pnlgSegundoFactor').show()");
            } else {
                addMessage("Logeo Fallido");
                JOptionPane.showMessageDialog(null, "Datos Incorrectos");
            }
        } catch (Exception e) {
            e.printStackTrace();
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
