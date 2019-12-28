package java1912;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SaoLei extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2655849741769066218L;
	private static int x = 10, y = 10;				//雪地面积
	private static int tx = 10, ty = 10;
	private static int leinum = 0;					//王晗数量
	private static int tleinum = 10;
	private static JFrame frame 	= new JFrame("扫雪");
	private static JPanel mainpan 	= new JPanel(new BorderLayout());
	private static SaoLei panel 	= new SaoLei();
	private static JMenuBar bar 	= new JMenuBar();
	private static JButton button 	= new JButton("设置");
	private static JLabel nonlabel 	= new JLabel("         ");
	private static JLabel timelabel = new JLabel("Time");
	private static ImageIcon image 	= new ImageIcon("D:\\JavaWorkSpace\\MyJava\\src\\java1912\\snowflakesmall.png");
	
	//构造方法
	private SaoLei() {
		super();
	}
	
	//在每次修改参数时重新绘制的方法
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		System.out.println("tx:"+tx+" ty"+ty+" tlei:"+tleinum);
		System.out.println("x:"+x+" y"+y+" lei:"+leinum);
		if(tx!=x||ty!=y||tleinum!=leinum) {
			System.out.println("进入");
			x = tx;
			y = ty;
			leinum = tleinum;
//			g.clearRect(0, 0, this.getWidth(), this.getHeight());
			this.removeAll();
			//行数,列数
			LeiButton.newLeiButton(x+2, y+2);
			LeiButton.setLei(leinum);
			this.setLayout(new GridLayout(LeiButton.numx-2,LeiButton.numy-2));
			for(int i=1;i<LeiButton.numx-1;i++) {
				for(int j=1;j<LeiButton.numy-1;j++) {
					this.add(LeiButton.getButton(i, j));
				}
			}
			this.repaint();
		}
	}
	
	//初始化界面
	public static void newFrame() {
		button.setFocusPainted(false);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame fra = new JFrame("设置");
				JPanel pan = new JPanel();
				JLabel lab1 = new JLabel("雪地面积:");
				JLabel lab2 = new JLabel("要把多少头王晗埋进雪地里?");
				JButton button = new JButton("确定");
				JTextField textX = new JTextField();
				JTextField textY = new JTextField();
				JTextField textLei = new JTextField();
				button.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							//获取tx, ty, tleinum
							tx = Integer.parseInt(textX.getText());
							ty = Integer.parseInt(textY.getText());
							tleinum = Integer.parseInt(textLei.getText());
							fra.dispose();
							frame.repaint();
						} catch (NumberFormatException e1) {
							e1.printStackTrace();
						} 
					}
				});
				pan.setLayout(null);
				textX.setBounds(120, 50, 50, 30);
				textY.setBounds(230, 50, 50, 30);
				textLei.setBounds(230, 110, 50, 30);
				lab1.setBounds(50, 50, 70, 30);
				lab2.setBounds(50, 110, 170, 30);
				button.setBounds(50, 150, 60, 30);
				button.setFocusPainted(false);
				fra.add(pan);
				pan.add(textX);
				pan.add(textY);
				pan.add(textLei);
				pan.add(lab1);
				pan.add(lab2);
				pan.add(button);
				fra.setIconImage(image.getImage());
				fra.setBounds(250, 250, 500, 500);
				fra.setVisible(true);
			}
		});
		bar.add(button);
		bar.add(nonlabel);
		bar.add(timelabel);
		mainpan.add("North", bar);
		mainpan.add("Center", panel);
		frame.setIconImage(image.getImage());
		frame.add(mainpan);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setBounds(200, 200, 600, 600);
	}
	
	//
	public static void setLeinum(int x) {
		leinum = x;
	}
	
	public static SaoLei getPanel() {
		return panel;
	}
	
	public static void main(String[] args) {
		newFrame();
	}
}
