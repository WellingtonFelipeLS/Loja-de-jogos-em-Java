package ClassesUtilitarias;

import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.awt.LayoutManager;
import java.awt.Color;

import java.io.File;
import java.io.IOException;

public class UtilitariosDeInterfaceGrafica {
	private static String fileSeparator = System.getProperty("file.separator");

	public static JLabel procurarImagemDoProduto(String nome, int largura, int altura) throws IOException{
		BufferedImage imagem = ImageIO.read(new File("src" + fileSeparator + "InterfaceGrafica"
                    + fileSeparator + "img" + fileSeparator + nome + ".png"));

		BufferedImage imagemRedimensionada = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = imagemRedimensionada.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(imagem, 0, 0, largura, altura, null);
        g2.dispose();

        return new JLabel(new ImageIcon(imagemRedimensionada));
	}

	public static JButton criarBotao(String nome, ActionListener acao) {
		JButton botao = new JButton(nome);
        botao.addActionListener(acao);

		return botao;
	}

	public static JPanel criarPainel(LayoutManager layoutManager, Color backgroundColor) {
        JPanel painel = new JPanel();
        painel.setLayout(layoutManager);
        painel.setBackground(backgroundColor);

		return painel;
	}
}
