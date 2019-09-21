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
import entidades.GeneratorCode;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Usuario;
import entidades.Message;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author santi
 */
public class GeneratorCodeJpaController implements Serializable {

    public GeneratorCodeJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GeneratorCode generatorCode) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (generatorCode.getMessageList() == null) {
            generatorCode.setMessageList(new ArrayList<Message>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuario idUser = generatorCode.getIdUser();
            if (idUser != null) {
                idUser = em.getReference(idUser.getClass(), idUser.getIdUser());
                generatorCode.setIdUser(idUser);
            }
            List<Message> attachedMessageList = new ArrayList<Message>();
            for (Message messageListMessageToAttach : generatorCode.getMessageList()) {
                messageListMessageToAttach = em.getReference(messageListMessageToAttach.getClass(), messageListMessageToAttach.getIdeMessage());
                attachedMessageList.add(messageListMessageToAttach);
            }
            generatorCode.setMessageList(attachedMessageList);
            em.persist(generatorCode);
            if (idUser != null) {
                idUser.getGeneratorCodeList().add(generatorCode);
                idUser = em.merge(idUser);
            }
            for (Message messageListMessage : generatorCode.getMessageList()) {
                GeneratorCode oldIdCodeOfMessageListMessage = messageListMessage.getIdCode();
                messageListMessage.setIdCode(generatorCode);
                messageListMessage = em.merge(messageListMessage);
                if (oldIdCodeOfMessageListMessage != null) {
                    oldIdCodeOfMessageListMessage.getMessageList().remove(messageListMessage);
                    oldIdCodeOfMessageListMessage = em.merge(oldIdCodeOfMessageListMessage);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findGeneratorCode(generatorCode.getIdCode()) != null) {
                throw new PreexistingEntityException("GeneratorCode " + generatorCode + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GeneratorCode generatorCode) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            GeneratorCode persistentGeneratorCode = em.find(GeneratorCode.class, generatorCode.getIdCode());
            Usuario idUserOld = persistentGeneratorCode.getIdUser();
            Usuario idUserNew = generatorCode.getIdUser();
            List<Message> messageListOld = persistentGeneratorCode.getMessageList();
            List<Message> messageListNew = generatorCode.getMessageList();
            List<String> illegalOrphanMessages = null;
            for (Message messageListOldMessage : messageListOld) {
                if (!messageListNew.contains(messageListOldMessage)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Message " + messageListOldMessage + " since its idCode field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idUserNew != null) {
                idUserNew = em.getReference(idUserNew.getClass(), idUserNew.getIdUser());
                generatorCode.setIdUser(idUserNew);
            }
            List<Message> attachedMessageListNew = new ArrayList<Message>();
            for (Message messageListNewMessageToAttach : messageListNew) {
                messageListNewMessageToAttach = em.getReference(messageListNewMessageToAttach.getClass(), messageListNewMessageToAttach.getIdeMessage());
                attachedMessageListNew.add(messageListNewMessageToAttach);
            }
            messageListNew = attachedMessageListNew;
            generatorCode.setMessageList(messageListNew);
            generatorCode = em.merge(generatorCode);
            if (idUserOld != null && !idUserOld.equals(idUserNew)) {
                idUserOld.getGeneratorCodeList().remove(generatorCode);
                idUserOld = em.merge(idUserOld);
            }
            if (idUserNew != null && !idUserNew.equals(idUserOld)) {
                idUserNew.getGeneratorCodeList().add(generatorCode);
                idUserNew = em.merge(idUserNew);
            }
            for (Message messageListNewMessage : messageListNew) {
                if (!messageListOld.contains(messageListNewMessage)) {
                    GeneratorCode oldIdCodeOfMessageListNewMessage = messageListNewMessage.getIdCode();
                    messageListNewMessage.setIdCode(generatorCode);
                    messageListNewMessage = em.merge(messageListNewMessage);
                    if (oldIdCodeOfMessageListNewMessage != null && !oldIdCodeOfMessageListNewMessage.equals(generatorCode)) {
                        oldIdCodeOfMessageListNewMessage.getMessageList().remove(messageListNewMessage);
                        oldIdCodeOfMessageListNewMessage = em.merge(oldIdCodeOfMessageListNewMessage);
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
                Integer id = generatorCode.getIdCode();
                if (findGeneratorCode(id) == null) {
                    throw new NonexistentEntityException("The generatorCode with id " + id + " no longer exists.");
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
            GeneratorCode generatorCode;
            try {
                generatorCode = em.getReference(GeneratorCode.class, id);
                generatorCode.getIdCode();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The generatorCode with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Message> messageListOrphanCheck = generatorCode.getMessageList();
            for (Message messageListOrphanCheckMessage : messageListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This GeneratorCode (" + generatorCode + ") cannot be destroyed since the Message " + messageListOrphanCheckMessage + " in its messageList field has a non-nullable idCode field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario idUser = generatorCode.getIdUser();
            if (idUser != null) {
                idUser.getGeneratorCodeList().remove(generatorCode);
                idUser = em.merge(idUser);
            }
            em.remove(generatorCode);
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

    public List<GeneratorCode> findGeneratorCodeEntities() {
        return findGeneratorCodeEntities(true, -1, -1);
    }

    public List<GeneratorCode> findGeneratorCodeEntities(int maxResults, int firstResult) {
        return findGeneratorCodeEntities(false, maxResults, firstResult);
    }

    private List<GeneratorCode> findGeneratorCodeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GeneratorCode.class));
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

    public GeneratorCode findGeneratorCode(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GeneratorCode.class, id);
        } finally {
            em.close();
        }
    }

    public int getGeneratorCodeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GeneratorCode> rt = cq.from(GeneratorCode.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
