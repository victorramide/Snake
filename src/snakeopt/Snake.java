package snakeopt;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Snake {

	public List<Node> nodeSnake; // Declara a lista de n�s que vai resultar na snake.
	public int snakeSpeed = 10; // velocidade da serpente.

	public Snake() {
		nodeSnake = new ArrayList<Node>(); // instancia a lista de n�s que vai resultar na snake.
		nodeSnake.add(new Node(0, 0)); // Adiciona um novo n� na Snake
		nodeSnake.add(new Node(0, 0)); // Adiciona um novo n� na Snake
		nodeSnake.add(new Node(0, 0)); // Adiciona um novo n� na Snake
	}
	
	//fun��o de update da snake que ser� colocada no update do game.
	public void update(Snake snake, Food food) {
		snakeAlive(snake); //fun��o que une o corpo da serpente � cabe�a
		wallCollision(snake); //Fun��o de colis�o com a parede.
		eatFood(snake, food); //Fun��o de comer e gerar comida.
	}
	

	// Fun��o para o restante da serpente acompanhar a cabe�a.
	public void snakeAlive(Snake snake) {
		for (int i = snake.nodeSnake.size() - 1; i > 0; i--) { // For que percorre todos os n�s da lista.
			snake.nodeSnake.get(i).positionX = snake.nodeSnake.get(i - 1).positionX; // determina que os n�s na posi��o
																						// X siga a anterior.
			snake.nodeSnake.get(i).positionY = snake.nodeSnake.get(i - 1).positionY; // determina que os n�s na posi��o
																						// Y siga a anterior.
		}
	}

	// Desenha a Snake
	public void draw(Snake snake, Graphics g) {
		for (int i = 0; i < snake.nodeSnake.size(); i++) { // Percorre todo a lista de n�s da snake
			g.setColor(Color.green); // seleciona a cor verde.
			g.fillRect(snake.nodeSnake.get(i).positionX, snake.nodeSnake.get(i).positionY, 10, 10);// Pinta o n� de
																									// verde no tamanho
																									// de 10px.
		}
	}

	// Fun��o de comer com a contagem de pontos e a gera��o de uma nova comida.
	public void eatFood(Snake snake, Food food) {
		if (new Rectangle(snake.nodeSnake.get(0).positionX, snake.nodeSnake.get(0).positionY, 10, 10)
				.intersects(new Rectangle(food.foodX, food.foodY, 10, 10))) {//estrutura de decis�o verificando se a cabe�a da snake encostou na comida.
			snake.nodeSnake.add(new Node(snake.nodeSnake.get(0).positionX, snake.nodeSnake.get(0).positionY)); //Caso positivo, adiciona um n� na snake.
			Game.score += 5; //O jogador ganha 5 pontos.
			Game.frameSpeed = Game.frameSpeed + 0.5; //A velocidade do jogo aumenta em 0.5 frame.
			System.out.println("Pontos: " + Game.score); //Imprime no console a pontua��o
			System.out.println("Frame/s: " + Game.frameSpeed.intValue()); //Imprime no console os Frames
			food.generate(); //Gera uma nova comida.
		}
	}

	// Configura a colis�o da Snake com o pr�prio corpo
	public void selfCollision(Snake snake) {

		for (int i = 0; i < snake.nodeSnake.size(); i++) { //Percorre a snake
			if (i == 0) //Quando chega na cabe�a ele pula.
				continue;
			Rectangle snakeHead = new Rectangle(snake.nodeSnake.get(0).positionX, snake.nodeSnake.get(0).positionY, 10, 10); //Coloca a posi��o 0 da Lista, ou seja, a cabe�a da Snake numa vari�vel do tipo Rectangle.
			Rectangle snakeBody = new Rectangle(snake.nodeSnake.get(i).positionX, snake.nodeSnake.get(i).positionY, 10, 10); //Coloca as demais posi��es em outra vari�vel.
			// colis�o com o pr�prio corpo.
			if (snakeHead.intersects(snakeBody)) {
				Game.gameOver = true; //Caso haja a colis�o da cabe�a com o corpo o jogo acaba.
			}
		}
	}

	// Configura os movimentos da Snake
	public void snakeMove(Snake snake) {
		// Movimenta��o da serpente com as teclas.
		if (Game.k_right) {
			snake.nodeSnake.get(0).positionX += snake.snakeSpeed; //Caso o movimento seja para a direita a Snake anda 10px.
			selfCollision(snake);
		} else if (Game.k_left) {
			snake.nodeSnake.get(0).positionX -= snake.snakeSpeed;//Caso o movimento seja para a esquerda a Snake anda 10px.
			selfCollision(snake);
		} else if (Game.k_up) {
			snake.nodeSnake.get(0).positionY -= snake.snakeSpeed; //Caso o movimento seja para cima a Snake anda 10px.
			selfCollision(snake);
		} else if (Game.k_down) {
			snake.nodeSnake.get(0).positionY += snake.snakeSpeed; //Caso o movimento seja para baixo a Snake anda 10px.
			selfCollision(snake);
		}
	}

	// Permite que a snake atravesse a parede - N�o utilizado.
	public void snakeCrossWall(Snake snake) {
		// mecanica de atravessar parede, caso a snake chegue a um extremo ele reaparece no outro oposto.
		if (snake.nodeSnake.get(0).positionX < 0) {
			snake.nodeSnake.get(0).positionX = Board.WIDTH;
		} else if (snake.nodeSnake.get(0).positionX >= Board.WIDTH) {
			snake.nodeSnake.get(0).positionX = 0;
		} else if (snake.nodeSnake.get(0).positionY < 0) {
			snake.nodeSnake.get(0).positionY = Board.HEIGHT;
		} else if (snake.nodeSnake.get(0).positionY >= Board.HEIGHT) {
			snake.nodeSnake.get(0).positionY = 0;
		}
	}

	//Fun��o de colis�o da Snake com a parede
	public void wallCollision(Snake snake) {
		if (snake.nodeSnake.get(0).positionX < 0) { //caso a cabe�a da Snake encoste na parede esquerda o jogo acaba.
			Game.gameOver = true;
		} else if (snake.nodeSnake.get(0).positionX >= Board.WIDTH) {//caso a cabe�a da Snake encoste na parede direita o jogo acaba.
			Game.gameOver = true;
		} else if (snake.nodeSnake.get(0).positionY  < 0) { //caso a cabe�a da Snake encoste na parede de cima o jogo acaba.
			Game.gameOver = true;
		} else if (snake.nodeSnake.get(0).positionY >= Board.HEIGHT) {//caso a cabe�a da Snake encoste na parede de baixo o jogo acaba.
			Game.gameOver = true;
		}
	}

}
