import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static GerenciadorHotel gerenciadorHotel = new GerenciadorHotel();
    private static Scanner scanner = new Scanner(System.in);
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) throws HotelException {
        boolean continuar = true;

        while (continuar) {
            mostrarMenu();
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarQuarto();
                    break;
                case 2:
                    cadastrarHospede();
                    break;
                case 3:
                    cadastrarFuncionario();
                    break;
                case 4:
                    realizarReserva();
                    break;
                case 5:
                    realizarCheckIn();
                    break;
                case 6:
                    realizarCheckOut();
                    break;
                case 7:
                    registrarPontoFuncionario();
                    break;
                case 8:
                    calcularSalarioFuncionario();
                    break;
                case 9:
                    mostrarRelatorioOcupacao();
                    break;
                case 10:
                    mostrarRelatorioReceita();
                    break;
                case 11:
                    verificarDisponibilidadeQuartos();
                    break;
                case 12:
                    atualizarReserva();
                    break;
                case 13:
                    atualizarFuncionario();
                    break;
                case 14:
                    excluirFuncionario();
                    break;
                case 15:
                    excluirQuarto();
                    break;
                case 16:
                    listarReservasAtivas();
                    break;
                case 17:
                    gerarRelatorioFuncionarios();
                    break;
                case 18:
                    atualizarStatusQuarto();
                    break;
                case 19:
                    listarQuartosPorStatus();
                    break;
                case 20:
                    atualizarHospede();
                    break;
                case 21:
                    cancelarReserva();
                    break;
                case 22:
                    System.out.println("Saindo...");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("====================================");
        System.out.println("MENU DO GERENCIADOR DE HOTEL");
        System.out.println("1. Cadastrar Quarto");
        System.out.println("2. Cadastrar Hóspede");
        System.out.println("3. Cadastrar Funcionário");
        System.out.println("4. Realizar Reserva");
        System.out.println("5. Realizar Check-in");
        System.out.println("6. Realizar Check-out");
        System.out.println("7. Registrar Ponto de Funcionário");
        System.out.println("8. Calcular Salário de Funcionário");
        System.out.println("9. Relatório de Ocupação");
        System.out.println("10. Relatório de Receita");
        System.out.println("11. Verificar Disponibilidade de Quartos");
        System.out.println("12. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void cadastrarQuarto() {
        try {
            System.out.print("Digite o número do quarto: ");
            int numero = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Digite o tipo do quarto (SOLTEIRO, CASAL, SUITE): ");
            TipoQuarto tipo = TipoQuarto.valueOf(scanner.nextLine().toUpperCase());
            System.out.print("Digite o status do quarto (DISPONIVEL, OCUPADO, MANUTENCAO): ");
            Status status = Status.valueOf(scanner.nextLine().toUpperCase());

            Quarto quarto = new Quarto(numero, tipo, status);
            gerenciadorHotel.cadastrarQuarto(quarto);
            System.out.println("Quarto cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar quarto: " + e.getMessage());
        }
    }

    private static void cadastrarHospede() {
        try {
            System.out.print("Digite o nome do hóspede: ");
            String nome = scanner.nextLine();
            System.out.print("Digite o CPF do hóspede: ");
            String cpf = scanner.nextLine();

            Hospede hospede = new Hospede(nome, cpf);
            gerenciadorHotel.cadastrarHospede(hospede);
            System.out.println("Hóspede cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar hóspede: " + e.getMessage());
        }
    }

    private static void cadastrarFuncionario() {
        try {
            System.out.print("Digite o nome do funcionário: ");
            String nome = scanner.nextLine();

            System.out.print("Digite o CPF do funcionário: ");
            String cpf = scanner.nextLine();

            System.out.print("Digite o cargo (RECEPCIONISTA, CAMAREIRA, GERENTE, MANUTENCAO): ");
            Cargo cargo = Cargo.valueOf(scanner.nextLine().toUpperCase());

            System.out.print("Digite o salário base do funcionário: ");
            double salarioBase = Double.parseDouble(scanner.nextLine());

            System.out.print("Digite o turno (MANHA, TARDE, NOITE): ");
            TurnoTrabalho turno = TurnoTrabalho.valueOf(scanner.nextLine().toUpperCase());

            Funcionario funcionario = new Funcionario(nome, cpf, cargo, salarioBase, turno);

            gerenciadorHotel.cadastrarFuncionario(funcionario);
            System.out.println("Funcionário cadastrado com sucesso!");

        } catch (IllegalArgumentException e) {
            System.out.println("Erro: valor inválido para cargo ou turno. Por favor, insira valores válidos.");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar funcionário: " + e.getMessage());
        }
    }


    private static void realizarReserva() {
        try {
            System.out.print("Digite o CPF do hóspede: ");
            String cpfHospede = scanner.nextLine();
            Hospede hospede = gerenciadorHotel.buscarHospedePorCpf(cpfHospede);

            System.out.print("Digite o número do quarto: ");
            int numeroQuarto = scanner.nextInt();
            scanner.nextLine();
            Quarto quarto = gerenciadorHotel.buscarQuartoPorNumero(numeroQuarto);

            System.out.print("Digite a data de entrada (dd/MM/yyyy): ");
            LocalDate dataEntrada = LocalDate.parse(scanner.nextLine(), formatter);
            System.out.print("Digite a data de saída (dd/MM/yyyy): ");
            LocalDate dataSaida = LocalDate.parse(scanner.nextLine(), formatter);

            gerenciadorHotel.realizarReserva(hospede, quarto, dataEntrada, dataSaida);
            System.out.println("Reserva realizada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao realizar reserva: " + e.getMessage());
        }
    }

    private static void realizarCheckIn() {
        try {
            System.out.print("Digite o ID da reserva: ");
            String idReserva = scanner.nextLine();
            gerenciadorHotel.realizarCheckIn(idReserva);
            System.out.println("Check-in realizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao realizar check-in: " + e.getMessage());
        }
    }

    private static void realizarCheckOut() {
        try {
            System.out.print("Digite o ID da reserva: ");
            String idReserva = scanner.nextLine();
            double valorTotal = gerenciadorHotel.realizarCheckOut(idReserva);
            System.out.println("Check-out realizado com sucesso! Valor total: R$ " + valorTotal);
        } catch (Exception e) {
            System.out.println("Erro ao realizar check-out: " + e.getMessage());
        }
    }

    private static void registrarPontoFuncionario() {
        try {
            System.out.print("Digite o CPF do funcionário: ");
            String cpfFuncionario = scanner.nextLine();
            Funcionario funcionario = gerenciadorHotel.buscarFuncionarioPorCpf(cpfFuncionario);

            System.out.print("Digite a data e hora de entrada (dd/MM/yyyy HH:mm): ");
            LocalDateTime entrada = LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            System.out.print("Digite a data e hora de saída (dd/MM/yyyy HH:mm): ");
            LocalDateTime saida = LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

            gerenciadorHotel.registrarPontoFuncionario(cpfFuncionario, entrada, saida);
            System.out.println("Ponto registrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao registrar ponto: " + e.getMessage());
        }
    }

    private static void calcularSalarioFuncionario() {
        try {
            System.out.print("Digite o CPF do funcionário: ");
            String cpfFuncionario = scanner.nextLine();
            System.out.print("Digite o mês (MM): ");
            int mes = scanner.nextInt();
            System.out.print("Digite o ano (YYYY): ");
            int ano = scanner.nextInt();

            double salario = gerenciadorHotel.calcularSalarioFuncionario(cpfFuncionario, mes, ano);
            System.out.println("Salário calculado: R$ " + salario);
        } catch (Exception e) {
            System.out.println("Erro ao calcular salário: " + e.getMessage());
        }
    }

    private static void mostrarRelatorioOcupacao() {
        try {
            System.out.print("Digite a data de início (dd/MM/yyyy): ");
            LocalDate dataInicio = LocalDate.parse(scanner.nextLine(), formatter);
            System.out.print("Digite a data de fim (dd/MM/yyyy): ");
            LocalDate dataFim = LocalDate.parse(scanner.nextLine(), formatter);

            double taxaOcupacao = gerenciadorHotel.calcularTaxaOcupacao(dataInicio, dataFim);
            System.out.println("Taxa de ocupação: " + taxaOcupacao + "%");
        } catch (Exception e) {
            System.out.println("Erro ao gerar relatório de ocupação: " + e.getMessage());
        }
    }

    private static void mostrarRelatorioReceita() {
        try {
            System.out.print("Digite a data de início (dd/MM/yyyy): ");
            LocalDate dataInicio = LocalDate.parse(scanner.nextLine(), formatter);
            System.out.print("Digite a data de fim (dd/MM/yyyy): ");
            LocalDate dataFim = LocalDate.parse(scanner.nextLine(), formatter);

            double receita = gerenciadorHotel.calcularReceita(dataInicio, dataFim);
            System.out.println("Receita gerada: R$ " + receita);
        } catch (Exception e) {
            System.out.println("Erro ao gerar relatório de receita: " + e.getMessage());
        }
    }

    private static void verificarDisponibilidadeQuartos() {
        try {
            System.out.print("Digite a data de entrada (dd/MM/yyyy): ");
            LocalDate dataEntrada = LocalDate.parse(scanner.nextLine(), formatter);
            System.out.print("Digite a data de saída (dd/MM/yyyy): ");
            LocalDate dataSaida = LocalDate.parse(scanner.nextLine(), formatter);

            List<Quarto> quartosDisponiveis = gerenciadorHotel.buscarQuartosDisponiveis(dataEntrada, dataSaida);
            System.out.println("Quartos disponíveis: ");
            quartosDisponiveis.forEach(q -> System.out.println("Número do Quarto: " + q.getNumero()));
        } catch (Exception e) {
            System.out.println("Erro ao verificar disponibilidade de quartos: " + e.getMessage());
        }
    }

    public static void atualizarReserva() throws HotelException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o ID da reserva que deseja atualizar:");
        String idReserva = scanner.nextLine();

        System.out.println("Digite a nova data de entrada (formato YYYY-MM-DD):");
        String dataEntradaStr = scanner.nextLine();
        LocalDate novaDataEntrada = LocalDate.parse(dataEntradaStr);

        System.out.println("Digite a nova data de saída (formato YYYY-MM-DD):");
        String dataSaidaStr = scanner.nextLine();
        LocalDate novaDataSaida = LocalDate.parse(dataSaidaStr);

        System.out.println("Digite o número do novo quarto:");
        int numeroQuarto = scanner.nextInt();

        Quarto novoQuarto = gerenciadorHotel.buscarQuartoPorNumero(numeroQuarto);

        if (novoQuarto != null) {
            try {
                gerenciadorHotel.atualizarReserva(idReserva, novaDataEntrada, novaDataSaida, novoQuarto);
            } catch (HotelException e) {
                System.out.println("Erro ao atualizar a reserva: " + e.getMessage());
            }
        } else {
            System.out.println("Quarto com número " + numeroQuarto + " não encontrado!");
        }
    }

    private static boolean existeReservaNoPeriodo(Quarto novoQuarto, LocalDate novaDataEntrada, LocalDate novaDataSaida) {
        for (Reserva reserva : gerenciadorHotel.getReservas()) {
            if (reserva.getQuarto().equals(novoQuarto) &&
                    (novaDataEntrada.isBefore(reserva.getDataSaida()) && novaDataSaida.isAfter(reserva.getDataEntrada()))) {
                return true;
            }
        }
            return false;
        }

    private static Reserva buscarReservaPorId(String idReserva) throws HotelException {
        for (Reserva reserva : gerenciadorHotel.getReservas()) {
            if (reserva.getId().equals(idReserva)) {
                return reserva;
            }
        }
        throw new HotelException("Reserva com ID " + idReserva + " não encontrada.");
    }


        public static void atualizarFuncionario() {
        System.out.println("Digite o CPF do funcionário que deseja atualizar:");
        String cpf = scanner.nextLine();

        System.out.println("Digite o novo nome do funcionário:");
        String novoNome = scanner.nextLine();

        System.out.println("Digite o novo cargo do funcionário:");
        String novoCargoString = scanner.nextLine();
        Cargo novoCargo = Cargo.valueOf(novoCargoString.toUpperCase());

        try {
            gerenciadorHotel.atualizarFuncionario(cpf, novoNome, novoCargo);
        } catch (HotelException e) {
            System.out.println("Erro ao atualizar o funcionário: " + e.getMessage());
        }
    }

    public static void gerarRelatorioFuncionarios() {
        System.out.println("Relatório de Funcionários:");
        Funcionario[] funcionarios = new Funcionario[0];
        for (Funcionario f : funcionarios) {
            System.out.println(f.getNome() + " - Cargo: " + f.getCargo() + " - CPF: " + f.getCpf());
        }
    }

    public static void excluirQuarto() throws HotelException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Informe o número do quarto a ser excluído: ");
        int numeroQuarto = scanner.nextInt();

        Quarto quartoExcluir = gerenciadorHotel.buscarQuartoPorNumero(numeroQuarto);

        if (quartoExcluir == null) {
            System.out.println("Quarto " + numeroQuarto + " não encontrado.");
        } else {
            gerenciadorHotel.getQuartos().remove(quartoExcluir);
            System.out.println("Quarto " + numeroQuarto + " excluído com sucesso!");
        }
    }

    private static void atualizarStatusQuarto() {
        try {
            System.out.print("Digite o número do quarto: ");
            int numeroQuarto = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Digite o novo status (DISPONIVEL, OCUPADO, MANUTENCAO): ");
            Status status = Status.valueOf(scanner.nextLine().toUpperCase());

            gerenciadorHotel.atualizarStatusQuarto(numeroQuarto, status);
            System.out.println("Status do quarto atualizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar o status do quarto: " + e.getMessage());
        }
    }

    private static void listarQuartosPorStatus() {
        try {
            System.out.print("Digite o status do quarto (DISPONIVEL, OCUPADO, MANUTENCAO): ");
            Status status = Status.valueOf(scanner.nextLine().toUpperCase());
            List<Quarto> quartos = gerenciadorHotel.listarQuartosPorStatus(status);
            System.out.println("Quartos com status " + status + ":");
            for (Quarto quarto : quartos) {
            System.out.println("Número do Quarto: " + quarto.getNumero());
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar quartos: " + e.getMessage());
        }
    }

    private static void atualizarHospede() {
        try {
            System.out.print("Digite o CPF do hóspede: ");
            String cpf = scanner.nextLine();
            Hospede hospede = gerenciadorHotel.buscarHospedePorCpf(cpf);
            System.out.print("Digite o novo nome do hóspede: ");
            String novoNome = scanner.nextLine();
            hospede.setNome(novoNome);
            gerenciadorHotel.atualizarHospede(hospede);
            System.out.println("Dados do hóspede atualizados com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar hóspede: " + e.getMessage());
        }
    }

    private static void cancelarReserva() {
        try {
            System.out.print("Digite o ID da reserva: ");
            String idReserva = scanner.nextLine();
            gerenciadorHotel.cancelarReserva(idReserva);
            System.out.println("Reserva cancelada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cancelar a reserva: " + e.getMessage());
        }
    }

    private static void excluirFuncionario() {
        try {
            System.out.print("Digite o CPF do funcionário: ");
            String cpf = scanner.nextLine();
            gerenciadorHotel.excluirFuncionario(cpf);
            System.out.println("Funcionário excluído com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao excluir funcionário: " + e.getMessage());
        }
    }

    private static void listarReservasAtivas() {
        try {
            List<Reserva> reservasAtivas = gerenciadorHotel.listarReservasAtivas();
            System.out.println("Reservas Ativas:");
            for (Reserva reserva : reservasAtivas) {
                System.out.println("ID: " + reserva.getId() + " - Quarto: " + reserva.getQuarto().getNumero() + " - Hóspede: " + reserva.getHospede().getNome());
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar reservas ativas: " + e.getMessage());
        }
    }
    }




