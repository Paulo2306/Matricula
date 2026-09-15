package com.ifsp.Matricula.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ifsp.Matricula.Model.Aluno;
import com.ifsp.Matricula.Model.Papel;
import com.ifsp.Matricula.Model.Usuario;
import com.ifsp.Matricula.Repository.AlunoRepository;
import com.ifsp.Matricula.Repository.UsuarioRepository;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @GetMapping("cadastraUsuario")
    public String cadastroUsuario(Model model) {
        model.addAttribute("listaAluno", alunoRepository.findAll());
        model.addAttribute("listaPapel", Papel.values());
        return "cadastraUsuario";
    }

    @PostMapping("Usuarios")
    public String saveUsuarios(@RequestParam String login,
                              @RequestParam String senhaHash,
                              @RequestParam Papel papel,
                              @RequestParam(required = false) String fotoPerfil,
                              @RequestParam(required = false) Long alunoId) {

        Aluno aluno = alunoId != null ? alunoRepository.findById(alunoId).orElse(null) : null;
        usuarioRepository.save(new Usuario(login, senhaHash, papel, fotoPerfil, aluno));
        return "redirect:/cadastraUsuario?sucesso=true";
    }

    @GetMapping("listaUsuario")
    public String listaUsuarios(Model model) {
        List<Usuario> listaUsuarios = usuarioRepository.findAll();
        model.addAttribute("listaUsuarios", listaUsuarios);
        return "listaUsuario";
    }

    @GetMapping("editarUsuario")
    public String editarUsuario(@RequestParam long id, Model model) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        model.addAttribute("usuario", usuario);
        model.addAttribute("listaAluno", alunoRepository.findAll());
        model.addAttribute("listaPapel", Papel.values());
        return "editarUsuario";
    }

    @PostMapping("atualizarUsuario")
    public String atualizarUsuario(@RequestParam long id,
                                  @RequestParam String login,
                                  @RequestParam String senhaHash,
                                  @RequestParam Papel papel,
                                  @RequestParam(required = false) String fotoPerfil,
                                  @RequestParam(required = false) Long alunoId) {

        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) {
            return "redirect:/listaUsuario";
        }

        usuario.setLogin(login);
        usuario.setSenhaHash(senhaHash);
        usuario.setPapel(papel);
        usuario.setFotoPerfil(fotoPerfil);
        usuario.setAluno(alunoId != null ? alunoRepository.findById(alunoId).orElse(null) : null);
        usuarioRepository.save(usuario);

        return "redirect:/listaUsuario";
    }

    @GetMapping("excluirUsuario")
    public String excluirUsuario(@RequestParam long id) {
        usuarioRepository.deleteById(id);
        return "redirect:/listaUsuario";
    }
}
