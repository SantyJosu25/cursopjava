/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.tda.curso.ejb.jpa;

import ec.tda.curso.ejb.jpa.exceptions.IllegalOrphanException;
import ec.tda.curso.ejb.jpa.exceptions.NonexistentEntityException;
import ec.tda.curso.ejb.jpa.exceptions.PreexistingEntityException;
import ec.tda.curso.ejb.jpa.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.GeneratorCode;
import entidades.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author santi
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (usuario.getGeneratorCodeList() == null) {
            usuario.setGeneratorCodeList(new ArrayList<GeneratorCode>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<GeneratorCode> attachedGeneratorCodeList = new ArrayList<GeneratorCode>();
            for (GeneratorCode generatorCodeListGeneratorCodeToAttach : usuario.getGeneratorCodeList()) {
                generatorCodeListGeneratorCodeToAttach = em.getReference(generatorCodeListGeneratorCodeToAttach.getClass(), generatorCodeListGeneratorCodeToAttach.getIdCode());
                attachedGeneratorCodeList.add(generatorCodeListGeneratorCodeToAttach);
            }
            usuario.setGeneratorCodeList(attachedGeneratorCodeList);
            em.persist(usuario);
            for (GeneratorCode generatorCodeListGeneratorCode : usuario.getGeneratorCodeList()) {
                Usuario oldIdUserOfGeneratorCodeListGeneratorCode = generatorCodeListGeneratorCode.getIdUser();
                generatorCodeListGeneratorCode.setIdUser(usuario);
                generatorCodeListGeneratorCode = em.merge(generatorCodeListGeneratorCode);
                if (oldIdUserOfGeneratorCodeListGeneratorCode != null) {
                    oldIdUserOfGeneratorCodeListGeneratorCode.getGeneratorCodeList().remove(generatorCodeListGeneratorCode);
                    oldIdUserOfGeneratorCodeListGeneratorCode = em.merge(oldIdUserOfGeneratorCodeListGeneratorCode);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findUsuario(usuario.getIdUser()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getIdUser());
            List<GeneratorCode> generatorCodeListOld = persistentUsuario.getGeneratorCodeList();
            List<GeneratorCode> generatorCodeListNew = usuario.getGeneratorCodeList();
            List<String> illegalOrphanMessages = null;
            for (GeneratorCode generatorCodeListOldGeneratorCode : generatorCodeListOld) {
                if (!generatorCodeListNew.contains(generatorCodeListOldGeneratorCode)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain GeneratorCode " + generatorCodeListOldGeneratorCode + " since its idUser field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<GeneratorCode> attachedGeneratorCodeListNew = new ArrayList<GeneratorCode>();
            for (GeneratorCode generatorCodeListNewGeneratorCodeToAttach : generatorCodeListNew) {
                generatorCodeListNewGeneratorCodeToAttach = em.getReference(generatorCodeListNewGeneratorCodeToAttach.getClass(), generatorCodeListNewGeneratorCodeToAttach.getIdCode());
                attachedGeneratorCodeListNew.add(generatorCodeListNewGeneratorCodeToAttach);
            }
            generatorCodeListNew = attachedGeneratorCodeListNew;
            usuario.setGeneratorCodeList(generatorCodeListNew);
            usuario = em.merge(usuario);
            for (GeneratorCode generatorCodeListNewGeneratorCode : generatorCodeListNew) {
                if (!generatorCodeListOld.contains(generatorCodeListNewGeneratorCode)) {
                    Usuario oldIdUserOfGeneratorCodeListNewGeneratorCode = generatorCodeListNewGeneratorCode.getIdUser();
                    generatorCodeListNewGeneratorCode.setIdUser(usuario);
                    generatorCodeListNewGeneratorCode = em.merge(generatorCodeListNewGeneratorCode);
                    if (oldIdUserOfGeneratorCodeListNewGeneratorCode != null && !oldIdUserOfGeneratorCodeListNewGeneratorCode.equals(usuario)) {
                        oldIdUserOfGeneratorCodeListNewGeneratorCode.getGeneratorCodeList().remove(generatorCodeListNewGeneratorCode);
                        oldIdUserOfGeneratorCodeListNewGeneratorCode = em.merge(oldIdUserOfGeneratorCodeListNewGeneratorCode);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getIdUser();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getIdUser();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<GeneratorCode> generatorCodeListOrphanCheck = usuario.getGeneratorCodeList();
            for (GeneratorCode generatorCodeListOrphanCheckGeneratorCode : generatorCodeListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the GeneratorCode " + generatorCodeListOrphanCheckGeneratorCode + " in its generatorCodeList field has a non-nullable idUser field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(usuario);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
