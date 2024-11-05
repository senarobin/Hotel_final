public interface Pessoa {
    String getNome();
    String getCpf();
    void validarCPF() throws ValidacaoException;
    boolean validar() throws ValidacaoException;
}