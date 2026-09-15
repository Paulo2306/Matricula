package com.ifsp.Matricula.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ifsp.Matricula.Model.Exemplar;
import com.ifsp.Matricula.Model.Livro;
import com.ifsp.Matricula.Model.StatusExemplar;
import com.ifsp.Matricula.Repository.ExemplarRepository;
import com.ifsp.Matricula.Repository.LivroRepository;

@Controller
public class ExemplarController {

    @Autowired
    private ExemplarRepository exemplarRepository;

    @Autowired
    private LivroRepository livroRepository;

    @GetMapping("cadastraExemplar")
    public String cadastroExemplar(Model model) {
        model.addAttribute("listaLivro", livroRepository.findAll());
        model.addAttribute("listaStatusExemplar", StatusExemplar.values());
        return "cadastraExemplar";
    }

    @PostMapping("Exemplares")
    public String saveExemplares(@RequestParam String tombo,
                                 @RequestParam StatusExemplar status,
                                 @RequestParam Long livroId) {

        Livro livro = livroRepository.findById(livroId).orElse(null);
        exemplarRepository.save(new Exemplar(tombo, status, livro));
        return "redirect:/cadastraExemplar?sucesso=true";
    }

    @GetMapping("listaExemplar")
    public String listaExemplares(Model model) {
        List<Exemplar> listaExemplares = exemplarRepository.findAll();
        model.addAttribute("listaExemplares", listaExemplares);
        return "listaExemplar";
    }

    @GetMapping("editarExemplar")
    public String editarExemplar(@RequestParam long id, Model model) {
        Exemplar exemplar = exemplarRepository.findById(id).orElse(null);
        model.addAttribute("exemplar", exemplar);
        model.addAttribute("listaLivro", livroRepository.findAll());
        model.addAttribute("listaStatusExemplar", StatusExemplar.values());
        return "editarExemplar";
    }

    @PostMapping("atualizarExemplar")
    public String atualizarExemplar(@RequestParam long id,
                                   @RequestParam String tombo,
                                   @RequestParam StatusExemplar status,
                                   @RequestParam Long livroId) {

        Exemplar exemplar = exemplarRepository.findById(id).orElse(null);
        if (exemplar == null) {
            return "redirect:/listaExemplar";
        }

        exemplar.setTombo(tombo);
        exemplar.setStatus(status);
        exemplar.setLivro(livroRepository.findById(livroId).orElse(null));
        exemplarRepository.save(exemplar);
        return "redirect:/listaExemplar";
    }

    @GetMapping("excluirExemplar")
    public String excluirExemplar(@RequestParam long id) {
        exemplarRepository.deleteById(id);
        return "redirect:/listaExemplar";
    }
}
