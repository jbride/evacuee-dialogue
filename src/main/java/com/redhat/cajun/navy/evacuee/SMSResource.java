package com.redhat.cajun.navy.evacuee;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import io.vertx.core.json.Json;

import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Message;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/sms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SMSResource {

    private static final Logger logger = LoggerFactory.getLogger(SMSResource.class);

    @Inject
    EvacueeDialogueService dialogueService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/sanity")
    public Response sanityCheck() {
        return Response.ok("good to go").build();
    }


    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/createIncident")
    public Response createIncident(String incidentS) {
        Incident iObj = Json.decodeValue(incidentS, Incident.class);
        dialogueService.createIncident(iObj);
        return Response.ok().build();
    }

    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/")
    public Response smRequestResponse(@Context HttpServletRequest request) throws IOException {
    

        Map<String, String> requestMap = new HashMap<String, String>();
        String requestString = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        if(requestString != null){
            String requestDecoded = URLDecoder.decode(requestString, "UTF-8");
            logger.debug("consumeSMS() requestDecoded = "+requestDecoded);
    
            for(String param : requestDecoded.split("&")){
                String key = StringUtils.substringBefore(param, "=");
                String value = StringUtils.substringAfter(param, "=");
                logger.debug("consumeSMS body key = "+key+" : value = "+value);
                requestMap.put(key, value);
            }
        }

        Map<String, String[]> requestParamsMap = request.getParameterMap();
        for(String key : requestParamsMap.keySet()){
            logger.debug("consumeSMS() params key = "+ key+ " : value = "+Arrays.toString(requestParamsMap.get(key)));
            requestMap.put(key, Arrays.toString(requestParamsMap.get(key)));
        }

        String responseString = dialogueService.evacueeDialogeRequestResponse(requestMap);

        Body body = new Body
            .Builder(responseString)
            .build();
        Message responseBody = new Message
            .Builder()
            .body(body)
            .build();
        MessagingResponse twiml = new MessagingResponse
            .Builder()
            .message(responseBody)
        .build();
        return Response.ok(twiml.toXml()).build();
    }

}
