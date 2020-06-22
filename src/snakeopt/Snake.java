package snakeopt;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Snake {

	public List<Node> nodeSnake; // Declara a lista de nós que vai resultar na snake.
	public int snakeSpeed = 10; // velocidade da serpente.

	public Snake() {
		nodeSnake = new ArrayList<Node>(); // instancia a lista de nós que vai resultar na snake.
		nodeSnake.add(new Node(0, 0)); // Adiciona um novo nó na Snake
		nodeSnake.add(new Node(0, 0)); // Adiciona um novo nó na Snake
		nodeSnake.add(new Node(0, 0)); // Adiciona um novo nó na Snake
	}
	
	//função de update da snake que será colocada no update do game.
	public void update(Snake snake, Food food) {
		snakeAlive(snake); //função que une o corpo da serpente à cabeça
		wallCollision(snake); //Função de colisão com a parede.
		eatFood(snake, food); //Função de comer e gerar comida.
	}
	

	// Função para o restante da serpente acompanhar a cabeça.
	public void snakeAlive(Snake snake) {
		for (int i = snake.nodeSnake.size() - 1; i > 0; i--) { // For que percorre todos os nós da lista.
			snake.nodeSnake.get(i).positionX = snake.nodeSnake.get(i - 1).positionX; // determina que os nós na posição
																						// X siga a anterior.
			snake.nodeSnake.get(i).positionY = snake.nodeSnake.get(i - 1).positionY; // determina que os nós na posição
																						// Y siga a anterior.
		}
	}

	// Desenha a Snake
	public void draw(Snake snake, Graphics g) {
		for (int i = 0; i < snake.nodeSnake.size(); i++) { // Percorre todo a lista de nós da snake
			g.setColor(Color.green); // seleciona a cor verde.
			g.fillRect(snake.nodeSnake.get(i).positionX, snake.nodeSnake.get(i).positionY, 10, 10);// Pinta o nó de
																									// verde no tamanho
																									// de 10px.
		}
	}

	// Função de comer com a contagem de pontos e a geração de uma nova comida.
	public void eatFood(Snake snake, Food food) {
		if (new Rectangle(snake.nodeSnake.get(0).positionX, snake.nodeSnake.get(0).positionY, 10, 10)
				.intersects(new Rectangle(food.foodX, food.foodY, 10, 10))) {//estrutura de decisão verificando se a cabeça da snake encostou na comida.
			snake.nodeSnake.add(new Node(snake.nodeSnake.get(0).positionX, snake.nodeSnake.get(0).positionY)); //Caso positivo, adiciona um nó na snake.
			Game.score += 5; //O jogador ganha 5 pontos.
			Game.frameSpeed = Game.frameSpeed + 0.5; //A velocidade do jogo aumenta em 0.5 frame.
			System.out.println("Pontos: " + Game.score); //Imprime no console a pontuação
			System.out.println("Frame/s: " + Game.frameSpeed.intValue()); //Imprime no console os Frames
			food.generate(); //Gera uma nova comida.
		}
	}

	// Configura a colisão da Snake com o próprio corpo
	public void selfCollision(Snake snake) {

		for (int i = 0; i < snake.nodeSnake.size(); i++) { //Percorre a snake
			if (i == 0) //Quando chega na cabeça ele pula.
				continue;
			Rectangle snakeHead = new Rectangle(snake.nodeSnake.get(0).positionX, snake.nodeSnake.get(0).positionY, 10, 10); //Coloca a posição 0 da Lista, ou seja, a cabeça da Snake numa variável do tipo Rectangle.
			Rectangle snakeBody = new Rectangle(snake.nodeSnake.get(i).positionX, snake.nodeSnake.get(i).positionY, 10, 10); //Coloca as demais posições em outra variável.
			// colisão com o próprio corpo.
			if (snakeHead.intersects(snakeBody)) {
				Game.gameOver = true; //Caso haja a colisão da cabeça com o corpo o jogo acaba.
			}
		}
	}

	// Configura os movimentos da Snake
	public void snakeMove(Snake snake) {
		// Movimentação da serpente com as teclas.
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

	// Permite que a snake atravesse a parede - Não utilizado.
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

	//Função de colisão da Snake com a parede
	public void wallCollision(Snake snake) {
		if (snake.nodeSnake.get(0).positionX < 0) { //caso a cabeça da Snake encoste na parede esquerda o jogo acaba.
			Game.gameOver = true;
		} else if (snake.nodeSnake.get(0).positionX >= Board.WIDTH) {//caso a cabeça da Snake encoste na parede direita o jogo acaba.
			Game.gameOver = true;
		} else if (snake.nodeSnake.get(0).positionY  < 0) { //caso a cabeça da Snake encoste na parede de cima o jogo acaba.
			Game.gameOver = true;
		} else if (snake.nodeSnake.get(0).positionY >= Board.HEIGHT) {//caso a cabeça da Snake encoste na parede de baixo o jogo acaba.
			Game.gameOver = true;
		}
	}

}
