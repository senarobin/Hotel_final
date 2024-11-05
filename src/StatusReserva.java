public enum StatusReserva {
    CONFIRMADA,
    CANCELADA,
    EM_ANDAMENTO,
    CONCLUIDA;

    public boolean podeFazerCheckIn() {
        return this == CONFIRMADA;
    }
}
