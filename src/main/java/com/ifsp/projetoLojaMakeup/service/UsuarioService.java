package com.ifsp.projetoLojaMakeup.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifsp.projetoLojaMakeup.model.Usuario;
import com.ifsp.projetoLojaMakeup.repository.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public void salvar(Usuario usuario){
        usuarioRepository.save(usuario);
    }

    public List<Usuario> listarTodos(){
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id){
        return usuarioRepository.findById(id);
    }

    public void atualizar(Usuario usuario){
        usuarioRepository.atualizar(usuario);
    }

    public void deletar(Long id){
        usuarioRepository.deletarUsuario(id);
    }

    public Usuario buscarPorEmail(String email){
        return usuarioRepository.findByEmail(email);
    }
}
