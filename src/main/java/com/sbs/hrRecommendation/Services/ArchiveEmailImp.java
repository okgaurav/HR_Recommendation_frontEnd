package com.sbs.hrRecommendation.Services;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class ArchiveEmailImp {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Configuration configuration;

    @Value("${spring.mail.username}") private String sender;

    public String sendArchiveMail(String rec, String sub, Map<String , Object> model, boolean isArchived, String userName, String rec_subject, String hr_name, String feedback)
    {
        // Creating a mime message
        MimeMessage mimeMessage
                = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper;

        try {
            // Setting multipart as true for attachments to
            // be send
            mimeMessageHelper
                    = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED,
                    StandardCharsets.UTF_8.name());

            if(isArchived){
                String message = "Dear " + userName + ",\r\n Your Recommendation entitiled " + rec_subject + " has been ARCHIVED by the HR " + hr_name + " with following reasons : ";
                model.put("data",message);
            }  else{
                String message = "Dear " + userName + ",\r\n Your Recommendation entitiled " + rec_subject + " has been UNARCHIVED with following reasons : ";
                model.put("data",message);
            }

            model.put("feedback", feedback);
            Template t = configuration.getTemplate("final.ftlh");
            FileSystemResource file
                    = new FileSystemResource(
                    new File("src/main/resources/templates/MicrosoftTeams-image_2.png"));
            FileSystemResource file1
                    = new FileSystemResource(
                    new File("src/main/resources/templates/SBSI.png"));

//
            mimeMessageHelper.addInline("image001", file);
            mimeMessageHelper.addInline("image002", file1);
            // FreeMarkerTemplateUtils is a utility class for working with FreeMarker template to send the email.
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(rec);
            mimeMessageHelper.setText(html, true);
            mimeMessageHelper.setSubject(sub);

            // Adding the attachment
//            String content = "<b>Dear guru</b>,<br><i>Please look at this nice picture:.</i>"
//                    + "<br><img src='cid:image001'/><br><b>Best Regards</b>";
//            mimeMessageHelper.setText(content, true);
//            FileSystemResource file
//                    = new FileSystemResource(
//                    new File("src/main/resources/templates/SBSS.png"));
//
//            mimeMessageHelper.addInline("image001", file);

            // Sending the mail
            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        }

        // Catch block to handle MessagingException
        catch (MessagingException | IOException | TemplateException e) {

            // Display message when exception occurred
            return "Error while sending mail!!!";
        }
    }

}
