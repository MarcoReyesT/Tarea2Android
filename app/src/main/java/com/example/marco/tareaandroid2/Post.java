package com.example.marco.tareaandroid2;

public class Post {

    private int idPost;
    private String titulo;
    private String cuerpo;
    private int idUsuario;

    public Post(int idPost, String titulo, String cuerpo, int idUsuario) {
        this.idPost = idPost;
        this.titulo = titulo;
        this.cuerpo = cuerpo;
        this.idUsuario = idUsuario;
    }

    public int getIdPost() {
        return idPost;
    }

    public void setIdPost(int idPost) {
        this.idPost = idPost;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public String toString() {
        return "Post{" +
                "idPost=" + idPost +
                ", titulo='" + titulo + '\'' +
                ", cuerpo='" + cuerpo + '\'' +
                ", idUsuario=" + idUsuario +
                '}';
    }
}
