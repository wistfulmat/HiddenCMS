package org.universalengine.content.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

@Entity
@Table(name = "LocalizedString")
public class LocalizedString implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_localized_string")
    @SequenceGenerator(name = "generator_localized_string", sequenceName = "localized_string_seq", allocationSize = 1)
    @XmlElement
    private Long id;

    @Basic
    @NotNull
    @NotEmpty
    @Column(columnDefinition = "text")
    @XmlElement
    private String value;

    @ManyToOne(fetch = FetchType.EAGER)
    @XmlElement
    private Language language;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
