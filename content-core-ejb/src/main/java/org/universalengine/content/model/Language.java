package org.universalengine.content.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.Locale;

@Entity
@Table(name = "Language")
public class Language implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator_language")
    @SequenceGenerator(name = "generator_language", sequenceName = "language_seq", allocationSize = 1)
    @XmlElement
    private Long id;

    @Basic
    @NotNull
    @NotEmpty
    @XmlElement
    private String name;

    @Basic
    @NotNull
    @NotEmpty
    @Column(unique = true)
    private String languageTag;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguageTag() {
        return languageTag;
    }

    public Locale getLocaleFromLanguageTag(){
        return Locale.forLanguageTag(languageTag);
    }

    public void setLanguageTag(String languageTag) {
        this.languageTag = languageTag;
    }

    public void setLanguageTagFromLocale(Locale locale) {
        this.languageTag = locale.toLanguageTag();
    }

    public void copyValues(Language language){
        this.id = language.getId();
        this.name = language.getName();
        this.languageTag = language.getLanguageTag();
    }

}
