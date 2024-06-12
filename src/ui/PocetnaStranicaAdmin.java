package ui;

import java.awt.*; 
import java.awt.event.*;
import java.io.IOException;
import java.lang.StackWalker.Option;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import model.*;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class PocetnaStranicaAdmin extends JFrame{
	private Korisnik k;
	
	private JFrame mainAdmin;
	
	private JScrollPane tblTermini;
	private JTable tabela;
	private DefaultTableModel model;
	private int izabranRed;
	private JFrame frameInfoTermin;
	private int indexKorisnikaZaPrikaz;
	private int indexTerminaZaPrikaz;
	
	private JFrame frameInfoKorisnik;
	private JFrame frameEditKorisnik;
	
	private JFrame frameInfoKarton;
	private JFrame framePrikazTermina;
	private JFrame frameInfoTermin1;
	private DefaultTableModel modelSpec;
	private int indexKartonaZaPrikaz;

	private static final String DATE_TIME_FORMAT = "dd.MM.yyyy. HH:mm";
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
	
	public PocetnaStranicaAdmin(Korisnik k) {
		this.k = k;
		
		Toolkit t = Toolkit.getDefaultToolkit();
		Dimension screenSize = t.getScreenSize();
		
		mainAdmin = new JFrame("Početna Stranica");
		setVisible(true);
		setResizable(false);
		setTitle("Admin");
		setSize(screenSize.width/2, screenSize.width /3);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(mainAdmin.DO_NOTHING_ON_CLOSE);
		setLayout(new MigLayout());
		addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent e) {
				int a = JOptionPane.showConfirmDialog(mainAdmin, "Da li ste sigurni da želite da napustite aplikaciju?", "Potvrda", JOptionPane.YES_NO_OPTION);
				
				if (a == JOptionPane.YES_OPTION) {
					System.exit(0);;
				} else {
					setVisible(true);
				}

			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}});
		
		JPanel pnlMain = new JPanel();
		pnlMain.setLayout(new MigLayout());
		
		JScrollPane tblKorisnici = tabelaKorisnici();
		JButton btnRegister = new JButton(new ImageIcon("img/register.png"));
		btnRegister.setMaximumSize(new Dimension(50, 50));
		btnRegister.setFocusPainted(false);
		btnRegister.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Toolkit t = Toolkit.getDefaultToolkit();
				Dimension screenSize = t.getScreenSize();
				
				setEnabled(false);
				
				JFrame register = new JFrame("Register");
				register.setDefaultCloseOperation(register.DISPOSE_ON_CLOSE);
				register.setVisible(true);
				register.setSize(new Dimension(500, 620));
				register.setLocationRelativeTo(null);
				register.setLayout(new BorderLayout());
				register.setResizable(false);
				register.addWindowListener(new WindowListener() {

					@Override
					public void windowOpened(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void windowClosing(WindowEvent e) {
						setEnabled(true);
					}

					@Override
					public void windowClosed(WindowEvent e) {
						//
					}

					@Override
					public void windowIconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void windowDeiconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void windowActivated(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void windowDeactivated(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
				});
				
				JPanel pnlRegister = new JPanel();
				pnlRegister.setLayout(new MigLayout());
				
				JLabel lblRegister = new JLabel("Register");
				lblRegister.setFont(new Font("Bold", Font.PLAIN, 30));
				lblRegister.setHorizontalAlignment(SwingConstants.CENTER);
				
				JLabel lblKImeReg = new JLabel("Korisničko ime:");
				JTextField txtFKIme = new JTextField(15);
				
				JLabel lblLozinkaReg = new JLabel("Lozinka:");
				JPasswordField txtFLozinka = new JPasswordField(15);
				
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
				
				JLabel lblUloga = new JLabel("Uloga:");
				String[] uloge = {"Pacijent", "Lekar", "Admin"};
				JComboBox boxUloga = new JComboBox(uloge);
				
				JLabel lblImeReg = new JLabel("Ime:");
				JTextField txtFIme = new JTextField(15);
				
				JLabel lblPrezimeReg = new JLabel("Prezime:");
				JTextField txtFPrezime = new JTextField(15);
				
				JLabel lblPol = new JLabel("Pol:");
				String[] pol = {"M", "Z"};
				JComboBox boxPol = new JComboBox(pol);
				
				JLabel lblJMBGReg = new JLabel("JMBG:");
				JTextField txtFJMBG = new JTextField(15);
				
				JLabel lblAdresaReg = new JLabel("Adresa:");
				JTextField txtFAdresa = new JTextField(15);
				
				JLabel lblBrojTelReg = new JLabel("Broj telefona:");
				JTextField txtFBrojTel = new JTextField(15);
				
				JButton registruj = new JButton("Registruj");
				registruj.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						int idKorisnik = DomZdravlja.getKorisnici().size();
						int id = DomZdravlja.getKorisnici().get(idKorisnik - 1).getId() + 10;
						String kIme = txtFKIme.getText();
						char[] lozinkaArr = txtFLozinka.getPassword();
						String lozinka = String.valueOf(lozinkaArr);
						Uloga uloga = Uloga.values()[boxUloga.getSelectedIndex()];
						String ime = txtFIme.getText();
						String prezime = txtFPrezime.getText();
						Pol pol = Pol.values()[boxPol.getSelectedIndex()];
						String jMBG = txtFJMBG.getText();
						String adresa = txtFPrezime.getText();
						String brTel = txtFBrojTel.getText();
						String datumPre = LocalDateTime.now().format(DATE_TIME_FORMATTER);
						LocalDateTime datum = LocalDateTime.parse(datumPre, DATE_TIME_FORMATTER);
						
						if (DomZdravlja.korisnickoImeCheck(kIme)) {
							if (kIme.compareTo("") != 0 && kIme.compareTo("") != 0 && ime.compareTo("") != 0 && prezime.compareTo("") != 0 && jMBG.compareTo("") != 0 && adresa.compareTo("") != 0 && brTel.compareTo("") != 0) {
								if (uloga == Uloga.ADMIN) {
									Admin a = new Admin(id, kIme, lozinka, ime, prezime, jMBG,pol, adresa, brTel, uloga, datum, true);
									DomZdravlja.getKorisnici().add(a);
									JOptionPane.showMessageDialog(register, "Korisnik Uspešno registrovan!", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
									model.addRow(new Object[]{id, kIme, ime, prezime, jMBG, pol, brTel, uloga});
									DomZdravlja.sacuvaj();
									setEnabled(true);
									register.dispose();
								}
								
								if (uloga == Uloga.LEKAR) {
									Lekar l = new Lekar(id, kIme, lozinka, ime, prezime, jMBG,pol, adresa, brTel, uloga, datum, true);
									DomZdravlja.getKorisnici().add(l);
									JOptionPane.showMessageDialog(register, "Korisnik Uspešno registrovan!", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
									model.addRow(new Object[]{id, kIme, ime, prezime, jMBG, pol, brTel, uloga});
									DomZdravlja.sacuvaj();
									setEnabled(true);
									register.dispose();
								}
								
								if (uloga == Uloga.PACIJENT) {
									Pacijent p = new Pacijent(id, kIme, lozinka, ime, prezime, jMBG,pol, adresa, brTel, uloga, datum, null, true);
									int idKart = DomZdravlja.getZdravstveniKartoni().size();
									ZdravstveniKarton zKartonP = new ZdravstveniKarton(DomZdravlja.getZdravstveniKartoni().get(idKart - 1).getId() + 1,p, true);
									p.setzKarton(zKartonP);
									DomZdravlja.getKorisnici().add(p);
									DomZdravlja.getZdravstveniKartoni().add(zKartonP);
									JOptionPane.showMessageDialog(register, "Korisnik Uspešno registrovan!", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
									model.addRow(new Object[]{id, kIme, ime, prezime, jMBG, pol, brTel, uloga});
									DomZdravlja.sacuvaj();
									setEnabled(true);
									register.dispose();
								}
							} else {
								JOptionPane.showMessageDialog(register,"Neophodno je popuniti sva polja!", "Obaveštenje", JOptionPane.WARNING_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(register,"Korisnik sa unetim korisničkim imenom već postoji!", "Obaveštenje", JOptionPane.WARNING_MESSAGE);
						}
					
						}});
					
				
				pnlRegister.add(lblKImeReg, "wrap, gap 140");
				pnlRegister.add(txtFKIme, "wrap, gap 140");
				pnlRegister.add(lblLozinkaReg, "wrap, gap 140");
				pnlRegister.add(txtFLozinka, "wrap, gap 140");
				pnlRegister.add(lblShowPw, "gapx 240");
				pnlRegister.add(boxShowPw,"wrap");
				pnlRegister.add(lblUloga, "wrap, gap 140");
				pnlRegister.add(boxUloga,"wrap, gap 140");
				pnlRegister.add(lblImeReg, "wrap, gap 140");
				pnlRegister.add(txtFIme, "wrap, gap 140");
				pnlRegister.add(lblPrezimeReg, "wrap, gap 140");
				pnlRegister.add(txtFPrezime, "wrap, gap 140");
				pnlRegister.add(lblPol, "wrap, gap 140");
				pnlRegister.add(boxPol,"wrap, gap 140");
				pnlRegister.add(lblJMBGReg, "wrap, gap 140");
				pnlRegister.add(txtFJMBG, "wrap, gap 140");
				pnlRegister.add(lblAdresaReg, "wrap, gap 140");
				pnlRegister.add(txtFAdresa, "wrap, gap 140");
				pnlRegister.add(lblBrojTelReg, "wrap, gap 140");
				pnlRegister.add(txtFBrojTel, "wrap, gap 140");
				pnlRegister.add(registruj, "wrap, gap 140");
				
				register.add(pnlRegister, BorderLayout.CENTER);
				register.add(lblRegister, BorderLayout.NORTH);
				
			}});

		
		
		JMenuBar menuBarAdmin = new JMenuBar();
		JMenu menuAdmin = new JMenu("Menu");
		
		JMenu menuKorisnici = new JMenu("Korisnici");
		
		JMenu menuTermini = new JMenu("Termini");
		
		JMenu menuKartoni = new JMenu("Zdravstveni Kartoni");
		
		JMenuItem korisniciMenuItem = new JMenuItem("Prikaži korisnike");
		korisniciMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JScrollPane tblKorisnici = tabelaKorisnici();
				btnRegister.setEnabled(true);
				pnlMain.removeAll();
				pnlMain.add(tblKorisnici, "grow, push");
				pnlMain.validate();
			}});
		
		JMenuItem terminiMenuItem = new JMenuItem("Prikaži termine");
		terminiMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tblTermini = tabelaTermini();
				btnRegister.setEnabled(false);
				pnlMain.removeAll();
				pnlMain.add(tblTermini, "grow, push");
				pnlMain.validate();
			}});
		
		JMenuItem kartoniMenuItem = new JMenuItem("Prikaži Zdravstvene Kartone");
		kartoniMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tblTermini = tabelaKartoni();
				btnRegister.setEnabled(false);				
				pnlMain.removeAll();
				pnlMain.add(tblTermini, "grow, push");
				pnlMain.validate();
			}
			
		});
		
		JMenuItem m0 = new JMenuItem("Sačuvaj");
		m0.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(PocetnaStranicaAdmin.this, "Podaci uspešno sačuvani", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
			}});
		
		JMenuItem m1 = new JMenuItem("Log out");
		m1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				int a = JOptionPane.showConfirmDialog(mainAdmin, "Da li ste sigurni da želite da se izlogujete?", "Potvrda", JOptionPane.YES_NO_OPTION);
					
					if (a == JOptionPane.YES_OPTION) {
						PocetnaStranicaAdmin.this.dispose();
						new Prozor();
					}
			}
		});
		
		JMenuItem m2 = new JMenuItem("Exit");
		m2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int a = JOptionPane.showConfirmDialog(mainAdmin, "Da li ste sigurni da želite da napustite aplikaciju?", "Potvrda", JOptionPane.YES_NO_OPTION);
				
				if (a == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
				
		JToolBar tbarAdmin = new JToolBar();
		tbarAdmin.setFloatable(false);
	
		pnlMain.add(tblKorisnici, "grow, push");
		
		tbarAdmin.add(btnRegister);
		tbarAdmin.addSeparator();
		
		menuAdmin.add(korisniciMenuItem);
		menuAdmin.add(m0);
		menuAdmin.addSeparator();
		menuAdmin.add(m1);
		menuAdmin.add(m2);
		
		menuKorisnici.add(korisniciMenuItem);
		menuTermini.add(terminiMenuItem);
		menuKartoni.add(kartoniMenuItem);
		
		menuBarAdmin.add(menuAdmin);
		menuBarAdmin.add(menuKorisnici);
		menuBarAdmin.add(menuTermini);
		menuBarAdmin.add(menuKartoni);
		
		setJMenuBar(menuBarAdmin);
		add(tbarAdmin, BorderLayout.NORTH);
		add(pnlMain,"grow, push");
	};
		
	protected JScrollPane tabelaKartoni() {
		ArrayList<Integer> redovi = new ArrayList<Integer>();
		String[] zaglavlja = new String[] {"ID","Pacijent"};

		ArrayList<String> kartonLn = null;
		try {
			kartonLn = DomZdravlja.procitajKartone();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Sadrzaj tabele[brojRedova][brojKolona]
		Object[][] sadrzaj = new Object[kartonLn.size()][zaglavlja.length];

		
		for(int i=0; i<sadrzaj.length; i++) {
			String[] info = kartonLn.get(i).split(",");
			if (info[2].compareTo("0") == 0) {
				sadrzaj[i][0] = info[0];
				for (Korisnik k : DomZdravlja.getKorisnici()) {
					if (k.getId() == Integer.parseInt(info[1])) {
						sadrzaj[i][1] = k.getKorisnickoIme();
						break;
					}
				}
			} else {
				redovi.add(i);
			}
		}
		
		model = new DefaultTableModel(sadrzaj, zaglavlja);
		tabela = new JTable(model);

		tabela.setRowSelectionAllowed(true);

		tabela.setColumnSelectionAllowed(false);

		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tabela.setDefaultEditor(Object.class, null);
		
		tabela.addMouseListener(new MouseListener() {


			@Override
			public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                	//Castujemo u JTable zato sto od nje dolazi event
                	JTable red = (JTable)e.getSource();
                    izabranRed = red.getSelectedRow();
                    if (izabranRed != -1) {
                    	PocetnaStranicaAdmin.this.setEnabled(false);
                    	frameInfoKarton = new JFrame();
                    	frameInfoKarton.setSize(new Dimension(500, 600));
                    	frameInfoKarton.setVisible(true);
                    	frameInfoKarton.setDefaultCloseOperation(frameInfoTermin.DISPOSE_ON_CLOSE);
                    	frameInfoKarton.setLocationRelativeTo(PocetnaStranicaAdmin.this);
                    	frameInfoKarton.setResizable(false);
                    	frameInfoKarton.addWindowListener(new WindowListener() {

							@Override
							public void windowOpened(WindowEvent e) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void windowClosing(WindowEvent e) {
								PocetnaStranicaAdmin.this.setEnabled(true);
								
							}

							@Override
							public void windowClosed(WindowEvent e) {
							}

							@Override
							public void windowIconified(WindowEvent e) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void windowDeiconified(WindowEvent e) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void windowActivated(WindowEvent e) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void windowDeactivated(WindowEvent e) {
								// TODO Auto-generated method stub
								
							}});
                    	
                    	JLabel lblKartonInfo = new JLabel("Informacije o Kartonu");
                    	lblKartonInfo.setFont(new Font("Bold", Font.PLAIN, 30));
                    	lblKartonInfo.setHorizontalAlignment(SwingUtilities.CENTER);
                    	
                    	indexKartonaZaPrikaz = 0;
                    	for (ZdravstveniKarton kt: DomZdravlja.getZdravstveniKartoni()) {
                    		if (kt.getId() == Integer.parseInt(model.getValueAt(izabranRed, 0).toString())) {
                    			break;
                    		}
                    		indexKartonaZaPrikaz += 1;
                    	}
                    	
                    	JPanel pnlKartonInfo = new JPanel();
                    	pnlKartonInfo.setLayout(new MigLayout());
                    	
                    	JLabel idKartona = new JLabel("ID Termina: ");
                    	idKartona.setFont(new Font("Bold", Font.PLAIN, 15));
                    	JLabel ID = new JLabel(Integer.toString(DomZdravlja.getZdravstveniKartoni().get(indexKartonaZaPrikaz).getId()));
                    	
                    	JLabel pacijentKarton = new JLabel("Pacijent: ");
                    	pacijentKarton.setFont(new Font("Bold", Font.PLAIN, 15));
                    	JLabel pacijent = new JLabel(DomZdravlja.getZdravstveniKartoni().get(indexKartonaZaPrikaz).getPacijent().getKorisnickoIme());
                    	
                    	JButton prikazTermina = new JButton("Prikaži Termine");
                    	prikazTermina.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								frameInfoKarton.setEnabled(false);
								framePrikazTermina = new JFrame();
								framePrikazTermina.setLayout(new MigLayout());
								framePrikazTermina.setSize(new Dimension(800, 400));
								framePrikazTermina.setVisible(true);
								framePrikazTermina.setDefaultCloseOperation(frameInfoTermin.DISPOSE_ON_CLOSE);
								framePrikazTermina.setLocationRelativeTo(PocetnaStranicaAdmin.this);
								framePrikazTermina.setResizable(false);
								framePrikazTermina.addWindowListener(new WindowListener(){

									@Override
									public void windowOpened(WindowEvent e) {
										// TODO Auto-generated method stub
										
									}

									@Override
									public void windowClosing(WindowEvent e) {
										frameInfoKarton.setEnabled(true);
									}

									@Override
									public void windowClosed(WindowEvent e) {
										// TODO Auto-generated method stub
										
									}

									@Override
									public void windowIconified(WindowEvent e) {
										// TODO Auto-generated method stub
										
									}

									@Override
									public void windowDeiconified(WindowEvent e) {
										// TODO Auto-generated method stub
										
									}

									@Override
									public void windowActivated(WindowEvent e) {
										// TODO Auto-generated method stub
										
									}

									@Override
									public void windowDeactivated(WindowEvent e) {
										// TODO Auto-generated method stub
										
									}});
							
								framePrikazTermina.add(tabelaKorisniciTermini(DomZdravlja.getZdravstveniKartoni().get(indexKartonaZaPrikaz).getPacijent()), "grow, push");
							}
                    		
                    	});
                    	
                    	pnlKartonInfo.add(idKartona, "gap 100");
                    	pnlKartonInfo.add(ID, "wrap, gap 100");
                    	pnlKartonInfo.add(pacijentKarton, "gap 100");
                    	pnlKartonInfo.add(pacijent, "wrap, gap 100");
                    	pnlKartonInfo.add(prikazTermina, "gap 100");

                    	
                    	
                    	frameInfoKarton.add(pnlKartonInfo,BorderLayout.CENTER);
                    	frameInfoKarton.add(lblKartonInfo,BorderLayout.NORTH);
                    }
                }
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}});
		
		tabela.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    izabranRed = tabela.getSelectedRow();
                }
			}});

		JScrollPane scrollPane = new JScrollPane(tabela);
		
		int i = 0;
		int velicina = model.getRowCount();
		while (i < velicina) {
			if (model.getValueAt(i, 0) == null) {
				model.removeRow(i);
				velicina -= 1;
			} else {
				i++;
			}
		}
		
		return scrollPane;
	}
	
	protected JScrollPane tabelaKorisnici() {
		ArrayList<Integer> redovi = new ArrayList<Integer>();
		
		String[] zaglavlja = new String[] {"ID", "Korisnicko Ime", "Ime", "Prezime", "JMBG", "Pol", "Broj Telefona", "Uloga"};

		ArrayList<String> korisniciLn = null;
		try {
			korisniciLn = DomZdravlja.procitajKorisnike();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Sadrzaj tabele[brojRedova][brojKolona]
		Object[][] sadrzaj = new Object[korisniciLn.size()][zaglavlja.length];

		
		for(int i=0; i<sadrzaj.length; i++) {
			String[] info = korisniciLn.get(i).split(",");
			if (info[11].compareTo("1") != 0) {
				sadrzaj[i][0] = info[0];
				sadrzaj[i][1] = info[1];
				sadrzaj[i][2] = info[3];
				sadrzaj[i][3] = info[4];
				sadrzaj[i][4] = info[5];
				sadrzaj[i][5] = Pol.values()[Integer.parseInt(info[6])];
				sadrzaj[i][6] = info[8];
				sadrzaj[i][7] = Uloga.values()[Integer.parseInt(info[9])];
			} else {
				redovi.add(i);
			}
		}
		
		model = new DefaultTableModel(sadrzaj, zaglavlja);
		tabela = new JTable(model);

		tabela.setRowSelectionAllowed(true);

		tabela.setColumnSelectionAllowed(false);

		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tabela.setDefaultEditor(Object.class, null);
		
		tabela.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                	//Castujemo u JTable zato sto od nje dolazi event
                	JTable red = (JTable)e.getSource();
                    izabranRed = red.getSelectedRow();
                    if (izabranRed != -1) {
                    	PocetnaStranicaAdmin.this.setEnabled(false);
                    	frameInfoKorisnik = new JFrame();
                    	frameInfoKorisnik.setSize(new Dimension(500, 600));
                    	frameInfoKorisnik.setVisible(true);
                    	frameInfoKorisnik.setDefaultCloseOperation(frameInfoKorisnik.DISPOSE_ON_CLOSE);
                    	frameInfoKorisnik.setLocationRelativeTo(PocetnaStranicaAdmin.this);
                    	frameInfoKorisnik.setResizable(false);
                    	frameInfoKorisnik.addWindowListener(new WindowListener() {

							@Override
							public void windowOpened(WindowEvent e) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void windowClosing(WindowEvent e) {
								PocetnaStranicaAdmin.this.setEnabled(true);
								
							}

							@Override
							public void windowClosed(WindowEvent e) {
							}

							@Override
							public void windowIconified(WindowEvent e) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void windowDeiconified(WindowEvent e) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void windowActivated(WindowEvent e) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void windowDeactivated(WindowEvent e) {
								// TODO Auto-generated method stub
								
							}});
                    	
                    	indexKorisnikaZaPrikaz = 0;
                    	for (Korisnik k: DomZdravlja.getKorisnici()) {
                    		if (k.getId() == Integer.parseInt(model.getValueAt(izabranRed, 0).toString())) {
                    			break;
                    		}
                    		indexKorisnikaZaPrikaz += 1;
                    	}
                    	
                    	JLabel infoKorisnik = new JLabel("Informacije o Korisniku");
                    	infoKorisnik.setFont(new Font("Bold", Font.PLAIN, 30));
                    	infoKorisnik.setHorizontalAlignment(SwingUtilities.CENTER);
                    	
                    	JLabel idKorisnika = new JLabel("ID Korisnika: ");
                    	idKorisnika.setFont(new Font("Bold", Font.PLAIN, 15));
                    	JLabel ID = new JLabel(Integer.toString(DomZdravlja.getKorisnici().get(indexKorisnikaZaPrikaz).getId()));
                    	
                    	JLabel korisnickoIme = new JLabel("Korisničko ime: ");
                    	korisnickoIme.setFont(new Font("Bold", Font.PLAIN, 15));
                    	JLabel kIme = new JLabel(DomZdravlja.getKorisnici().get(indexKorisnikaZaPrikaz).getKorisnickoIme());
                    	
                    	JLabel ulogaKorisnika = new JLabel("Uloga: ");
                    	ulogaKorisnika.setFont(new Font("Bold", Font.PLAIN, 15));
                    	JLabel uloga = new JLabel(DomZdravlja.getKorisnici().get(indexKorisnikaZaPrikaz).getUloga().toString());
                    	
                    	JLabel imeKorisnika = new JLabel("Ime: ");
                    	imeKorisnika.setFont(new Font("Bold", Font.PLAIN, 15));
                    	JLabel ime  = new JLabel(DomZdravlja.getKorisnici().get(indexKorisnikaZaPrikaz).getIme());
                    	
                    	JLabel prezimeKorisnika = new JLabel("Prezime: ");
                    	prezimeKorisnika.setFont(new Font("Bold", Font.PLAIN, 15));
                    	JLabel prezime = new JLabel(DomZdravlja.getKorisnici().get(indexKorisnikaZaPrikaz).getPrezime());
                    	
                    	JLabel polKorisnika = new JLabel("Pol: ");
                    	polKorisnika.setFont(new Font("Bold", Font.PLAIN, 15));
                    	JLabel pol = new JLabel(DomZdravlja.getKorisnici().get(indexKorisnikaZaPrikaz).getPol().toString());
                    	
                    	JLabel jMBGKorisnika = new JLabel("JMBG: ");
                    	jMBGKorisnika.setFont(new Font("Bold", Font.PLAIN, 15));
                    	JLabel jmbg = new JLabel(DomZdravlja.getKorisnici().get(indexKorisnikaZaPrikaz).getJMBG());
                    
                    	JLabel adresaKorisnika = new JLabel("Adresa: ");
                    	adresaKorisnika.setFont(new Font("Bold", Font.PLAIN, 15));
                    	JLabel adresa = new JLabel(DomZdravlja.getKorisnici().get(indexKorisnikaZaPrikaz).getAdresa());
                    	
                    	JLabel brojKorisnika = new JLabel("Broj Telefona: ");
                    	brojKorisnika.setFont(new Font("Bold", Font.PLAIN, 15));
                    	JLabel broj = new JLabel(DomZdravlja.getKorisnici().get(indexKorisnikaZaPrikaz).getBrojTelefona());
                    	
                    	JButton btnIzmeniKorisnika = new JButton("Izmeni Korisnika");
                    	btnIzmeniKorisnika.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								frameEditKorisnik = new JFrame();
		                    	frameEditKorisnik.setSize(new Dimension(500, 700));
		                    	frameEditKorisnik.setVisible(true);
		                    	frameEditKorisnik.setDefaultCloseOperation(frameEditKorisnik.DISPOSE_ON_CLOSE);
		                    	frameEditKorisnik.setLocationRelativeTo(PocetnaStranicaAdmin.this);
		                    	frameEditKorisnik.setResizable(false);
		                    	frameInfoKorisnik.setEnabled(false);
		                    	frameEditKorisnik.addWindowListener(new WindowListener() {

									@Override
									public void windowOpened(WindowEvent e) {
										// TODO Auto-generated method stub
										
									}

									@Override
									public void windowClosing(WindowEvent e) {
										frameInfoKorisnik.setEnabled(true);
									}

									@Override
									public void windowClosed(WindowEvent e) {
										// TODO Auto-generated method stub
										
									}

									@Override
									public void windowIconified(WindowEvent e) {
										// TODO Auto-generated method stub
										
									}

									@Override
									public void windowDeiconified(WindowEvent e) {
										// TODO Auto-generated method stub
										
									}

									@Override
									public void windowActivated(WindowEvent e) {
										// TODO Auto-generated method stub
										
									}

									@Override
									public void windowDeactivated(WindowEvent e) {
										// TODO Auto-generated method stub
										
									}
		                    		
		                    	});
		                    	
		                    	JLabel infoKorisnik = new JLabel("Izmeni podatke o Korisniku");
		                    	infoKorisnik.setFont(new Font("Bold", Font.PLAIN, 30));
		                    	infoKorisnik.setHorizontalAlignment(SwingUtilities.CENTER);
		                    	
		                    	JLabel idKorisnika = new JLabel("ID Korisnika: ");
		                    	idKorisnika.setFont(new Font("Bold", Font.PLAIN, 15));
		                    	JLabel ID = new JLabel(Integer.toString(DomZdravlja.getKorisnici().get(indexKorisnikaZaPrikaz).getId()));
		                    	
		                    	JLabel korisnickoIme = new JLabel("Korisničko ime: ");
		                    	korisnickoIme.setFont(new Font("Bold", Font.PLAIN, 15));
		                    	JTextField kIme = new JTextField(20);
		                    	kIme.setText(DomZdravlja.getKorisnici().get(indexKorisnikaZaPrikaz).getKorisnickoIme());
		                    	
		                    	JLabel ulogaKorisnika = new JLabel("Uloga: ");
		                    	ulogaKorisnika.setFont(new Font("Bold", Font.PLAIN, 15));
		                    	JLabel uloga = new JLabel(DomZdravlja.getKorisnici().get(indexKorisnikaZaPrikaz).getUloga().toString());
		                    	
		                    	JLabel imeKorisnika = new JLabel("Ime: ");
		                    	imeKorisnika.setFont(new Font("Bold", Font.PLAIN, 15));
		                    	JTextField ime  = new JTextField(20);
		                    	ime.setText(DomZdravlja.getKorisnici().get(indexKorisnikaZaPrikaz).getIme());
		                    	
		                    	JLabel prezimeKorisnika = new JLabel("Prezime: ");
		                    	prezimeKorisnika.setFont(new Font("Bold", Font.PLAIN, 15));
		                    	JTextField prezime = new JTextField(20);
		                    	prezime.setText(DomZdravlja.getKorisnici().get(indexKorisnikaZaPrikaz).getPrezime());
		                    	
		                    	JLabel polKorisnika = new JLabel("Pol: ");
		                    	polKorisnika.setFont(new Font("Bold", Font.PLAIN, 15));
		        				JLabel lblPol = new JLabel("Pol:");
		        				String[] pol = {"M", "Z"};
		        				JComboBox boxPol = new JComboBox(pol);
		        				boxPol.setSelectedItem(DomZdravlja.getKorisnici().get(indexKorisnikaZaPrikaz).getPol().toString());
		                    	
		                    	JLabel jMBGKorisnika = new JLabel("JMBG: ");
		                    	jMBGKorisnika.setFont(new Font("Bold", Font.PLAIN, 15));
		                    	JTextField jmbg = new JTextField(20);
		                    	jmbg.setText(DomZdravlja.getKorisnici().get(indexKorisnikaZaPrikaz).getJMBG());
		                    
		                    	JLabel adresaKorisnika = new JLabel("Adresa: ");
		                    	adresaKorisnika.setFont(new Font("Bold", Font.PLAIN, 15));
		                    	JTextField adresa = new JTextField(20);
		                    	adresa.setText(DomZdravlja.getKorisnici().get(indexKorisnikaZaPrikaz).getAdresa());
		                    	
		                    	JLabel brojKorisnika = new JLabel("Broj Telefona: ");
		                    	brojKorisnika.setFont(new Font("Bold", Font.PLAIN, 15));
		                    	JTextField broj = new JTextField(20);
		                    	broj.setText(DomZdravlja.getKorisnici().get(indexKorisnikaZaPrikaz).getBrojTelefona());
		                    	
		                    	JButton btnAzurirajKorisnika = new JButton("Ažuriraj");
		                    	btnAzurirajKorisnika.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										String newKorisnickoIme = kIme.getText();
										String newImeKorisnika = ime.getText();
										String newPrezimeKorisnika = prezime.getText();
										Pol newPol = Pol.values()[boxPol.getSelectedIndex()];
										String newjmbg = jmbg.getText();
										String newAdresa = adresa.getText();
										String newBrojTel = broj.getText();
										
										if ((DomZdravlja.korisnickoImeCheck(newKorisnickoIme) || (!DomZdravlja.korisnickoImeCheck(newKorisnickoIme) && DomZdravlja.getKorisnici().get(indexKorisnikaZaPrikaz).getKorisnickoIme().compareTo(newKorisnickoIme) == 0)) && newKorisnickoIme.compareTo("") != 0 && newImeKorisnika.compareTo("") != 0 && newPrezimeKorisnika.compareTo(" ") != 0 && newjmbg.compareTo("") != 0 && newAdresa.compareTo("") != 0 && newBrojTel.compareTo("") != 0) {
											Korisnik k = DomZdravlja.getKorisnici().get(indexKorisnikaZaPrikaz);
											k.setKorisnickoIme(newKorisnickoIme);
											k.setIme(newImeKorisnika);
											k.setPrezime(newPrezimeKorisnika);
											k.setPol(newPol);
											k.setJMBG(newjmbg);
											k.setAdresa(newAdresa);
											k.setBrojTelefona(newBrojTel);
											DomZdravlja.sacuvaj();
											
											model.setValueAt(newKorisnickoIme, izabranRed, 1);
											model.setValueAt(newImeKorisnika, izabranRed, 2);
											model.setValueAt(newPrezimeKorisnika, izabranRed, 3);
											model.setValueAt(newPol.toString(), izabranRed, 5);
											model.setValueAt(newjmbg, izabranRed, 4);
											model.setValueAt(newBrojTel, izabranRed, 6);
											JOptionPane.showMessageDialog(frameEditKorisnik, "Izmene su sačuvane!", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
											frameEditKorisnik.dispose();
											frameInfoKorisnik.dispose();
											setEnabled(true);
											setVisible(true);
										} else if (newKorisnickoIme.compareTo("") == 0 || newImeKorisnika.compareTo("") == 0 || newPrezimeKorisnika.compareTo(" ") == 0 || newjmbg.compareTo("") == 0 || newAdresa.compareTo("") == 0 || newBrojTel.compareTo("") == 0) {
											JOptionPane.showMessageDialog(frameEditKorisnik, "Neophodno je popuniti sva polja!", "Upozorenje", JOptionPane.WARNING_MESSAGE);
										} else if (!DomZdravlja.korisnickoImeCheck(newKorisnickoIme)) {
											JOptionPane.showMessageDialog(frameEditKorisnik, "Uneto korisničko ime je zauzeto!", "Upozorenje", JOptionPane.WARNING_MESSAGE);
										}
									}
		                    		
		                    	});
		                    	
		                    	JPanel pnlEditKorisnik = new JPanel();
		                    	pnlEditKorisnik.setLayout(new MigLayout());
		                    	pnlEditKorisnik.add(idKorisnika,"gap 90,wrap");
		                    	pnlEditKorisnik.add(ID,"wrap, gap 90, gapy 0 10");
		                    	pnlEditKorisnik.add(korisnickoIme,"gap 90, wrap");
		                    	pnlEditKorisnik.add(kIme, "wrap, gap 90, gapy 0 10");
		                    	pnlEditKorisnik.add(ulogaKorisnika,"gap 90,wrap");
		                    	pnlEditKorisnik.add(uloga, "wrap, gap 90, gapy 0 10");
		                    	pnlEditKorisnik.add(imeKorisnika,"gap 90, wrap");
		                    	pnlEditKorisnik.add(ime, "wrap, gap 90, gapy 0 10");
		                    	pnlEditKorisnik.add(prezimeKorisnika,"gap 90, wrap");
		                    	pnlEditKorisnik.add(prezime, "wrap, gap 90, gapy 0 10");
		                    	pnlEditKorisnik.add(polKorisnika,"gap 90,wrap");
		                    	pnlEditKorisnik.add(boxPol, "wrap, gap 90, gapy 0 10");
		                    	pnlEditKorisnik.add(jMBGKorisnika,"gap 90, wrap");
		                    	pnlEditKorisnik.add(jmbg, "wrap, gap 90, gapy 0 10");
		                    	pnlEditKorisnik.add(adresaKorisnika,"gap 90,wrap");
		                    	pnlEditKorisnik.add(adresa, "wrap, gap 90, gapy 0 10");
		                    	pnlEditKorisnik.add(brojKorisnika,"gap 90, wrap");
		                    	pnlEditKorisnik.add(broj,"wrap, gap 90, gapy 0 10");
		                    	pnlEditKorisnik.add(btnAzurirajKorisnika, "gap 190");
		                    	
		                    	frameEditKorisnik.add(infoKorisnik, BorderLayout.NORTH);
		                    	frameEditKorisnik.add(pnlEditKorisnik, BorderLayout.CENTER);
							}
                    		
                    	});
                    	JPanel pnlInfoKorisnik = new JPanel();
                    	
                    	JButton btnObrisiKorisnika = new JButton("Obriši Korisnika");
                    	if (k.getId() == DomZdravlja.getKorisnici().get(indexKorisnikaZaPrikaz).getId()) {
                    		btnObrisiKorisnika.setEnabled(false);
                    	} else {
                    		btnObrisiKorisnika.setEnabled(true);
                    	}
                    	btnObrisiKorisnika.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								int a =JOptionPane.showConfirmDialog(pnlInfoKorisnik, "Da li ste sigurni da želite da obrišete korisnika " + DomZdravlja.getKorisnici().get(indexKorisnikaZaPrikaz).getKorisnickoIme() + "?", "Potvrda", JOptionPane.OK_CANCEL_OPTION);
								if (a == JOptionPane.OK_OPTION) {
										if (DomZdravlja.getKorisnici().get(indexKorisnikaZaPrikaz).getUloga() == Uloga.LEKAR) {
											ArrayList<Termin> zakazaniTermini = new ArrayList<Termin>();
											Lekar l = (Lekar)DomZdravlja.getKorisnici().get(indexKorisnikaZaPrikaz);
											for(Termin t: l.getZakazaniTermini()) {
												if (t.getDatumTermina().isAfter(LocalDateTime.now()) && t.getStatus() == Status.Zakazan) {
													zakazaniTermini.add(t);
												}
											}
											
											if (zakazaniTermini.size() != 0) {
												JOptionPane.showMessageDialog(pnlInfoKorisnik, "Ne možete obrisati lekara koji ima zakazane termine!", "Upozorenje", JOptionPane.WARNING_MESSAGE);
											} else {
												DomZdravlja.getKorisnici().get(indexKorisnikaZaPrikaz).setAktivan(false);
												model.removeRow(izabranRed);
												JOptionPane.showMessageDialog(pnlInfoKorisnik, "Uspešno ste obrisali korisnika!", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
												frameInfoKorisnik.dispose();
												setEnabled(true);
												setVisible(true);
												DomZdravlja.sacuvaj();
											}
										}
										
										if (DomZdravlja.getKorisnici().get(indexKorisnikaZaPrikaz).getUloga() == Uloga.PACIJENT) {
											ArrayList<Termin> zakazaniTermini = new ArrayList<Termin>();
											Pacijent p = (Pacijent)DomZdravlja.getKorisnici().get(indexKorisnikaZaPrikaz);
											for(Termin t: p.getzKarton().getTermini()) {
												if (t.getDatumTermina().isAfter(LocalDateTime.now()) && t.getStatus() == Status.Zakazan) {
													zakazaniTermini.add(t);
												}
											}
											if (zakazaniTermini.size() != 0) {
												JOptionPane.showMessageDialog(pnlInfoKorisnik, "Ne možete obrisati Pacijenta koji ima zakazane termine!", "Upozorenje", JOptionPane.WARNING_MESSAGE);
											} else {
												Pacijent p1 = (Pacijent)DomZdravlja.getKorisnici().get(indexKorisnikaZaPrikaz);
												p1.setAktivan(false);
												p1.getzKarton().setAktivan(false);
												model.removeRow(izabranRed);
												JOptionPane.showMessageDialog(pnlInfoKorisnik, "Uspešno ste obrisali korisnika!", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
												frameInfoKorisnik.dispose();
												setEnabled(true);
												setVisible(true);
												DomZdravlja.sacuvaj();
											}
										}
										if (DomZdravlja.getKorisnici().get(indexKorisnikaZaPrikaz).getUloga() == Uloga.ADMIN) {
											DomZdravlja.getKorisnici().remove(indexKorisnikaZaPrikaz);
											model.removeRow(izabranRed);
											JOptionPane.showMessageDialog(pnlInfoKorisnik, "Uspešno ste obrisali korisnika!", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
											frameInfoKorisnik.dispose();
											setEnabled(true);
											setVisible(true);
											DomZdravlja.sacuvaj();
										}
								}
							}
						});
                    	
                    	pnlInfoKorisnik.setLayout(new MigLayout());
                    	pnlInfoKorisnik.add(idKorisnika,"gap 100");
                    	pnlInfoKorisnik.add(ID,"wrap, gap 100");
                    	pnlInfoKorisnik.add(korisnickoIme,"gap 100");
                    	pnlInfoKorisnik.add(kIme, "wrap, gap 100");
                    	pnlInfoKorisnik.add(ulogaKorisnika,"gap 100");
                    	pnlInfoKorisnik.add(uloga, "wrap, gap 100");
                    	pnlInfoKorisnik.add(imeKorisnika,"gap 100");
                    	pnlInfoKorisnik.add(ime, "wrap, gap 100");
                    	pnlInfoKorisnik.add(prezimeKorisnika,"gap 100");
                    	pnlInfoKorisnik.add(prezime, "wrap, gap 100");
                    	pnlInfoKorisnik.add(polKorisnika,"gap 100");
                    	pnlInfoKorisnik.add(pol, "wrap, gap 100");
                    	pnlInfoKorisnik.add(jMBGKorisnika,"gap 100");
                    	pnlInfoKorisnik.add(jmbg, "wrap, gap 100");
                    	pnlInfoKorisnik.add(adresaKorisnika,"gap 100");
                    	pnlInfoKorisnik.add(adresa, "wrap, gap 100");
                    	pnlInfoKorisnik.add(brojKorisnika,"gap 100");
                    	pnlInfoKorisnik.add(broj,"wrap, gap 100");
                    	pnlInfoKorisnik.add(btnIzmeniKorisnika, "gap 100");
                    	pnlInfoKorisnik.add(btnObrisiKorisnika);
                    	
                    	frameInfoKorisnik.add(infoKorisnik, BorderLayout.NORTH);
                    	frameInfoKorisnik.add(pnlInfoKorisnik, BorderLayout.CENTER);
                    }
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}});
		
		JScrollPane scrollPane = new JScrollPane(tabela);
		
		int i = 0;
		int velicina = model.getRowCount();
		while (i < velicina) {
			if (model.getValueAt(i, 0) == null) {
				model.removeRow(i);
				velicina -= 1;
			} else {
				i++;
			}
		}
		
		
		return scrollPane;
		
	}
	
	protected JScrollPane tabelaKorisniciTermini(Pacijent p) {
		ArrayList<Integer> redovi = new ArrayList<Integer>();
		
		String[] zaglavlja = new String[] {"ID","Lekar", "Status", "Datum i vreme Termina"};

		ArrayList<String> terminLn = null;
		try {
			terminLn = DomZdravlja.procitajTermine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Sadrzaj tabele[brojRedova][brojKolona]
		Object[][] sadrzaj = new Object[terminLn.size()][zaglavlja.length];

		
		for(int i=0; i<sadrzaj.length; i++) {
			String[] info = terminLn.get(i).split(",");
			if (info[1].compareTo(" ") != 0 && Integer.parseInt(info[1]) == p.getId()) {
				sadrzaj[i][0] = info[0];
				
				for (Korisnik k : DomZdravlja.getKorisnici()) {
					if (k.getId() == Integer.parseInt(info[2])) {
						sadrzaj[i][1] = k.getKorisnickoIme();
						break;
					}
				}
				
				sadrzaj[i][2] = Status.values()[Integer.parseInt(info[3])];
				sadrzaj[i][3] = LocalDateTime.parse(info[4], DATE_TIME_FORMATTER);
			} else {
				redovi.add(i);
			}
		}
		
		modelSpec = new DefaultTableModel(sadrzaj, zaglavlja);
		tabela = new JTable(modelSpec);

		tabela.setRowSelectionAllowed(true);

		tabela.setColumnSelectionAllowed(false);

		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tabela.setDefaultEditor(Object.class, null);
		
		tabela.addMouseListener(new MouseListener() {


			@Override
			public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                	//Castujemo u JTable zato sto od nje dolazi event
                	JTable red = (JTable)e.getSource();
                    izabranRed = red.getSelectedRow();
                    if (izabranRed != -1) {
                    	PocetnaStranicaAdmin.this.setEnabled(false);
                    	
                    	framePrikazTermina.setEnabled(false);
                    	frameInfoTermin1 = new JFrame();
                    	frameInfoTermin1.setSize(new Dimension(500, 600));
                    	frameInfoTermin1.setVisible(true);
                    	frameInfoTermin1.setDefaultCloseOperation(frameInfoTermin.DISPOSE_ON_CLOSE);
                    	frameInfoTermin1.setLocationRelativeTo(PocetnaStranicaAdmin.this);
                    	frameInfoTermin1.setResizable(false);
                    	
                    	frameInfoTermin1.addWindowListener(new WindowListener() {

							@Override
							public void windowOpened(WindowEvent e) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void windowClosing(WindowEvent e) {
								framePrikazTermina.setEnabled(true);
							}

							@Override
							public void windowClosed(WindowEvent e) {
							}

							@Override
							public void windowIconified(WindowEvent e) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void windowDeiconified(WindowEvent e) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void windowActivated(WindowEvent e) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void windowDeactivated(WindowEvent e) {
								// TODO Auto-generated method stub
								
							}});
                    	
                    	JLabel lblTerminInfo = new JLabel("Informacije o terminu");
                    	lblTerminInfo.setFont(new Font("Bold", Font.PLAIN, 30));
                    	lblTerminInfo.setHorizontalAlignment(SwingUtilities.CENTER);
                    	
                    	indexTerminaZaPrikaz = 0;
                    	for (Termin t: DomZdravlja.getTermini()) {
                    		if (t.getId() == Integer.parseInt(modelSpec.getValueAt(izabranRed, 0).toString())) {
                    			break;
                    		}
                    		indexTerminaZaPrikaz += 1;
                    	}
                    	
                    	JPanel pnlTerminInfo = new JPanel();
                    	pnlTerminInfo.setLayout(new MigLayout());
                    	
                    	JLabel idTermina = new JLabel("ID Termina: ");
                    	idTermina.setFont(new Font("Bold", Font.PLAIN, 15));
                    	JLabel ID = new JLabel(Integer.toString(DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getId()));
                    	
                    	JLabel pacijentTermin = new JLabel("Pacijent: ");
                    	pacijentTermin.setFont(new Font("Bold", Font.PLAIN, 15));
                    	JLabel pacijent = null;
                    	if (DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getPacijent() != null) {
                    		pacijent = new JLabel(DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getPacijent().getKorisnickoIme());
                    	} else {
                    		pacijent = new JLabel("/");
                    	}
                    	
                    	JLabel lekarTermin = new JLabel("Lekar: ");
                    	lekarTermin.setFont(new Font("Bold", Font.PLAIN, 15));
                    	JLabel lekar = new JLabel(DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getLekar().getKorisnickoIme());
                    	
                    	JLabel datumTermina = new JLabel("Datum Termina: ");
                    	datumTermina.setFont(new Font("Bold", Font.PLAIN, 15));
                    	JLabel datum = new JLabel(DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getDatumTermina().toString());
                    	
                    	JLabel statusTermin = new JLabel("Status Termina: ");
                    	statusTermin.setFont(new Font("Bold", Font.PLAIN, 15));
                    	JLabel status = new JLabel(DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getStatus().toString());
                    	
                    	JButton otkaziTermin = new JButton("Otkaži Termin");
                    	if (status.getText() == "Otkazan" || status.getText() == "Slobodan" || DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getDatumTermina().isBefore(LocalDateTime.now())) {
                    		otkaziTermin.setEnabled(false);
                    	} 
                    	otkaziTermin.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								if (DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getStatus() == Status.Zakazan && DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getDatumTermina().isAfter(LocalDateTime.now())) {
									int a = JOptionPane.showConfirmDialog(frameInfoTermin1, "Da li ste sigurni da želite da otkažete termin?", "Potvrda", JOptionPane.YES_NO_OPTION);
										if (a == JOptionPane.YES_OPTION) {
											for (Termin t: DomZdravlja.getTermini()) {
												if (t.getId() == Integer.parseInt(ID.getText())) {
													t.setStatus(Status.Otkazan);
												}
											}
											setEnabled(true);
											modelSpec.setValueAt("Otkazan", izabranRed, 2);
											JOptionPane.showMessageDialog(frameInfoTermin1, "Termin je uspešno otkazan!", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
											DomZdravlja.sacuvaj();
											frameInfoTermin1.dispose();
										}
								} 
							}});
                    	
                    	pnlTerminInfo.add(idTermina, "gap 100");
                    	pnlTerminInfo.add(ID, "wrap, gap 100");
                    	pnlTerminInfo.add(pacijentTermin, "gap 100");
                    	pnlTerminInfo.add(pacijent, "wrap, gap 100");
                    	pnlTerminInfo.add(lekarTermin, "gap 100");
                    	pnlTerminInfo.add(lekar, "wrap, gap 100");
                    	pnlTerminInfo.add(datumTermina, "gap 100");
                    	pnlTerminInfo.add(datum, "wrap, gap 100");
                    	pnlTerminInfo.add(statusTermin, "gap 100");
                    	pnlTerminInfo.add(status, "wrap, gap 100");
                    	pnlTerminInfo.add(otkaziTermin, "gap 100");
                    	
                    	
                    	frameInfoTermin1.add(pnlTerminInfo,BorderLayout.CENTER);
                    	frameInfoTermin1.add(lblTerminInfo,BorderLayout.NORTH);
                    }
                }
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}});
		
		tabela.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    izabranRed = tabela.getSelectedRow();
                }
			}});

		JScrollPane scrollPane = new JScrollPane(tabela);
		
		int i = 0;
		int velicina = modelSpec.getRowCount();
		while (i < velicina) {
			if (modelSpec.getValueAt(i, 0) == null) {
				modelSpec.removeRow(i);
				velicina -= 1;
			} else {
				i++;
			}
		}
		
		return scrollPane;
		
	}
	
	protected JScrollPane tabelaTermini() {
		String[] zaglavlja = new String[] {"ID","Pacijent","Lekar", "Status", "Datum i vreme Termina"};

		ArrayList<String> terminLn = null;
		try {
			terminLn = DomZdravlja.procitajTermine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Sadrzaj tabele[brojRedova][brojKolona]
		Object[][] sadrzaj = new Object[terminLn.size()][zaglavlja.length];

		
		for(int i=0; i<sadrzaj.length; i++) {
			String[] info = terminLn.get(i).split(",");
			sadrzaj[i][0] = info[0];
			
			if (info[1].compareTo(" ") != 0){
				for (Korisnik k : DomZdravlja.getKorisnici()) {
					if (k.getId() == Integer.parseInt(info[1])) {
						sadrzaj[i][1] = k.getKorisnickoIme();
						break;
					}
				}
			} else {
				sadrzaj[i][1] = " ";
			}
			
			for (Korisnik k : DomZdravlja.getKorisnici()) {
				if (k.getId() == Integer.parseInt(info[2])) {
					sadrzaj[i][2] = k.getKorisnickoIme();
					break;
				}
			}
			
			sadrzaj[i][3] = Status.values()[Integer.parseInt(info[3])];
			sadrzaj[i][4] = LocalDateTime.parse(info[4], DATE_TIME_FORMATTER);
			}
		
		model = new DefaultTableModel(sadrzaj, zaglavlja);
		tabela = new JTable(model);

		tabela.setRowSelectionAllowed(true);

		tabela.setColumnSelectionAllowed(false);

		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tabela.setDefaultEditor(Object.class, null);
		
		tabela.addMouseListener(new MouseListener() {


			@Override
			public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                	//Castujemo u JTable zato sto od nje dolazi event
                	JTable red = (JTable)e.getSource();
                    izabranRed = red.getSelectedRow();
                    if (izabranRed != -1) {
                    	PocetnaStranicaAdmin.this.setEnabled(false);
                    	frameInfoTermin = new JFrame();
                    	frameInfoTermin.setSize(new Dimension(500, 600));
                    	frameInfoTermin.setVisible(true);
                    	frameInfoTermin.setDefaultCloseOperation(frameInfoTermin.DISPOSE_ON_CLOSE);
                    	frameInfoTermin.setLocationRelativeTo(PocetnaStranicaAdmin.this);
                    	frameInfoTermin.setResizable(false);
                    	frameInfoTermin.addWindowListener(new WindowListener() {

							@Override
							public void windowOpened(WindowEvent e) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void windowClosing(WindowEvent e) {
								PocetnaStranicaAdmin.this.setEnabled(true);
								
							}

							@Override
							public void windowClosed(WindowEvent e) {
							}

							@Override
							public void windowIconified(WindowEvent e) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void windowDeiconified(WindowEvent e) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void windowActivated(WindowEvent e) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void windowDeactivated(WindowEvent e) {
								// TODO Auto-generated method stub
								
							}});
                    	
                    	JLabel lblTerminInfo = new JLabel("Informacije o terminu");
                    	lblTerminInfo.setFont(new Font("Bold", Font.PLAIN, 30));
                    	lblTerminInfo.setHorizontalAlignment(SwingUtilities.CENTER);
                    	
                    	indexTerminaZaPrikaz = 0;
                    	for (Termin t: DomZdravlja.getTermini()) {
                    		if (t.getId() == Integer.parseInt(model.getValueAt(izabranRed, 0).toString())) {
                    			break;
                    		}
                    		indexTerminaZaPrikaz += 1;
                    	}
                    	
                    	JPanel pnlTerminInfo = new JPanel();
                    	pnlTerminInfo.setLayout(new MigLayout());
                    	
                    	JLabel idTermina = new JLabel("ID Termina: ");
                    	idTermina.setFont(new Font("Bold", Font.PLAIN, 15));
                    	JLabel ID = new JLabel(Integer.toString(DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getId()));
                    	
                    	JLabel pacijentTermin = new JLabel("Pacijent: ");
                    	pacijentTermin.setFont(new Font("Bold", Font.PLAIN, 15));
                    	JLabel pacijent = null;
                    	if (DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getPacijent() != null) {
                    		pacijent = new JLabel(DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getPacijent().getKorisnickoIme());
                    	} else {
                    		pacijent = new JLabel("/");
                    	}
                    	
                    	JLabel lekarTermin = new JLabel("Lekar: ");
                    	lekarTermin.setFont(new Font("Bold", Font.PLAIN, 15));
                    	JLabel lekar = new JLabel(DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getLekar().getKorisnickoIme());
                    	
                    	JLabel datumTermina = new JLabel("Datum Termina: ");
                    	datumTermina.setFont(new Font("Bold", Font.PLAIN, 15));
                    	JLabel datum = new JLabel(DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getDatumTermina().toString());
                    	
                    	JLabel statusTermin = new JLabel("Status Termina: ");
                    	statusTermin.setFont(new Font("Bold", Font.PLAIN, 15));
                    	JLabel status = new JLabel(DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getStatus().toString());
                    	
                    	JButton otkaziTermin = new JButton("Otkaži Termin");
                    	if (status.getText() == "Otkazan" || status.getText() == "Slobodan" || DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getDatumTermina().isBefore(LocalDateTime.now())) {
                    		otkaziTermin.setEnabled(false);
                    	} 
                    	otkaziTermin.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								if (DomZdravlja.getTermini().get(izabranRed).getStatus() == Status.Zakazan && DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getDatumTermina().isAfter(LocalDateTime.now())) {
									int a = JOptionPane.showConfirmDialog(frameInfoTermin, "Da li ste sigurni da želite da otkažete termin?", "Potvrda", JOptionPane.YES_NO_OPTION);
										if (a == JOptionPane.YES_OPTION) {
											for (Termin t: DomZdravlja.getTermini()) {
												if (t.getId() == Integer.parseInt(ID.getText())) {
													t.setStatus(Status.Otkazan);
												}
											}
											setEnabled(true);
											model.setValueAt("Otkazan", izabranRed, 3);
											JOptionPane.showMessageDialog(frameInfoTermin, "Termin je uspešno otkazan!", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
											DomZdravlja.sacuvaj();
											frameInfoTermin.dispose();
										}
								} 
							}});
                    	
                    	pnlTerminInfo.add(idTermina, "gap 100");
                    	pnlTerminInfo.add(ID, "wrap, gap 100");
                    	pnlTerminInfo.add(pacijentTermin, "gap 100");
                    	pnlTerminInfo.add(pacijent, "wrap, gap 100");
                    	pnlTerminInfo.add(lekarTermin, "gap 100");
                    	pnlTerminInfo.add(lekar, "wrap, gap 100");
                    	pnlTerminInfo.add(datumTermina, "gap 100");
                    	pnlTerminInfo.add(datum, "wrap, gap 100");
                    	pnlTerminInfo.add(statusTermin, "gap 100");
                    	pnlTerminInfo.add(status, "wrap, gap 100");
                    	pnlTerminInfo.add(otkaziTermin, "gap 100");
                    	
                    	
                    	frameInfoTermin.add(pnlTerminInfo,BorderLayout.CENTER);
                    	frameInfoTermin.add(lblTerminInfo,BorderLayout.NORTH);
                    }
                }
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}});
		
		tabela.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    izabranRed = tabela.getSelectedRow();
                }
			}});

		JScrollPane scrollPane = new JScrollPane(tabela);
		
		return scrollPane;
		
	}
	
}