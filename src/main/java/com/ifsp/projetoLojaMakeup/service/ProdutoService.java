package com.ifsp.projetoLojaMakeup.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifsp.projetoLojaMakeup.model.Produto;
import com.ifsp.projetoLojaMakeup.model.Produto;
import com.ifsp.projetoLojaMakeup.repository.ProdutoRepository;

@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository produtoRepository;

    public void salvarProduto (Produto produto){
        produtoRepository.save(produto);
    }

     public List<Produto> listarTodos(){
        return produtoRepository.findAll();
    }

    public Produto buscarPorId(Long id){
        return produtoRepository.findById(id);
    }

    public void atualizar(Produto Produto){
        produtoRepository.update(Produto);
    }

    public void deletar(Long id){
        produtoRepository.delete(id);
    }

    public List<Produto> buscarPorTitulo(String titulo){
        List<Produto> produtos = produtoRepository.buscarPorTitulo(titulo);
        return produtos;
    }
}
