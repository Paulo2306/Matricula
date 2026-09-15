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
import com.ifsp.Matricula.Model.Livro;
import com.ifsp.Matricula.Model.Reserva;
import com.ifsp.Matricula.Model.StatusReserva;
import com.ifsp.Matricula.Repository.AlunoRepository;
import com.ifsp.Matricula.Repository.LivroRepository;
import com.ifsp.Matricula.Repository.ReservaRepository;

@Controller
public class ReservaController {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private LivroRepository livroRepository;

    @GetMapping("cadastraReserva")
    public String cadastroReserva(Model model) {
        model.addAttribute("listaAluno", alunoRepository.findAll());
        model.addAttribute("listaLivro", livroRepository.findAll());
        model.addAttribute("listaStatusReserva", StatusReserva.values());
        return "cadastraReserva";
    }

    @PostMapping("Reservas")
    public String saveReservas(@RequestParam LocalDate dataReserva,
                              @RequestParam int posicaoFila,
                              @RequestParam StatusReserva status,
                              @RequestParam Long alunoId,
                              @RequestParam Long livroId) {

        Aluno aluno = alunoRepository.findById(alunoId).orElse(null);
        Livro livro = livroRepository.findById(livroId).orElse(null);
        reservaRepository.save(new Reserva(dataReserva, posicaoFila, status, aluno, livro));
        return "redirect:/cadastraReserva?sucesso=true";
    }

    @GetMapping("listaReserva")
    public String listaReservas(Model model) {
        List<Reserva> listaReservas = reservaRepository.findAll();
        model.addAttribute("listaReservas", listaReservas);
        return "listaReserva";
    }

    @GetMapping("editarReserva")
    public String editarReserva(@RequestParam long id, Model model) {
        Reserva reserva = reservaRepository.findById(id).orElse(null);
        model.addAttribute("reserva", reserva);
        model.addAttribute("listaAluno", alunoRepository.findAll());
        model.addAttribute("listaLivro", livroRepository.findAll());
        model.addAttribute("listaStatusReserva", StatusReserva.values());
        return "editarReserva";
    }

    @PostMapping("atualizarReserva")
    public String atualizarReserva(@RequestParam long id,
                                  @RequestParam LocalDate dataReserva,
                                  @RequestParam int posicaoFila,
                                  @RequestParam StatusReserva status,
                                  @RequestParam Long alunoId,
                                  @RequestParam Long livroId) {

        Reserva reserva = reservaRepository.findById(id).orElse(null);
        if (reserva == null) {
            return "redirect:/listaReserva";
        }

        reserva.setDataReserva(dataReserva);
        reserva.setPosicaoFila(posicaoFila);
        reserva.setStatus(status);
        reserva.setAluno(alunoRepository.findById(alunoId).orElse(null));
        reserva.setLivro(livroRepository.findById(livroId).orElse(null));
        reservaRepository.save(reserva);
        return "redirect:/listaReserva";
    }

    @GetMapping("excluirReserva")
    public String excluirReserva(@RequestParam long id) {
        reservaRepository.deleteById(id);
        return "redirect:/listaReserva";
    }
}
