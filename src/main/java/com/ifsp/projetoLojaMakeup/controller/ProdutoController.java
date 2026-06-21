package com.ifsp.projetoLojaMakeup.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ifsp.projetoLojaMakeup.model.Produto;
import com.ifsp.projetoLojaMakeup.model.Usuario;
import com.ifsp.projetoLojaMakeup.service.ProdutoService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/produtos")
    public String listarProdutos(Model model){
        
        List<Produto> listaProdutos = produtoService.listarTodos();
        model.addAttribute("lista", listaProdutos);
        return "produtos";
    }

    @GetMapping("/admin/produtos")
    public String listarProdutosAdmin(Model model, HttpSession sessao){
        if(usuarioNaoEhAdmin(sessao)){
            return "redirect:/";
        }

        List<Produto> listaProdutos = produtoService.listarTodos();

        model.addAttribute("lista", listaProdutos);

        return "admin/listarProdutos";
    }
    @GetMapping("/produto/{id}")
    public String detalhesProduto(Model model, @PathVariable Long id){
        Produto produto = produtoService.buscarPorId(id);
        model.addAttribute("produto", produto);

        return "produto";

    }

    @GetMapping("/admin/produto/novo")
    public String cadastrarProduto(HttpSession sessao){
        if(usuarioNaoEhAdmin(sessao)){
            return "redirect:/";
        }
        return "admin/cadastrarProduto";
    }

    @PostMapping("/admin/produto/salvar")
    public String salvarProduto(Produto produto, HttpSession sessao, @RequestParam("arquivo") MultipartFile arquivo){
        if(usuarioNaoEhAdmin(sessao)){
            return "redirect:/";
        }

        try {

        String nomeArquivo = arquivo.getOriginalFilename();

        Path caminho = Paths.get(
                "src/main/resources/static/img/"
                + nomeArquivo);

        Files.write(caminho, arquivo.getBytes());

        produto.setImg(nomeArquivo);

        } catch (Exception e) {

            e.printStackTrace();

        }
        produtoService.salvarProduto(produto);

        return "redirect:/admin/produtos";
    }

    @GetMapping("/admin/produto/editar/{id}")
    public String editarProduto(Model model, @PathVariable Long id, HttpSession sessao){
        if(usuarioNaoEhAdmin(sessao)){
            return "redirect:/";
        }
        Produto produto = produtoService.buscarPorId(id);

        model.addAttribute("produto", produto);

        return "admin/editarProduto";
    }

    @PostMapping("/admin/produto/atualizar")
    public String atualizarProduto(
            Produto produto,
            @RequestParam("arquivo") MultipartFile arquivo,
            HttpSession sessao){

        if(usuarioNaoEhAdmin(sessao)){
            return "redirect:/";
        }

        try{

            if(!arquivo.isEmpty()){

                String nomeArquivo =
                    arquivo.getOriginalFilename();

                Path caminho =
                    Paths.get(
                        "src/main/resources/static/img/"
                        + nomeArquivo
                    );

                Files.write(
                    caminho,
                    arquivo.getBytes()
                );

                produto.setImg(nomeArquivo);
            }

            else{

                Produto produtoBanco =
                    produtoService.buscarPorId(
                        produto.getId()
                    );

                produto.setImg(
                    produtoBanco.getImg()
                );
            }

            produtoService.atualizar(produto);

        }

        catch(Exception e){

            e.printStackTrace();
        }

        return "redirect:/admin/produtos";
    } 

    @GetMapping("/admin/produto/excluir/{id}")
    public String excluitProduto(@PathVariable Long id, HttpSession sessao){
        if(usuarioNaoEhAdmin(sessao)){
            return "redirect:/";
        }
        produtoService.deletar(id);

        return "redirect:/admin/produtos";
    }

    @GetMapping("/buscar")
    public String buscarProduto(String titulo,
                            Model model,
                            HttpSession sessao){

        Usuario usuario = (Usuario) sessao.getAttribute("usuario");
        model.addAttribute("usuario", usuario);
        List<Produto> listaProdutos =
                produtoService.buscarPorTitulo(titulo);

        model.addAttribute("lista", listaProdutos);

        return "produtos";
    }

    private boolean usuarioNaoEhAdmin(HttpSession sessao){

        Usuario usuario = (Usuario) sessao.getAttribute("usuario");
        if(usuario == null){
            return true;
        }

        return !usuario.getPerfil().equals("ADMIN");
    }

}
