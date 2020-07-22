package com.redhat.cajun.navy.evacuee;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@ApplicationScoped
public class SMSProducer {

    private static final Logger logger = LoggerFactory.getLogger("SMSProducer");
    private static final String TWILIO_ACCOUNT_ID = "er.demo.TWILIO_ACCOUNT_SID";
    private static final String TWILIO_AUTH_TOKEN = "er.demo.TWILIO_AUTH_TOKEN";
    private static final String TWILIO_FROM_PHONE_NUMBER = "er.demo.TWILIO_FROM_PHONE_NUMBER";

    @Inject
    @ConfigProperty(name = TWILIO_ACCOUNT_ID )
    public String accountSID = null;

    @Inject
    @ConfigProperty(name = TWILIO_AUTH_TOKEN )
    public String authToken = null;

    @Inject
    @ConfigProperty(name = TWILIO_FROM_PHONE_NUMBER )
    public String fromPhoneNumber = null;

    @PostConstruct
    public void start() {
    }


    public String sendMessage(String toPhoneNumber, String body) {

        try {
            Twilio.init(accountSID, authToken);
            Message message = Message.creator(
                new PhoneNumber(toPhoneNumber),
                new PhoneNumber(fromPhoneNumber),
                body
            ).create();
            logger.info("sendMessage() toPhoneNumber = "+toPhoneNumber+" : messageSid = "+message.getSid());
            return message.getSid();
        }catch(com.twilio.exception.ApiException x) {
            x.printStackTrace();
            return x.getLocalizedMessage();
        }
    }

    @PreDestroy
    public void end() {
    }
}
