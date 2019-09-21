package ec.tda.curso.dobleautenticacionejb;

import ec.tda.curso.ejb.jpa.UsuarioJpaController;
import entidades.Usuario;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.persistence.Persistence;

;

@ManagedBean
public class LoginController {

    @EJB
    private UsuarioJpaController jpa;

    private List<Usuario> list1;

    private List<Usuario> list2;

    public List<Usuario> getList1() {
        return list1;
    }

    public void setList1(List<Usuario> list1) {
        this.list1 = list1;
    }

    public List<Usuario> getList2() {
        return list2;
    }

    public void setList2(List<Usuario> list2) {
        this.list2 = list2;
    }
    
    @PostConstruct
    public void iniciarLista(){
        jpa = new UsuarioJpaController(Persistence.createEntityManagerFactory("ec.tda.curso_ejb-jpa_ejb_1.0-SNAPSHOTPU"));
        list1 = jpa.findUsuarioEntities();
    }
        
}
