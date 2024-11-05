import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GerenciadorHotel {
    private List<Quarto> quartos;
    private List<Hospede> hospedes;
    private List<Funcionario> funcionarios;
    private List<Reserva> reservas;
    private static GerenciadorHotel instance;


    public GerenciadorHotel() {
        this.quartos = new ArrayList<>();
        this.hospedes = new ArrayList<>();
        this.funcionarios = new ArrayList<>();
        this.reservas = new ArrayList<>();
    }

    public void cadastrarQuarto(Quarto quarto) throws HotelException {
        if (quartos.stream().anyMatch(q -> q.getNumero() == quarto.getNumero())) {
            throw new HotelException("Quarto já cadastrado");
        }
        quartos.add(quarto);
    }

    public static GerenciadorHotel getInstance() {
        if (instance == null) {
            instance = new GerenciadorHotel();
        }
        return instance;
    }

    private void validarDatasReserva(LocalDate dataEntrada, LocalDate dataSaida) throws ValidacaoException {
        if (dataEntrada == null || dataSaida == null) {
            throw new ValidacaoException("Datas não podem ser nulas");
        }
        if (dataEntrada.isAfter(dataSaida)) {
            throw new ValidacaoException("Data de entrada deve ser anterior à data de saída");
        }
        if (dataEntrada.isBefore(LocalDate.now())) {
            throw new ValidacaoException("Data de entrada não pode ser anterior à data atual");
        }
    }

    public void atualizarStatusQuarto(int numeroQuarto, Status status) throws HotelException {
        Quarto quarto = buscarQuartoPorNumero(numeroQuarto);
        quarto.setStatus(status);
    }

    public Quarto buscarQuartoPorNumero(int numero) throws HotelException {
        if (quartos == null) {
            throw new HotelException("Lista de quartos não inicializada.");
        }

        return quartos.stream()
                .filter(q -> q.getNumero() == numero)
                .findFirst()
                .orElseThrow(() -> new HotelException("Quarto não encontrado"));
    }


    public List<Quarto> listarQuartosPorStatus(Status status) {
        return quartos.stream()
                .filter(q -> q.getStatus() == status)
                .collect(Collectors.toList());
    }

    public void cadastrarHospede(Hospede hospede) throws HotelException {
        if (!hospede.validar()) {
            throw new HotelException("Dados de hóspede inválidos");
        }
        if (hospedes.stream().anyMatch(h -> h.getCpf().equals(hospede.getCpf()))) {
            throw new HotelException("CPF já cadastrado");
        }
        hospedes.add(hospede);
    }

    public void atualizarHospede(Hospede hospede) throws HotelException {
        if (!hospede.validar()) {
            throw new HotelException("Dados do hóspede inválidos");
        }
        Hospede hospedeExistente = buscarHospedePorCpf(hospede.getCpf());
        int index = hospedes.indexOf(hospedeExistente);
        hospedes.set(index, hospede);
    }

    public Hospede buscarHospedePorCpf(String cpf) throws HotelException {
        return hospedes.stream()
                .filter(h -> h.getCpf().equals(cpf))
                .findFirst()
                .orElseThrow(() -> new HotelException("Hospede não encontrado"));
    }

    public List<Quarto> buscarQuartosDisponiveis(LocalDate dataEntrada, LocalDate dataSaida) {
        return quartos.stream()
                .filter(q -> q.getStatus() == Status.DISPONIVEL)
                .filter(quarto -> !existeReservaNoPeriodo(quarto, dataEntrada, dataSaida))
                .collect(Collectors.toList());
    }

    private boolean existeReservaNoPeriodo(Quarto quarto, LocalDate dataEntrada, LocalDate dataSaida) {
        return reservas.stream()
                .anyMatch(r -> r.getQuarto().equals(quarto) &&
                        !((r.getDataSaida().isBefore(dataEntrada)) ||
                                (r.getDataEntrada().isAfter(dataSaida))));
    }

    public void realizarReserva(Hospede hospede, Quarto quarto, LocalDate dataEntrada, LocalDate dataSaida) throws HotelException {
        validarDatasReserva(dataEntrada, dataSaida);
        if (quarto.getStatus() != Status.DISPONIVEL) {
            throw new HotelException("Quarto não está disponível");
        }
        if (existeReservaNoPeriodo(quarto, dataEntrada, dataSaida)) {
            throw new HotelException("Já existe reserva para este período");
        }
        Reserva reserva = new Reserva(hospede, quarto, dataEntrada, dataSaida);
        reservas.add(reserva);
    }

    public void cancelarReserva(String idReserva) throws HotelException {
        Reserva reserva = buscarReservaPorId(idReserva);
        if (reserva.getStatus() == StatusReserva.CONCLUIDA) {
            throw new HotelException("Não é possível cancelar uma reserva concluída");
        }
        reserva.setStatus(StatusReserva.CANCELADA);
    }

    public void realizarCheckIn(String idReserva) throws HotelException {
        Reserva reserva = buscarReservaPorId(idReserva);
        if (reserva.getStatus() != StatusReserva.CONFIRMADA) {
            throw new HotelException("Reserva não está confirmada");
        }
        if (LocalDate.now().isBefore(reserva.getDataEntrada())) {
            throw new HotelException("Ainda não é possível realizar o check-in");
        }
        reserva.setStatus(StatusReserva.EM_ANDAMENTO);
        reserva.getQuarto().setStatus(Status.OCUPADO);
    }

    public double realizarCheckOut(String idReserva) throws HotelException {
        Reserva reserva = buscarReservaPorId(idReserva);
        if (reserva.getStatus() != StatusReserva.EM_ANDAMENTO) {
            throw new HotelException("Check-out não pode ser realizado");
        }
        reserva.setStatus(StatusReserva.CONCLUIDA);
        reserva.getQuarto().setStatus(Status.DISPONIVEL);
        return reserva.calcularValorTotal();
    }

    public void cadastrarFuncionario(Funcionario funcionario) throws HotelException {
        if (!funcionario.validar()) {
            throw new HotelException("Dados do funcionário inválidos");
        }
        if (funcionarios.stream().anyMatch(f -> f.getCpf().equals(funcionario.getCpf()))) {
            throw new HotelException("CPF já cadastrado");
        }
        funcionarios.add(funcionario);
    }

    public void registrarPontoFuncionario(String cpf, LocalDateTime entrada, LocalDateTime saida)
            throws HotelException {
        Funcionario funcionario = buscarFuncionarioPorCpf(cpf);
        RegistroTrabalho registro = new RegistroTrabalho(entrada, saida);
        funcionario.adicionarRegistroTrabalho(registro);
    }

    public double calcularSalarioFuncionario(String cpf, int mes, int ano) throws HotelException {
        Funcionario funcionario = buscarFuncionarioPorCpf(cpf);
        return funcionario.calcularSalario(mes, ano);
    }

    private Reserva buscarReservaPorId(String id) throws HotelException {
        return reservas.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new HotelException("Reserva não encontrada"));
    }

    Funcionario buscarFuncionarioPorCpf(String cpf) throws HotelException {
        return funcionarios.stream()
                .filter(f -> f.getCpf().equals(cpf))
                .findFirst()
                .orElseThrow(() -> new HotelException("Funcionário não encontrado"));
    }

    public void atualizarReserva(String idReserva, LocalDate novaDataEntrada, LocalDate novaDataSaida, Quarto novoQuarto) throws HotelException {
        Reserva reservaExistente = buscarReservaPorId(idReserva);

        if (reservaExistente.getStatus() != StatusReserva.CONFIRMADA) {
            throw new HotelException("Não é possível alterar uma reserva que não está confirmada");
        }

        if (existeReservaNoPeriodo(novoQuarto, novaDataEntrada, novaDataSaida)) {
            throw new HotelException("Já existe reserva para este quarto neste período");
        }

        reservaExistente.setDataEntrada(novaDataEntrada);
        reservaExistente.setDataSaida(novaDataSaida);
        reservaExistente.setQuarto(novoQuarto);
        System.out.println("Reserva atualizada com sucesso!");
    }

    public void atualizarFuncionario(String cpf, String novoNome, Cargo novoCargo) throws HotelException {
        Funcionario funcionario = buscarFuncionarioPorCpf(cpf);
        funcionario.setNome(novoNome);
        funcionario.setCargo(novoCargo);
        System.out.println("Dados do funcionário atualizados com sucesso!");
    }

    public void excluirFuncionario(String cpf) throws HotelException {
        Funcionario funcionario = buscarFuncionarioPorCpf(cpf);
        funcionarios.remove(funcionario);
    }

    public List<Reserva> listarReservasAtivas() {
        return reservas.stream()
                .filter(r -> r.getStatus() != StatusReserva.CONCLUIDA && r.getStatus() != StatusReserva.CANCELADA)
                .collect(Collectors.toList());
    }

    public List<Quarto> getQuartos() {
        return quartos;
    }


    public double calcularTaxaOcupacao(LocalDate dataInicio, LocalDate dataFim) {
        int quartosOcupados = 0;
        int totalDeQuartos = quartos.size();

        for (Quarto quarto : quartos) {
            for (Reserva reserva : reservas) {
                if (reserva.getQuarto().equals(quarto) &&
                        !(reserva.getDataSaida().isBefore(dataInicio) || reserva.getDataEntrada().isAfter(dataFim))) {
                    quartosOcupados++;
                    break;
                }
            }
        }

        return (totalDeQuartos == 0) ? 0 : (double) quartosOcupados / totalDeQuartos * 100;
    }

    public double calcularReceita(LocalDate dataInicio, LocalDate dataFim) {
        double receitaTotal = 0;

        for (Reserva reserva : reservas) {
            if (!(reserva.getDataSaida().isBefore(dataInicio) || reserva.getDataEntrada().isAfter(dataFim))) {
                receitaTotal += reserva.calcularValorTotal();
            }
        }

        return receitaTotal;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }
}
