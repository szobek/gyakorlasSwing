package orabolt;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JButton;

public class OraboltFoFrame {

	private JFrame frame;
	private JTextField txtMegnevezes;
	private JList<Ora> lstOraAdatok;
	private JTable tblOraAdatok;

	private List<Ora> orak;
	private DefaultListModel<Ora> listModel;
	private Ora ora;


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
		Image img = new ImageIcon(this.getClass().getResource("/a.png")).getImage();
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				String[] valaszok = { "Igen", "Nem" };
				if (JOptionPane.showOptionDialog(frame, "Biztos,hogy kilép?", "Kilépés", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, valaszok, valaszok[1]) == JOptionPane.YES_OPTION) {

					System.exit(0);

				}
			}
		});
		frame.setBounds(100, 100, 700, 500);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setTitle("Órabolt");
		frame.setIconImage(img);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Megnevez\u00E9s:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(30, 59, 88, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblTpus = new JLabel("T\u00EDpus:");
		lblTpus.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTpus.setBounds(30, 107, 88, 14);
		frame.getContentPane().add(lblTpus);
		
		JLabel lblNewLabel_1_1 = new JLabel("\u00C1r:");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1_1.setBounds(30, 155, 88, 14);
		frame.getContentPane().add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("V\u00EDz\u00E1ll\u00F3:");
		lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1_1_1.setBounds(30, 201, 88, 14);
		frame.getContentPane().add(lblNewLabel_1_1_1);
		
		txtMegnevezes = new JTextField();
		txtMegnevezes.setBounds(128, 56, 142, 20);
		frame.getContentPane().add(txtMegnevezes);
		txtMegnevezes.setColumns(10);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(128, 103, 142, 22);
		frame.getContentPane().add(comboBox);
		
		JSpinner spnAr = new JSpinner();
		spnAr.setBounds(128, 152, 142, 20);
		frame.getContentPane().add(spnAr);
		
		JCheckBox chbVizallo = new JCheckBox("");
		chbVizallo.setBounds(131, 197, 97, 23);
		frame.getContentPane().add(chbVizallo);
		
		
		orak = new ArrayList<Ora>(); //adatok tárolása
		listModel = new DefaultListModel<Ora>(); // eszköz a JListbe íráshoz

		ora = new Ora("Festina", OraTipusok.KARORA, 49000, true);		
		orak.add(ora);
		listModel.addElement(ora);

		
		lstOraAdatok = new JList();
		lstOraAdatok.setBounds(356, 58, 296, 157);
		frame.getContentPane().add(lstOraAdatok);
		
		lstOraAdatok.setModel(listModel);

		
		tblOraAdatok = new JTable();
		tblOraAdatok.setBounds(360, 260, 292, 169);
		frame.getContentPane().add(tblOraAdatok);
		
		JButton btnUjAdat = new JButton("\u00DAj adat felvitele");
		btnUjAdat.setBounds(128, 260, 142, 23);
		frame.getContentPane().add(btnUjAdat);
		
		JLabel lblNewLabel_1 = new JLabel("\u00D3ra megnevez\u00E9sek");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(356, 33, 296, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("\u00D3r\u00E1k adatai");
		lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_2.setBounds(356, 235, 296, 14);
		frame.getContentPane().add(lblNewLabel_1_2);
	}
}
