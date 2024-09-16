package com.eldar.pkcs11app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.Provider;
import java.security.Security;

/**
 * @author claudio.vilas
 * Clase de configuración para el proveedor de seguridad PKCS11.
 */
@Configuration
public class Pcks11Config {
    @Value("${hsm.config.path}")
    private String hsmConfigPath;

    /**
     * Método para obtener el proveedor de seguridad PKCS11.
     * @return Proveedor de seguridad PKCS11.
     */
    @Bean
    public Provider pcks11Provider() {
        String configName = hsmConfigPath;
        Provider provider = Security.getProvider("SunPKCS11");
        return provider.configure(configName);
    }
}
