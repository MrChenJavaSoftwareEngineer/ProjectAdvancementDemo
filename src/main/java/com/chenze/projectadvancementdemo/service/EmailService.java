package com.chenze.projectadvancementdemo.service;

public interface EmailService {
    

    //to是我们要发给谁，subject是我们的主题，text是我们的邮件正文
    void sendSimpleMessage(String to,String subject,String text);

}
