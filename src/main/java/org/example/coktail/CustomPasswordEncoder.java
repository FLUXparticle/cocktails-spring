package org.example.coktail;

import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;

public class CustomPasswordEncoder implements PasswordEncoder {
    
    private final PasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();
    
    @Override
    public String encode(CharSequence rawPassword) {
        // Hier könntest du entscheiden, welcher Encoder verwendet werden soll,
        // z.B. immer den BCryptPasswordEncoder verwenden
        return bcryptEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        // Zuerst versuchen, das Passwort mit dem BCryptPasswordEncoder zu überprüfen
        boolean bcryptMatches = bcryptEncoder.matches(rawPassword, encodedPassword);
        
        // Wenn das Passwort mit dem BCryptPasswordEncoder nicht übereinstimmt,
        // versuche es als unverschlüsseltes Passwort zu überprüfen
        if (!bcryptMatches) {
            // Hier könntest du eine andere Methode zur Überprüfung des unverschlüsselten Passworts verwenden,
            // z.B. einen einfachen String-Vergleich
            return rawPassword.toString().equals(encodedPassword);
        }
        
        return bcryptMatches;
    }
}
