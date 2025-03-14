package com.chatpoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Point d'entrée de l'application Spring Boot.
 * Cette classe démarre l'application et configure automatiquement
 * les composants Spring Boot.
 */
@SpringBootApplication 
public class ApplicationChat {

    /**
     * Méthode principale qui démarre l'application.
     *
     * @param args Arguments de la ligne de commande (non utilisés ici).
     */
    public static void main(String[] args) {
        SpringApplication.run(ApplicationChat.class, args); // Démarre l'application Spring Boot
    }
}
