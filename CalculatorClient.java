import java.awt.*;
import java.awt.event.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.*;

public class CalculatorClient extends JFrame {

    private Calculator calc;
    private JTextField num1Field, num2Field;
    private JLabel resultLabel;
    private String lastOp = "+"; // last operator

    public CalculatorClient() {
        // Connect to RMI server
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            calc = (Calculator) registry.lookup("CalculatorService");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to connect to RMI Server");
            System.exit(0);
        }

        // ===== Window Settings =====
        setTitle("RMI Calculator");
        setSize(320, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null); // center window

        // ===== Input Panel =====
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5); // padding

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Number 1:"), gbc);

        gbc.gridx = 1;
        num1Field = new JTextField(8);
        inputPanel.add(num1Field, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Number 2:"), gbc);

        gbc.gridx = 1;
        num2Field = new JTextField(8);
        inputPanel.add(num2Field, gbc);

        // ===== Button Panel =====
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 8, 8));
        JButton addBtn = new JButton("+");
        JButton subBtn = new JButton("−");
        JButton mulBtn = new JButton("×");
        JButton divBtn = new JButton("÷");

        // smaller font
        Font btnFont = new Font("Arial", Font.BOLD, 16);
        addBtn.setFont(btnFont);
        subBtn.setFont(btnFont);
        mulBtn.setFont(btnFont);
        divBtn.setFont(btnFont);

        // add buttons
        buttonPanel.add(addBtn);
        buttonPanel.add(subBtn);
        buttonPanel.add(mulBtn);
        buttonPanel.add(divBtn);

        // ===== Result =====
        resultLabel = new JLabel("Result: ", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(resultLabel, BorderLayout.SOUTH);

        // ===== Button Actions =====
        addBtn.addActionListener(e -> calculate("+"));
        subBtn.addActionListener(e -> calculate("-"));
        mulBtn.addActionListener(e -> calculate("*"));
        divBtn.addActionListener(e -> calculate("/"));

        // ===== Keyboard Support =====
        KeyAdapter keyHandler = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ADD:
                    case KeyEvent.VK_PLUS:
                        calculate("+");
                        break;
                    case KeyEvent.VK_SUBTRACT:
                    case KeyEvent.VK_MINUS:
                        calculate("-");
                        break;
                    case KeyEvent.VK_MULTIPLY:
                        calculate("*");
                        break;
                    case KeyEvent.VK_DIVIDE:
                    case KeyEvent.VK_SLASH:
                        calculate("/");
                        break;
                    case KeyEvent.VK_ENTER:
                        calculate(lastOp);
                        break;
                }
            }
        };

        num1Field.addKeyListener(keyHandler);
        num2Field.addKeyListener(keyHandler);
    }

    private void calculate(String op) {
        try {
            double a = Double.parseDouble(num1Field.getText());
            double b = Double.parseDouble(num2Field.getText());
            double result = 0;

            lastOp = op;

            switch (op) {
                case "+": result = calc.add(a, b); break;
                case "-": result = calc.subtract(a, b); break;
                case "*": result = calc.multiply(a, b); break;
                case "/": result = calc.divide(a, b); break;
            }

            resultLabel.setText("Result: " + a + " " + op + " " + b + " = " + result);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter valid numbers");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CalculatorClient().setVisible(true);
        });
    }
}
