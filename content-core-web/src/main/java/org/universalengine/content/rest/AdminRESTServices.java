package org.universalengine.content.rest;

import org.universalengine.content.data.LanguageRepository;
import org.universalengine.content.dto.LocalizedStringDTO;
import org.universalengine.content.model.InternationalizedString;
import org.universalengine.content.model.Language;
import org.universalengine.content.service.AuthenticationTools;
import org.universalengine.content.service.InternationalizedStringRegistration;
import org.universalengine.content.service.LanguageRegistration;
import org.universalengine.content.service.LocalizedStringRegistration;
import org.universalengine.content.service.exceptions.AuthenticationException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

@Path("/content-admin")
@RequestScoped
public class AdminRESTServices {
    @Inject
    private Logger logger;

    @Inject
    private AuthenticationTools authenticationTools;

    @Inject
    private LanguageRepository languageRepository;

    @Inject
    private LanguageRegistration languageRegistration;



    // **************************************************************
    // ******************** listAllLanguages ************************
    // **************************************************************
    @GET
    @Path("/language/list/{keyChain}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllLanguages(@PathParam("keyChain") String keyChain){
        Response.ResponseBuilder builder;
        List<Language> languages;
        try{
            authenticationTools.authenticate(keyChain);
            languages = languageRepository.findAllOrderedByName();
            builder = Response.ok(languages);
        }catch (AuthenticationException ae){
            logger.severe("Access denied");
            builder = Response.status(Response.Status.FORBIDDEN);
        }catch (Exception e){
            logger.severe("Error during languages listing");
            builder = Response.serverError();
        }
        return builder.build();
    }

    // **************************************************************
    // ******************** listAllLanguageTags *********************
    // **************************************************************
    @GET
    @Path("/language-tag/list/{keyChain}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllLanguageTags(@PathParam("keyChain") String keyChain){
        Response.ResponseBuilder builder;
        Map<String,String> languageTags;
        try{
            authenticationTools.authenticate(keyChain);
            Locale[] locales = Locale.getAvailableLocales();
            languageTags = new HashMap<>();
            for(Locale locale : locales){
                languageTags.put(locale.toLanguageTag(),locale.getDisplayName());
            }
            // languages = languageRepository.findAllOrderedByName();
            builder = Response.ok(languageTags);
        }catch (AuthenticationException ae){
            logger.severe("Access denied");
            builder = Response.status(Response.Status.FORBIDDEN);
        }catch (Exception e){
            logger.severe("Error during language tags listing");
            builder = Response.serverError();
        }
        return builder.build();
    }


    // **************************************************************
    // ******************** registerNewAvailableLanguage ************
    // **************************************************************
    @PUT
    @Path("/language/register/{id}/{name}/{languageTag}/{keyChain}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerLanguage(
            @PathParam("id") Long id,
            @PathParam("name") String name,
            @PathParam("languageTag") String languageTag,
            @PathParam("keyChain") String keyChain){
        Response.ResponseBuilder builder;
        Language language;
        try{
            authenticationTools.authenticate(keyChain);
            language = new Language();
            language.setId(id);
            language.setName(name);
            language.setLanguageTag(languageTag);
            languageRegistration.register(language);
            builder = Response.ok();
        }catch (AuthenticationException ae){
            logger.severe("Access denied");
            builder = Response.status(Response.Status.FORBIDDEN);
        }catch (Exception e){
            logger.severe("Error during language registration");
            builder = Response.serverError();
        }

        return builder.build();
    }
}
