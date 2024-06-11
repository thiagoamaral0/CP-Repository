package model;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoModel {
    private int idAluno;
    private String nome;
    private List<Float> listaNotas;
    private float media;
    private static final int NUMAXNOTAS = 3;

    public AlunoModel(int idAluno, String nome) {
        this.idAluno = idAluno;
        this.nome = nome;
        this.listaNotas = new ArrayList<>();
        this.media = 0;
    }

    public AlunoModel(int idAluno, String nome, List<Float> listaNotas, float media) {
        this.idAluno = idAluno;
        this.nome = nome;
        this.listaNotas = listaNotas;
        this.media = media;
    }

    public int getIdAluno() {
        return idAluno;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Float> getListaNotas() {
        return listaNotas;
    }

    public float getMedia() {
        return media;
    }

    public void setMedia(float media) {
        this.media = media;
    }

    public int getNUMAXNOTAS() {
        return NUMAXNOTAS;
    }

    public float calcularMedia() {
        float soma = 0;
        for (float nota : listaNotas) {
            soma += nota;
        }
        return soma / NUMAXNOTAS;
    }

    public void adicionarNota(float nota) {
        try {
            if (nota < 0 || nota > 10) {
                throw new IllegalArgumentException("A nota deve estar entre 0 e 10.");
            }
            listaNotas.add(nota);
            media = calcularMedia();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }




}

