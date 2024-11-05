import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Funcionario extends Pessoas implements Gerenciavel {
    private Cargo cargo;
    private double salarioBase;
    private TurnoTrabalho turno;
    private List<RegistroTrabalho> registroTrabalho;

    public Funcionario(String nome, String cpf, Cargo cargo, double salarioBase, TurnoTrabalho turno) {
        super(nome, cpf);
        this.cargo = cargo;
        this.salarioBase = salarioBase;
        this.turno = turno;
        this.registroTrabalho = new ArrayList<>();
    }



    public void registrarHorasTrabalhadas(LocalDateTime entrada, LocalDateTime saida) {
        registroTrabalho.add(new RegistroTrabalho(entrada, saida));
    }

    public void adicionarRegistroTrabalho(RegistroTrabalho registro) {
        registroTrabalho.add(registro);
    }

    public double calcularSalario(int mes, int ano) {
        double salarioCalculado = salarioBase;
        for (RegistroTrabalho registro : registroTrabalho) {
            salarioCalculado += registro.getDuracaoTrabalho().toHours() * 10;
        }
        return salarioCalculado;
    }

    // Métodos de cadastro, atualização e exclusão (não implementados aqui)
    @Override
    public void cadastrar() throws HotelException {
        // Lógica para cadastrar funcionário
    }

    @Override
    public void atualizar() throws HotelException {
        // Lógica para atualizar dados do funcionário
    }

    @Override
    public void excluir() throws HotelException {
        // Lógica para excluir o funcionário
    }

    // Getters e Setters para cargo e nome
    public Cargo getCargo() {
        return this.cargo;
    }

    public void setCargo(Cargo novoCargo) {
        this.cargo = novoCargo;
    }

    @Override
    public void setNome(String novoNome) {
        super.setNome(novoNome);
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(double salarioBase) {
        this.salarioBase = salarioBase;
    }

    public TurnoTrabalho getTurno() {
        return turno;
    }

    public void setTurno(TurnoTrabalho turno) {
        this.turno = turno;
    }
}
