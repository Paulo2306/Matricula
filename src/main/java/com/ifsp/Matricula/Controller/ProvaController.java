package com.ifsp.Matricula.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ifsp.Matricula.Model.OfertaDisciplina;
import com.ifsp.Matricula.Model.Prova;
import com.ifsp.Matricula.Repository.OfertaDisciplinaRepository;
import com.ifsp.Matricula.Repository.ProvaRepository;

@Controller
public class ProvaController {

    @Autowired
    private ProvaRepository provaRepository;

    @Autowired
    private OfertaDisciplinaRepository ofertaDisciplinaRepository;

    @GetMapping("cadastraProva")
    public String cadastroProva(Model model) {
        model.addAttribute("listaOfertaDisciplina", ofertaDisciplinaRepository.findAll());
        return "cadastraProva";
    }

    @PostMapping("Provas")
    public String saveProvas(@RequestParam LocalDate data,
                            @RequestParam String conteudo,
                            @RequestParam Double peso,
                            @RequestParam Long ofertaDisciplinaId) {

        OfertaDisciplina oferta = ofertaDisciplinaRepository.findById(ofertaDisciplinaId).orElse(null);
        provaRepository.save(new Prova(data, conteudo, peso, oferta));
        return "redirect:/cadastraProva?sucesso=true";
    }

    @GetMapping("listaProva")
    public String listaProvas(Model model) {
        List<Prova> listaProvas = provaRepository.findAll();
        model.addAttribute("listaProvas", listaProvas);
        return "listaProva";
    }

    @GetMapping("editarProva")
    public String editarProva(@RequestParam long id, Model model) {
        Prova prova = provaRepository.findById(id).orElse(null);
        model.addAttribute("prova", prova);
        model.addAttribute("listaOfertaDisciplina", ofertaDisciplinaRepository.findAll());
        return "editarProva";
    }

    @PostMapping("atualizarProva")
    public String atualizarProva(@RequestParam long id,
                                @RequestParam LocalDate data,
                                @RequestParam String conteudo,
                                @RequestParam Double peso,
                                @RequestParam Long ofertaDisciplinaId) {

        Prova prova = provaRepository.findById(id).orElse(null);
        if (prova == null) {
            return "redirect:/listaProva";
        }

        prova.setData(data);
        prova.setConteudo(conteudo);
        prova.setPeso(peso);
        prova.setOfertaDisciplina(ofertaDisciplinaRepository.findById(ofertaDisciplinaId).orElse(null));
        provaRepository.save(prova);
        return "redirect:/listaProva";
    }

    @GetMapping("excluirProva")
    public String excluirProva(@RequestParam long id) {
        provaRepository.deleteById(id);
        return "redirect:/listaProva";
    }
}
