package org.universalengine.content.rest;

import org.universalengine.content.dto.LocalizedStringDTO;
import org.universalengine.content.model.InternationalizedString;
import org.universalengine.content.service.AuthenticationTools;
import org.universalengine.content.service.InternationalizedStringRegistration;
import org.universalengine.content.service.LocalizedStringRegistration;
import org.universalengine.content.service.exceptions.AuthenticationException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

@Path("/content-contributor")
@RequestScoped
public class ContentContributorRESTServices {
    @Inject
    Logger logger;

    @Inject
    AuthenticationTools authenticationTools;

    @Inject
    InternationalizedStringRegistration internationalizedStringRegistration;

    @Inject
    LocalizedStringRegistration localizedStringRegistration;

    // **************************************************************
    // ******************** createInternationalizedString ***********
    // **************************************************************
    @PUT
    @Path("/create/{keyChain}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createInternationalizedString(@PathParam("keyChain") String keyChain){
        Response.ResponseBuilder builder;
        InternationalizedString internationalizedString;
        try{
            authenticationTools.authenticate(keyChain);
            internationalizedString = new InternationalizedString();
            internationalizedStringRegistration.register(internationalizedString);
            builder = Response.ok(internationalizedString.getId());
        }catch (AuthenticationException ae){
            logger.severe("Access denied");
            builder = Response.status(Response.Status.FORBIDDEN);
        }catch (Exception e){
            logger.severe("Error during InternationalizedString registration");
            builder = Response.serverError();
        }

        return builder.build();
    }


    // **************************************************************
    // ******************** saveLocalizedString *********************
    // **************************************************************
    @POST
    @Path("/{internationalizedStringId}/{localizedStringId}/{localizedStringValue}/{localizedStringLanguageTag}/{keyChain}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveLocalizedString(
            @PathParam("internationalizedStringId") Long internationalizedStringId,
            @PathParam("localizedStringId") Long localizedStringId,
            @PathParam("localizedStringValue") String localizedStringValue,
            @PathParam("localizedStringLanguageTag") String localizedStringLanguageTag,
            @PathParam("keyChain") String keyChain){
        Response.ResponseBuilder builder;
        LocalizedStringDTO localizedStringDTO;
        try {
            authenticationTools.authenticate(keyChain);
            localizedStringDTO = new LocalizedStringDTO(
                    localizedStringId,
                    localizedStringValue,
                    localizedStringLanguageTag
            );
            localizedStringRegistration.register(localizedStringDTO, internationalizedStringId);
            builder = Response.ok();
        }catch (AuthenticationException ae){
            logger.severe("Access denied");
            builder = Response.status(Response.Status.FORBIDDEN);
        }
        return builder.build();
    }
}
