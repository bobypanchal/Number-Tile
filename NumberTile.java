import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class NumberTile extends JFrame{
	JPanel top = null;
	JPanel center = null;
	JPanel bottom = null;
	JButton[][] button = null;
	JButton reset = null;
	ImageIcon img = null;
	List<List<Integer>> arr = new ArrayList<List<Integer>>();
	JLabel win = null;
	JLabel countStep = null;
	int totalStep;
	public NumberTile() {
		setSize(400,400);
		setTitle("NUMBER TILE'S");

		//TOP
		top = new JPanel();
		add(top, BorderLayout.NORTH);
		win = new JLabel();
		win.setText("GAME IS ON");
		top.add(win);
		reset = new JButton("Reset");
		top.add(reset);

		//CENTER
		center = new JPanel();
		add(center, BorderLayout.CENTER);
		center.setSize(300,300);
		center.setLayout(new GridLayout(3,3));
		button = new JButton[3][3];

		//BOTTOM
		bottom = new JPanel();
		add(bottom, BorderLayout.SOUTH);
		countStep = new JLabel();
		countStep.setText("No. of Step : 0");
		bottom.add(countStep);
		totalStep = 0;

		//fist 8 button arr shuffle last add 0 for black button
		List<Integer> tempArr = new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6,7,8));
		Collections.shuffle(tempArr);
		for(int i=0; i<9; i=i+3)
			arr.add(new ArrayList<Integer>(Arrays.asList(tempArr.get(i),tempArr.get(i+1),tempArr.get(i+2))));

		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++){
				button[i][j] = new JButton();
				if(arr.get(i).get(j) != 0){
					img = new ImageIcon("image\\" + arr.get(i).get(j) + ".png");
					button[i][j].setIcon(img);
				}
				center.add(button[i][j]);
			}
		}

		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++){
				final int finalRow = i;
				final int finalCol = j;
				button[i][j].addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						change(finalRow, finalCol);
					}
				});
			}
		}
		//Reset function
		reset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				for(int i=0; i<3; i++){
					for(int j=0; j<3; j++){
						button[i][j].setIcon(null);
						button[i][j].setEnabled(true);
						final List<Integer> temp = new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6,7,8));
						Collections.shuffle(temp);
						for(int x=0; x<9; x=x+3)
							arr.add(new ArrayList<Integer>(Arrays.asList(temp.get(x),temp.get(x+1),temp.get(x+2))));
						win.setText("GAME IS ON");
						countStep.setText("No. of Step : 0");
						top.setBackground(null);
					}
				}
			}
		});
		
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void change(int i, int j) {
		int a = i;
		int b = j;
		if(j>0 && arr.get(i).get(j-1) == 0)
			b = j-1;
		else if(j<2 && arr.get(i).get(j+1) == 0)
			b = j+1;
		else if(i>0 && arr.get(i-1).get(j) == 0)
			a = i-1;
		else if(i<2 && arr.get(i+1).get(j) == 0)
			a = i+1;

		if(!(i == a && j == b)){
			button[a][b].setIcon(button[i][j].getIcon());
			button[i][j].setIcon(null);

			arr.get(a).set(b, arr.get(i).get(j));
			arr.get(i).set(j, 0);
			totalStep++;
			countStep.setText("No. of Step : " + totalStep);
		}

		final_check();
	}

	public void final_check(){
		int count = 1;
		for(int i=0; i<3; i++) 
			for(int j=0; j<3; j++) 
				if((count%9) == arr.get(i).get(j)) 
					count++;
				else
					return;

		if(count == 10) {
			win.setText("YOU WIN");
			top.setBackground(Color.GREEN);
			for(int i=0; i<3; i++)
				for(int j=0; j<3; j++)
					button[i][j].setEnabled(false);
		}
	}
	public static void main(String []a) throws Exception{
		new NumberTile();
	}
}
