package pl.sda.spring.customers.util;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

final class PasswordEncoder {

    @Test
    void generatePasswordToBcrypt() {
        final var encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("test123"));
    }
}
