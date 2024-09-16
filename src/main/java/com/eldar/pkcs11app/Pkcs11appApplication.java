package com.eldar.pkcs11app;

import com.eldar.pkcs11app.service.TestCrypto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class Pkcs11appApplication implements CommandLineRunner {
    private final TestCrypto testCrypto;

    public static void main(String[] args) {
        SpringApplication.run(Pkcs11appApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        testCrypto.testCrypto();
    }
}
