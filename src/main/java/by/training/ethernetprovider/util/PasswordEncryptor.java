package by.training.ethernetprovider.util;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

public class PasswordEncryptor {
    public String encode(String password) {
        PasswordEncoder passwordEncoder = new SCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}
