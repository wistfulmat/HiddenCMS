/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.universalengine.content.service;

import org.universalengine.content.data.InternationalizedStringRepository;
import org.universalengine.content.data.LanguageRepository;
import org.universalengine.content.data.LocalizedStringRepository;
import org.universalengine.content.dto.LocalizedStringDTO;
import org.universalengine.content.model.InternationalizedString;
import org.universalengine.content.model.Language;
import org.universalengine.content.model.LocalizedString;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.logging.Logger;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class LocalizedStringRegistration {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Inject
    private LocalizedStringRepository localizedStringRepository;

    @Inject
    private InternationalizedStringRepository internationalizedStringRepository;

    @Inject
    private LanguageRepository languageRepository;

    public void register(LocalizedStringDTO localizedStringDTO, Long internationalizedStringId) throws NoResultException {
        LocalizedString localizedStringToRegister;
        Language languageAffected = languageRepository.findByLanguageTag(localizedStringDTO.getLanguageTag());

        if(localizedStringDTO.getId().compareTo(0L)>0){
            localizedStringToRegister = localizedStringRepository.findById(localizedStringDTO.getId());
            localizedStringToRegister.setValue(localizedStringDTO.getValue());
            localizedStringToRegister.setLanguage(languageAffected);
            em.merge(localizedStringToRegister);
        }else{
            if( internationalizedStringId.compareTo(0L)>0){
                InternationalizedString internationalizedString = internationalizedStringRepository.findById(internationalizedStringId);
                localizedStringToRegister = new LocalizedString();
                localizedStringToRegister.setId(null);
                localizedStringToRegister.setValue(localizedStringDTO.getValue());
                localizedStringToRegister.setLanguage(languageAffected);
                internationalizedString.getValues().add(localizedStringToRegister);
                em.merge(internationalizedString);
            }else{
                log.info("Unable to store localizedString without internationalizedString container");
                throw new NoResultException();
            }
        }
        log.info("Registering LocalizedString with id : " + localizedStringToRegister.getId());
    }
}
