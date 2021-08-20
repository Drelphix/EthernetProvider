package by.training.ethernetprovider.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncryptor{
    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String encode(CharSequence password) {
        return encoder.encode(password);
    }

    public static boolean compare(String encryptedPassword, CharSequence password){
        return encoder.matches(password, encryptedPassword);
    }
}
