public interface Reservavel {
    boolean isDisponivel();
    void reservar() throws ReservaException;
    void liberar() throws ReservaException;
}