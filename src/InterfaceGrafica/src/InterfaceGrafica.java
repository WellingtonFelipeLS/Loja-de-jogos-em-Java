package InterfaceGrafica.src;

import RegrasDeNegocio.Venda;

import javax.imageio.ImageIO;
import javax.swing.*;

import ManipulacaoBancoDeDados.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class InterfaceGrafica implements MouseListener{
	private Venda novaVenda;
	private ControleDeEstoque controleDeEstoque;
	private ControleDeVendas controleDeVendas;
	private ControleDeCadastroDeClientes controleDeCadastroDeClientes;

    InterfaceGrafica() {
		this.novaVenda = new Venda();
		this.controleDeEstoque = new ControleDeEstoque();
		this.controleDeVendas = new ControleDeVendas(controleDeEstoque);
		this.controleDeCadastroDeClientes = new ControleDeCadastroDeClientes();

		JFrame interfacePrincipal = criarInterfacePrincipal();
		JPanel painelSuperior = criarPainelSuperior();
		JPanel painelPrincipal = criarPainelPrincipal();

		JScrollPane scrollPane = new JScrollPane(painelPrincipal);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

		JComboBox marcadoresNomes = criarComboBox(painelPrincipal, gbc);
		JButton botaoDeBusca = criarBotaoDeBusca(marcadoresNomes);
        JButton botaoDeOpcao = criarBotaoDeOpcao();
		JButton botaoDoCarrinho = criarBotaoDoCarrinho();
		JButton botaoDeCompra = criarBotaoDeConta();


        interfacePrincipal.add(painelSuperior, BorderLayout.NORTH);
		interfacePrincipal.add(scrollPane, BorderLayout.CENTER);

        painelSuperior.add(marcadoresNomes);
		painelSuperior.add(botaoDeBusca);

        //totalLabel
        JLabel totalLabel = new JLabel();
        totalLabel.setPreferredSize(new Dimension(85, 20));
        totalLabel.setForeground(Color.WHITE);
        totalLabel.setEnabled(false);
        painelSuperior.add(totalLabel);

        painelSuperior.add(botaoDeOpcao);
		painelSuperior.add(botaoDoCarrinho);
		painelSuperior.add(botaoDeCompra);

        //String[] lista = {"Xcom", "Xcom 2", "Red Dead Redemption", "Red Dead Redemption 2", "Civilization 6", "Diablo 3"};
        try{
			exporProdutos(controleDeEstoque.listarNomeDosProdutosDisponiveis(), painelPrincipal, gbc);
		}catch(IOException ioe) {
			System.out.println("FALHA");
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

	private JButton criarBotaoDeBusca(JComboBox caixaDeBusca) {
        JButton botaoDeBusca = new JButton( "pesquisar" );
        botaoDeBusca.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
                new ProductWindow(caixaDeBusca.getSelectedItem().toString(), novaVenda, controleDeEstoque);
            }
        });

		return botaoDeBusca;
	}

    private JButton criarBotaoDeOpcao() {
        JButton botaoDeOpcao = new JButton( "Opções" );
        botaoDeOpcao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new JanelaDeOpcao(controleDeEstoque, controleDeCadastroDeClientes, controleDeVendas);

            }
        });

        return botaoDeOpcao;
    }

	private JButton criarBotaoDoCarrinho() {
        JButton botaoDoCarrinho = new JButton( "Carrinho" );
        botaoDoCarrinho.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				new JanelaDoCarrinho(novaVenda, controleDeEstoque, controleDeVendas);
            }
        });

		return botaoDoCarrinho;
	}

    private JButton criarBotaoDeConta() {
        JButton contaButton = new JButton( "Login" );
        contaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new JanelaDaConta(contaButton, novaVenda, controleDeVendas, controleDeCadastroDeClientes);
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

    private JComboBox criarComboBox(JPanel painelPrincipal, GridBagConstraints gbc) {
        String[] marcadores = {"", "Jogo", "Console", "Fone", "Mouse", "Teclado"};
        JComboBox comboBox = new JComboBox(marcadores);
        comboBox.setPreferredSize(new Dimension(300, 25));
		comboBox.setEditable(true);
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				try{
				ArrayList<String> lista = (comboBox.getSelectedItem().toString().equals("")) ?
								controleDeEstoque.listarNomeDosProdutosDisponiveis() :
								controleDeEstoque.listarProdutosPorCategoria(comboBox.getSelectedItem().toString());

				exporProdutos(lista,painelPrincipal, gbc);
				}catch(IOException ioe) {
					System.out.println("FALHA");
				}
				
            }
        });
        return comboBox;
    }

    private void exporProdutos(ArrayList<String> lista, JPanel painelPrincipal, GridBagConstraints gbc) {
        painelPrincipal.removeAll();
		painelPrincipal.repaint();
		painelPrincipal.revalidate();

		int x = 0, y = 0, ultimo = 0;
        JLabel[] listaJLabel = new JLabel[0];
        JLabel[] listaJLabelNome = new JLabel[0];
        for (String i: lista) {
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
                listaJLabel[ultimo] = getImage(i);
                painelPrincipal.add(listaJLabel[ultimo], gbc);

                gbc.gridy = y + 1;
                listaJLabelNome[ultimo] = new JLabel(i);
                painelPrincipal.add(listaJLabelNome[ultimo], gbc);
                if (x % 4 == 0 && x != 0) {
                    x = 0;
                    y += 2;
                } else {
                    x += 1;
                }
            } catch (IOException ex) {
                System.out.println("Error " + i);
            }
            ultimo += 1;
        }

        x = 0;
        for (int i = 0; i < listaJLabel.length; i++) {
            listaJLabel[i].setName(listaJLabelNome[i].getText());
            listaJLabel[i].addMouseListener(this);
            listaJLabel[i].setToolTipText(listaJLabel[i].getText());
        }
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
        new ProductWindow(((JLabel)e.getSource()).getName(), this.novaVenda, controleDeEstoque);
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
