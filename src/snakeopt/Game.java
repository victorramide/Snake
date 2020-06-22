package snakeopt;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

public class Game extends JPanel {

	public Food food; //declara a comida
	public Snake snake;//declara a snake
	protected static boolean k_up = false; //declara a tecla para cima
	protected static boolean k_down = false;//declara a tecla para baixo
	protected static boolean k_left = false;//declara a tecla para esquerda
	protected static boolean k_right = false;//declara a tecla para direita
	protected static boolean k_enter = false;//declara a tecla enter

	public static boolean gameOver = false; //declara o game over
	public static int score = 0; //declara a pontuação
	public static Double frameSpeed = 4.0; //declara os frames do jogo

	public Game() {

		food = new Food(); //instancia uma nova comida
		snake = new Snake();//instancia uma nova snake
		
		keySettings(); //Define os controles

		setFocusable(true); //Coloca a janela em foco.
		setLayout(null); //Não determina um gerenciador de layout ao container

		new Thread(new Runnable() { // instancia da Thread + classe interna anonima
			@Override
			public void run() {
				gameloop(); // inicia o gameloop
			}
		}).start(); // dispara a Thread

	}

	public void gameloop() {
		while (true) { // repetição intermitente do gameloop
			handlerEvents();
			update();
			render();
			try {
				Thread.sleep(1000 / frameSpeed.intValue());
			} catch (Exception e) {
			}
		}

	}

	public void handlerEvents() {
		snake.snakeMove(snake); //Movimentação da Snake
	}

	public void update() {
		snake.update(snake, food); //Update da Snake
	}

	public void render() {
		repaint(); //Redesenha a tela
	}

	// Desenha os componentes na tela.
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.black); //Desenha a tela preta
		doDrawing(g); //chama a função que desenha os componentes na tela.
	}

	//--------------------------------------ON-SCREEN DRAWING FUNCTIONS---------------------------------

	// Função que controla a mudança de tela.
	public void doDrawing(Graphics g) {
		if (gameOver == false) { //Se o jogo tiver em andamento
			g.setColor(Color.white); //pinta as letras de cor branca
			g.setFont(new Font("Arial", Font.BOLD, 12)); //determina a fonte e tamanho das letras
			g.drawString("Pontuação: " + score, Board.WIDTH / 2 - 40, Board.HEIGHT/2 - 135); //Escreve na tela e determina sua posição na tela.
			snake.draw(snake, g); //desenha a snake
			food.draw(food, g); //desenha a comida
			Toolkit.getDefaultToolkit().sync(); //sincroniza a tela como um update.
		} else {
			gameOver(g); //chama a tela de game over.
		}
	}

	// Desenha a tela de gameover.
	public void gameOver(Graphics g) {
		g.setColor(Color.white); //pinta as letras de cor branca
		g.setFont(new Font("Arial", Font.BOLD, 14));//determina a fonte e tamanho das letras
		g.drawString("GAME OVER! ", Board.WIDTH / 2 - 45, Board.HEIGHT / 2 - 15);
		g.drawString("Sua pontuação foi de: " + score + " pontos", Board.WIDTH / 2 - 110, Board.HEIGHT / 2);//Escreve na tela e determina sua posição na tela.
		g.drawString(">>Pressione Enter para jogar de novo<<", Board.WIDTH / 2 - 145, Board.HEIGHT / 2 + 15);//Escreve na tela e determina sua posição na tela.
	}

	
	//---------------------------------------SETTING FUNCTIONS--------------------------------------
	//Função que configura as teclas que controla a snake
	public void keySettings() {
		addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {}
			@Override
			public void keyReleased(KeyEvent arg0) {}
			// Configuração das teclas.
			@Override
			public void keyPressed(KeyEvent e) { //Ao pressionar as teclas...
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP: // caso pressione a tecla para cima.
					if(k_down) { // se a snake já tiver indo para baixo.
						k_up = false; //a tecla para cima não fará nada.
					} 
					// Se não, a snake vai para cima e as outras direções ficam falsas.
					k_up = true;
					k_left = false;
					k_right = false;
					break;
				case KeyEvent.VK_DOWN:// caso pressione a tecla para baixo.
					if(k_up) {// se a snake já tiver indo para cima.
						k_down = false;//a tecla para baixo não fará nada.
					}
					// Se não, a snake vai para baixo e as outras direções ficam falsas.
					k_down = true;
					k_left = false;
					k_right = false;
					break;
				case KeyEvent.VK_LEFT:// caso pressione a tecla para esquerda.
					if(k_right) {// se a snake já tiver indo para a direita.
						k_left = false;//a tecla para a esquerda não fará nada.
					}
					// Se não, a snake vai para a esquerda e as outras direções ficam falsas.
					k_left = true;
					k_down = false;
					k_up = false;
					break;
				case KeyEvent.VK_RIGHT:// caso pressione a tecla para a direita.
					if(k_left) {// se a snake já tiver indo para a esquerda.
						k_right = false;//a tecla para a direita não fará nada.
					}
					// Se não, a snake vai para a direita e as outras direções ficam falsas.
					k_right = true;
					k_down = false;
					k_up = false;
					break;
				case KeyEvent.VK_ENTER:// caso pressione a tecla enter.
					if (gameOver) { //Se o jogo tiver em game over
						k_enter = true; //A tecla enter é pressionada
						resetGame(); //O jogo é resetado
					}
					//Se não, o Enter não funciona.
				}
			}
		});
	}
	
	// configura o reinicio do jogo.
	public void resetGame() {
		k_enter = false; //O enter fica falso
		gameOver = false; //O jogo deixa de estar no estado de game over.
		frameSpeed = 4.0; //O frame do jogo volta a 4fps.
		score = 0; //A pontuação do jogador passa a zero.
		k_right = false; //As teclas ficam falsas, deixando a snake parada
		k_up = false;//As teclas ficam falsas, deixando a snake parada
		k_down = false;//As teclas ficam falsas, deixando a snake parada
		k_left = false;//As teclas ficam falsas, deixando a snake parada
		snake = new Snake(); //Instancia uma nova Snake.
	}

}