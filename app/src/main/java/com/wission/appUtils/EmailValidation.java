package com.wission.appUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidation {
    private static final String VALID_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-].+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /*"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"*/

    public static boolean checkEmailIsCorrect(String strEmail) {
//		try {
        boolean isvalid;
        Pattern pattern = Pattern.compile(VALID_EMAIL);
        Matcher matcher = pattern.matcher(strEmail);
        isvalid = matcher.matches();
        return isvalid;
    }

    public static boolean isValidUrl(String txtWebsite) {
        Pattern regex = Pattern.compile("^[a-zA-Z0-9\\-\\.]+\\.(com|org|net|mil|edu|COM|ORG|NET|MIL|EDU)$");
        Matcher matcher = regex.matcher(txtWebsite);
        return matcher.matches();
    }
    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }
}
