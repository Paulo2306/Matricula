package com.ifsp.Matricula.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ifsp.Matricula.Model.Disciplina;
import com.ifsp.Matricula.Model.Livro;
import com.ifsp.Matricula.Repository.DisciplinaRepository;
import com.ifsp.Matricula.Repository.LivroRepository;
import com.ifsp.Matricula.Service.LivroService;

@Controller
public class LivroController {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private LivroService livroService;

    @GetMapping("cadastraLivro")
    public String cadastroLivro(Model model) {
        model.addAttribute("listaDisciplina", disciplinaRepository.findAll());
        return "cadastraLivro";
    }

    @PostMapping("Livros")
    public String saveLivros(@RequestParam String titulo,
                            @RequestParam String autor,
                            @RequestParam String isbn,
                            @RequestParam String edicao,
                            @RequestParam(value = "imagem", required = false) MultipartFile capaArquivo,
                            @RequestParam(required = false) List<Long> disciplinasIds) {

        String capaImagem = null;
        try {
            if (capaArquivo != null && !capaArquivo.isEmpty()) {
                capaImagem = livroService.salvarCapa(capaArquivo);
            }
        } catch (IOException e) {
            return "Erro ao salvar o arquivo: " + e.getMessage();
        }

        Livro livro = new Livro(titulo, autor, isbn, edicao, capaImagem);

        if (disciplinasIds != null) {
            List<Disciplina> disciplinas = disciplinaRepository.findAllById(disciplinasIds);
            livro.setDisciplinas(disciplinas);
        }

        livroRepository.save(livro);
        return "redirect:/cadastraLivro?sucesso=true";
    }

    @GetMapping("listaLivro")
    public String listaLivros(Model model) {
        List<Livro> listaLivros = livroRepository.findAll();
        model.addAttribute("listaLivros", listaLivros);
        return "listaLivro";
    }

    @GetMapping("editarLivro")
    public String editarLivro(@RequestParam long id, Model model) {
        Livro livro = livroRepository.findById(id).orElse(null);
        model.addAttribute("livro", livro);
        model.addAttribute("listaDisciplina", disciplinaRepository.findAll());
        return "editarLivro";
    }

    @PostMapping("atualizarLivro")
    public String atualizarLivro(@RequestParam long id,
                                @RequestParam String titulo,
                                @RequestParam String autor,
                                @RequestParam String isbn,
                                @RequestParam String edicao,
                                @RequestParam(value = "imagem", required = false) MultipartFile capaArquivo,
                                @RequestParam(required = false) List<Long> disciplinasIds) {

        Livro livro = livroRepository.findById(id).orElse(null);
        if (livro == null) {
            return "redirect:/listaLivro";
        }

        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setIsbn(isbn);
        livro.setEdicao(edicao);
        if (capaArquivo != null && !capaArquivo.isEmpty()) {
            try {
                livro.setCapaImagem(livroService.salvarCapa(capaArquivo));
            } catch (IOException e) {
                return "Erro ao salvar o arquivo: " + e.getMessage();
            }
        }
        livro.setDisciplinas(disciplinasIds != null ? disciplinaRepository.findAllById(disciplinasIds) : List.of());
        livroRepository.save(livro);
        return "redirect:/listaLivro";
    }

    @GetMapping("excluirLivro")
    public String excluirLivro(@RequestParam long id) {
        livroRepository.deleteById(id);
        return "redirect:/listaLivro";
    }
}
