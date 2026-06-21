package com.ifsp.projetoLojaMakeup.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.ifsp.projetoLojaMakeup.model.Produto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

 @Repository
public class ProdutoRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Produto produto){
        String sql = "INSERT INTO produtos (titulo, descricao, preco, img) VALUES (:titulo, :descricao, :preco, :img)";
        Query query = em.createNativeQuery(sql);
        query.setParameter("titulo", produto.getTitulo());
        query.setParameter("descricao", produto.getDescricao());
        query.setParameter("preco", produto.getPreco());
        query.setParameter("img", produto.getImg());
        query.executeUpdate();
    }

    @Transactional
    public List<Produto> findAll() {
    String sql = "SELECT * FROM produtos";
    Query query = em.createNativeQuery(sql, Produto.class);
    List<Produto> produtos = query.getResultList();
    return produtos;
    }
    
    @Transactional
    public Produto findById(Long id){
        String sql = "SELECT * FROM produtos WHERE id = :id";
        Query q = em.createNativeQuery(sql, Produto.class);
        q.setParameter("id", id);
        Produto produto = (Produto) q.getSingleResult();
        return produto;
    }

    @Transactional
    public void update(Produto produto){
        String sql = "UPDATE produtos SET titulo = :titulo, descricao = :descricao, preco = :preco, img = :img WHERE id = :id";
        Query query =em.createNativeQuery(sql);
        query.setParameter("id", produto.getId());
        query.setParameter("titulo",produto.getTitulo());
        query.setParameter("descricao",produto.getDescricao());
        query.setParameter("preco", produto.getPreco());
        query.setParameter("img",produto.getImg());
        query.executeUpdate();
    }

    @Transactional
    public void delete(long id){
        String sql = "DELETE FROM produtos WHERE id = :id";
        Query query = em.createNativeQuery(sql);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Transactional
    public List<Produto> buscarPorTitulo(String titulo){

        String sql = "SELECT * FROM produtos WHERE titulo LIKE :titulo";

        Query query = em.createNativeQuery(sql, Produto.class);

        query.setParameter("titulo", "%" + titulo + "%");

        List<Produto> produtos = query.getResultList();

        return produtos;
    }
}
