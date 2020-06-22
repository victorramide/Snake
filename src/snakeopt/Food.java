package snakeopt;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Food {

	public int foodX; //declara a posição horizontal da comida.
	public int foodY; //declara a posição vertical da comida.
	
	public Food() {
		generate(); //Gera uma nova comida no mapa no momento em que instancia.
	}
	
	//Função que desenha a comida na tela.
	public void draw(Food food, Graphics g) { 
		g.setColor(Color.getHSBColor(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255))); //Seleciona a cor da comida, no caso, aleatória.
		g.fillRect(food.foodX, food.foodY, 7, 7); //pinta a comida com o tamanho de 7x7px.
	}
	
	//Gera uma nova comida no mapa
	public void generate() {
		foodX = new Random().nextInt(Board.WIDTH - 10); //Gera de maneira aleatoria a posição horizontal da comida subtraindo 10px do valor para impedir o nascimento no limite da janela.
		foodY = new Random().nextInt(Board.HEIGHT - 10);//Gera de maneira aleatoria a posição vertical da comida subtraindo 10px do valor para impedir o nascimento no limite da janela.
	}
}
