package com.ifsp.projetoLojaMakeup.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="usuarios")
public class Usuario {
    
    public Usuario(){

    }

    public Usuario(String nome, String email, String telefone, String senha, String perfil){
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
        this.perfil = perfil;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //id:
    @Column(name = "id")
    private Long id;

    public Long getId() {
    return id;
    }

    public void setId(Long id) {
    this.id = id;
    }
    //nome:
    @Column(name = "nome")
    private String nome;
    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    //email:
    @Column(name = "email")
    private String email;
    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    //telefone:
    @Column(name = "telefone")
    private String telefone;
    public String getTelefone(){
        return telefone;
    }

    public void setTelefone(String telefone){
        this.telefone = telefone;
    }

    //senha:
    @Column(name = "senha")
    private String senha;
    public String getSenha(){
        return senha;
    }

    public void setSenha(String senha){
        this.senha = senha;
    }

    //perfil(adm ou cliene)
    @Column(name = "perfil")
    private String perfil;

    public String getPerfil(){
        return perfil;
    }

    public void setPerfil(String perfil){
        this.perfil = perfil;
    }

}
