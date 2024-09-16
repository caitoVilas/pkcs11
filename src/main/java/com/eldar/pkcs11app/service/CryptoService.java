package com.eldar.pkcs11app.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;

/**
 * @author claudio.vilas
 * Servicio para cifrar y descifrar datos con RSA.
 */
@Service
@Slf4j
public class CryptoService {
    private final Provider pcks11Provider;
    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    /**
     * Constructor de la clase.
     * Inicializa el proveedor de seguridad PKCS11 y obtiene la clave privada y pública.
     * @param pcks11Provider Proveedor de seguridad PKCS11.
     */
    public CryptoService(Provider pcks11Provider)  {
        try {
            this.pcks11Provider = pcks11Provider;
            KeyStore keyStore = KeyStore.getInstance("PKCS11", pcks11Provider);
            String alias = "RSA001"; // Alias de la clave en el HSM
            char[] pin = "666321".toCharArray();   // PIN del HSM
            keyStore.load(null, pin);
            privateKey = (PrivateKey) keyStore.getKey(alias, pin);
            X509Certificate certificate = (X509Certificate) keyStore.getCertificate(alias);
            publicKey = certificate.getPublicKey();
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException |
                 UnrecoverableKeyException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método para cifrar datos con RSA.
     * @param data Datos a cifrar.
     * @return Datos cifrados.
     */
    public String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", pcks11Provider);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedData = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método para descifrar datos con RSA.
     * @param encryptedData Datos cifrados.
     * @return Datos descifrados.
     */
    public String decrypt(String encryptedData) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", pcks11Provider);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(decryptedData);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

}
