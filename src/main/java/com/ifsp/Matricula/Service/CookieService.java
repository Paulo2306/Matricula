
package com.ifsp.Matricula.Service;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
public class CookieService {

    public ResponseCookie criarCookie(String nomeUsuario) {

        return ResponseCookie.from("usuario", nomeUsuario)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(60 * 60 * 24)
                .sameSite("Lax")
                .build();
    }

    public ResponseCookie removerCookie() {

        return ResponseCookie.from("usuario", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();
    }
}
