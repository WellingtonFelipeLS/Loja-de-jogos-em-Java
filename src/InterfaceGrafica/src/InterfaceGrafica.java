package InterfaceGrafica.src;

import RegrasDeNegocio.Venda;
import RegrasDeNegocio.Produtos.CategoriaDeProdutos;

import javax.imageio.ImageIO;
import javax.swing.*;

import ManipulacaoBancoDeDados.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class InterfaceGrafica implements MouseListener{
	private Venda novaVenda;
	private ControleDeEstoque controleDeEstoque;
	private ControleDeVendas controleDeVendas;
	private ControleDeCadastroDeClientes controleDeCadastroDeClientes;
	private String fileSeparator = System.getProperty("file.separator");

    public InterfaceGrafica() {
		this.novaVenda = new Venda();
		this.controleDeEstoque = new ControleDeEstoque("src" + fileSeparator + "BancoDeDados");
		this.controleDeVendas = new ControleDeVendas(controleDeEstoque, "src" + fileSeparator + "BancoDeDados");
		this.controleDeCadastroDeClientes = new ControleDeCadastroDeClientes("src" + fileSeparator + "BancoDeDados");

		JFrame interfacePrincipal = criarInterfacePrincipal();
		JPanel painelSuperior = criarPainel(new FlowLayout(FlowLayout.CENTER), Color.DARK_GRAY);
		JPanel painelPrincipal = criarPainel(new GridBagLayout(), Color.GRAY);
		painelPrincipal.setPreferredSize(new Dimension(1280, 720));

		JScrollPane scrollPane = new JScrollPane(painelPrincipal);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

		JComboBox<String> marcadoresNomes = criarComboBox(painelPrincipal, gbc);
		JButton botaoDeBusca = criarBotaoDeBusca(marcadoresNomes);
        JButton botaoDeOpcao = criarBotaoDeOpcao();
		JButton botaoDoCarrinho = criarBotaoDoCarrinho();
		JButton botaoDeLogin = criarBotaoDeLogin();

        interfacePrincipal.add(painelSuperior, BorderLayout.NORTH);
		interfacePrincipal.add(scrollPane, BorderLayout.CENTER);

        painelSuperior.add(marcadoresNomes);
		painelSuperior.add(botaoDeBusca);

        painelSuperior.add(botaoDeOpcao);
		painelSuperior.add(botaoDoCarrinho);
		painelSuperior.add(botaoDeLogin);

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

	private JPanel criarPainel(LayoutManager layoutManager, Color backgroundColor) {
        JPanel painelSuperior = new JPanel();
        painelSuperior.setLayout(layoutManager);
        painelSuperior.setBackground(backgroundColor);

		return painelSuperior;
	}

	private JButton criarBotaoDeBusca(JComboBox<String> caixaDeBusca) {
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

    private JButton criarBotaoDeLogin() {
        JButton contaButton = new JButton( "Login" );
        contaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new JanelaDaConta(contaButton, novaVenda, controleDeVendas, controleDeCadastroDeClientes);
            }
        });

        return contaButton;
    }

    private JComboBox<String> criarComboBox(JPanel painelPrincipal, GridBagConstraints gbc) {
        String[] marcadores = CategoriaDeProdutos.getCategoriasDeProduto();
        JComboBox<String> comboBox = new JComboBox<String>(marcadores);
        comboBox.setPreferredSize(new Dimension(300, 25));
		comboBox.setEditable(true);
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				try{
				ArrayList<String> listaDeNomesDosProdutos = (comboBox.getSelectedItem().toString().equals(CategoriaDeProdutos.QUALQUER.toString())) ?
										   			 		 controleDeEstoque.listarNomeDosProdutosDisponiveis() :
										   			 		 controleDeEstoque.listarProdutosPorCategoria(comboBox.getSelectedItem().toString());

				exporProdutos(listaDeNomesDosProdutos,painelPrincipal, gbc);
				}catch(IOException ioe) {
					System.out.println("Falha na comunicação com o banco de dados");
				}
				
            }
        });
        return comboBox;
    }

	private void atualizarPainel(JPanel painel) {
		painel.removeAll();
		painel.repaint();
		painel.revalidate();
	}

	private void adicionarAcoesNaImagem(JLabel imagem, String nome) {
		imagem.setName(nome);
		imagem.addMouseListener(this);
        imagem.setToolTipText(nome);
	}

	private void adicionarImagemAoPainel(JPanel painel, JLabel imagem, JLabel nome, GridBagConstraints gbc) {
		painel.add(imagem, gbc);
		gbc.gridy++;
		painel.add(nome, gbc);
		gbc.gridy--;
	}

    private void exporProdutos(ArrayList<String> listaDeNomesDosProdutos, JPanel painelPrincipal, GridBagConstraints gbc) {
		atualizarPainel(painelPrincipal);

		// Espaço entre as imagens alterado dinamicamente com a mudança de tamanho da janela
		gbc.weightx = 1.0;

		int numeroDeColunas = 10;
		int numeroDeLinhas = listaDeNomesDosProdutos.size() % numeroDeColunas == 0 ? listaDeNomesDosProdutos.size() / numeroDeColunas 
																				   : listaDeNomesDosProdutos.size() / numeroDeColunas + 1;
     
		Iterator<String> iterListaDeProdutos = listaDeNomesDosProdutos.iterator();
		JLabel imagem, nome;
		String tempNomeProduto = null;
		for (gbc.gridy = 0; gbc.gridy < numeroDeLinhas * 2; gbc.gridy += 2) {
			try {
				for (gbc.gridx = 0; gbc.gridx < numeroDeColunas && iterListaDeProdutos.hasNext(); gbc.gridx++) {
					tempNomeProduto =  iterListaDeProdutos.next();

					imagem = pegarImagem(tempNomeProduto);
					nome = new JLabel(tempNomeProduto);

					adicionarAcoesNaImagem(imagem, tempNomeProduto);
					adicionarImagemAoPainel(painelPrincipal, imagem, nome, gbc);
				}
			}catch (IOException ex) {
                System.out.println("Falha no carregamento da imagem do produto: " + tempNomeProduto);
            }
		}
    }

	private JLabel pegarImagem(String nome) throws IOException {
        
        BufferedImage imagem = ImageIO.read(Objects.requireNonNull(
										   this.getClass().getResource( "/InterfaceGrafica" + fileSeparator + "img" + fileSeparator + nome + ".png")));

        BufferedImage imagemRedimensionada = new BufferedImage(130, 130, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = imagemRedimensionada.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(imagem, 0, 0, 130, 130, null);
        g2.dispose();

        return new JLabel(new ImageIcon(imagemRedimensionada));

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
}
