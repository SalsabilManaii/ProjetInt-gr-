
package com.example.demo.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import entity.Personne;
import repository.PersonneRepository;

@Service
public class AuthServices {

    private final PersonneRepository utlrepo;

    public AuthServices(PersonneRepository utlrepo) {
        this.utlrepo = utlrepo;
    }

    @Transactional
    public Personne register(Personne utl) {

        if (utl.getNom() == null ) {
            throw new RuntimeException(
                "Le nom doit contenir uniquement des lettres"
            );
        }

        if (utl.getPrenom() == null) {
            throw new RuntimeException(
                "Le prenom doit contenir uniquement des lettres"
            );
        }

        if (utl.getEmail() == null) {
            throw new RuntimeException("Format d'email invalide");
        }

        if (utl.getMdp() == null || utl.getMdp().isEmpty()) {
            throw new RuntimeException("Mot de passe requis");
        }

        if (utl.getRole() == null) {
            throw new RuntimeException("Role requis");
        }

        // Normalize the role
        String role = utl.getRole().trim().toUpperCase();

        String roleFinal;

        switch (role) {
        case "CLIENT":
            roleFinal = "CLIENT";
            break;

        case "TECHNICIEN":
            roleFinal = "TECHNICIEN";
            break;

        case "RESPONSABLE":
        case "RESPONSABLEMAINTENANCE":
            roleFinal = "RESPONSABLE";
            break;

        case "ADMINISTRATEUR":
        case "ADMIN":
            roleFinal = "ADMIN";
            break;

        default:
            throw new RuntimeException("Role invalide !");
    }

        Personne n = new Personne(
        );
        n.setNom(utl.getNom());
        n.setPrenom(utl.getPrenom());
        n.setEmail(utl.getEmail());
        n.setMdp(utl.getMdp());
        n.setRole(roleFinal);

        return utlrepo.save(n);
    }

    @Transactional
    public Personne login(Personne utl) {

        if (utl == null) {
            throw new RuntimeException("Données invalides !");
        }

        if (utl.getEmail() == null || utl.getMdp() == null) {
            throw new RuntimeException( "Email et mot de passe requis" );
        }

        Optional<Personne> u =
            utlrepo.findByEmail(utl.getEmail());

        if (u.isEmpty()) {
            throw new RuntimeException("Email ou mot de passe incorrect");
        }

        if (!utl.getMdp().equals(u.get().getMdp())) {
            throw new RuntimeException("Email ou mot de passe incorrect");
        }

        return u.get();
    }
}
