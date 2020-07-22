package com.redhat.cajun.navy.evacuee;

import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.runtime.StartupEvent;
import io.vertx.core.shareddata.LocalMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class SimpleDialogue implements IDialogue {

    private static final Logger logger = LoggerFactory.getLogger(SimpleDialogue.class);

    @Inject
    io.vertx.mutiny.core.Vertx vertx;

    @Inject
    @ConfigProperty(name = Constants.VOLUNTEER_HOTLINE)
    String volunteerHotline;

    LocalMap<String, Incident> incidentMap = null;

    void onStart(@Observes StartupEvent ev){
        incidentMap = vertx.getDelegate().sharedData().getLocalMap(Constants.INCIDENT_MAP);
    }

	public String nextMessage(Map<String, String> requestParams) {
        String fromNum = requestParams.get(Constants.FROM_NUMBER);
        String fromBody = requestParams.get(Constants.FROM_BODY).toLowerCase();
        if(StringUtils.isEmpty(fromNum)){
            throw new RuntimeException("request does not include a value for: "+Constants.FROM_NUMBER);
        }

        // Set incidentObj
        Incident iObj = null;
        if(!incidentMap.containsKey(fromNum)){
            if(fromBody.contains(Constants.HELP)) {
                Incident incident = new Incident();
                incident.setVictimPhoneNumber(fromNum);
                incidentMap.put(fromNum, incident);
                iObj = incident;
            }else {
                return Constants.INITIATION_MESSAGE;
            }
        }else {
            iObj = incidentMap.get(fromNum);
        }

        // Process sms body 
        if(fromBody.contains(Constants.NAME)) {
            iObj.setVictimName(fromBody);
        } else if(fromBody.contains(Constants.NUM_PEOPLE)){
            try {
                int numPeople = Integer.parseInt(fromNum);
                if(1 >= numPeople && 50 >= numPeople) {
                    iObj.setNumberOfPeople(Integer.parseInt(fromNum));
                } else {
                    logger.error("nextMessage() will not set number of people to "+numPeople+ " for "+fromNum);
                }
            }catch(Exception x) {
                x.printStackTrace();
            }
        } else if (fromBody.contains(Constants.MEDICAL)) {
            iObj.setMedicalNeeded(true);
        } else if (fromBody.contains(Constants.NONE)) {
            iObj.setSentimentData(Constants.NONE);
        } else {
            iObj.setSentimentData(fromBody);
            iObj.setStatus(Constants.REQUEST_INFO_COMPLETE);
        }

        // Respond to SMS
        if(StringUtils.isEmpty(iObj.getVictimName())){
            return Constants.NAME_QUESTION;
        } else if(iObj.getNumberOfPeople() < 1) {
            return Constants.NUM_PEOPLE_QUESTION;
        } else if(StringUtils.isEmpty(iObj.getSentimentData())) {
            return Constants.ADDITIONAL_EMERGENCY_SERVICES;
        } else {
            return Constants.PLEASE_CALL+Constants.VOLUNTEER_HOTLINE;
        }
	}
    
}