package com.redhat.cajun.navy.evacuee;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import io.smallrye.mutiny.Uni;

@Path("")
@RegisterRestClient
public interface IncidentClient {

    @POST
    @Path("/incident")
    @Consumes(MediaType.APPLICATION_JSON)
    Uni<Response> createIncident(Incident incident);

}
