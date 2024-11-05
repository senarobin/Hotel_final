public enum TipoQuarto {
    SOLTEIRO(1), CASAL(2), SUITE(4);

    private final int capacidade;

    TipoQuarto(int capacidade) {
        this.capacidade = capacidade;
    }

    public int getCapacidade() {
        return capacidade;
    }
}