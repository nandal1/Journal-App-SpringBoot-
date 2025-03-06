package net.engineeringdigest.journalApp.Service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;


    @Disabled
    @Test
    void emailServiceTest(){
        emailService.sendEmail("adarshnandal3@gmail.com","Email Sender Test from SpringBoot","Hi," +
                "I am sending this email to just test whether my email is working or not " );

        emailService.sendEmail("adarshnandal3@gmail.com","Email Sender Test from SpringBoot","Hello," +
                "BUddy!!!   " +
                "I am sending this email to just test whether my email is working or not " );




    }
}
