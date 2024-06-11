import controller.AlunoController;
import controller.TurmaController;
import model.AlunoModel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculoMediaTest {

    @Test
    public void testCalcularMediaSimples() throws Exception {
        AlunoController alunoController = new AlunoController();
        TurmaController turmaController = new TurmaController();

        AlunoModel novoAluno = alunoController.criarAluno("Ingrid Coelho");
        AlunoModel ingrid = alunoController.buscarAlunosPorId(novoAluno.getIdAluno());

        turmaController.criarTurma("Qualidade de Software");
        turmaController.adicionarAlunoNaTurma("Qualidade de Software", ingrid);

        float[] notas = { 8f, 8f, 7f };
        float media = (notas[0] + notas[1] + notas[2]) / notas.length;
        alunoController.adicionarNotaAoAluno(ingrid.getIdAluno(), notas[0]);
        alunoController.adicionarNotaAoAluno(ingrid.getIdAluno(), notas[1]);
        alunoController.adicionarNotaAoAluno(ingrid.getIdAluno(), notas[2]);

        ingrid = alunoController.buscarAlunosPorId(ingrid.getIdAluno());
        float mediaIngrid = ingrid.getMedia();

        System.out.println("ID do Aluno: " + ingrid.getIdAluno());
        System.out.println("Nome do Aluno: " + ingrid.getNome());
        System.out.println("Notas do Aluno: " + ingrid.getListaNotas());
        System.out.println("Média do Aluno: " + mediaIngrid);

        assertEquals(media, mediaIngrid, "A média calculada não está correta.");
    }
    @Test
    public void testCalcularMediaNovaNota() throws Exception {
        AlunoController alunoController = new AlunoController();
        TurmaController turmaController = new TurmaController();

        AlunoModel novoAluno =alunoController.criarAluno("Thiago Amaral");
        AlunoModel thiago = alunoController.buscarAlunosPorId(novoAluno.getIdAluno());

        //turmaController.criarTurma("Qualidade de Software");
        turmaController.adicionarAlunoNaTurma("Qualidade de Software", thiago);

        float[] notas = { 5f, 5f, 5f, 4f };
        float media = (notas[0] + notas[1] + notas[2]) / thiago.getNUMAXNOTAS();
        alunoController.adicionarNotaAoAluno(thiago.getIdAluno(), notas[0]);
        alunoController.adicionarNotaAoAluno(thiago.getIdAluno(), notas[1]);
        alunoController.adicionarNotaAoAluno(thiago.getIdAluno(), notas[2]);
        alunoController.adicionarNotaAoAluno(thiago.getIdAluno(), notas[3]);

        thiago = alunoController.buscarAlunosPorId(thiago.getIdAluno());
        float mediaThiago = thiago.getMedia();

        System.out.println("ID do Aluno: " + thiago.getIdAluno());
        System.out.println("Nome do Aluno: " + thiago.getNome());
        System.out.println("Notas do Aluno: " + thiago.getListaNotas());
        System.out.println("Média do Aluno: " + mediaThiago);

        assertEquals(media, mediaThiago, "A média calculada não está correta.");
    }
    @Test
    public void testInserirNotasForaDoPadrao() throws Exception {
        AlunoController alunoController = new AlunoController();
        TurmaController turmaController = new TurmaController();

        AlunoModel novoAluno = alunoController.criarAluno("Renef Silva");
        AlunoModel renef = alunoController.buscarAlunosPorId(novoAluno.getIdAluno());

        turmaController.adicionarAlunoNaTurma("Qualidade de Software", renef);

        alunoController.adicionarNotaAoAluno(renef.getIdAluno(), 7);
        alunoController.adicionarNotaAoAluno(renef.getIdAluno(), 8f);
        alunoController.adicionarNotaAoAluno(renef.getIdAluno(), -6f);
        alunoController.adicionarNotaAoAluno(renef.getIdAluno(), 6f);

        renef = alunoController.buscarAlunos("Renef Silva").getFirst();
        System.out.println("Notas do Aluno: " + renef.getListaNotas());
        System.out.println("Média do Aluno: " + renef.getMedia());

        assertEquals(7, renef.getMedia(), "A média deve ser 7.0.");
    }
}
