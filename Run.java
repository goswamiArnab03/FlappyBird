import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Run extends JPanel implements ActionListener,KeyListener{ // KeyListener is to perform access to a key press
	int fwidth=360;
	int fheight=640;
	
	Image bgimg;
	Image birdimg;
	Image toppipe;
	Image bottompipe;
	
	boolean gameOver = false;
	double score=0;
	
	//bird
	int birdx=fwidth/8;
	int birdy=fheight/2;
	int bwidth=34;//These all are in px
	int bheight=24;
	
	
	class Bird{
		int x = birdx;
		int y = birdy;
		int width = bwidth;
		int height = bheight;
		Image img;
		Bird(Image img){
			this.img=img;
		}
	}
	Bird bird;
	
	//pipe
	int pipex=fwidth;
	int pipey=0;
	int pipeWidth=64;
	int pipeHeight=512; //all scaled by 1/6
	
	class Pipe{
		int x=pipex;
		int y=pipey;
		int width=pipeWidth;
		int height=pipeHeight;
		boolean pass=false;
		Image img;
		
		Pipe(Image img){
			this.img=img;
		}
	
	}
	
	ArrayList<Pipe> pipes = new ArrayList<Pipe>();//to store pipes
	Random rand = new Random();
	
	javax.swing.Timer gameLoop;
	javax.swing.Timer pipesTimer;
	
	int vely=-11;//y-axis
	int velx=1;//x-axis downwards for the gravity
	int pipe_velx=-5;//pipe velocity x axis
	
	
	
	Run(){
		setPreferredSize(new Dimension(fwidth,fheight));
		setBackground(Color.blue);
		
		setFocusable(true);//key (w) focused in this obj
		addKeyListener(this);//To make sure three functions of KeyListener executes correctly
		
		bgimg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
		birdimg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
		toppipe = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
	
		bottompipe = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();
		
		bird = new Bird(birdimg);
		gameLoop = new javax.swing.Timer(1000/60,this);/*1000 ms = 1 sec ; 1000/60 => 60 frames per second; this --> refers to the flappy bird class*/ //or second argument is for an action
		gameLoop.start();//continiously draw the game
		pipesTimer = new javax.swing.Timer (1500,new ActionListener(){
				public void actionPerformed(ActionEvent e){
					placePipes();
				}
			
			});
		pipesTimer.start();
		
	}
	
	public void placePipes(){
		int randY=(int)(pipey-(pipeHeight/4)-Math.random()*(pipeHeight/2)); 
		int space=fheight/4;
		Pipe topPipe=new Pipe(toppipe);
		topPipe.y=randY;
		pipes.add(topPipe);
		
		Pipe bottomPipe=new Pipe(bottompipe);
		bottomPipe.y=topPipe.y+pipeHeight+space;
		pipes.add(bottomPipe);
	}
	
	public void draw(Graphics g){
		g.drawImage(bgimg , 0 , 0 , fwidth,fheight,null);
		g.drawImage(bird.img,bird.x,bird.y,bird.width,bird.height,null);
		
		for(int i=0;i<pipes.size();i++){
			Pipe p=pipes.get(i);
			g.drawImage(p.img,p.x,p.y,p.width,p.height,null);
		}
		
		g.setColor(Color.black);
		g.setFont(new Font("Arial",Font.PLAIN,20));
		if(gameOver){
			g.drawString("Game over : "+String.valueOf((int)score),5,20);
		}
		else{
			g.drawString(String.valueOf((int)score),5,20);
		}
			
	}
	
	public void paint(Graphics g){
		super.paintComponent(g);
		draw(g);
	}
	public void move(){
		vely+=velx;
		bird.y+=vely;
		bird.y=Math.max(bird.y,0);
		
		for(int i=0;i<pipes.size();i++){
			Pipe p=pipes.get(i);
			p.x+=pipe_velx;
			if(!p.pass && bird.x>p.x+p.width){
				p.pass=true;
				score+=0.5;
			}
			if(collision(bird,p)){
				gameOver=true;
			}
		
		}
		if(bird.y > fheight){
			gameOver=true;
		}
	}
	public void actionPerformed(ActionEvent e){
		move();
		repaint();
		
		if(gameOver){
			gameLoop.stop();
			pipesTimer.stop();
		}
	}
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_W){//if pressed key is w change velocity Y
			vely=-11;
		}
	}
	public void keyTyped(KeyEvent e){
		
	}
	public void keyReleased(KeyEvent e){
		
	}
	
	public boolean collision(Bird b,Pipe p){
		return b.x<p.x+p.width &&
		b.y<p.y+p.height &&
		b.x+b.width>p.x &&
		b.y+b.height>p.y;
	}
	
}
