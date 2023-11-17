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

    private static final String SNS_SUBJECT_UPLOAD_CONFIRMATION = "SNS from image-service. File Uploaded" ;
    private static final String SNS_SUBJECT_DELETE_CONFIRMATION = "SNS from image-service. File Deleted" ;

    private static final Logger logger = Logger.getLogger(SNSHandler.class.getName());

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


        String subject = request.getRecords().get(0).getSNS().getSubject();

//        switch (subject) {
//            case SNS_SUBJECT_UPLOAD_CONFIRMATION: {
//                text = text + "\n" + " FILE UPLOADED !!!" ;
//            }
//            case SNS_SUBJECT_DELETE_CONFIRMATION: {
//                text = text + "\n" + " FILE DELETED !!!" ;
//            }
//            default: {
//                throw new IllegalArgumentException("Unsupported subject of SNS notification!");
//            }
//        }

        if (SNS_SUBJECT_UPLOAD_CONFIRMATION.equals(subject)) {
            text = text + "\n" + " FILE UPLOADED !!!" ;
        } else if (SNS_SUBJECT_DELETE_CONFIRMATION.equals(subject)) {
            text = text + "\n" + " FILE DELETED !!!" ;
        } else {
            throw new IllegalArgumentException("Unsupported subject of SNS notification!");
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