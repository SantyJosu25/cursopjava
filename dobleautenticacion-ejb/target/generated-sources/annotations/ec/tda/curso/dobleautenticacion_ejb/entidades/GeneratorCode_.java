package ec.tda.curso.dobleautenticacion_ejb.entidades;

import ec.tda.curso.dobleautenticacion_ejb.entidades.Message;
import ec.tda.curso.dobleautenticacion_ejb.entidades.Usuario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-09-14T11:52:45")
@StaticMetamodel(GeneratorCode.class)
public class GeneratorCode_ { 

    public static volatile SingularAttribute<GeneratorCode, Usuario> idUser;
    public static volatile SingularAttribute<GeneratorCode, String> codCode;
    public static volatile ListAttribute<GeneratorCode, Message> messageList;
    public static volatile SingularAttribute<GeneratorCode, Integer> idCode;
    public static volatile SingularAttribute<GeneratorCode, Date> codDataStart;
    public static volatile SingularAttribute<GeneratorCode, Date> codDataEnd;

}