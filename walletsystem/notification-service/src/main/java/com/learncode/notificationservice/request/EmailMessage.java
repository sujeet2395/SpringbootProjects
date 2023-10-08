package com.learncode.notificationservice.request;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
public class EmailMessage {
    private String to;
    private String cc;
    private String bcc;
    private String subject;
    private String mailBody;
}
