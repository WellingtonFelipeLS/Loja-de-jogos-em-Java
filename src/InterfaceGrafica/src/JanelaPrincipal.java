package InterfaceGrafica.src;

import RegrasDeNegocio.Venda;
import RegrasDeNegocio.Produtos.CategoriaDeProdutos;

import ClassesUtilitarias.UtilitariosDeInterfaceGrafica;

import ManipulacaoBancoDeDados.ControleDeCadastroDeClientes;
import ManipulacaoBancoDeDados.ControleDeEstoque;
import ManipulacaoBancoDeDados.ControleDeVendas;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.JTextComponent;

import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.ActionEvent;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;

public class JanelaPrincipal implements MouseListener{
	private Venda novaVenda;
	private ControleDeEstoque controleDeEstoque;
	private ControleDeVendas controleDeVendas;
	private ControleDeCadastroDeClientes controleDeCadastroDeClientes;
	private String fileSeparator = System.getProperty("file.separator");
	private JanelaDoProduto janelaDoProdutoAtual = null;
	private GridBagConstraints gbc;

    public JanelaPrincipal() {
		this.novaVenda = new Venda();
		this.controleDeEstoque = new ControleDeEstoque("src" + fileSeparator + "BancoDeDados");
		this.controleDeVendas = new ControleDeVendas(controleDeEstoque, "src" + fileSeparator + "BancoDeDados");
		this.controleDeCadastroDeClientes = new ControleDeCadastroDeClientes("src" + fileSeparator + "BancoDeDados");
		this.gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

		JFrame interfacePrincipal = criarInterfacePrincipal();
		JPanel painelSuperior = UtilitariosDeInterfaceGrafica.criarPainel(new FlowLayout(FlowLayout.CENTER), Color.DARK_GRAY);
		JPanel painelPrincipal = UtilitariosDeInterfaceGrafica.criarPainel(new GridBagLayout(), Color.WHITE);
		painelPrincipal.setPreferredSize(new Dimension(1280, 720));

		JScrollPane scrollPane = new JScrollPane(painelPrincipal);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		JComboBox<String> marcadoresNomes = criarComboBox(painelPrincipal, gbc);

		JButton botaoDeBusca = UtilitariosDeInterfaceGrafica.criarBotao("Pesquisar", 
			e -> new JanelaDoProduto(marcadoresNomes.getSelectedItem().toString(), novaVenda, controleDeEstoque));
        
		JButton botaoDeOpcao = UtilitariosDeInterfaceGrafica.criarBotao("Opção",
			e -> new JanelaDeOpcao(controleDeEstoque, controleDeCadastroDeClientes, controleDeVendas));
		
		JButton botaoDoCarrinho = UtilitariosDeInterfaceGrafica.criarBotao("Carrinho",
			e -> new JanelaDoCarrinho(novaVenda, controleDeEstoque, controleDeVendas));
		
		JButton botaoDeLogin = UtilitariosDeInterfaceGrafica.criarBotao("Login",
			e -> new JanelaDeLogin(new JButton(), novaVenda, controleDeVendas, controleDeCadastroDeClientes));

        painelSuperior.add(marcadoresNomes);
		painelSuperior.add(botaoDeBusca);
        painelSuperior.add(botaoDeOpcao);
		painelSuperior.add(botaoDoCarrinho);
		painelSuperior.add(botaoDeLogin);

		interfacePrincipal.add(painelSuperior, BorderLayout.NORTH);
		interfacePrincipal.add(scrollPane, BorderLayout.CENTER);

        try{
			exporProdutos(controleDeEstoque.listarNomeDosProdutosDisponiveis(), painelPrincipal);
		}catch(IOException ioe) {
			System.out.println("FALHA NA EXPOSIÇÃO DOS PRODUTOS");
		}
		
        interfacePrincipal.pack();
        interfacePrincipal.setVisible( true );
	}

	private JFrame criarInterfacePrincipal() {
        JFrame interfacePrincipal = new JFrame("Loja WM");
        interfacePrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        interfacePrincipal.setSize( 800, 400 );
        interfacePrincipal.setLayout( new BorderLayout() );

		return interfacePrincipal;
	}	

    private JComboBox<String> criarComboBox(JPanel painelPrincipal, GridBagConstraints gbc) {
        String[] marcadores = CategoriaDeProdutos.getCategoriasDeProduto();
        JComboBox<String> comboBox = new JComboBox<String>(marcadores);
        comboBox.setPreferredSize(new Dimension(300, 25));
		comboBox.setEditable(true);
        comboBox.addActionListener(e -> {
				try{
				ArrayList<String> listaDeNomesDosProdutos = (comboBox.getSelectedItem().toString().equals(CategoriaDeProdutos.QUALQUER.toString())) ?
										   			 		 controleDeEstoque.listarNomeDosProdutosDisponiveis() :
										   			 		 controleDeEstoque.listarProdutosPorCategoria(comboBox.getSelectedItem().toString());
				exporProdutos(listaDeNomesDosProdutos,painelPrincipal);
				}catch(IOException ioe) {
					System.out.println("Falha na comunicação com o banco de dados");
				}
				
            }
        );

		JTextField editor = (JTextField) comboBox.getEditor().getEditorComponent();
		editor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent event) {
				try {
					ArrayList<String> lista = controleDeEstoque.listarProdutosPorNome(comboBox.getSelectedItem().toString());
					exporProdutos(lista, painelPrincipal);
				} catch (IOException ex) {
					System.out.print("FALHA NA COMUNICAÇÃO COM O BANCO DE DADOS");
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

    private void exporProdutos(ArrayList<String> listaDeNomesDosProdutos, JPanel painelPrincipal) {
		atualizarPainel(painelPrincipal);

		// Espaço entre as imagens alterado dinamicamente com a mudança de tamanho da janela
		this.gbc.weightx = 1.0;

		int numeroDeColunas = 10;
		int numeroDeLinhas = listaDeNomesDosProdutos.size() % numeroDeColunas == 0 ? listaDeNomesDosProdutos.size() / numeroDeColunas 
																				   : listaDeNomesDosProdutos.size() / numeroDeColunas + 1;
     
		Iterator<String> iterListaDeProdutos = listaDeNomesDosProdutos.iterator();
		JLabel imagem, nome;
		String tempNomeProduto = null;
		for (this.gbc.gridy = 0; this.gbc.gridy < numeroDeLinhas * 2; this.gbc.gridy += 2) {
			try {
				for (this.gbc.gridx = 0; this.gbc.gridx < numeroDeColunas && iterListaDeProdutos.hasNext(); this.gbc.gridx++) {
					tempNomeProduto =  iterListaDeProdutos.next();

					imagem = UtilitariosDeInterfaceGrafica.procurarImagemDoProduto(tempNomeProduto, 130, 130);

					nome = new JLabel(tempNomeProduto);

					adicionarAcoesNaImagem(imagem, tempNomeProduto);
					adicionarImagemAoPainel(painelPrincipal, imagem, nome, this.gbc);
				}
			}catch (IOException ex) {
                System.out.println("Falha no carregamento da imagem do produto: " + tempNomeProduto);
            }
		}
    }

	@Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
		if (janelaDoProdutoAtual != null)
			janelaDoProdutoAtual.fecharJanela();

        janelaDoProdutoAtual = new JanelaDoProduto(((JLabel)e.getSource()).getName(), this.novaVenda, controleDeEstoque);
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
