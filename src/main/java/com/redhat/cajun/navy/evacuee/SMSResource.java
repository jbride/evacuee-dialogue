package com.redhat.cajun.navy.evacuee;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.inject.Inject;

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
        String sid = dialogueService.sanityCheck();
        return Response.ok(sid).build();
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/createIncident")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createIncident() {
        dialogueService.createIncident();
        return Response.ok().build();
    }
}
