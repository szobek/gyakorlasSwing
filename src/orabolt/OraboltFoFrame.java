package orabolt;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import javax.swing.table.DefaultTableCellRenderer;

import com.mysql.fabric.xmlrpc.base.Param;

import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;

public class OraboltFoFrame {

	private JFrame frame;
	private JTextField txtMegnevezes;
	private JList<Ora> lstOraAdatok;
	private JTable tblOraAdatok;

	private List<Ora> orak;
	private DefaultListModel<Ora> listModel;
	private Ora ora;
	private JButton btnFilter;

	private JButton btnFirstElemDataShow;

	private JButton btnUjAdat;
	private JComboBox comboBox;
	private JSpinner spnAr;
	private JCheckBox chbVizallo;
	private DefaultTableModel tablaModel;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Setting.putDData();
		
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

		orak = new ArrayList<Ora>(); // adatok tárolása
		listModel = new DefaultListModel<Ora>(); // eszköz a JListbe íráshoz

		orak = DbHandle.all(orak); // db beolvasás is

		if (orak.size() == 0) {
			ora = new Ora("Festina", OraTipusok.KARORA, 49000, true);
			orak.add(ora);
		}
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Image img = new ImageIcon(this.getClass().getResource("/frame_icon.png")).getImage();
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				String[] valaszok = { "Igen", "Nem" };
				if (JOptionPane.showOptionDialog(frame, "Biztos,hogy kilép?", "Kilépés", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, valaszok, valaszok[1]) == JOptionPane.YES_OPTION) {

					FileHandle.writeFile(orak);
					orak.clear();

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

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(OraTipusok.values()));
		comboBox.setBounds(128, 103, 142, 22);
		frame.getContentPane().add(comboBox);

		spnAr = new JSpinner();
		spnAr.setBounds(128, 152, 142, 20);
		frame.getContentPane().add(spnAr);

		chbVizallo = new JCheckBox("");
		chbVizallo.setBounds(131, 197, 97, 23);
		frame.getContentPane().add(chbVizallo);

		for (Ora ora : orak) {
			listModel.addElement(ora);
		}

		lstOraAdatok = new JList();
		lstOraAdatok.setBounds(356, 58, 296, 157);
		frame.getContentPane().add(lstOraAdatok);

		lstOraAdatok.setModel(listModel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(360, 260, 292, 169);
		frame.getContentPane().add(scrollPane);

		tblOraAdatok = new JTable();
		frame.getContentPane().add(tblOraAdatok);
		scrollPane.setViewportView(tblOraAdatok);

		// beszúrás a táblázat getRowCount()-adik sorába

		String[] oszlopnevek = { "Megnevezés", "Típus", "Ár", "VÍzállóság" };
		tablaModel = new DefaultTableModel(null, oszlopnevek);
		tblOraAdatok.setModel(tablaModel);

		// középre kiíratás:
		DefaultTableCellRenderer crenderer = new DefaultTableCellRenderer();
		crenderer.setHorizontalAlignment(SwingConstants.CENTER);
		for (int i = 0; i < tablaModel.getColumnCount(); i++) {

			tblOraAdatok.getColumnModel().getColumn(i).setCellRenderer(crenderer);

		}

		for (Ora oraPld : orak) {

			Object[] adatok = new Object[] { oraPld.getMegnevezes(), oraPld.getTipus(), oraPld.getAr(),
					oraPld.isVizallo() ? "vízálló" : "nem vízálló" };
			tablaModel.insertRow(tblOraAdatok.getRowCount(), adatok);
		}

		btnUjAdat = new JButton("\u00DAj adat felvitele");
		btnUjAdat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				felvitel();
			}

		});
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

		btnFilter = new JButton("Sz\u0171r\u00E9s (olcv\u00F3bb mint 100000)");
		btnFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Ora> szurt = orak.stream().filter((x) -> x.getAr() < 100000).toList();
				System.out.println(szurt);
			}
		});
		btnFilter.setForeground(new Color(0, 102, 51));
		btnFilter.setBounds(10, 427, 89, 23);
		frame.getContentPane().add(btnFilter);
		
		btnFirstElemDataShow = new JButton("Els\u0151 elem adata");
		btnFirstElemDataShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Ora data=orak.get(0);
				JOptionPane.showMessageDialog(frame, data.toString(),"Adatok",JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnFirstElemDataShow.setBounds(139, 427, 142, 23);
		frame.getContentPane().add(btnFirstElemDataShow);
	}

	private void felvitel() {

		if (!txtMegnevezes.getText().isBlank()) {

			ora = new Ora(txtMegnevezes.getText(), (OraTipusok) comboBox.getSelectedItem(), (int) spnAr.getValue(),
					chbVizallo.isSelected());
			orak.add(ora);
			listModel.addElement(ora);

			Object[] adatok = new Object[] { ora.getMegnevezes(), ora.getTipus(), ora.getAr(),
					ora.isVizallo() ? "vízálló" : "nem vízálló" };
			tablaModel.insertRow(tblOraAdatok.getRowCount(), adatok);
			tblOraAdatok.setModel(tablaModel);

			DbHandle.ujOra(ora);

			txtMegnevezes.setText("");
			spnAr.setValue(0);
			chbVizallo.setSelected(false);

		} else {
			JOptionPane.showMessageDialog(frame, "Megnevezés nem lehet üres", "Figyelmeztetés",
					JOptionPane.WARNING_MESSAGE);
		}

	}
}
