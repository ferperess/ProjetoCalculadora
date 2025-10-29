public class Calculadora {

    public double soma(double v1, double v2) {
        return v1 + v2;
    }

    public double subtracao(double v1, double v2) {
        return v1 - v2;
    }

    public double multiplicacao(double v1, double v2) {
        return v1 * v2;
    }

    public double divisao(double v1, double v2) throws ArithmeticException {
        if (v2 == 0.0) {
            // Lança a exceção conforme a especificação
            throw new ArithmeticException("Impossível dividir por zero.");
        }
        return v1 / v2;
    }

}