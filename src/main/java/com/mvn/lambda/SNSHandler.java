package com.mvn.lambda;


import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


//https://docs.aws.amazon.com/ses/latest/dg/send-an-email-using-sdk-programmatically.html
public class SNSHandler implements RequestHandler<SNSEvent, Object> {


    private static final String EMAIL_SUBJECT = "Message From AWS Lambda" ;
    private static final String SUBJECT_SNS_FROM_IMAGE_SERVICE = "SNS from image-service" ;

    private static Logger logger = Logger.getLogger(SNSHandler.class.getName());

    public Object handleRequest(SNSEvent request, Context context) {
        //String email = request.getRecords().get(0).getSNS().getMessage();
        String email = "laptiev.denys.18@gmail.com" ;

        logger.info("--> request=" + request);
        logger.info("--> request.getRecords()=" + request.getRecords());
        logger.info("--> request.getRecords().get(0)=" + request.getRecords().get(0));
        logger.info("--> request.getRecords().get(0).getSNS()=" + request.getRecords().get(0).getSNS());
        logger.info("--> request.getRecords().get(0).getSNS().getSubject()=" + request.getRecords().get(0).getSNS().getSubject());
        logger.info("--> request.getRecords().get(0).getSNS().getMessage()=" + request.getRecords().get(0).getSNS().getMessage());

        String text = "--> request=" + request + "\n"
                + "--> request.getRecords()=" + request.getRecords() + "\n"
                + "--> request.getRecords().get(0)=" + request.getRecords().get(0) + "\n"
                + "--> request.getRecords().get(0).getSNS()=" + request.getRecords().get(0).getSNS() + "\n"
                + "--> request.getRecords().get(0).getSNS().getSubject()=" + request.getRecords().get(0).getSNS().getSubject()
                + "--> request.getRecords().get(0).getSNS().getMessage()=" + request.getRecords().get(0).getSNS().getMessage();

        if(SUBJECT_SNS_FROM_IMAGE_SERVICE.equals(request.getRecords().get(0).getSNS().getSubject())){
            text=text+"\n"+" SNS WAS RECEIVED FROM IMAGE-SERVICE!!!";
        }

        sendEmail(email, EMAIL_SUBJECT, text);
        return null;
    }


    public static void sendEmail(String emailTo, String subject, String text) {

        try {
            AmazonSimpleEmailService client =
                    AmazonSimpleEmailServiceClientBuilder.standard()
                            // Replace US_WEST_2 with the AWS Region you're using for
                            // Amazon SES.
                            .withRegion(Regions.EU_WEST_3).build();

            SendEmailRequest request = new SendEmailRequest()
                    .withDestination(
                            new Destination().withToAddresses(emailTo))
                    .withMessage(new Message()
                            .withBody(new Body()
                                    .withText(new Content()
                                            .withCharset("UTF-8").withData(text)))
                            .withSubject(new Content()
                                    .withCharset("UTF-8").withData(subject)))
                    .withSource("laptiev.denys.18@gmail.com");

            // Comment or remove the next line if you are not using a
            // configuration set
            // .withConfigurationSetName(CONFIGSET);
            client.sendEmail(request);
            System.out.println("Email sent!");
        } catch (Exception ex) {
            System.out.println("The email was not sent. Error message: "
                    + ex.getMessage());
        }
    }

    //TODO: remove this after testing
    public static void main(String[] args) {
        SNSEvent.SNS sns = new SNSEvent.SNS();
        sns.setMessage("this is a message");

        SNSEvent.SNSRecord snsRecord = new SNSEvent.SNSRecord();
        snsRecord.setSns(sns);

        List<SNSEvent.SNSRecord> snsRecordList = new ArrayList<>();
        snsRecordList.add(snsRecord);

        SNSEvent request = new SNSEvent();
        request.setRecords(snsRecordList);

        String text = "--> request=" + request + "\n"
                + "--> request.getRecords()=" + request.getRecords() + "\n"
                + "--> request.getRecords().get(0)=" + request.getRecords().get(0) + "\n"
                + "--> request.getRecords().get(0).getSNS()=" + request.getRecords().get(0).getSNS() + "\n"
                + "--> request.getRecords().get(0).getSNS().getMessage()=" + request.getRecords().get(0).getSNS().getMessage();

        System.out.println(text);
    }
}