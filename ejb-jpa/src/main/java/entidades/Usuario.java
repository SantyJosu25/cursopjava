/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author santi
 */
@Entity
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
    , @NamedQuery(name = "Usuario.findByIdUser", query = "SELECT u FROM Usuario u WHERE u.idUser = :idUser")
    , @NamedQuery(name = "Usuario.findByUsUser", query = "SELECT u FROM Usuario u WHERE u.usUser = :usUser")
    , @NamedQuery(name = "Usuario.findByUsUsername", query = "SELECT u FROM Usuario u WHERE u.usUsername = :usUsername")
    , @NamedQuery(name = "Usuario.findByUsPass", query = "SELECT u FROM Usuario u WHERE u.usPass = :usPass")
    , @NamedQuery(name = "Usuario.findByUsEmail", query = "SELECT u FROM Usuario u WHERE u.usEmail = :usEmail")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_user")
    private Integer idUser;
    @Size(max = 50)
    @Column(name = "us_user")
    private String usUser;
    @Size(max = 50)
    @Column(name = "us_username")
    private String usUsername;
    @Size(max = 50)
    @Column(name = "us_pass")
    private String usPass;
    @Size(max = 60)
    @Column(name = "us_email")
    private String usEmail;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUser")
    private List<GeneratorCode> generatorCodeList;

    public Usuario() {
    }

    public Usuario(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getUsUser() {
        return usUser;
    }

    public void setUsUser(String usUser) {
        this.usUser = usUser;
    }

    public String getUsUsername() {
        return usUsername;
    }

    public void setUsUsername(String usUsername) {
        this.usUsername = usUsername;
    }

    public String getUsPass() {
        return usPass;
    }

    public void setUsPass(String usPass) {
        this.usPass = usPass;
    }

    public String getUsEmail() {
        return usEmail;
    }

    public void setUsEmail(String usEmail) {
        this.usEmail = usEmail;
    }

    @XmlTransient
    public List<GeneratorCode> getGeneratorCodeList() {
        return generatorCodeList;
    }

    public void setGeneratorCodeList(List<GeneratorCode> generatorCodeList) {
        this.generatorCodeList = generatorCodeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUser != null ? idUser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.idUser == null && other.idUser != null) || (this.idUser != null && !this.idUser.equals(other.idUser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Usuario[ idUser=" + idUser + " ]";
    }
    
}
