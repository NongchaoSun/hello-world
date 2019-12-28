package java1912;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LeiButton extends JButton implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3486720637865380589L;
	private int x,y;
	static int numx=12, numy=12;			//按钮数量
	private boolean isOpen = false;			//按钮是否点开
	static int openNum = 0;
	private static int leiNum ;
	private static boolean died = false;
	private static boolean gameOver = false;
	private static int[][] lei;
	private static Map<Integer, LeiButton> map = new HashMap<Integer, LeiButton>();
	private static final ImageIcon wanghan = new ImageIcon("D:\\JavaWorkSpace\\MyJava\\src\\java1912\\wanghan.jpg");
	private static final ImageIcon[] snowFlakes = {new ImageIcon("D:\\JavaWorkSpace\\MyJava\\src\\java1912\\snowflake1.png"),
			new ImageIcon("D:\\JavaWorkSpace\\MyJava\\src\\java1912\\snowflake2.png"),
			new ImageIcon("D:\\JavaWorkSpace\\MyJava\\src\\java1912\\snowflake3.png"),
			new ImageIcon("D:\\JavaWorkSpace\\MyJava\\src\\java1912\\snowflake4.png")
	};
	
	//类初始化方法
	public static void newLeiButton(int numx, int numy) {
		if(numx>5 && numx<35				//只处理合法数据
				&& numy>5 && numy<35) {		//存在初值12,12(10行10列)
			LeiButton.numx = numx;
			LeiButton.numy = numy;
		}
		lei = new int[numx][numy];
		map.clear();
		LeiButton.died = false;
		LeiButton.gameOver = false;
		Random rand = new Random(System.currentTimeMillis());
		for(int i=0;i<LeiButton.numx;i++) {
			for(int j=0;j<LeiButton.numy;j++) {
				LeiButton button = new LeiButton(i, j);
				button.setFocusPainted(false);
				button.setBackground(Color.WHITE);
				button.setIcon(snowFlakes[rand.nextInt(4)]);
				map.put(i*100+j, button);
			}
		}
	}
	
	//构造方法
	private LeiButton(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		this.addActionListener(this);
	}
	
	//初始化设置雷
	public static void setLei(int n) {
		if(n<3||n>numx*numy*7/10)		//数量不多于70%
			n = 10;
		leiNum = n;
		Random rand = new Random(System.currentTimeMillis());
		int xrand, yrand;
		for(int i=0;i<n;i++) {
			xrand = rand.nextInt(LeiButton.numx-2) + 1;
			yrand = rand.nextInt(LeiButton.numy-2) + 1;
			while(lei[xrand][yrand]!=0) {
				xrand = rand.nextInt(LeiButton.numx-2) + 1;
				yrand = rand.nextInt(LeiButton.numy-2) + 1;
			}
			lei[xrand][yrand]=9;
		}
		for(int i=1;i<numx-1;i++) {
			for(int j=1;j<numy-1;j++) {
				setNum(i, j);
			}
		}
		
	}
	
	//将所有按钮翻开
	private static void openAllButton() {
		for(int i=0;i<numx;i++) {
			for(int j=0;j<numy;j++) {
				if(!LeiButton.getButton(i, j).isOpen) {
					boom(i, j);
				}
			}
		}
	}
	
	//胜利提示框
	private static void win() {
		JFrame frame = new JFrame("提示信息");
		JPanel panel = new JPanel();
		JLabel label = new JLabel("<html><body><p>太好了,我们将所有老母猪都留在了雪地里</p></body></html>");
		JButton button1 = new JButton("再来一局");
		JButton button2 = new JButton("退出游戏");
		Font font = new Font("宋体",Font.PLAIN,20);
		button1.setFocusPainted(false);
		button2.setFocusPainted(false);
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SaoLei.setLeinum(0);
				SaoLei.getPanel().repaint();
				frame.dispose();
			}
		});
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		label.setFont(font);
		panel.setLayout(null);
		label.setBounds(70, 70, 250, 60);
		button1.setBounds(70, 150, 90, 40);
		button2.setBounds(200, 150, 90, 40);
		frame.add(panel);
		panel.add(label);
		panel.add(button1);
		panel.add(button2);
		frame.setVisible(true);
		frame.setBounds(300, 300, 400, 300);
	}
	
	//失败提示框
	private static void died() {
		died = false;
		JFrame frame = new JFrame("FBI WARNING");
		JPanel panel = new JPanel();
		JLabel label = new JLabel("<html><body><p>你竟然把王晗扫出来了,你这个der货</p></body></html>");
		JButton but1 = new JButton("重新开始");
		JButton but2 = new JButton("退出游戏");
		Font font = new Font("宋体",Font.PLAIN,23);
		label.setFont(font);
		panel.setLayout(null);
		label.setBounds(70, 70, 250, 60);
		but1.setBounds(70, 150, 90, 40);
		but2.setBounds(200, 150, 90, 40);
		but1.setFocusPainted(false);
		but2.setFocusPainted(false);
		but1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SaoLei.setLeinum(0);
				SaoLei.getPanel().repaint();
				frame.dispose();
			}
		});
		but2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		panel.add(label);
		panel.add(but1);
		panel.add(but2);
		frame.add(panel);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setBounds(300, 300, 400, 300);
	}
	
	//获取指定按钮
	public static LeiButton getButton(int x, int y) {
		return map.get(x*100+y);
	}
	
	//自动调用,计算数组数据
	static private void setNum(int x, int y) {
		int i=0;
		if(lei[x][y]==9)
			return;
		else {
			if(lei[x-1][y-1]==9)
				i++;
			if(lei[x-1][y]==9)
				i++;
			if(lei[x-1][y+1]==9)
				i++;
			if(lei[x][y-1]==9)
				i++;
			if(lei[x][y+1]==9)
				i++;
			if(lei[x+1][y-1]==9)
				i++;
			if(lei[x+1][y]==9)
				i++;
			if(lei[x+1][y+1]==9)
				i++;
			lei[x][y] = i;
		}
	}
	
	//按钮按下处理
	private static void boom(int i, int j) {
		if(i==0||i==numx-1)
			return;
		if(j==0||j==numy-1)
			return;
		if(LeiButton.getButton(i, j).isOpen)
			return;
		if(LeiButton.lei[i][j]!=9 && !LeiButton.getButton(i, j).isOpen) {
			openNum++;
			LeiButton.getButton(i, j).setIcon(null);
		}
		LeiButton.getButton(i, j).setBackground(Color.GRAY);
		LeiButton.getButton(i, j).addActionListener(null);
		LeiButton.getButton(i, j).isOpen = true;
		if(LeiButton.lei[i][j] == 9) {
			died = true;
			LeiButton.getButton(i, j).setIcon(wanghan);
//			LeiButton.getButton(i, j).setText("雷");
		}else if(LeiButton.lei[i][j] != 0) {
			LeiButton.getButton(i, j).setText(""+LeiButton.lei[i][j]);
		}else if(LeiButton.lei[i][j] == 0) {
			boom(i-1, j);
			boom(i+1, j);
			boom(i, j-1);
			boom(i, j+1);
			boom(i-1, j-1);
			boom(i+1, j+1);
			boom(i-1, j+1);
			boom(i+1, j-1);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		boom(this.x, this.y);
		if(!gameOver) {
			if(died) {
				openAllButton();
				died();
				openNum = 0;
				gameOver = true;
			}
			if((numx-2)*(numy-2)-leiNum==openNum && !gameOver) {
				win();
				openNum = 0;
				gameOver = true;
			}
		}
		
	}

}
