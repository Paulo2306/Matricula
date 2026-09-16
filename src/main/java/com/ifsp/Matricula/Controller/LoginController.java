package com.ifsp.Matricula.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ifsp.Matricula.Model.Usuario;
import com.ifsp.Matricula.Repository.UsuarioRepository;

@Controller
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String autenticar(@RequestParam String login,
                             @RequestParam String senha,
                             Model model) {
        Optional<Usuario> usuario = usuarioRepository.findByLogin(login);

        if (usuario.isPresent() && usuario.get().getSenhaHash().equals(senha)) {
            return "redirect:/dashboard";
        }

        model.addAttribute("erro", "Login ou senha inválidos.");
        return "login";
    }
}
