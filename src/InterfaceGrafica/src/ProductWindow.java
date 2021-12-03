package InterfaceGrafica.src;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ProductWindow {

    ProductWindow(String name) {
        //productFrame
        JFrame productFrame = new JFrame(name);
        productFrame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE);
        productFrame.setLocation(740, 0);
        productFrame.setPreferredSize(new Dimension(240, 480));

        //mainPanel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(Color.GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        productFrame.add(mainPanel, BorderLayout.CENTER);

        //imageLabel
        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(this.getClass().getResource("/InterfaceGrafica"
                    + System.getProperty("file.separator") + "img" + System.getProperty("file.separator") + name + ".png")));
            BufferedImage imageResized = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = imageResized.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(image, 0, 0, 200, 200, null);
            g2.dispose();

            JLabel imageLabel = new JLabel(new ImageIcon(imageResized));
            gbc.gridx = 0;
            gbc.gridy = 0;
            mainPanel.add(imageLabel);
        } catch (IOException ex) {
            System.out.println("Error productWindow " + name);
        }
    /*
        try {
            BufferedReader buffRead = new BufferedReader(new FileReader("src/BancoDeDados/RegistroDeClientes.txt"));
            String[] linha = buffRead.readLine().split("/");
            while (linha != null) {
                for (String palavra: linha) {
                    System.out.println(palavra);
                }
                try {
                    linha = buffRead.readLine().split("/");
                } catch (NullPointerException ex) {
                    linha = null;
                }

            }
            buffRead.close();
        } catch (IOException ex) {
        System.out.println("Error");
    }
    */
        //DescriptionText
        JTextArea descriptionLabel = new JTextArea("Aqui é onde fica a descrição do produto. Testando a descrição do" +
                " produto para ver se tudo está cabendo no layout.");
        descriptionLabel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        descriptionLabel.setEditable(false);
        descriptionLabel.setLineWrap(true);
        descriptionLabel.setWrapStyleWord(true);
        descriptionLabel.setColumns(20);
        descriptionLabel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        gbc.gridy += 1;
        mainPanel.add(descriptionLabel, gbc);
        gbc.gridy += 1;


        //priceLabel1 e 2
        JLabel priceLabel1 = new JLabel("Preço unitário: ");
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        mainPanel.add(priceLabel1, gbc);
        JLabel priceLabel2 = new JLabel("200");
        gbc.anchor = GridBagConstraints.LINE_END;
        mainPanel.add(priceLabel2, gbc);

        //quantityLabel
        JLabel quantityLabel = new JLabel("Quantidade:");
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridy = 3;
        mainPanel.add(quantityLabel, gbc);

        //quantityField
        JTextField quantityField = new JTextField();
        quantityField.setColumns(5);

        gbc.anchor = GridBagConstraints.LINE_END;
        mainPanel.add(quantityField, gbc);

        //priceProductLabel1 e 2
        JLabel priceProductLabel1 = new JLabel("Preço total:");
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridy = 4;
        mainPanel.add(priceProductLabel1, gbc);
        JLabel priceProductLabel2 = new JLabel("Digite uma quantidade.");
        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        mainPanel.add(priceProductLabel2, gbc);



        //cancelButton
        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productFrame.dispatchEvent(new WindowEvent(productFrame, WindowEvent.WINDOW_CLOSING));
            }
        });
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        mainPanel.add(cancelButton, gbc);

        //addCarButton
        JButton addCarButton = new JButton("Adicionar");
        addCarButton.setEnabled(false);
        addCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productFrame.dispatchEvent(new WindowEvent(productFrame, WindowEvent.WINDOW_CLOSING));
            }
        });

        gbc.anchor = GridBagConstraints.FIRST_LINE_END;
        mainPanel.add(addCarButton, gbc);

        quantityField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    int numero = Integer.parseInt(quantityField.getText());
                    if (numero > 0) {
                        priceProductLabel2.setText(Integer.toString(numero * Integer.parseInt(priceLabel2.getText())));
                        addCarButton.setEnabled(true);
                    } else {
                        priceProductLabel2.setText("Quantidade inválida!");
                        addCarButton.setEnabled(false);
                    }
                } catch (NumberFormatException ex) {
                    priceProductLabel2.setText("Quantidade inválida!");
                    addCarButton.setEnabled(false);
                }
            }
        });

        productFrame.pack();
        productFrame.setVisible(true);
    }
}
