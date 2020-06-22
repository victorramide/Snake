package snakeopt;

import java.awt.Dimension;
import javax.swing.JFrame;

public class Board {
	public static final int WIDTH = 300; //Altura da tela
	public static final int HEIGHT = 300; //Largura da tela
	
	public Board() {
		JFrame window = new JFrame("Snake"); // cria a janela
		Game game = new Game(); // cria a tela do jogo
		game.setPreferredSize(new Dimension(WIDTH, HEIGHT)); //Define as dimensões da tela
		window.getContentPane().add(game); // adiciona a tela do jogo na janela
		window.setResizable(false); // impede redimensionamento
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // modo de encerramento
		window.setLocation(1000, 250); // posição da janela na tela
		window.setVisible(true); // torna a janela visível
		window.pack(); //Empacota tudo
	}
	public static void main(String[] args) {
		new Board(); // dispara a aplicação
	}
}