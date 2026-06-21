package com.ifsp.projetoLojaMakeup.repository;

import java.util.List;

import org.springframework.stereotype.Repository;


import com.ifsp.projetoLojaMakeup.model.Usuario;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
public class UsuarioRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Usuario usuario){
        String sql = "INSERT INTO usuarios (nome, email, telefone, senha, perfil) VALUES (:nome, :email, :telefone, :senha, :perfil)";
        Query query = em.createNativeQuery(sql);
        query.setParameter("nome", usuario.getNome());
        query.setParameter("email", usuario.getEmail());
        query.setParameter("telefone", usuario.getTelefone());
        query.setParameter("senha", usuario.getSenha());
        query.setParameter("perfil", usuario.getPerfil());
        query.executeUpdate();
    }

    @Transactional
    public List<Usuario> findAll() {
        String sql = "SELECT * FROM usuarios";
        Query query = em.createNativeQuery(sql, Usuario.class);
        List<Usuario> usuarios = query.getResultList();
        return usuarios;
    }
    
    @Transactional
    public Usuario findById(Long id){
        String sql = "SELECT * FROM usuarios WHERE id = :id";
        Query query = em.createNativeQuery(sql, Usuario.class);
        query.setParameter("id", id);

        Usuario usuario = (Usuario) query.getSingleResult();
        return usuario;
    }

    @Transactional
    public void atualizar(Usuario usuario){

        String sql = "UPDATE usuarios SET nome = :nome, email = :email, telefone = :telefone, senha = :senha, perfil = :perfil WHERE id = :id";
        Query query = em.createNativeQuery(sql);

        query.setParameter("id", usuario.getId());
        query.setParameter("nome", usuario.getNome());
        query.setParameter("email", usuario.getEmail());
        query.setParameter("telefone", usuario.getTelefone());
        query.setParameter("senha", usuario.getSenha());
        query.setParameter("perfil", usuario.getPerfil());

        query.executeUpdate();
    }

    @Transactional
    public void deletarUsuario(Long id){

        String sql = "DELETE FROM usuarios WHERE id = :id";

        Query query = em.createNativeQuery(sql);
        query.setParameter("id", id);

        query.executeUpdate();
    }

    @Transactional
    public Usuario findByEmail(String email){

        String sql = "SELECT * FROM usuarios WHERE email = :email";
        Query query = em.createNativeQuery(sql, Usuario.class);
        query.setParameter("email", email);
        List<Usuario> lista = query.getResultList();

        if(lista.isEmpty()){
            return null;
        }
        return lista.get(0);
    }
}
