/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.tda.curso.ejb.jpa;

import ec.tda.curso.ejb.jpa.exceptions.NonexistentEntityException;
import ec.tda.curso.ejb.jpa.exceptions.PreexistingEntityException;
import ec.tda.curso.ejb.jpa.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.GeneratorCode;
import entidades.Message;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author santi
 */
public class MessageJpaController implements Serializable {

    public MessageJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Message message) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            GeneratorCode idCode = message.getIdCode();
            if (idCode != null) {
                idCode = em.getReference(idCode.getClass(), idCode.getIdCode());
                message.setIdCode(idCode);
            }
            em.persist(message);
            if (idCode != null) {
                idCode.getMessageList().add(message);
                idCode = em.merge(idCode);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findMessage(message.getIdeMessage()) != null) {
                throw new PreexistingEntityException("Message " + message + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Message message) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Message persistentMessage = em.find(Message.class, message.getIdeMessage());
            GeneratorCode idCodeOld = persistentMessage.getIdCode();
            GeneratorCode idCodeNew = message.getIdCode();
            if (idCodeNew != null) {
                idCodeNew = em.getReference(idCodeNew.getClass(), idCodeNew.getIdCode());
                message.setIdCode(idCodeNew);
            }
            message = em.merge(message);
            if (idCodeOld != null && !idCodeOld.equals(idCodeNew)) {
                idCodeOld.getMessageList().remove(message);
                idCodeOld = em.merge(idCodeOld);
            }
            if (idCodeNew != null && !idCodeNew.equals(idCodeOld)) {
                idCodeNew.getMessageList().add(message);
                idCodeNew = em.merge(idCodeNew);
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
                Integer id = message.getIdeMessage();
                if (findMessage(id) == null) {
                    throw new NonexistentEntityException("The message with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Message message;
            try {
                message = em.getReference(Message.class, id);
                message.getIdeMessage();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The message with id " + id + " no longer exists.", enfe);
            }
            GeneratorCode idCode = message.getIdCode();
            if (idCode != null) {
                idCode.getMessageList().remove(message);
                idCode = em.merge(idCode);
            }
            em.remove(message);
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

    public List<Message> findMessageEntities() {
        return findMessageEntities(true, -1, -1);
    }

    public List<Message> findMessageEntities(int maxResults, int firstResult) {
        return findMessageEntities(false, maxResults, firstResult);
    }

    private List<Message> findMessageEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Message.class));
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

    public Message findMessage(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Message.class, id);
        } finally {
            em.close();
        }
    }

    public int getMessageCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Message> rt = cq.from(Message.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
