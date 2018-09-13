package org.universalengine.content.dto;

import org.universalengine.content.model.InternationalizedString;
import org.universalengine.content.model.LocalizedString;

import java.util.ArrayList;
import java.util.List;

public class InternationalizedStringDTO {

    private Long id;
    private List<LocalizedStringDTO> values;

    public InternationalizedStringDTO(InternationalizedString internationalizedString) {
        this.id = internationalizedString.getId();
        this.values = new ArrayList<>();
        for(LocalizedString localizedString : internationalizedString.getValues()){
            this.values.add(new LocalizedStringDTO(localizedString));
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<LocalizedStringDTO> getValues() {
        return values;
    }

    public void setValues(List<LocalizedStringDTO> values) {
        this.values = values;
    }
}
