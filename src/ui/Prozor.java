package ui;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import model.*;
import model.Uloga;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class Prozor extends JFrame{
		public Prozor() {
			inicijalizujLogIn();
		};	
		
		private void inicijalizujLogIn() {
			Toolkit t = Toolkit.getDefaultToolkit();
			Dimension screenSize = t.getScreenSize();
			
			JFrame logIn = new JFrame("Log in");
			logIn.setLayout(new MigLayout("align 50% 30%"));
			logIn.setResizable(false);
			logIn.setSize(screenSize.height / 2, screenSize.width/ 3);
			logIn.setLocationRelativeTo(null);
			logIn.setVisible(true);
			logIn.setDefaultCloseOperation(logIn.EXIT_ON_CLOSE);
			logIn.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					Prozor.this.setEnabled(true);
				}
			});
			
			JPanel jp = new JPanel();
			jp.setLayout(new MigLayout());
			
			JLabel lblLogIn = new JLabel("Log in");
			lblLogIn.setFont(new Font("Bold", Font.PLAIN, 35));
			lblLogIn.setVerticalAlignment(SwingConstants.NORTH);
			lblLogIn.setHorizontalAlignment(SwingConstants.CENTER);
			
			JLabel lblKIme = new JLabel("Korisničko ime:");
			JTextField txtFKIme = new JTextField(20);
			
			JLabel lblLozinka = new JLabel("Lozinka:");
			JPasswordField txtFLozinka = new JPasswordField(20);
			
			JLabel lblShowPw = new JLabel("Prikaži Lozinku");
			JCheckBox boxShowPw = new JCheckBox();
			boxShowPw.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					if (boxShowPw.isSelected()) {
						txtFLozinka.setEchoChar((char)0);
					} else {
						txtFLozinka.setEchoChar('*');
					}
				}
				
			});
			
			JButton btnLogIn = new JButton("Log in");
			btnLogIn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					String korisnickoIme = txtFKIme.getText().strip();
					char[] lozinkaCharSeq = txtFLozinka.getPassword();
					String lozinka = String.valueOf(lozinkaCharSeq).strip();
					
					if (DomZdravlja.validirajLogIn(korisnickoIme, lozinka)) {
						JOptionPane.showMessageDialog(logIn, "Uspešno ste se ulogovali!");
						logIn.dispose();
						inicijalizujMain(DomZdravlja.getKorisnik(korisnickoIme));
							
					} else {
						JOptionPane.showMessageDialog(logIn, "Uneti podaci su neispravni!");
						txtFLozinka.setText("");
						txtFKIme.setText("");
					}
				}
			});
			
			btnLogIn.addKeyListener(new KeyListener() {

				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void keyPressed(KeyEvent e) {
					int key = e.getKeyCode();
					if (key == KeyEvent.VK_ENTER) {
						String korisnickoIme = txtFKIme.getText().strip();
						char[] lozinkaCharSeq = txtFLozinka.getPassword();
						String lozinka = String.valueOf(lozinkaCharSeq).strip();
						
						if (DomZdravlja.validirajLogIn(korisnickoIme, lozinka)) {
							JOptionPane.showMessageDialog(logIn, "Uspešno ste se ulogovali!");
							logIn.dispose();
							inicijalizujMain(DomZdravlja.getKorisnik(korisnickoIme));
								
						} else {
							JOptionPane.showMessageDialog(logIn, "Uneti podaci su neispravni!", "Obaveštenje", JOptionPane.OK_OPTION);
							txtFLozinka.setText("");
							txtFKIme.setText("");
						}
					}
					
				}

				@Override
				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}
				
			});
			
			txtFKIme.addKeyListener(new KeyListener() {

				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void keyPressed(KeyEvent e) {
					int key = e.getKeyCode();
					if (key == KeyEvent.VK_ENTER) {
						String korisnickoIme = txtFKIme.getText().strip();
						char[] lozinkaCharSeq = txtFLozinka.getPassword();
						String lozinka = String.valueOf(lozinkaCharSeq).strip();
						
						if (DomZdravlja.validirajLogIn(korisnickoIme, lozinka)) {
							JOptionPane.showMessageDialog(logIn, "Uspešno ste se ulogovali!");
							logIn.dispose();
							inicijalizujMain(DomZdravlja.getKorisnik(korisnickoIme));
								
						} else {
							JOptionPane.showMessageDialog(logIn, "Uneti podaci su neispravni!");
							txtFLozinka.setText("");
							txtFKIme.setText("");
						}
					}
					
				}

				@Override
				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}
				
			});
			
			
			txtFLozinka.addKeyListener(new KeyListener() {

				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void keyPressed(KeyEvent e) {
					int key = e.getKeyCode();
					if (key == KeyEvent.VK_ENTER) {
						String korisnickoIme = txtFKIme.getText().strip();
						char[] lozinkaCharSeq = txtFLozinka.getPassword();
						String lozinka = String.valueOf(lozinkaCharSeq).strip();
						
						if (DomZdravlja.validirajLogIn(korisnickoIme, lozinka)) {
							JOptionPane.showMessageDialog(logIn, "Uspešno ste se ulogovali!");
							logIn.dispose();
							inicijalizujMain(DomZdravlja.getKorisnik(korisnickoIme));
								
						} else {
							JOptionPane.showMessageDialog(logIn, "Uneti podaci su neispravni!");
							txtFLozinka.setText("");
							txtFKIme.setText("");
						}
					}
					
				}

				@Override
				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}});
			
			jp.add(lblKIme, "wrap");
			jp.add(txtFKIme, "wrap");
			jp.add(lblLozinka, "wrap");
			jp.add(txtFLozinka, "wrap");			
			jp.add(btnLogIn, "wrap, gapx");
			jp.add(lblShowPw, "gapx 150");
			jp.add(boxShowPw, "wrap");
			logIn.add(lblLogIn, "wrap, dock north");
			logIn.add(jp, "wrap, dock south, gapy 15");
			pack();	
		}

		private void inicijalizujMain(Korisnik k) {
			Prozor.this.dispose();
			if (k.getUloga() == Uloga.PACIJENT) {
				PocetnaStranicaPacijent p = new PocetnaStranicaPacijent((Pacijent)k);
			}
			
			if (k.getUloga() == Uloga.LEKAR) {
				PocetnaStranicaLekar l = new PocetnaStranicaLekar(k);
			}
			
			if (k.getUloga() == Uloga.ADMIN) {
				PocetnaStranicaAdmin a = new PocetnaStranicaAdmin(k);
			}
			
		}
		
}
