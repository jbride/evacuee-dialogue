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

@Path("/sms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SMSResource {

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
    @Path("/createIncident")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createIncident(String incidentS) {
        Incident iObj = Json.decodeValue(incidentS, Incident.class);
        dialogueService.createIncident(iObj);
        return Response.ok().build();
    }
}
