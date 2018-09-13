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
package org.universalengine.content.test;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.universalengine.content.model.InternationalizedString;
import org.universalengine.content.service.InternationalizedStringRegistration;
import org.universalengine.content.util.Resources;

import javax.inject.Inject;
import java.util.logging.Logger;

import static org.junit.Assert.assertNotNull;

@RunWith(Arquillian.class)
public class InternationalizedStringRegistrationTest {
    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(InternationalizedString.class, InternationalizedStringRegistration.class, Resources.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                // Deploy our test datasource
                .addAsWebInfResource("test-ds.xml", "test-ds.xml");
    }

    @Inject
    InternationalizedStringRegistration internationalizedStringRegistration;

    @Inject
    Logger log;

    @Test
    public void testRegister() throws Exception {
        InternationalizedString internationalizedString = new InternationalizedString();
        internationalizedStringRegistration.register(internationalizedString);
        assertNotNull(internationalizedString.getId());
        log.info("InternationalizedString was persisted with id " + internationalizedString.getId());
    }

}
