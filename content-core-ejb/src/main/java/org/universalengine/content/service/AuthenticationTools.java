package org.universalengine.content.service;

import org.universalengine.content.service.exceptions.AuthenticationException;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.logging.Logger;

@Stateless
public class AuthenticationTools {

    @Inject
    private Logger log;

    private boolean initOK;

    public AuthenticationTools(){
        initOK = false;
    }

    @PostConstruct
    private void init(){

        initOK = true;
        log.info(this.getClass().getName()+" correctly initialize");
    }

    private void isInitOk() throws AuthenticationException {
        if( !initOK ){
            log.severe(this.getClass().getName()+" failed to initialize : it can't perform the request");
            throw new AuthenticationException();
        }
    }

    public void authenticate(final String senderAPIcode, final String apiKey, final String passwd) throws AuthenticationException {
        isInitOk();
        if(!senderAPIcode.equals("test") || !apiKey.equals("test") || !passwd.equals("test")){
            log.severe("Authentication failed");
            throw new AuthenticationException();
        }
    }

    public void authenticate(final String keyChainSerialized) throws AuthenticationException {
        KeyChain keyChain;
        keyChain = deserializeKeyChain(keyChainSerialized);
        authenticate(keyChain.getSenderApiCode(),keyChain.getApiKey(),keyChain.getPasswd());
    }


    private KeyChain deserializeKeyChain(final String keyChain){
        KeyChain returnValue = null;
        int type;
        String[] parts = keyChain.split("-param-");
        if(parts.length > 0){
            try{
                type = Integer.parseInt(parts[0]);
            }catch (NumberFormatException e){
                log.severe("Key chain serialization type is not an interger !");
                type = 0;
            }

            switch (type){
                case 1 :
                    if(parts.length == 4){
                        returnValue = new KeyChain(parts[1],parts[2],parts[3]);
                    }else{
                        log.severe("Wrong keyChain formatting for type 1");
                    }
                    break;
                default:
                    log.severe("Wrong keyChain serialize type");

            }

        }else{
            log.severe("Wrong keyChain formatting");
        }

        if(returnValue==null){
            log.info("Unable to deserialize kainchain, giving a empty result...");
            returnValue = new KeyChain("","","");
        }
        return returnValue;
    }

    public class KeyChain {
        private final String senderApiCode;
        private final String apiKey;
        private final String passwd;

        public KeyChain(String senderApiCode, String apiKey, String passwd){
            this.senderApiCode = senderApiCode;
            this.apiKey = apiKey;
            this.passwd = passwd;
        }

        public String getSenderApiCode() {
            return senderApiCode;
        }

        public String getApiKey() {
            return apiKey;
        }

        public String getPasswd() {
            return passwd;
        }
    }

}
