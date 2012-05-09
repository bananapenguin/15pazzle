import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PazzlePanel extends JPanel {

	class Key extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			change(e);
		}
	}

	class Mouse extends MouseAdapter{
		public void mouseClicked(MouseEvent e) {
			change(e);
		}
	}

	Container cnt;
	Image img;

	int[][] box;

	int spaceX, spaceY;

	JLabel state;
	int counter = 0;

	public PazzlePanel(){
		img = new ImageIcon("hit.png").getImage();
		box = new int[4][4];
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
				box[i][j] = 4*i+j;
			}
		}
		addKeyListener(new Key());
		addMouseListener(new Mouse());
		spaceX = 3;
		spaceY = 3;
	}

	public void shuffle(){
		for(int i=0; i<100; i++){
			int tmp;
			switch ((int)(4*Math.random())) {
			case 0:
				if(spaceY!=3){
					tmp = box[spaceY+1][spaceX];
					box[spaceY+1][spaceX] = 15;
					box[spaceY][spaceX] = tmp;
					spaceY++;
				}
				break;
			case 1:
				if(spaceY!=0){
					tmp = box[spaceY-1][spaceX];
					box[spaceY-1][spaceX] = 15;
					box[spaceY][spaceX] = tmp;
					spaceY--;
				}
				break;
			case 2:
				if(spaceX!=3){
					tmp = box[spaceY][spaceX+1];
					box[spaceY][spaceX+1] = 15;
					box[spaceY][spaceX] = tmp;
					spaceX++;
				}
				break;
			case 3:
				if(spaceX!=0){
					tmp = box[spaceY][spaceX-1];
					box[spaceY][spaceX-1] = 15;
					box[spaceY][spaceX] = tmp;
					spaceX--;
				}
				break;
			default:
			}
			repaint();
		}
	}

	public boolean isComletion(){
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
				if(box[i][j]!=4*i+j)
					return false;
			}
		}
		return true;
	}

	public void change(KeyEvent e){
		int key = e.getKeyCode();
		int tmp;
		switch (key) {
		case KeyEvent.VK_UP:
			if(spaceY!=3){
				tmp = box[spaceY+1][spaceX];
				box[spaceY+1][spaceX] = 15;
				box[spaceY][spaceX] = tmp;
				spaceY++;
				updataLabel();
			}
			break;
		case KeyEvent.VK_DOWN:
			if(spaceY!=0){
				tmp = box[spaceY-1][spaceX];
				box[spaceY-1][spaceX] = 15;
				box[spaceY][spaceX] = tmp;
				spaceY--;
				updataLabel();
			}
			break;
		case KeyEvent.VK_LEFT:
			if(spaceX!=3){
				tmp = box[spaceY][spaceX+1];
				box[spaceY][spaceX+1] = 15;
				box[spaceY][spaceX] = tmp;
				spaceX++;
				updataLabel();
			}
			break;
		case KeyEvent.VK_RIGHT:
			if(spaceX!=0){
				tmp = box[spaceY][spaceX-1];
				box[spaceY][spaceX-1] = 15;
				box[spaceY][spaceX] = tmp;
				spaceX--;
				updataLabel();
			}
			break;
		default:
		}
		repaint();

	}

	public void change(MouseEvent e){
		int tmp;
		int X = e.getX()/50;
		int Y = e.getY()/50;
		if(Y!=3&&box[Y+1][X]==15){
			tmp = box[Y][X];
			box[Y][X] = 15;
			box[Y+1][X] = tmp;
			spaceY--;
			updataLabel();
			repaint();
		}else if(Y!=0&&box[Y-1][X]==15){
			tmp = box[Y][X];
			box[Y][X] = 15;
			box[Y-1][X] = tmp;
			spaceY++;
			updataLabel();
			repaint();
		}else if(X!=0&&box[Y][X-1]==15){
			tmp = box[Y][X];
			box[Y][X] = 15;
			box[Y][X-1] = tmp;
			spaceX++;
			updataLabel();
			repaint();
		}else if(X!=3&&box[Y][X+1]==15){
			tmp = box[Y][X];
			box[Y][X] = 15;
			box[Y][X+1] = tmp;
			spaceX--;
			updataLabel();
			repaint();
		}
//		repaint();

	}

	public void paint(Graphics g){
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, 200, 200);
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
				if(box[i][j]!=15)
					g.drawImage(img,
							j*50,
							i*50,
							(j+1)*50,
							(i+1)*50,
							(box[i][j]%4)*50,
							(box[i][j]/4)*50,
							(box[i][j]%4+1)*50,
							(box[i][j]/4+1)*50,
							this);
			}
		}
		if(isComletion()){
			g.drawImage(img, 150, 150, 200, 200, 150, 150, 200, 200, this);
			if(counter>0){
				state.setForeground(Color.BLUE);
				state.setText(state.getText()+" Complete!");	
			}
		}
	}

	public void setStateLabel(JLabel state){
		this.state = state;
	}

	public void updataLabel(){
		counter++;
		state.setForeground(Color.BLACK);
		state.setText("move: "+ counter);
	}

	public void showPoint(){
		System.out.println("("+spaceX+","+spaceY+")");
	}

	public void showTable(){
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
				System.out.print(box[i][j] + "\t");
			}
			System.out.println();
		}
	}
	
	public void clear(){
		String str = "完成です。おめでとう！\n" +
		"移動させた回数は"+counter+"回です。";
		JOptionPane.showConfirmDialog(this,
				str,
				"help about 15 Pazzle",
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE);
	}
}
