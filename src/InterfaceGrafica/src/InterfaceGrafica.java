package InterfaceGrafica.src;

import RegrasDeNegocio.Produtos.*;
import RegrasDeNegocio.Venda;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import java.io.IOException;

import java.util.Objects;
import java.util.Set;

public class InterfaceGrafica implements MouseListener{
	private Venda novaVenda;

    InterfaceGrafica() {
		this.novaVenda = new Venda();

		JFrame interfacePrincipal = criarInterfacePrincipal();
		JPanel painelSuperior = criarPainelSuperior();
		JPanel painelPrincipal = criarPainelPrincipal();
		JTextField caixaDeBusca = criarCaixaDeBusca();
		JButton botaoDeBusca = criarBotaoDeBusca(caixaDeBusca);
		JButton botaoDoCarrinho = criarBotaoDoCarrinho();
		JButton botaoDeCompra = criarBotaoDeConta();


        interfacePrincipal.add(painelSuperior, BorderLayout.NORTH);
		interfacePrincipal.add(painelPrincipal, BorderLayout.CENTER);

        painelSuperior.add(caixaDeBusca);
		painelSuperior.add(botaoDeBusca);

        //totalLabel
        JLabel totalLabel = new JLabel();
        totalLabel.setPreferredSize(new Dimension(150, 20));
        totalLabel.setForeground(Color.WHITE);
        totalLabel.setEnabled(false);
        painelSuperior.add(totalLabel);

		painelSuperior.add(botaoDoCarrinho);
		painelSuperior.add(botaoDeCompra);

		GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        Produto[] listaJogos = new Jogo[5];
        listaJogos[0] = new Jogo("Xcom", 30f, 100, "Jogo do Geralt", Set.of("PS5", "XBOX", "PC"), Set.of("Fantasia", "Acao", "Aventura"));
        listaJogos[1] = new Jogo("Xcom 2", 60f, 100, "Jogo do Geralt", Set.of("PS5", "XBOX", "PC"), Set.of("Fantasia", "Acao", "Aventura"));
        listaJogos[2] = new Jogo("Red Dead Redemption", 30f, 100, "Jogo do Geralt", Set.of("PS5", "XBOX", "PC"), Set.of("Fantasia", "Acao", "Aventura"));
        listaJogos[3] = new Jogo("Red Dead Redemption 2", 30f, 100, "Jogo do Geralt", Set.of("PS5", "XBOX", "PC"), Set.of("Fantasia", "Acao", "Aventura"));
        listaJogos[4] = new Jogo("Civilization 6", 30f, 100, "Jogo do Geralt", Set.of("PS5", "XBOX", "PC"), Set.of("Fantasia", "Acao", "Aventura"));

        int x = 0, y = 0, ultimo = 0;
        JLabel[] listaJLabel = new JLabel[0];
        JLabel[] listaJLabelNome = new JLabel[0];
        for (Produto i: listaJogos) {
            gbc.gridx = x;
            gbc.gridy = y;
            try {
                if (listaJLabel.length == ultimo) {

                    JLabel[] listaAux = new JLabel[ultimo + 1];
                    System.arraycopy(listaJLabel, 0, listaAux, 0, listaJLabel.length);
                    listaJLabel = listaAux;

                    listaAux = new JLabel[ultimo + 1];
                    System.arraycopy(listaJLabelNome, 0, listaAux, 0, listaJLabelNome.length);
                    listaJLabelNome = listaAux;
                }
                listaJLabel[ultimo] = getImage(i.getNome());
                painelPrincipal.add(listaJLabel[ultimo], gbc);

                gbc.gridy = y + 1;
                listaJLabelNome[ultimo] = new JLabel(i.getNome());
                painelPrincipal.add(listaJLabelNome[ultimo], gbc);
                if (x % 4 == 0 && x != 0) {
                    x = 0;
                    y += 2;
                } else {
                    x += 1;
                }
            } catch (IOException ex) {
                System.out.println("Error " + i.getNome());
            }
            ultimo += 1;
        }

        x = 0;
        for (int i = 0; i < listaJLabel.length; i++) {
            listaJLabel[i].setName(listaJLabelNome[i].getText());
            listaJLabel[i].addMouseListener(this);
            listaJLabel[i].setToolTipText(listaJLabel[i].getName());
        }

        interfacePrincipal.pack();
        interfacePrincipal.setVisible( true );
    }

	private JFrame criarInterfacePrincipal() {
        JFrame interfacePrincipal = new JFrame("Loja WM");
        interfacePrincipal.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        interfacePrincipal.setSize( 800, 400 );
        interfacePrincipal.setLayout( new BorderLayout() );

		return interfacePrincipal;
	}

	private JPanel criarPainelSuperior() {
        JPanel painelSuperior = new JPanel();
        painelSuperior.setLayout(new FlowLayout(FlowLayout.CENTER));
        painelSuperior.setBackground(Color.DARK_GRAY);

		return painelSuperior;
	}

	private JTextField criarCaixaDeBusca() {
        JTextField campoDeBusca = new JTextField( "Digite o nome do produto." );
        campoDeBusca.setColumns(30);
        campoDeBusca.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (campoDeBusca.getText().equals( "Digite o nome do produto." )) {
                    campoDeBusca.setText( "" );
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (campoDeBusca.getText().equals( "" )) {
                    campoDeBusca.setText( "Digite o nome do produto." );
                }
            }
        });

		return campoDeBusca;
	}

	private JButton criarBotaoDeBusca(JTextField caixaDeBusca) {
        JButton botaoDeBusca = new JButton( "pesquisar" );
        botaoDeBusca.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
                new ProductWindow(caixaDeBusca.getText(), novaVenda);

            }
        });

		return botaoDeBusca;
	}

	private JButton criarBotaoDoCarrinho() {
        JButton botaoDoCarrinho = new JButton( "Carrinho" );
        botaoDoCarrinho.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				new JanelaDoCarrinho(novaVenda);
            }
        });

		return botaoDoCarrinho;
	}

    private JButton criarBotaoDeConta() {
        JButton contaButton = new JButton( "Login" );
        contaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new JanelaDaConta(contaButton, novaVenda);
            }
        });

        return contaButton;
    }


	private JPanel criarPainelPrincipal() {
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new GridBagLayout());
        painelPrincipal.setBackground(Color.GRAY);

		return painelPrincipal;
	}

	private JLabel getImage(String name) throws IOException {
        
        BufferedImage image = ImageIO.read(Objects.requireNonNull(this.getClass().getResource( "/InterfaceGrafica"
                + System.getProperty("file.separator") + "img" + System.getProperty("file.separator") + name + ".png")));

        BufferedImage imageResized = new BufferedImage(130, 130, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = imageResized.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(image, 0, 0, 130, 130, null);
        g2.dispose();

        return new JLabel(new ImageIcon(imageResized));

    }

	@Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        new ProductWindow(((JLabel)e.getSource()).getName(), this.novaVenda);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

	public static void main(String[] args) {
		new InterfaceGrafica();
	}
}
