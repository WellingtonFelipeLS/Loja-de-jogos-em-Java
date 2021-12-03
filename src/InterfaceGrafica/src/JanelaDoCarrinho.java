package InterfaceGrafica.src;

//import java.awt.event.MouseListener;
//import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import ManipulacaoBancoDeDados.ControleDeVendas;

import ClassesUtilitarias.Venda;

public class JanelaDoCarrinho{
	
	public JanelaDoCarrinho(Venda novaVenda) {
		JFrame janelaDoCarrinho = new JFrame("Carrinho");
        janelaDoCarrinho.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        janelaDoCarrinho.setSize( 800, 400 );
        janelaDoCarrinho.setLayout( new BorderLayout() );

		JButton botao = new JButton("Comprar");
		botao.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					ControleDeVendas.cadastrarVenda(novaVenda);
					ControleDeVendas.listarVendas();
					novaVenda.limparDadosDaVenda();
				}
				catch(IOException ioe) {
					ioe.printStackTrace();
				}
			}
		});
		janelaDoCarrinho.add(botao);

		janelaDoCarrinho.setVisible(true);
	}
}
