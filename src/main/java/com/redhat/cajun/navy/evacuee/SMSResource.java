package com.redhat.cajun.navy.evacuee;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.inject.Inject;

import io.vertx.core.json.Json;

import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/sms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SMSResource {

    private static final Logger logger = LoggerFactory.getLogger(SMSResource.class);

    @Inject
    DialogueService dialogueService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/sanity")
    public Response sanityCheck() {
        return Response.ok("good to go").build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/smsSanity")
    public Response smsSanityCheck() {
        String sid = dialogueService.sanityCheck();
        return Response.ok(sid).build();
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response consumeSMS(String sms) {
        logger.info("consumeSMS() sms = "+sms);
        
        Body body = new Body
            .Builder("The Robots are coming! Head for the hills!")
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
