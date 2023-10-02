package com.learncode.authservice.apiconstant;

public class Constant {
    public static final int OTP_LENGTH = 6;
    public static final String RESET_PASSWORD_SUBJECT = "Reset Password OTP";
    public static final String NEW_USER_SUBJECT = "Welcome to Wallet System";
    private static final String GREET_MSG = "Hello ";
    private static final String RESET_PASSWORD_MSG1 = "<br/><br/>You have requested to reset your password.<br/>Your OTP is <strong>";
    private static final String RESET_PASSWORD_MSG2 = "</strong>. Validity of OTP is 10min.";
    private static final String RESET_PASSWORD_MSG3 = "<br/>If you did not request a password reset, please ignore this email. Your password will remain unchanged.";
    private static final String NEW_USER_MSG1 = "<br/><br/>Welcome to Wallet System!<br/>We are excited to have you on board. Your account has been successfully created, and you can now start using our wallet system to manage your finances efficiently." +
            " Here are some key features of your new account:<br/>- <strong>Username:</strong> ";
    private static final String NEW_USER_MSG2 = "<br/>- <strong>Email:</strong> ";
    private static final String NEW_USER_MSG3 = "<br/>Feel free to explore our wallet system and get started with managing your funds, making payments, and more. If you have any questions or need assistance, please don't hesitate to reach out to our support team at sujeet.sharma2395@gmail.com.";
    private static final String NEW_USER_MSG4 = "<br/>Thank you for choosing Wallet System! We look forward to serving you.";
    private static final String BASE_MSG = "<br/><br/>Thank you,<br/>Learn Code.";
    private static final String BASE_MSG1 = "<br/><br/>Best regards,<br/>Learn Code.";
    public static String getResetPasswordMailBody(String username, String otp) {
        return GREET_MSG+username+","+RESET_PASSWORD_MSG1+otp+RESET_PASSWORD_MSG2+RESET_PASSWORD_MSG3+BASE_MSG;
    }

    public static String getNewUserMailBody(String username, String email) {
        return GREET_MSG+username+","+NEW_USER_MSG1+username+NEW_USER_MSG2+email+NEW_USER_MSG3+NEW_USER_MSG4+BASE_MSG1;
    }
}
