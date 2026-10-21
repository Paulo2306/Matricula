package com.ifsp.Matricula.Security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ifsp.Matricula.Model.Usuario;
import com.ifsp.Matricula.Model.UsuarioRole;
import com.ifsp.Matricula.Repository.UsuarioRepository;

@Configuration
public class BootstrapAdminConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(BootstrapAdminConfiguration.class);

    @Bean
    ApplicationRunner bootstrapAdmin(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            Environment environment,
            @Value("${app.bootstrap-admin.username:}") String configuredUsername,
            @Value("${app.bootstrap-admin.password:}") String configuredPassword) {
        return args -> {
            boolean usernameConfigured = configuredUsername != null && !configuredUsername.isBlank();
            boolean passwordConfigured = configuredPassword != null && !configuredPassword.isBlank();

            if (!usernameConfigured && !passwordConfigured) {
                logger.info("Bootstrap admin não configurado; nenhum usuário inicial foi criado.");
                return;
            }
            if (!usernameConfigured || !passwordConfigured) {
                throw new IllegalStateException(
                        "Configure APP_BOOTSTRAP_USERNAME e APP_BOOTSTRAP_PASSWORD juntos.");
            }
            if (!environment.acceptsProfiles(Profiles.of("local")) && configuredPassword.length() < 12) {
                throw new IllegalStateException("APP_BOOTSTRAP_PASSWORD deve ter pelo menos 12 caracteres.");
            }

            String username = configuredUsername.trim();
            if (usuarioRepository.findByUsername(username).isPresent()) {
                logger.info("O usuário administrador inicial '{}' já existe; nenhuma alteração foi feita.", username);
                return;
            }

            Usuario admin = new Usuario();
            admin.setUsername(username);
            admin.setPassword(passwordEncoder.encode(configuredPassword));
            admin.setPapel(UsuarioRole.ADMIN);
            usuarioRepository.save(admin);

            logger.info("Usuário administrador inicial '{}' criado.", username);
        };
    }
}
