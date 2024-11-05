import java.time.Duration;
import java.time.LocalDateTime;

public class RegistroTrabalho {
    private LocalDateTime entrada;
    private LocalDateTime saida;

    public RegistroTrabalho(LocalDateTime entrada, LocalDateTime saida) {
        this.entrada = entrada;
        this.saida = saida;
    }

    public Duration getDuracaoTrabalho() {
        if (entrada != null && saida != null) {
            return Duration.between(entrada, saida);
        }
        return Duration.ZERO;
    }

    public LocalDateTime getEntrada() {
        return entrada;
    }

    public LocalDateTime getSaida() {
        return saida;
    }
}
