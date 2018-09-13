package org.universalengine.content.dto;

import org.universalengine.content.model.LocalizedString;

public class LocalizedStringDTO {

    private  Long id;
    private String value;
    private String languageTag;


    public LocalizedStringDTO(Long id, String value, String languageTag) {
        this.id = id;
        this.value = value;
        this.languageTag = languageTag;
    }

    public LocalizedStringDTO (LocalizedString localizedString){
        this.id = localizedString.getId();
        this.value = localizedString.getValue();
        this.languageTag = localizedString.getLanguage().getLanguageTag();
    }


    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLanguageTag() {
        return languageTag;
    }

    public void setLanguageTag(String languageTag) {
        this.languageTag = languageTag;
    }
}
