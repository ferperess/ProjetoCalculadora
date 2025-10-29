import javax.swing.SwingUtilities;

/**
 * Classe principal para iniciar a aplicação da calculadora.
 */
public class Main {
    public static void main(String[] args) {
        // Garante que a GUI seja criada na thread de despacho de eventos (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Cria e torna a janela visível
                CalculadoraGUI gui = new CalculadoraGUI();
                gui.setVisible(true);
            }
        });
    }
}