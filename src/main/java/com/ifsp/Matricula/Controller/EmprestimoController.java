package com.ifsp.Matricula.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ifsp.Matricula.Model.Aluno;
import com.ifsp.Matricula.Model.Emprestimo;
import com.ifsp.Matricula.Model.Exemplar;
import com.ifsp.Matricula.Model.StatusEmprestimo;
import com.ifsp.Matricula.Repository.AlunoRepository;
import com.ifsp.Matricula.Repository.EmprestimoRepository;
import com.ifsp.Matricula.Repository.ExemplarRepository;

@Controller
public class EmprestimoController {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ExemplarRepository exemplarRepository;

    @GetMapping("cadastraEmprestimo")
    public String cadastroEmprestimo(Model model) {
        model.addAttribute("listaAluno", alunoRepository.findAll());
        model.addAttribute("listaExemplar", exemplarRepository.findAll());
        model.addAttribute("listaStatusEmprestimo", StatusEmprestimo.values());
        return "cadastraEmprestimo";
    }

    @PostMapping("Emprestimos")
    public String saveEmprestimos(@RequestParam LocalDate dataRetirada,
                                 @RequestParam LocalDate dataPrevista,
                                 @RequestParam(required = false) LocalDate dataDevolucao,
                                 @RequestParam StatusEmprestimo status,
                                 @RequestParam int renovacoes,
                                 @RequestParam Long alunoId,
                                 @RequestParam Long exemplarId) {

        Aluno aluno = alunoRepository.findById(alunoId).orElse(null);
        Exemplar exemplar = exemplarRepository.findById(exemplarId).orElse(null);
        emprestimoRepository.save(new Emprestimo(dataRetirada, dataPrevista, dataDevolucao, status, renovacoes, aluno, exemplar));
        return "redirect:/cadastraEmprestimo?sucesso=true";
    }

    @GetMapping("listaEmprestimo")
    public String listaEmprestimos(Model model) {
        List<Emprestimo> listaEmprestimos = emprestimoRepository.findAll();
        model.addAttribute("listaEmprestimos", listaEmprestimos);
        return "listaEmprestimo";
    }

    @GetMapping("editarEmprestimo")
    public String editarEmprestimo(@RequestParam long id, Model model) {
        Emprestimo emprestimo = emprestimoRepository.findById(id).orElse(null);
        model.addAttribute("emprestimo", emprestimo);
        model.addAttribute("listaAluno", alunoRepository.findAll());
        model.addAttribute("listaExemplar", exemplarRepository.findAll());
        model.addAttribute("listaStatusEmprestimo", StatusEmprestimo.values());
        return "editarEmprestimo";
    }

    @PostMapping("atualizarEmprestimo")
    public String atualizarEmprestimo(@RequestParam long id,
                                    @RequestParam LocalDate dataRetirada,
                                    @RequestParam LocalDate dataPrevista,
                                    @RequestParam(required = false) LocalDate dataDevolucao,
                                    @RequestParam StatusEmprestimo status,
                                    @RequestParam int renovacoes,
                                    @RequestParam Long alunoId,
                                    @RequestParam Long exemplarId) {

        Emprestimo emprestimo = emprestimoRepository.findById(id).orElse(null);
        if (emprestimo == null) {
            return "redirect:/listaEmprestimo";
        }

        emprestimo.setDataRetirada(dataRetirada);
        emprestimo.setDataPrevista(dataPrevista);
        emprestimo.setDataDevolucao(dataDevolucao);
        emprestimo.setStatus(status);
        emprestimo.setRenovacoes(renovacoes);
        emprestimo.setAluno(alunoRepository.findById(alunoId).orElse(null));
        emprestimo.setExemplar(exemplarRepository.findById(exemplarId).orElse(null));
        emprestimoRepository.save(emprestimo);
        return "redirect:/listaEmprestimo";
    }

    @GetMapping("excluirEmprestimo")
    public String excluirEmprestimo(@RequestParam long id) {
        emprestimoRepository.deleteById(id);
        return "redirect:/listaEmprestimo";
    }
}
