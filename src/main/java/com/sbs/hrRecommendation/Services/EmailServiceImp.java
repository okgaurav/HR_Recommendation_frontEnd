package com.sbs.hrRecommendation.Services;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import freemarker.template.Configuration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class EmailServiceImp {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Configuration configuration;

    @Value("${spring.mail.username}") private String sender;

                // CREATED FOR PLAIN/SIMPLE EMAIL......
//    public String sendMail(String rec, String msgBody, String sub)
//    {
//
//        try {
//            SimpleMailMessage mailMessage
//                    = new SimpleMailMessage();
//
//            // Setting up necessary details
//            mailMessage.setFrom(sender);
//            mailMessage.setTo(rec);
//            mailMessage.setText(msgBody);
//            mailMessage.setSubject(sub);
//
//            // Sending the mail
//            javaMailSender.send(mailMessage);
//            return "Mail Sent Successfully...";
//        }
//        catch (Exception e) {
//            return "Error while Sending Mail";
//        }
//    }
                // CREATED FOR ATTACHMENTS AND ADD INLINE IMAGES, HTML......
//    public String sendMailWithAttachment(String rec, String msgBody, String sub)
//    {
//        // Creating a mime message
//        MimeMessage mimeMessage
//                = javaMailSender.createMimeMessage();
//        MimeMessageHelper mimeMessageHelper;
//
//        try {
//
//            // Setting multipart as true for attachments to
//            // be send
//            mimeMessageHelper
//                    = new MimeMessageHelper(mimeMessage, true);
//            mimeMessageHelper.setFrom(sender);
//            mimeMessageHelper.setTo(rec);
//            mimeMessageHelper.setText(msgBody);
//            mimeMessageHelper.setSubject(sub);
//
//            // Adding the attachment
//            String content = "<b>Dear guru</b>,<br><i>Please look at this nice picture:.</i>"
//                    + "<br><img src='cid:image001'/><br><b>Best Regards</b>";
//            mimeMessageHelper.setText(content, true);
//            FileSystemResource file
//                    = new FileSystemResource(
//                    new File("src/main/resources/templates/SBSS.jfif"));
//
//            mimeMessageHelper.addInline("image001", file);
//
//            // Sending the mail
//            javaMailSender.send(mimeMessage);
//            return "Mail sent Successfully";
//        }
//
//        // Catch block to handle MessagingException
//        catch (MessagingException e) {
//
//            // Display message when exception occurred
//            return "Error while sending mail!!!";
//        }
//    }
                    // CREATED HTML TEMPLATE FOR EMAIL MESSAGE BODY USING FreeMarkerTemplate......
    public String sendMailWithAttachment(String rec, String msgBody, String sub, Map<String , Object> model)
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

            Template t = configuration.getTemplate("email-template.ftlh");
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
//                    new File("src/main/resources/templates/SBSS.jfif"));
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

