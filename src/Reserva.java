import java.time.LocalDate;
import java.util.UUID;

public class Reserva {
    private String id;
    private Hospede hospede;
    private Quarto quarto;
    private LocalDate dataEntrada;
    private LocalDate dataSaida;
    private StatusReserva status;

    public Reserva(Hospede hospede, Quarto quarto, LocalDate dataEntrada, LocalDate dataSaida) {
        this.id = UUID.randomUUID().toString();
        this.hospede = hospede;
        this.quarto = quarto;
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
        this.status = StatusReserva.CONFIRMADA;
    }

    double calcularValorTotal() {
        long dias = java.time.temporal.ChronoUnit.DAYS.between(dataEntrada, dataSaida);
        return dias * quarto.getPrecoDiaria();
    }

    public String getId() {
        return id;
    }

    public StatusReserva getStatus() {
        return status;
    }

    public void setStatus(StatusReserva status) {
        this.status = status;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public void setDataEntrada(LocalDate novaDataEntrada) {
        this.dataEntrada = novaDataEntrada;
    }

    public void setDataSaida(LocalDate novaDataSaida) {
        this.dataSaida = novaDataSaida;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public LocalDate getDataSaida() {
        return dataSaida;
    }

    public Pessoa getHospede() {
        return this.hospede;
    }

    public void setQuarto(Quarto novoQuarto) {
        this.quarto = novoQuarto;

    }

}
