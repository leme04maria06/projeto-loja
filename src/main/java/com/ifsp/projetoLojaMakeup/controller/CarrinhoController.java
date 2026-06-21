package com.ifsp.projetoLojaMakeup.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ifsp.projetoLojaMakeup.model.ItemCarrinho;
import com.ifsp.projetoLojaMakeup.model.Produto;
import com.ifsp.projetoLojaMakeup.service.ProdutoService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CarrinhoController {
    @Autowired
    private ProdutoService produtoService;

    @PostMapping("/carrinho/adicionar/{id}")
    public String adicionarAoCarrinho(
            @PathVariable Long id,
            @RequestParam Integer quantidade,
            HttpSession sessao){

        Produto produto =
                produtoService.buscarPorId(id);

        List<ItemCarrinho> carrinho =
                (List<ItemCarrinho>)
                sessao.getAttribute("carrinho");

        if(carrinho == null){

            carrinho = new ArrayList<>();
        }

        boolean produtoExiste = false;

        for(ItemCarrinho item : carrinho){

            if(item.getProduto().getId().equals(id)){

                item.setQuantidade(
                        item.getQuantidade() + quantidade
                );

                produtoExiste = true;

                break;
            }
        }

        if(!produtoExiste){

            ItemCarrinho item =
                    new ItemCarrinho();

            item.setProduto(produto);

            item.setQuantidade(quantidade);

            carrinho.add(item);
        }

        sessao.setAttribute(
                "carrinho",
                carrinho
        );

        System.out.println(carrinho.size());
        return "redirect:/produto/" + id;
    }

    @GetMapping("/carrinho")
    public String verCarrinho(
            HttpSession sessao,
            Model model){

        List<ItemCarrinho> carrinho =
                (List<ItemCarrinho>)
                sessao.getAttribute("carrinho");

        if(carrinho == null){

            carrinho = new ArrayList<>();
        }

        model.addAttribute(
                "carrinho",
                carrinho
        );
        double total = 0;

        for(ItemCarrinho item : carrinho){

            total +=
                item.getProduto().getPreco()
                *
                item.getQuantidade();
        }

        model.addAttribute(
            "total",
            total
        );

        return "carrinho";
    }

    @GetMapping("/carrinho/remover/{id}")
    public String removerProduto(
            @PathVariable Long id,
            HttpSession sessao){

        List<ItemCarrinho> carrinho =
                (List<ItemCarrinho>)
                sessao.getAttribute("carrinho");

        if(carrinho != null){

            carrinho.removeIf(
                item ->
                    item.getProduto()
                        .getId()
                        .equals(id)
            );
        }

        return "redirect:/carrinho";
    }
}
