package com.eldar.pkcs11app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author claudio.vilas
 * Servicio para probar el cifrado y descifrado de datos.
 */
@Service
@RequiredArgsConstructor
public class TestCrypto {
    private final CryptoService cryptoService;

    /**
     * Método para probar el cifrado y descifrado de datos.
     * Imprime en consola los datos originales, cifrados y descifrados.
     *
     */
    public void testCrypto() {
        String data = "Hola Mundo!";
        String encryptedData = cryptoService.encrypt(data);
        String decryptedData = cryptoService.decrypt(encryptedData);
        System.out.println("Data: " + data);
        System.out.println("Encrypted Data: " + encryptedData);
        System.out.println("Decrypted Data: " + decryptedData);
    }
}
