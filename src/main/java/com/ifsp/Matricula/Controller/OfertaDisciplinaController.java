package com.ifsp.Matricula.Controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ifsp.Matricula.Model.Disciplina;
import com.ifsp.Matricula.Model.OfertaDisciplina;
import com.ifsp.Matricula.Model.Professor;
import com.ifsp.Matricula.Repository.DisciplinaRepository;
import com.ifsp.Matricula.Repository.MatriculaRepository;
import com.ifsp.Matricula.Repository.OfertaDisciplinaRepository;
import com.ifsp.Matricula.Repository.ProfessorRepository;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OfertaDisciplinaController {
    
    @Autowired
    private OfertaDisciplinaRepository OfertaDisciplinaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private MatriculaRepository matriculaRepository;




    @GetMapping("cadastraOfertaDisciplina")
    public String cadastroOfertaDisciplina(Model model){
        model.addAttribute("listaProfessor", professorRepository.findAll());
        model.addAttribute("listaDisciplina", disciplinaRepository.findAll());
        return "/cadastraOfertaDisciplina";
    }
    
    @PostMapping("OfertaDisciplinas")
    public String saveOfertaDisciplinas(@RequestParam Disciplina disciplina_id, 
                               @RequestParam Professor professor_id){
        OfertaDisciplinaRepository.save(new OfertaDisciplina(professor_id, disciplina_id));
        return "redirect:/cadastraOfertaDisciplina?sucesso=true";
    }

    @GetMapping("listaOfertaDisciplina")
    public String listaOfertaDisciplinas(Model model){
        List<OfertaDisciplina> listaOfertaDisciplinas = OfertaDisciplinaRepository.findAll();
        model.addAttribute("listaOfertaDisciplinas",listaOfertaDisciplinas);
        model.addAttribute("listaProfessor", professorRepository.findAll());
        model.addAttribute("listaDisciplina", disciplinaRepository.findAll());
        return "/listaOfertaDisciplina";
    }

    // CARREGAR DADOS PARA EDIÇÃO
    @GetMapping("editarOfertaDisciplina")
    public String editarOfertaDisciplina(@RequestParam long id, Model model){
        OfertaDisciplina OfertaDisciplina = OfertaDisciplinaRepository.findById(id).orElse(null);
        model.addAttribute("OfertaDisciplina", OfertaDisciplina);
        model.addAttribute("listaProfessor", professorRepository.findAll());
        model.addAttribute("listaDisciplina", disciplinaRepository.findAll());
        return "editarOfertaDisciplina";
    }

    // SALVAR EDIÇÃO
    @PostMapping("atualizarOfertaDisciplina")
    public String atualizarOfertaDisciplina(@RequestParam long id,
                                   @RequestParam Disciplina disciplina,
                                   @RequestParam Professor professor){

        OfertaDisciplina OfertaDisciplina = OfertaDisciplinaRepository.findById(id).orElse(null);;
        OfertaDisciplina.setDisciplina(disciplina);
        OfertaDisciplina.setProfessor(professor);
        OfertaDisciplinaRepository.save(OfertaDisciplina);
        return "redirect:/listaOfertaDisciplina";
    }

    @GetMapping("excluirOfertaDisciplina")
    public String excluirOfertaDisciplina(
            @RequestParam long id,
            RedirectAttributes redirectAttributes) {

        if (matriculaRepository.existsByOfertaDisciplinaId(id)) {

            redirectAttributes.addFlashAttribute(
                "erro",
                "Não é possível excluir esta oferta porque ela está vinculada a uma matrícula."
            );

            return "redirect:/listaOfertaDisciplina";
        }

        OfertaDisciplinaRepository.deleteById(id);

        redirectAttributes.addFlashAttribute(
            "sucesso",
            "Oferta de disciplina excluída com sucesso."
        );

        return "redirect:/listaOfertaDisciplina";
    }
    
}
