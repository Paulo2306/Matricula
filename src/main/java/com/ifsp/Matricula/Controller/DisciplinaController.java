package com.ifsp.Matricula.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ifsp.Matricula.Model.Curso;
import com.ifsp.Matricula.Model.Disciplina;
import com.ifsp.Matricula.Model.Livro;
import com.ifsp.Matricula.Repository.CursoRepository;
import com.ifsp.Matricula.Repository.LivroRepository;
import java.util.ArrayList;
import com.ifsp.Matricula.Repository.DisciplinaRepository;
import com.ifsp.Matricula.Repository.OfertaDisciplinaRepository;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DisciplinaController {

    @Autowired
    private DisciplinaRepository DisciplinaRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private OfertaDisciplinaRepository ofertaDisciplinaRepository;

    @Autowired
    private LivroRepository livroRepository;

    @GetMapping("cadastraDisciplina")
    public String cadastroDisciplina(Model model) {
        model.addAttribute("listaCurso", cursoRepository.findAll());
        model.addAttribute("listaLivros", livroRepository.findAll());
        return "/cadastraDisciplina";
    }

    @PostMapping("Disciplinas")
    public String saveDisciplinas(@RequestParam String nome, @RequestParam int cargaHoraria,
            @RequestParam Curso curso, @RequestParam(required = false) List<Long> livrosIds) {
        Disciplina disciplina = new Disciplina(nome, cargaHoraria, curso);
        if (livrosIds != null) {
            disciplina.setLivros(livroRepository.findAllById(livrosIds));
        }
        DisciplinaRepository.save(disciplina);
        return "redirect:/cadastraDisciplina?sucesso=true";
    }

    @GetMapping("listaDisciplina")
    public String listaDisciplinas(Model model) {
        List<Disciplina> listaDisciplinas = DisciplinaRepository.findAll();
        model.addAttribute("listaCurso", cursoRepository.findAll());
        model.addAttribute("listaDisciplinas", listaDisciplinas);
        return "/listaDisciplina";
    }

    // CARREGAR DADOS PARA EDIÇÃO
    @GetMapping("editarDisciplina")
    public String editarDisciplina(@RequestParam long id, Model model) {
        Disciplina Disciplina = DisciplinaRepository.findById(id).orElse(null);
        model.addAttribute("listaCurso", cursoRepository.findAll());
        model.addAttribute("listaLivros", livroRepository.findAll());
        model.addAttribute("Disciplina", Disciplina);
        return "editarDisciplina";
    }

    // SALVAR EDIÇÃO
    @PostMapping("atualizarDisciplina")
    public String atualizarDisciplina(@RequestParam long id,
            @RequestParam String nome,
            @RequestParam int cargaHoraria,
            @RequestParam Curso curso,
            @RequestParam(required = false) List<Long> livrosIds) {

        Disciplina Disciplina = DisciplinaRepository.findById(id).orElse(null);
        ;
        Disciplina.setNome(nome);
        Disciplina.setCargaHoraria(cargaHoraria);
        Disciplina.setCurso(curso);
        if (livrosIds != null) {
            Disciplina.setLivros(livroRepository.findAllById(livrosIds));
        } else {
            Disciplina.setLivros(new ArrayList<>());
        }
        DisciplinaRepository.save(Disciplina);
        return "redirect:/listaDisciplina";
    }

    // EXCLUIR
    @GetMapping("excluirDisciplina")
    public String excluirDisciplina(@RequestParam long id, RedirectAttributes redirectAttributes) {
        if (ofertaDisciplinaRepository.existsByDisciplinaId(id)) {

            redirectAttributes.addFlashAttribute(
                    "erro",
                    "Não é possível excluir esta disciplina porque ela está vinculada a uma oferta de disciplina.");

            return "redirect:/listaDisciplina";
        }

        DisciplinaRepository.deleteById(id);

        redirectAttributes.addFlashAttribute(
                "sucesso",
                "Disciplina excluída com sucesso.");

        return "redirect:/listaDisciplina";
    }

}
