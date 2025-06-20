package com.aluraapi.literatura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    private Long id;
    private String titulo;
    private String idioma;
    private Integer descargas;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getIdioma() {
        return idioma;
    }
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
    public Integer getDescargas() {
        return descargas;
    }
    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }
    public Autor getAutor() {
        return autor;
    }
    public void setAutor(Autor autor) {
        this.autor = autor;
    }
    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", idioma='" + idioma + '\'' +
                ", descargas=" + descargas +
                ", autor=" + autor +
                '}';
    }
}