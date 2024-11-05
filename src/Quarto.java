public class Quarto implements Reservavel, Gerenciavel {
    private int numero;
    private TipoQuarto tipo;
    private Status status;
    private double precoDiaria;
    private int capacidade;

    public Quarto(int numero, TipoQuarto tipo, Status precoDiaria) {
        this.numero = numero;
        this.tipo = tipo;
        this.status = Status.DISPONIVEL;
        this.precoDiaria = precoDiaria.ordinal();
        this.capacidade = tipo.getCapacidade();
    }

    @Override
    public boolean isDisponivel() {
        return status == Status.DISPONIVEL;
    }

    @Override
    public void reservar() throws ReservaException {
        if (!isDisponivel()) {
            throw new ReservaException("Quarto não está disponível");
        }
        this.status = Status.OCUPADO;
    }

    @Override
    public void liberar() throws ReservaException {
        if (status != Status.OCUPADO) {
            throw new ReservaException("Quarto não está ocupado");
        }
        this.status = Status.DISPONIVEL;
    }

    public int getNumero() {
        return numero;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public TipoQuarto getTipo() {
        return tipo;
    }

    public double getPrecoDiaria() {
        return precoDiaria;
    }

    @Override
    public void cadastrar() throws HotelException {

    }

    @Override
    public void atualizar() throws HotelException {

    }

    @Override
    public void excluir() throws HotelException {

    }
}
