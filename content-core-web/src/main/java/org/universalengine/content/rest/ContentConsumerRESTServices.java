package org.universalengine.content.rest;

import org.universalengine.content.data.InternationalizedStringRepository;
import org.universalengine.content.dto.InternationalizedStringDTO;
import org.universalengine.content.model.InternationalizedString;
import org.universalengine.content.service.AuthenticationTools;
import org.universalengine.content.service.DatabaseTools;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

@Path("/content-consumer")
@RequestScoped
public class ContentConsumerRESTServices {
    @Inject
    Logger logger;

    @Inject
    AuthenticationTools authenticationTools;

    @Inject
    InternationalizedStringRepository internationalizedStringRepository;

    // **************************************************************
    // ******************** readInternationalizedString *************
    // **************************************************************
    @GET
    @Path("/{id}/{keyChain}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readInternationalizedString(
            @PathParam("id") Long id,
            @PathParam("keyChain") String keyChain){
        Response.ResponseBuilder builder;
        InternationalizedString internationalizedString;
        InternationalizedStringDTO internationalizedStringDTO;
        try {
            authenticationTools.authenticate(keyChain);
            internationalizedString = internationalizedStringRepository.findById(id);
            internationalizedStringDTO = new InternationalizedStringDTO(internationalizedString);
            builder = Response.ok(internationalizedStringDTO);
        }catch (Exception e){
            builder = Response.noContent();
        }
        return builder.build();
    }
}
