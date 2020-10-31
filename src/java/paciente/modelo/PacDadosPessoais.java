package paciente.modelo;

/**
 *
 * @author Diogo
 */
public class PacDadosPessoais extends Paciente {

    private String nome;
    private String nomeMae;
    private String dataNascimento;//pesquisar nova classe para data
    private String sexo;
    private String logradouro;
    private String complemento;
    private String estado;
    private String cidade;
    private String bairro;
    private String situacao;
    private String cpf;
    private String telefone;

    /**
     *
     */
    public PacDadosPessoais() {
        this.id_paciente = 0;
        this.nome = null;
        this.nomeMae = null;
        this.dataNascimento = null;
        this.sexo = null;
        this.logradouro = null;
        this.complemento = null;
        this.estado = null;
        this.cidade = null;
        this.bairro = null;
        this.situacao = null;
        this.cpf = null;
        this.telefone = null;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

}
