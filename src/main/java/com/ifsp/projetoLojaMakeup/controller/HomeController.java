package com.ifsp.projetoLojaMakeup.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ifsp.projetoLojaMakeup.model.ItemCarrinho;
import com.ifsp.projetoLojaMakeup.model.Produto;
import com.ifsp.projetoLojaMakeup.model.Usuario;
import com.ifsp.projetoLojaMakeup.service.ProdutoService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
    @Autowired
    private ProdutoService produtoService;
    @GetMapping("/")
    public String abrirHome(Model model, HttpSession sessao){

        Usuario usuario = (Usuario) sessao.getAttribute("usuario");
        model.addAttribute("usuario", usuario);
        
        List<Produto> listaProdutos = produtoService.listarTodos();
        model.addAttribute("lista", listaProdutos);
        List<ItemCarrinho> carrinho =
        (List<ItemCarrinho>)
        sessao.getAttribute("carrinho");

        model.addAttribute(
            "carrinho",
            carrinho
        );
        int quantidadeCarrinho = 0;

        if(carrinho != null){

            for(ItemCarrinho item : carrinho){

                quantidadeCarrinho +=
                    item.getQuantidade();
            }
        }

        model.addAttribute(
            "quantidadeCarrinho",
            quantidadeCarrinho
        );

        return "home";
    }

    @GetMapping("/login")
    public String abrirLogin(){
        return "login";
    }

    
/*
    @GetMapping("/buscar")
    public String buscarProduto(String titulo, Model model){

        List<Produto> listaProdutos = produtoService.buscarPorTitulo(titulo);

        model.addAttribute("lista", listaProdutos);

        return "produtos";
    }
         */
}
