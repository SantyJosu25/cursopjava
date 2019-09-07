package ec.tda.curso.dobleautenticacion_ejb.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "message")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Message.findAll", query = "SELECT m FROM Message m")
    , @NamedQuery(name = "Message.findByIdeMessage", query = "SELECT m FROM Message m WHERE m.ideMessage = :ideMessage")
    , @NamedQuery(name = "Message.findByValueMessage", query = "SELECT m FROM Message m WHERE m.valueMessage = :valueMessage")})
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ide_message")
    private Integer ideMessage;
    @Size(max = 100)
    @Column(name = "value_message")
    private String valueMessage;
    @JoinColumn(name = "id_code", referencedColumnName = "id_code")
    @ManyToOne(optional = false)
    private GeneratorCode idCode;

    public Message() {
    }

    public Message(Integer ideMessage) {
        this.ideMessage = ideMessage;
    }

    public Integer getIdeMessage() {
        return ideMessage;
    }

    public void setIdeMessage(Integer ideMessage) {
        this.ideMessage = ideMessage;
    }

    public String getValueMessage() {
        return valueMessage;
    }

    public void setValueMessage(String valueMessage) {
        this.valueMessage = valueMessage;
    }

    public GeneratorCode getIdCode() {
        return idCode;
    }

    public void setIdCode(GeneratorCode idCode) {
        this.idCode = idCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ideMessage != null ? ideMessage.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Message)) {
            return false;
        }
        Message other = (Message) object;
        if ((this.ideMessage == null && other.ideMessage != null) || (this.ideMessage != null && !this.ideMessage.equals(other.ideMessage))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.tda.curso.dobleautenticacion_ejb.entidades.Message[ ideMessage=" + ideMessage + " ]";
    }

}
