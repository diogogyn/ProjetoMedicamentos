package paciente.modelo;

/**
 *
 * @author Diogo
 */
public class Paciente {
    
    protected int id_paciente;
    private PacDadosPessoais pacDadosPessoais;
    private PacProntuario pacProntuario;
    private PacPlanosSaude pacPlanosSaude;
    private PacAtencaoContinuada pacAtencaoContinuada;

    public Paciente() {
        
    }

    public int getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(int id_paciente) {
        this.id_paciente = id_paciente;
    }

    public PacDadosPessoais getPacDadosPessoais() {
        return pacDadosPessoais;
    }

    public void setPacDadosPessoais(PacDadosPessoais pacDadosPessoais) {
        this.pacDadosPessoais = pacDadosPessoais;
    }

    public PacProntuario getPacProntuario() {
        return pacProntuario;
    }

    public void setPacProntuario(PacProntuario pacProntuario) {
        this.pacProntuario = pacProntuario;
    }

    public PacPlanosSaude getPacPlanosSaude() {
        return pacPlanosSaude;
    }

    public void setPacPlanosSaude(PacPlanosSaude pacPlanosSaude) {
        this.pacPlanosSaude = pacPlanosSaude;
    }

    public PacAtencaoContinuada getPacAtencaoContinuada() {
        return pacAtencaoContinuada;
    }

    public void setPacAtencaoContinuada(PacAtencaoContinuada pacAtencaoContinuada) {
        this.pacAtencaoContinuada = pacAtencaoContinuada;
    }
    
    
       
}
