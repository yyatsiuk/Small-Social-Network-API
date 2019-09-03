package com.yyatsiuk.social.sonet.service.impl;

import com.yyatsiuk.social.sonet.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.activation.MimetypesFileTypeMap;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@SuppressWarnings("Duplicates")
@Service
public class EmailServiceImpl implements EmailService {
    private static final String EMAIL_CONFIRM_LINK = "html/confirm";
    private static final String EMAIL_BAN_USER = "html/block";
    private static final String EMAIL_RESTORE_PASSWORD = "html/restore-pass";
    private static final String EMAIL_RESTORE_SUCCESSFUL = "html/restore-successful";
    private static final String imageResourceName = "../img/logo.png";

    private final JavaMailSender mailSender;

    private final TemplateEngine htmlTemplateEngine;

    @Autowired
    public EmailServiceImpl(@Qualifier(value = "appMailSender") JavaMailSender mailSender, @Qualifier(value = "appEmailTemplateEngine") TemplateEngine htmlTemplateEngine) {
        this.mailSender = mailSender;
        this.htmlTemplateEngine = htmlTemplateEngine;
    }


    private ResourceLoader resourceLoader;

    @Autowired
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Async
    @Override
    public void sendConfirm(String email, String confirmLink) throws MessagingException {
        final Context ctx = new Context(null);
        ctx.setVariable("name", email);
        ctx.setVariable("link", confirmLink);

        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message
                = new MimeMessageHelper(mimeMessage, true /* multipart */, "UTF-8");
        message.setSubject("Confirm email");
        message.setFrom("SONET");
        message.setTo(email);
        final String htmlContent = this.htmlTemplateEngine.process(EMAIL_CONFIRM_LINK, ctx);
        message.setText(htmlContent, true /* isHtml */);

        this.mailSender.send(mimeMessage);


    }

    @Async
    @Override
    public void sendBan(String email) throws MessagingException {
        final Context ctx = new Context(null);
        ctx.setVariable("name", email);
        ctx.setVariable("imageResourceName", imageResourceName);

        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message
                = new MimeMessageHelper(mimeMessage, true /* multipart */, "UTF-8");
        message.setSubject("Ban");
        message.setFrom("SONET");
        message.setTo(email);

        final String htmlContent = this.htmlTemplateEngine.process(EMAIL_BAN_USER, ctx);
        message.setText(htmlContent, true /* isHtml */);

        attachLogo(message);
        this.mailSender.send(mimeMessage);
    }

    @Async
    @Override
    public void sendRestorePassword(String email, String confirmLink) throws MessagingException {
        final Context ctx = new Context(null);
        ctx.setVariable("name", email);
        ctx.setVariable("link", confirmLink);
        ctx.setVariable("imageResourceName", imageResourceName);
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message
                = new MimeMessageHelper(mimeMessage, true /* multipart */, "UTF-8");
        message.setSubject("Restore password");
        message.setFrom("SONET");
        message.setTo(email);
        final String htmlContent = this.htmlTemplateEngine.process(EMAIL_RESTORE_PASSWORD, ctx);
        message.setText(htmlContent, true /* isHtml */);
        attachLogo(message);
        this.mailSender.send(mimeMessage);
    }

    @Async
    @Override
    public void sendRestoreSuccessful(String email) throws MessagingException {
        final Context ctx = new Context(null);
        ctx.setVariable("imageResourceName", imageResourceName);

        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message
                = new MimeMessageHelper(mimeMessage, true /* multipart */, "UTF-8");
        message.setSubject("Successfully reset");
        message.setFrom("SONET");
        message.setTo(email);

        final String htmlContent = this.htmlTemplateEngine.process(EMAIL_RESTORE_SUCCESSFUL, ctx);
        message.setText(htmlContent, true /* isHtml */);

        attachLogo(message);
        this.mailSender.send(mimeMessage);
    }

    private void attachLogo(MimeMessageHelper message) throws MessagingException {
        try {
            Resource res = resourceLoader.getResource("classpath:mail/img/logo.png");
            String imgContentType = new MimetypesFileTypeMap().getContentType(res.getFile());
            message.addInline(imageResourceName, res, imgContentType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
