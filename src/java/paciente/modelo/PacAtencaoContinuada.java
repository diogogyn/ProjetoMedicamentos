package paciente.modelo;

/**
 *
 * @author Diogo
 */
public class PacAtencaoContinuada extends Paciente {
    private int idAtencContinuada;
    private String nomeAtencao;

    public PacAtencaoContinuada() {
        this.idAtencContinuada = 0;
        this.nomeAtencao = null;
    }

    public int getIdAtencContinuada() {
        return idAtencContinuada;
    }

    public void setIdAtencContinuada(int idAtencContinuada) {
        this.idAtencContinuada = idAtencContinuada;
    }

    public String getNomeAtencao() {
        return nomeAtencao;
    }

    public void setNomeAtencao(String nomeAtencao) {
        this.nomeAtencao = nomeAtencao;
    }
    
    
}
