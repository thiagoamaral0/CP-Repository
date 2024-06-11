package controller;

import model.AlunoModel;
import model.TurmaModel;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class TurmaController {
    private final JsonManager jsonManager;

    public TurmaController() {
        this.jsonManager = new JsonManager();
    }

    public void criarTurma(String nome) {
        List<TurmaModel> turmas = jsonManager.carregarDadosTurmas();

        for (TurmaModel turma : turmas) {
            if (turma.getNome().equalsIgnoreCase(nome)) {
                throw new RuntimeException("O nome da sala já existe! Por favor, insira um nome único para a sala.");
            }
        }

        int idTurma = turmas != null ? turmas.size() + 1 : 1;
        TurmaModel novaTurma = new TurmaModel(idTurma, nome, 0, 0, new ArrayList<>());
        turmas.add(novaTurma);
        jsonManager.salvarDadosTurmas(turmas);
    }


    public void editarNomeTurma(String nomeAtual, String novoNome) {
        List<TurmaModel> turmas = jsonManager.carregarDadosTurmas();
        if (turmas != null) {
            for (TurmaModel turma : turmas) {
                if (turma.getNome().equalsIgnoreCase(nomeAtual)) {

                    for (TurmaModel outraTurma : turmas) {
                        if (outraTurma.getNome().equalsIgnoreCase(novoNome)) {
                            throw new IllegalArgumentException("Nome existente: " + novoNome);
                        }
                    }
                    turma.setNome(novoNome);
                    jsonManager.salvarDadosTurmas(turmas);
                    return;
                }
            }
        }
        throw new NoSuchElementException("Turma não encontrada: " + nomeAtual);
    }

    public void excluirTurma(String nomeTurma) {
        List<TurmaModel> turmas = jsonManager.carregarDadosTurmas();
        if (turmas != null) {
            String nomeTurmaUppercase = nomeTurma.toUpperCase();

            turmas.removeIf(turma -> turma.getNome().toUpperCase().equals(nomeTurmaUppercase));

            jsonManager.salvarDadosTurmas(turmas);
        }
    }

    public void adicionarAlunoNaTurma(String nomeTurma, AlunoModel aluno) throws Exception {
        List<AlunoModel> alunos = jsonManager.carregarDadosAlunos();
        List<TurmaModel> turmas = jsonManager.carregarDadosTurmas();

        boolean alunoExisteNoJSON = alunos != null && alunos.stream()
                .anyMatch(a -> a.getNome().equalsIgnoreCase(aluno.getNome()));

        if (!alunoExisteNoJSON) {
            throw new Exception("O aluno não está cadastrado.");
        }

        boolean alunoJaNaTurma = turmas != null && turmas.stream()
                .filter(t -> t.getNome().equalsIgnoreCase(nomeTurma))
                .anyMatch(turma -> turma.getAlunos().stream()
                        .anyMatch(a -> a.getNome().equalsIgnoreCase(aluno.getNome())));

        if (alunoJaNaTurma) {
            throw new Exception("O aluno já está presente em uma turma.");
        }

        for (TurmaModel turma : turmas) {
            if (turma.getNome().equalsIgnoreCase(nomeTurma)) {
                turma.adicionarAluno(aluno);
                jsonManager.salvarDadosTurmas(turmas);
                System.out.println("Aluno adicionado à turma com sucesso.");
                return;
            }
        }

        throw new Exception("Turma não encontrada.");
    }



    public void removerAlunoDaTurma(int idTurma, int idAluno) {
        List<TurmaModel> turmas = jsonManager.carregarDadosTurmas();
        if (turmas != null) {
            for (TurmaModel turma : turmas) {
                if (turma.getIdTurma() == idTurma) {
                    turma.removerAluno(idAluno);
                    jsonManager.salvarDadosTurmas(turmas);
                    break;
                }
            }
        }
    }

    public TurmaModel buscarTurma(String nome) {
        List<TurmaModel> turmas = jsonManager.carregarDadosTurmas();
        if (turmas != null) {
            String nomeUpperCase = nome.toUpperCase();
            for (TurmaModel turma : turmas) {
                if (turma.getNome().toUpperCase().equals(nomeUpperCase)) {
                    return turma;
                }
            }
        }
        return null;
    }
}
