import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Hospede extends Pessoas implements Gerenciavel {
    private LocalDate dataNascimento;
    private List<Reserva> historicoReserva;

    public Hospede(String nome, String cpf, String endereco, String telefone, LocalDate dataNascimento) {
        super(nome, cpf);
        this.dataNascimento = dataNascimento;
        this.historicoReserva = new ArrayList<>();
    }

    public Hospede(String nome, String cpf) {
        super(nome, cpf);
        this.dataNascimento = LocalDate.now();
        this.historicoReserva = new ArrayList<>();
    }

    public void adicionarReserva(Reserva reserva) {
        historicoReserva.add(reserva);
    }

    @Override
    public void cadastrar() throws HotelException {
        GerenciadorHotel.getInstance().cadastrarHospede(this);
    }

    @Override
    public void atualizar() throws HotelException {
    }

    @Override
    public void excluir() throws HotelException {
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public List<Reserva> getHistoricoReserva() {
        return historicoReserva;
    }

    public void setHistoricoReserva(List<Reserva> historicoReserva) {
        this.historicoReserva = historicoReserva;
    }

    @Override
    public void setNome(String novoNome) {
        super.setNome(novoNome);
    }
}
