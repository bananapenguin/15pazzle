import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class FifteenPazzle extends JApplet {

	PazzlePanel mp;
	Container cnt;
	JPanel p1, p2;
	JLabel title, state;
	JButton shuffle, help;
	JOptionPane helpPane;

	public void init(){
		cnt = getContentPane();
		mp = new PazzlePanel();
		p1 = new JPanel();
		p1.setLayout(new BorderLayout());
		p2 = new JPanel();
		title = new JLabel("15 Pazzle");
		state = new JLabel("Click the shuffle button to start!");
		shuffle = new JButton("shuffle");
		help = new JButton("help");

		setSize(200, 268);
		mp.setMinimumSize(new Dimension(200, 200));
		mp.setStateLabel(state);
		shuffle.addActionListener(new Action());
		help.addActionListener(new Action());
		
		title.setHorizontalAlignment(SwingConstants.CENTER);

		cnt.setLayout(new BorderLayout());
		p1.add(title, BorderLayout.NORTH);
		p1.add(state, BorderLayout.SOUTH);
		p2.add(shuffle);
		p2.add(help);
		cnt.add(p1,BorderLayout.NORTH);
		cnt.add(mp,BorderLayout.CENTER);
		cnt.add(p2,BorderLayout.SOUTH);
	}

	class Action implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("shuffle")){
				mp.shuffle();
				mp.counter=0;
				state.setText("move: 0");
			}else{
				String str = "よくある典型的な15パズルです。\n" +
				"動かしたいパネルをクリックして元の絵を完成させましょう。";
				JOptionPane.showConfirmDialog(cnt,
						str,
						"help about 15 Pazzle",
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
			}

		}

	}
}
