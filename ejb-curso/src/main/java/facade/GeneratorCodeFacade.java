/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entidades.GeneratorCode;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author santi
 */
@Stateless
public class GeneratorCodeFacade extends AbstractFacade<GeneratorCode> implements GeneratorCodeFacadeLocal {

    @PersistenceContext(unitName = "ec.tda.curso_ejb-curso_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GeneratorCodeFacade() {
        super(GeneratorCode.class);
    }
    
}
