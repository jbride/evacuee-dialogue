package com.redhat.cajun.navy.evacuee;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("")
@RegisterRestClient
public interface IncidentClient {

    @POST
    @Path("/incidents")
    @Consumes(MediaType.APPLICATION_JSON)
    String createIncident(Incident incident);

}
