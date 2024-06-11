package model;

import java.util.List;

public class TurmaModel {
    private static final int MAX_ALUNOS = 5;
    private int idTurma;
    private String nome;
    private int qtdAlunos;
    private float mediaTurma;
    private List<AlunoModel> alunos;

    public TurmaModel(int idTurma, String nome, int qtdAlunos, float mediaTurma, List<AlunoModel> alunos) {
        this.idTurma = idTurma;
        this.nome = nome;
        this.qtdAlunos = qtdAlunos;
        this.mediaTurma = mediaTurma;
        this.alunos = alunos;
    }

    public TurmaModel() {}

    public int getIdTurma() {
        return idTurma;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQtdAlunos() {
        return qtdAlunos;
    }

    public float getMediaTurma() {
        return mediaTurma;
    }

    public List<AlunoModel> getAlunos() {
        return alunos;
    }

    public float calcularMediaTurma() {
        float soma = 0;
        for (AlunoModel aluno : alunos) {
            soma += aluno.getMedia();
        }
        return soma / alunos.size();
    }

    public void adicionarAluno(AlunoModel aluno) throws Exception {
        if (qtdAlunos >= MAX_ALUNOS) {
            throw new Exception("A turma já possui o número máximo de alunos.");
        }
        alunos.add(aluno);
        qtdAlunos++;
        mediaTurma = calcularMediaTurma();
    }

    public void removerAluno(int idAluno) {
        alunos.removeIf(aluno -> aluno.getIdAluno() == idAluno);
        qtdAlunos--;
        mediaTurma = calcularMediaTurma();
    }
}
