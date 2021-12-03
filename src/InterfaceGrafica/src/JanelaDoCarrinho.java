package InterfaceGrafica.src;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;


import java.awt.BorderLayout;

public class JanelaDoCarrinho{
	
	public JanelaDoCarrinho() {
		JFrame janelaDoCarrinho = new JFrame("Carrinho");
        janelaDoCarrinho.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        janelaDoCarrinho.setSize( 800, 400 );
        janelaDoCarrinho.setLayout( new BorderLayout() );

		JButton botao = new JButton("Botão");
		janelaDoCarrinho.add(botao);

		janelaDoCarrinho.setVisible(true);
	}

	/*@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}*/
}
