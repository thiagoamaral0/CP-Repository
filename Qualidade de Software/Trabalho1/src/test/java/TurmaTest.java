import controller.AlunoController;
import controller.TurmaController;
import model.AlunoModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TurmaTest {
    @Test
    public void testRegistroSalaNomeUnico() {
        TurmaController turmaController = new TurmaController();

        turmaController.criarTurma("Qualidade de Software");

        String mensagemErro = "";
        try {
            turmaController.criarTurma("Qualidade de Software");
        } catch (RuntimeException e) {
            mensagemErro = e.getMessage();
        }

        assertEquals("O nome da sala já existe! Por favor, insira um nome único para a sala.", mensagemErro,
                "A mensagem de erro não corresponde à esperada.");
    }
    @Test
    public void testLimiteDeSala() throws Exception {
        AlunoController alunoController = new AlunoController();
        TurmaController turmaController = new TurmaController();

        turmaController.criarTurma("Qualidade de Software");

        for (int i = 0; i < 5; i++) {
            AlunoModel alunoAluno = alunoController.criarAluno("Aluno " + (i + 1));
            AlunoModel aluno = alunoController.buscarAlunosPorId(alunoAluno.getIdAluno());
            turmaController.adicionarAlunoNaTurma("Qualidade de Software", aluno);
        }

        AlunoModel novoAluno6 = alunoController.criarAluno("Aluno 6");
        AlunoModel aluno6 = alunoController.buscarAlunosPorId(novoAluno6.getIdAluno());

        Exception exception = assertThrows(Exception.class, () -> {
            turmaController.adicionarAlunoNaTurma("Qualidade de Software", aluno6);
        });

        assertEquals("A turma já possui o número máximo de alunos.", exception.getMessage());
    }

}

