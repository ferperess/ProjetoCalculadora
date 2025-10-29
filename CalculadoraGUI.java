import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Classe responsável pela Interface Gráfica e pelo tratamento de eventos.
 * Utiliza a classe Calculadora para a lógica de negócio.
 */
public class CalculadoraGUI extends JFrame implements ActionListener {

    private JTextField display;
    private Calculadora calculadora;

    // Variáveis de estado da calculadora
    private String operacaoAtual = "";
    private double primeiroValor = 0.0;
    private boolean novoNumero = true;

    public CalculadoraGUI() {
        calculadora = new Calculadora();

        // Configurações da Janela
        setTitle("Calculadora JAVA");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Configuracao do Display
        display = new JTextField("0");
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("Arial", Font.BOLD, 24));

        JPanel painelPrincipal = new JPanel(new BorderLayout(5, 5));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painelPrincipal.add(display, BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel(new GridLayout(5, 4, 5, 5)); // 5 linhas, 4 colunas

        // Ordem dos botões
        String[] botoes = {
                "CE", "*", "/", "+", "7", "8", "9",
                "-", "4", "5", "6", ".", "1", "2",
                "3", "0"
        };

        // Criação e adição dos botões
        for (String texto : botoes) {
            JButton botao = new JButton(texto);
            botao.addActionListener(this);
            botao.setFont(new Font("Arial", Font.PLAIN, 18));
            painelBotoes.add(botao);
        }

        // Adiciona o botão de Igual (=)
        JButton btnIgual = new JButton("=");
        btnIgual.addActionListener(this);
        btnIgual.setFont(new Font("Arial", Font.BOLD, 18));
        painelBotoes.add(btnIgual);

        // Adiciona o painel de botões ao painel principal
        painelPrincipal.add(painelBotoes, BorderLayout.CENTER);

        // Adiciona o painel principal ao frame
        add(painelPrincipal);

        // Ajusta o tamanho da janela para caber todos os componentes
        pack();
        setLocationRelativeTo(null); // Centraliza a janela
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        if (comando.matches("[0-9]")) {
            // Se for um dígito
            if (novoNumero || display.getText().equals("0")) {
                display.setText(comando);
                novoNumero = false;
            } else {
                display.setText(display.getText() + comando);
            }
        } else if (comando.equals(".")) {
            // Se for o ponto decimal
            if (novoNumero) {
                display.setText("0.");
                novoNumero = false;
            } else if (!display.getText().contains(".")) {
                display.setText(display.getText() + ".");
            }
        } else if (comando.equals("CE")) {
            // Limpar/Resetar
            display.setText("0");
            operacaoAtual = "";
            primeiroValor = 0.0;
            novoNumero = true;
        } else if (comando.matches("[+\\-*/]")) {
            // Se for uma operação (+, -, *, /)
            if (!operacaoAtual.isEmpty()) {
                // Se já houver uma operação pendente, calcula o resultado intermediário
                calcular(display.getText());
            }
            primeiroValor = Double.parseDouble(display.getText());
            operacaoAtual = comando;
            novoNumero = true;
        } else if (comando.equals("=")) {
            // Se for o botão de igual
            if (!operacaoAtual.isEmpty()) {
                calcular(display.getText());
                operacaoAtual = "";
            }
        }
    }

    /**
     * Realiza o cálculo com base na operação atual.
     */
    private void calcular(String segundoValorStr) {
        try {
            double segundoValor = Double.parseDouble(segundoValorStr);
            double resultado = 0.0;

            switch (operacaoAtual) {
                case "+":
                    resultado = calculadora.soma(primeiroValor, segundoValor);
                    break;
                case "-":
                    resultado = calculadora.subtracao(primeiroValor, segundoValor);
                    break;
                case "*":
                    resultado = calculadora.multiplicacao(primeiroValor, segundoValor);
                    break;
                case "/":
                    // O metodo divisao lança ArithmeticException
                    resultado = calculadora.divisao(primeiroValor, segundoValor);
                    break;
            }

            display.setText(String.valueOf(resultado));
            primeiroValor = resultado;
            novoNumero = true;

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro: Valor inválido.",
                    "Erro de Entrada",
                    JOptionPane.ERROR_MESSAGE);
            display.setText("Erro");
            novoNumero = true;
        } catch (ArithmeticException ex) {
            // Captura a exceção de divisão por zero
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Erro de Operação",
                    JOptionPane.ERROR_MESSAGE);
            // Mostrar zero como resultado no campo result.
            display.setText("0.0");
            primeiroValor = 0.0;
            novoNumero = true;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Ocorreu um erro inesperado: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            display.setText("Erro");
            novoNumero = true;
        }
    }
}