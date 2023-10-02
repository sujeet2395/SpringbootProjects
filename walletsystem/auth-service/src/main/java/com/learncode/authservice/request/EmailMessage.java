package com.learncode.authservice.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EmailMessage {
    private String to;
    private String cc;
    private String bcc;
    private String subject;
    private String mailBody;
}
