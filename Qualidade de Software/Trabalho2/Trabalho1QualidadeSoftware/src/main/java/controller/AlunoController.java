package controller;

import model.AlunoModel;
import model.TurmaModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlunoController {
    private final JsonManager jsonManager;

    public AlunoController() {
        this.jsonManager = new JsonManager();
    }

    public AlunoModel criarAluno(String nome) {
        List<AlunoModel> alunos = jsonManager.carregarDadosAlunos();
        int idAluno = alunos != null ? alunos.size() + 1 : 1;
        AlunoModel novoAluno = new AlunoModel(idAluno, nome);
        alunos.add(novoAluno);
        jsonManager.salvarDadosAlunos(alunos);
        return novoAluno;
    }

    public int excluirAluno(int idAluno) {
        int removed = 0;
        List<AlunoModel> alunos = jsonManager.carregarDadosAlunos();

        if (alunos != null && removerAlunoDaLista(alunos, idAluno)) {
            removed = 1;
            jsonManager.salvarDadosAlunos(alunos);
            atualizarTurmas(idAluno);
        }

        return removed;
    }

    private boolean removerAlunoDaLista(List<AlunoModel> alunos, int idAluno) {
        return alunos.removeIf(aluno -> aluno.getIdAluno() == idAluno);
    }

    private void atualizarTurmas(int idAluno) {
        List<TurmaModel> turmas = jsonManager.carregarDadosTurmas();

        if (turmas != null) {
            for (TurmaModel turma : turmas) {
                turma.removerAluno(idAluno);
            }
            jsonManager.salvarDadosTurmas(turmas);
        }
    }


    public void editarNomeAluno(int idAluno, String novoNome) {
        List<AlunoModel> alunos = jsonManager.carregarDadosAlunos();
        if (alunos != null) {
            for (AlunoModel aluno : alunos) {
                if (aluno.getIdAluno() == idAluno) {
                    aluno.setNome(novoNome);
                    jsonManager.salvarDadosAlunos(alunos);
                    break;
                }
            }
        }
    }

    public void adicionarNotaAoAluno(int idAluno, float nota) {
        List<AlunoModel> alunos = jsonManager.carregarDadosAlunos();
        if (alunos == null) {
            return;
        }

        for (AlunoModel aluno : alunos) {
            if (aluno.getIdAluno() == idAluno) {
                processarNotaAluno(aluno, nota, alunos);
                return;
            }
        }
    }

    private void processarNotaAluno(AlunoModel aluno, float nota, List<AlunoModel> alunos) {
        if (aluno.getListaNotas() == null || aluno.getListaNotas().size() < aluno.getNUMAXNOTAS()) {
            adicionarNota(aluno, nota, alunos);
        } else {
            atualizarNota(aluno, nota, alunos);
        }
    }

    private void adicionarNota(AlunoModel aluno, float nota, List<AlunoModel> alunos) {
        aluno.adicionarNota(nota);
        if (aluno.getListaNotas().size() == aluno.getNUMAXNOTAS()) {
            System.out.println("Nova média calculada: " + aluno.calcularMedia());
        } else {
            System.out.println("Nota adicionada com sucesso.");
        }
        jsonManager.salvarDadosAlunos(alunos);
    }

    private void atualizarNota(AlunoModel aluno, float nota, List<AlunoModel> alunos) {
        float mediaAtual = aluno.calcularMedia();
        if (mediaAtual >= 7) {
            System.out.println("O aluno já possui três notas e sua média é maior que 7.0. Não é possível adicionar mais notas.");
            return;
        }

        float menorNota = Collections.min(aluno.getListaNotas());
        if (nota > menorNota) {
            aluno.getListaNotas().remove(menorNota);
            aluno.getListaNotas().add(nota);
            aluno.setMedia(aluno.calcularMedia());
            jsonManager.salvarDadosAlunos(alunos);
        } else {
            System.out.println("Nota não adicionada.");
        }
    }


    public List<AlunoModel> buscarAlunos(String nome) {
        List<AlunoModel> alunos = jsonManager.carregarDadosAlunos();
        List<AlunoModel> alunosEncontrados = new ArrayList<>();
        if (alunos != null) {
            for (AlunoModel aluno : alunos) {
                if (aluno.getNome().equals(nome)) {
                    alunosEncontrados.add(aluno);
                }
            }
        }
        return alunosEncontrados;
    }
    public AlunoModel buscarAlunosPorId(int idAluno) {
        List<AlunoModel> alunos = jsonManager.carregarDadosAlunos();
        AlunoModel alunoEncontrado = null;
        if (alunos != null) {
            for (AlunoModel aluno : alunos) {
                if (aluno.getIdAluno() == idAluno) {
                    alunoEncontrado = aluno;
                }
            }
        }
        return alunoEncontrado;
    }
}
