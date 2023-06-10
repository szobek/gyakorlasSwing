package orabolt;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Color;

public class OraboltFoFrame {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OraboltFoFrame window = new OraboltFoFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public OraboltFoFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Image img = new ImageIcon(this.getClass().getResource("/frame_icon.png")).getImage();
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(102, 204, 204));
		frame.getContentPane().setForeground(new Color(0, 0, 102));
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				String[] valaszok = { "Igen", "Nem" };
				if (JOptionPane.showOptionDialog(frame, "Biztos,hogy kilép?", "Kilépés", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, valaszok, valaszok[1]) == JOptionPane.YES_OPTION) {

					System.exit(0);
					

				}
			}
		});
		frame.setBounds(100, 100, 830, 425);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setTitle("Órabolt");
		frame.setIconImage(img);
		frame.getContentPane().setLayout(null);
	}

}
