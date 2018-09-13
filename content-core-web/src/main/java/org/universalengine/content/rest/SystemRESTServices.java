package org.universalengine.content.rest;

import org.universalengine.content.service.DatabaseTools;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.logging.Logger;

@Path("/system")
@RequestScoped
public class SystemRESTServices {
    @Inject
    Logger logger;

    @Inject
    DatabaseTools databaseTools;

    // ******************************************************
    // ******************** IS ALIVE ************************
    // ******************************************************
    @GET
    @Path("/isalive")
    public String isAlive(){ return "ok"; }

    // ******************************************************
    // ******************** ECHO ****************************
    // ******************************************************
    @GET
    @Path("/echo/{echo}")
    public String echo(@PathParam("echo") String echo){
        return databaseTools.echoFromDatabase(echo);
    }

}
