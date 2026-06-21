package com.ifsp.projetoLojaMakeup.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="produtos")

public class Produto {

    public Produto(){

    }

    public Produto(String titulo, String descricao, double preco, String img){
        this.titulo = titulo;
        this.descricao = descricao;
        this.preco = preco;
        this.img = img;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    public Long getId() {
    return id;
    }

    public void setId(Long id) {
    this.id = id;
    }

     @Column(name = "titulo")
    private String titulo;
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

     @Column(name = "descricao")
    private String descricao;
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

     @Column(name = "preco")
    private double preco;
    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    @Column(name = "img")
    private String img;
    public String getImg(){
        return img;
    }

    public void setImg(String img){
        this.img = img;
    }
}