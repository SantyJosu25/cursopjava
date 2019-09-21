/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entidades.GeneratorCode;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author santi
 */
@Local
public interface GeneratorCodeFacadeLocal {

    void create(GeneratorCode generatorCode);

    void edit(GeneratorCode generatorCode);

    void remove(GeneratorCode generatorCode);

    GeneratorCode find(Object id);

    List<GeneratorCode> findAll();

    List<GeneratorCode> findRange(int[] range);

    int count();
    
}
