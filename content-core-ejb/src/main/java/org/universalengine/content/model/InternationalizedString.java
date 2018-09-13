package org.universalengine.content.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "InternationalizedString")
public class InternationalizedString implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_internationalized_string")
    @SequenceGenerator(name = "generator_internationalized_string", sequenceName = "internationalized_string_seq", allocationSize = 1)
    @XmlElement
    private Long id;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @XmlElement
    private List<LocalizedString> values;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<LocalizedString> getValues() {
        return values;
    }

    public void setValues(List<LocalizedString> values) {
        this.values = values;
    }
}
