package ec.tda.curso.dobleautenticacion_ejb.dao.generic;

import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBMetaData;
import javax.ejb.Handle;
import javax.ejb.HomeHandle;
import javax.ejb.RemoveException;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class DaoGenericoImplements<E> implements DaoGenerico<E> {
    
//    @PersistenceContext(unitName = "DobleFatenticacion-PU")
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("DobleFatenticacion-PU");
    private EntityManager entityManager = emf.createEntityManager();

    public EntityManager getEntityManager() {
        return entityManager;
    }
    
    @Override
    public void save(E objeto) throws Exception {
        entityManager.persist(objeto);
    }

    @Override
    public void update(E objeto) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(E objeto) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getById(Class<E> classe, Object pk) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<E> getAll(Class<E> classe) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer generarId(String entidad, String Id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(Handle handle) throws RemoteException, RemoveException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(Object o) throws RemoteException, RemoveException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EJBMetaData getEJBMetaData() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HomeHandle getHomeHandle() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
