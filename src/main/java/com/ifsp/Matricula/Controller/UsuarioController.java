package com.ifsp.Matricula.Controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ifsp.Matricula.Model.UsuarioRole;
import com.ifsp.Matricula.Model.Pessoa;
import com.ifsp.Matricula.Model.Usuario;
import com.ifsp.Matricula.Repository.PessoaRepository;
import com.ifsp.Matricula.Repository.UsuarioRepository;
import com.ifsp.Matricula.Service.UsuarioService;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired 
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String formLogin() {
        return "login";
    }

    @GetMapping("cadastraUsuario")
    public String cadastroUsuario(Model model) {
        model.addAttribute("listaPessoa", listarPessoasDisponiveis(null));
        return "cadastraUsuario";
    }

    @PostMapping("Usuarios")
    public String saveUsuarios(@RequestParam("username") String username,
                                @RequestParam("password") String password,
                                @RequestParam(value = "imagem", required = false) MultipartFile fotoPerfil,
                                @RequestParam(value = "pessoa", required = false) Integer pessoaId,
                                Model model) {

        if (usuarioRepository.findByUsername(username).isPresent()) {
            model.addAttribute("listaPessoa", listarPessoasDisponiveis(null));
            model.addAttribute("loginInformado", username);
            model.addAttribute("erroLogin", true);
            return "cadastraUsuario";
        }

        Pessoa pessoa = pessoaId == null ? null : pessoaRepository.findById(pessoaId).orElse(null);
        if (pessoaId != null && pessoa == null) {
            model.addAttribute("listaPessoa", listarPessoasDisponiveis(null));
            model.addAttribute("loginInformado", username);
            model.addAttribute("erroPessoa", true);
            return "cadastraUsuario";
        }

        try {
            Usuario usuario = new Usuario();
            
            if (fotoPerfil != null && !fotoPerfil.isEmpty()) {
                usuario.setFotoPerfil(usuarioService.salvarImagem(fotoPerfil));
            }
            
            usuario.setUsername(username);
            // Criptografa a senha com BCrypt antes de salvar
            usuario.setPassword(passwordEncoder.encode(password));
            
            usuario.setPapel(UsuarioRole.USER);
            usuario.setPessoa(pessoa);
            
            usuarioRepository.save(usuario);
            return "redirect:/cadastraUsuario?sucesso=true";
            
        } catch (IOException e) {
            return "Erro ao salvar o arquivo: " + e.getMessage();
        }
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
        model.addAttribute("listaPessoa", listarPessoasDisponiveis(id));
        model.addAttribute("listaUsuarioRole", UsuarioRole.values());
        return "editarUsuario";
    }

    @PostMapping("atualizarUsuario")
    public String atualizarUsuario(@RequestParam long id,
                                    @RequestParam("username") String username,
                                    @RequestParam(value = "password", required = false) String password,
                                    @RequestParam("usuarioRole") UsuarioRole usuarioRole,
                                    @RequestParam(value = "imagem", required = false) MultipartFile fotoPerfil,
                                    @RequestParam(value = "pessoa", required = false) Integer pessoaId,
                                    Model model) {

        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) {
            return "redirect:/listaUsuario";
        }

        Usuario usuarioComMesmoLogin = usuarioRepository.findByUsername(username).orElse(null);
        if (usuarioComMesmoLogin != null && usuarioComMesmoLogin.getId() != id) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("listaPessoa", listarPessoasDisponiveis(id));
            model.addAttribute("listaUsuarioRole", UsuarioRole.values());
            model.addAttribute("usernameInformado", username);
            model.addAttribute("erroLogin", true);
            return "editarUsuario";
        }

        Pessoa pessoa = pessoaId == null ? null : pessoaRepository.findById(pessoaId).orElse(null);
        if (pessoaId != null && pessoa == null) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("listaPessoa", listarPessoasDisponiveis(id));
            model.addAttribute("listaUsuarioRole", UsuarioRole.values());
            model.addAttribute("erroPessoa", true);
            return "editarUsuario";
        }

        Usuario usuarioComMesmoVinculo = pessoa != null
                ? usuarioRepository.findByPessoa_IdPessoa(pessoa.getIdPessoa()).orElse(null)
                : null;
        if (usuarioComMesmoVinculo != null && usuarioComMesmoVinculo.getId() != id) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("listaPessoa", listarPessoasDisponiveis(id));
            model.addAttribute("listaUsuarioRole", UsuarioRole.values());
            model.addAttribute("usernameInformado", username);
            model.addAttribute("erroPessoa", true);
            return "editarUsuario";
        }

        usuario.setUsername(username);
        if (password != null && !password.isBlank()) {
            usuario.setPassword(passwordEncoder.encode(password));
        }
        usuario.setPapel(usuarioRole);
        
        if (fotoPerfil != null && !fotoPerfil.isEmpty()) {
            try {
                usuario.setFotoPerfil(usuarioService.salvarImagem(fotoPerfil));
            } catch (IOException e) {
                return "Erro ao salvar o arquivo: " + e.getMessage();
            }
        }
        
        usuario.setPessoa(pessoa);
        usuarioRepository.save(usuario);

        return "redirect:/listaUsuario";
    }

    @GetMapping("excluirUsuario")
    public String excluirUsuario(@RequestParam long id) {
        usuarioRepository.deleteById(id);
        return "redirect:/listaUsuario";
    }

    private List<Pessoa> listarPessoasDisponiveis(Long usuarioId) {
        Set<Integer> pessoasVinculadas = new HashSet<>();
        for (Usuario usuario : usuarioRepository.findAll()) {
            if (usuario.getPessoa() != null && (usuarioId == null || usuario.getId() != usuarioId)) {
                pessoasVinculadas.add(usuario.getPessoa().getIdPessoa());
            }
        }

        return pessoaRepository.findAll().stream()
                .filter(pessoa -> !pessoasVinculadas.contains(pessoa.getIdPessoa()))
                .toList();
    }
}