package paciente.modelo;

/**
 *
 * @author Diogo
 */
public class PacPlanosSaude extends Paciente{

    private int idPlanoSaude;
    private String nomePlano;
    private String dtValidade;//trocar para data
    private String numBeneficio;

    public PacPlanosSaude() {
        this.idPlanoSaude = 0;
        this.nomePlano = null;
        this.dtValidade = null;
        this.numBeneficio = null;
    }

    public int getIdPlanoSaude() {
        return idPlanoSaude;
    }

    public void setIdPlanoSaude(int idPlanoSaude) {
        this.idPlanoSaude = idPlanoSaude;
    }

    public String getNomePlano() {
        return nomePlano;
    }

    public void setNomePlano(String nomePlano) {
        this.nomePlano = nomePlano;
    }

    public String getDtValidade() {
        return dtValidade;
    }

    public void setDtValidade(String dtValidade) {
        this.dtValidade = dtValidade;
    }

    public String getNumBeneficio() {
        return numBeneficio;
    }

    public void setNumBeneficio(String numBeneficio) {
        this.numBeneficio = numBeneficio;
    }

    
    
}
