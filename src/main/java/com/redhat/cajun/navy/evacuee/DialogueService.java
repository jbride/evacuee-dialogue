package com.redhat.cajun.navy.evacuee;

import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import io.smallrye.mutiny.Uni;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class DialogueService {

    private static final String TWILIO_TEST_TO_PHONE_NUMBER = "er.demo.TWILIO_TEST_TO_PHONE_NUMBER";
    private static final Logger logger = LoggerFactory.getLogger(DialogueService.class);

    @Inject
    SMSProducer smsProducer;

    @Inject
    @ConfigProperty(name = TWILIO_TEST_TO_PHONE_NUMBER)
    String testToPhoneNumber;

    @Inject
    @RestClient
    IncidentClient incidentClient;

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

    public String sanityCheck() {
        return smsProducer.sendMessage(testToPhoneNumber, "sanity check");
    }

    public void createIncident(Incident iObj) {
        String response = incidentClient.createIncident(iObj);
        logger.info("createIncident() response = "+response);
    }

}
