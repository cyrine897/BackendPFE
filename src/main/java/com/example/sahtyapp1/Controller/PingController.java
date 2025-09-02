package com.example.sahtyapp1.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "http://localhost:4200") // Pour Angular
public class PingController {

    // Endpoint public (devrait renvoyer 200 OK)
    @GetMapping("/ping")
    public String ping() {
        return "Pong!";
    }

    // Endpoint simulant l'accès sécurisé (devrait renvoyer 401 si non authentifié)
    @GetMapping("/secure")
    public String securePing() {
        return "Secure Pong!";
    }
}

