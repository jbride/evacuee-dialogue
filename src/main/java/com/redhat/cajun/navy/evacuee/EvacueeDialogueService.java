package com.redhat.cajun.navy.evacuee;

import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import io.quarkus.runtime.StartupEvent;
import io.vertx.core.shareddata.LocalMap;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class EvacueeDialogueService {

    private static final Logger logger = LoggerFactory.getLogger(EvacueeDialogueService.class);

    @Inject
    @ConfigProperty(name = Constants.TWILIO_TEST_TO_PHONE_NUMBER)
    String testToPhoneNumber;
    
    @Inject
    @ConfigProperty(name = Constants.DIALOGUE_TYPE)
    String dialogueType;
    
    @Inject
    @RestClient
    IncidentClient incidentClient;

    @Inject
    @ChosenDialogue
    @ApplicationScoped
    IDialogue dialogueImpl;
    
    @Produces
    @ChosenDialogue
    @ApplicationScoped
    public IDialogue getDialogue() {
        
        logger.info("getDialogue() using the following implementation type: "+dialogueType);
        switch(dialogueType){
            case Constants.SIMPLE_DIALOGUE:
                return new SimpleDialogue();
            case Constants.NLP_DIALOGUE:
                return new NLPDialogue();
            default:
                return null;
        }
    }

    @Inject
    io.vertx.mutiny.core.Vertx vertx;


    void onStart(@Observes StartupEvent ev) {

        System.out.println("              _   _            _____    _____           ");
        System.out.println("             | \\ | |    /\\    |  __ \\  / ____|          ");
        System.out.println("             |  \\| |   /  \\   | |__) || (___            ");
        System.out.println("             | . ` |  / /\\ \\  |  ___/  \\___ \\           ");
        System.out.println("             | |\\  | / ____ \\ | |      ____) |          ");
        System.out.println("             |_| \\_|/_/    \\_\\|_|     |_____/           ");
        System.out.println("  ______  _____          _____                          ");
        System.out.println(" |  ____||  __ \\        |  __ \\                         ");
        System.out.println(" | |__   | |__) |______ | |  | |  ___  _ __ ___    ___  ");
        System.out.println(" |  __|  |  _  /|______|| |  | | / _ \\| '_ ` _ \\  / _ \\ ");
        System.out.println(" | |____ | | \\ \\        | |__| ||  __/| | | | | || (_) |");
        System.out.println(" |______||_|  \\_\\       |_____/  \\___||_| |_| |_| \\___/ ");
        System.out.println("                                                        ");
        System.out.println("                                    Powered by Red Hat  ");
    }

    public void createIncident(Incident iObj) {
        String response = incidentClient.createIncident(iObj);
        logger.info("createIncident() response = "+response);
    }

    public String evacueeDialogeRequestResponse(Map<String, String> requestMap) {
        LocalMap<String, Incident> incidentMap = vertx.getDelegate().sharedData().getLocalMap(Constants.INCIDENT_MAP);
        String response =  dialogueImpl.nextMessage(requestMap);
        String fromNum = requestMap.get(Constants.FROM_NUMBER);
        Incident iObj = incidentMap.get(fromNum);
        if(iObj != null && iObj.getStatus().equals(Constants.REQUEST_INFO_COMPLETE)){
            createIncident(iObj);
        }
        return response;
    }

}
