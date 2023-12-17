package com.learn.datedemo;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DatedemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatedemoApplication.class, args);
		// Create a java.util.Date object
        Date date = new Date();

        System.out.println("System Current Date time: "+date);
        // Convert the java.util.Date to ZonedDateTime in IST
        ZonedDateTime istDateTime = date.toInstant().atZone(ZoneId.of("Asia/Kolkata"));
        
        System.out.println("IST Date and Time: " + istDateTime);
        
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        
        System.out.println("IST Date and Time: " + dateTimeFormat.format(istDateTime));
        
        LocalDateTime localDateTime = istDateTime.toLocalDateTime();
        
        System.out.println("IST Date and Time: " + dateTimeFormat.format(localDateTime));
	}

}
