import javax.swing.*;

public class FlappyBird{
	public static void main(String[] args){
		int fwidth=360;
		int fheight=640;
		
		JFrame frame = new JFrame("Flappy Bird");
		
		frame.setSize(fwidth,fheight);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Run r = new Run();
		frame.add(r);
		frame.pack();
		r.requestFocus();
		frame.setVisible(true);
	}
}
