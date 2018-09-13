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
package org.universalengine.content.data;

import org.universalengine.content.model.Language;
import org.universalengine.content.model.Language_;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@ApplicationScoped
public class LanguageRepository {

    @Inject
    private EntityManager em;

    public List<Language> findAllOrderedByName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Language> criteria = cb.createQuery(Language.class);
        Root<Language> languageRoot = criteria.from(Language.class);
        criteria.select(languageRoot).orderBy(cb.asc(languageRoot.get(Language_.name)));
        return em.createQuery(criteria).getResultList();
    }

    public Language findById(Long id) throws NoResultException {
        Language languageAvailable = em.find(Language.class, id);
        if( languageAvailable==null ){
            throw new NoResultException();
        }
        return languageAvailable;
    }

    public Language findByLanguageTag(String languageTag) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Language> criteria = cb.createQuery(Language.class);
        Root<Language> languageRoot = criteria.from(Language.class);
        criteria.select(languageRoot).where(cb.equal(languageRoot.get(Language_.languageTag),languageTag));
        return em.createQuery(criteria).getSingleResult();
    }
}
