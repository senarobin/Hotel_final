public abstract class Pessoas implements Pessoa {
    protected String nome;
    protected String cpf;
    protected String endereco;
    protected String telefone;

    public Pessoas(String nome, String cpf, String endereco, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.telefone = telefone;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public String getCpf() {
        return cpf;
    }

    @Override
    public void validarCPF() throws ValidacaoException {
        if (cpf == null || cpf.length() != 11) {
            throw new ValidacaoException("Cpf inválido");
        }
    }

    @Override
    public boolean validar() throws ValidacaoException {
        if (nome == null || nome.isEmpty()) {
            throw new ValidacaoException("Nome não pode ser vazio");
        }
        if (endereco == null || endereco.isEmpty()) {
            throw new ValidacaoException("Endereço não pode ser vazio");
        }
        if (telefone == null || telefone.isEmpty()) {
            throw new ValidacaoException("Telefone não pode ser vazio");
        }
        validarCPF();
        return true;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}

