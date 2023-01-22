package InterfaceGrafica.src;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.JButton;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.MouseInfo;
import java.awt.ComponentOrientation;
import java.awt.Point;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.io.IOException;

import ManipulacaoBancoDeDados.ControleDeEstoque;

import RegrasDeNegocio.Produtos.Produto;
import RegrasDeNegocio.Venda;

import ClassesUtilitarias.UtilitariosDeInterfaceGrafica;

public class JanelaDoProduto {
	private JFrame interfaceDoProduto;
	private GridBagConstraints gbc;

    JanelaDoProduto(String nome, Venda novaVenda, ControleDeEstoque controleDeEstoque) {
		this.interfaceDoProduto = criarInterfaceDoProduto(nome);
		this.gbc = new GridBagConstraints();
        this.gbc.insets = new Insets(5, 5, 5, 5);
		this.gbc.gridx = 0;
		this.gbc.gridy = 0;

		JPanel painelPrincipal = UtilitariosDeInterfaceGrafica.criarPainel(new GridBagLayout(), Color.WHITE);

        interfaceDoProduto.add(painelPrincipal, BorderLayout.CENTER);

        try {
            JLabel imageLabel = UtilitariosDeInterfaceGrafica.procurarImagemDoProduto(nome, 200, 200);
			painelPrincipal.add(imageLabel);

			Produto produto = controleDeEstoque.procurarProdutoNoEstoque(nome);

			JTextArea espacoDaDescricao = criarEspacoParaADescricao(produto.getDescricao()); 

			painelPrincipal.add(espacoDaDescricao, gbc);
			
			gbc.gridy += 1;
			inserirLegendaFixaNoPainel("Preço unitário:", String.valueOf(produto.getPreco()), painelPrincipal);
			
			gbc.gridy += 1;
			inserirLegendaFixaNoPainel("Estoque:", String.valueOf(produto.getQntNoEstoque()), painelPrincipal);
			
			gbc.gridy += 1;
			JTextField campoDaQuantidade = inserirLegendaEditavelNoPainel("Quantidade:", painelPrincipal);
			
			gbc.gridy += 1;
			JLabel textoCampoDaQuantidade = inserirLegendaVolatilNoPainel(
					"Preço total:", "Digite uma quantidade.", painelPrincipal);
			
			gbc.gridy += 1;
			JButton botaoDeCancelar = UtilitariosDeInterfaceGrafica.criarBotao("Cancelar", e -> fecharJanela());
			gbc.anchor = GridBagConstraints.FIRST_LINE_START;
			painelPrincipal.add(botaoDeCancelar, gbc);

			JButton botaoDeAdiconarAoCarrinho = criarBotaoDeAdicionarAoCarrinho(nome, campoDaQuantidade, novaVenda);
			
			gbc.anchor = GridBagConstraints.FIRST_LINE_END;
			painelPrincipal.add(botaoDeAdiconarAoCarrinho, gbc);

			campoDaQuantidade.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					try {
						int qntSolicitada = Integer.parseInt(campoDaQuantidade.getText());
						int qntNoCarrinho = (novaVenda.getCarrinho().get(nome) == null) ? 0 : novaVenda.getCarrinho().get(nome);
						if (qntSolicitada + qntNoCarrinho <= produto.getQntNoEstoque() && qntSolicitada > 0) {
							textoCampoDaQuantidade.setText(Float.toString(qntSolicitada * produto.getPreco()));
							botaoDeAdiconarAoCarrinho.setEnabled(true);
						} else {
							String mensagem = qntSolicitada == 0 ? "Quantidade inválida!" : "Estoque insuficiente!";
							textoCampoDaQuantidade.setText(mensagem);
							botaoDeAdiconarAoCarrinho.setEnabled(false);
						}
					} catch (NumberFormatException ex) {
						textoCampoDaQuantidade.setText("Quantidade inválida!");
						campoDaQuantidade.setText("");
						botaoDeAdiconarAoCarrinho.setEnabled(false);
					}
				}
        	});

			interfaceDoProduto.pack();
			interfaceDoProduto.setVisible(true);
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}

	}

	private JFrame criarInterfaceDoProduto(String nome) {
		JFrame interfaceDoProduto = new JFrame(nome);
		interfaceDoProduto.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		Point p = MouseInfo.getPointerInfo().getLocation();
		interfaceDoProduto.setLocation((int) p.getX(), (int) p.getY());

		return interfaceDoProduto;
	}

	private JTextArea criarEspacoParaADescricao(String descricao) {
		JTextArea espacoParaADescricao = new JTextArea(descricao);
		espacoParaADescricao.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		espacoParaADescricao.setEditable(false);
		espacoParaADescricao.setLineWrap(true);
		espacoParaADescricao.setWrapStyleWord(true);
		espacoParaADescricao.setColumns(20);
		espacoParaADescricao.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		return espacoParaADescricao;
	}

	private JButton criarBotaoDeAdicionarAoCarrinho(String nome, JTextField campoDaQuantidade, Venda novaVenda) {
		JButton botaoDeAdicionarAoCarrinho = UtilitariosDeInterfaceGrafica.criarBotao("Adicionar",
			e -> novaVenda.adicionarProdutoAoCarrinho(nome, Integer.parseInt(campoDaQuantidade.getText())));
			
		botaoDeAdicionarAoCarrinho.setEnabled(false);
		return botaoDeAdicionarAoCarrinho;
	}

	private JLabel criarLegendaGenerica(String mensagem,GridBagConstraints gbc, int option) {
		JLabel legendaGenerica = new JLabel(mensagem);
        gbc.anchor = option;

		return legendaGenerica;
	}

	private void inserirLegendaFixaNoPainel(String nomeEsquerda, String nomeDireita, JPanel painel) {
		JLabel textoEsquerdo = criarLegendaGenerica(nomeEsquerda, this.gbc, GridBagConstraints.FIRST_LINE_START);
		painel.add(textoEsquerdo, this.gbc);

		JLabel textoDireito = criarLegendaGenerica(nomeDireita, this.gbc, GridBagConstraints.LINE_END);
		painel.add(textoDireito, this.gbc);
	}

	private JLabel inserirLegendaVolatilNoPainel(String nomeEsquerda, String nomeDireita, JPanel painel) {
		JLabel textoEsquerdo = criarLegendaGenerica(nomeEsquerda, this.gbc, GridBagConstraints.FIRST_LINE_START);
		painel.add(textoEsquerdo, this.gbc);

		JLabel textoDireito = criarLegendaGenerica(nomeDireita, this.gbc, GridBagConstraints.LINE_END);		
		painel.add(textoDireito, this.gbc);

		return textoDireito;
	}

	private JTextField inserirLegendaEditavelNoPainel(String nome, JPanel painel) {
		JLabel textoEsquerdo = criarLegendaGenerica(nome, gbc, GridBagConstraints.FIRST_LINE_START);
		painel.add(textoEsquerdo, gbc);

		JTextField campoEditavel = new JTextField();
		campoEditavel.setColumns(5);
		gbc.anchor = GridBagConstraints.LINE_END;
		painel.add(campoEditavel, gbc);

		return campoEditavel;
	}

	public void fecharJanela() {
		this.interfaceDoProduto.dispose();
	}
}
