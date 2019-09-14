package ec.tda.curso.dobleautenticacion_ejb.entidades;

import ec.tda.curso.dobleautenticacion_ejb.entidades.GeneratorCode;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-09-14T11:52:45")
@StaticMetamodel(Usuario.class)
public class Usuario_ { 

    public static volatile SingularAttribute<Usuario, Integer> idUser;
    public static volatile SingularAttribute<Usuario, String> usUsername;
    public static volatile SingularAttribute<Usuario, String> usUser;
    public static volatile SingularAttribute<Usuario, String> usEmail;
    public static volatile ListAttribute<Usuario, GeneratorCode> generatorCodeList;
    public static volatile SingularAttribute<Usuario, String> usPass;

}