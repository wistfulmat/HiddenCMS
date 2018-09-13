package org.universalengine.content.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

@Stateless
public class DatabaseTools {

    @Inject
    private EntityManager em;


    public String echoFromDatabase(String msg){
        String returnValue;
        StoredProcedureQuery storedProcedureQuery = em.createStoredProcedureQuery("content.echo");
        storedProcedureQuery.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter(2, String.class, ParameterMode.OUT);
        storedProcedureQuery.setParameter(1,msg);
        storedProcedureQuery.execute();
        returnValue = (String)storedProcedureQuery.getOutputParameterValue(2);
        returnValue += " (from database)";
        return returnValue;
    }
}
