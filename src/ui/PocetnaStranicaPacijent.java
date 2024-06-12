package ui;

import java.awt.*; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import model.*;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class PocetnaStranicaPacijent extends JFrame{
	private Pacijent k;
	private JPanel mainSection;
	
	private JFrame mainPacijent;
	private JMenuBar menuBarPacijent;
	private JMenu terminiMenu;
	private JMenu terapijeMenu;
	private JToolBar tBarPacijent;
	private JButton btnPocetna;
	
	private JButton btnZakazi;
	private JFrame frameZakazi;
	private JFrame frameTermin;
	
	private DefaultTableModel model;
	private JTable tabela;
	private int izabranRed;
	private JScrollPane tblTermini;
	private JFrame frameInfoTermin;
	private int indexTerminaZaPrikaz;
	private TableRowSorter<TableModel> sorter;
	
	private JFrame frameInfoTerapija;
	private JScrollPane tblTerapije;
	private int indexTerapijeZaPrikaz;
	private DefaultTableModel modelTerapija;
	
	private DefaultTableModel modelStats;
	private JTable tabelaStats;
	private JScrollPane tblStats;
	private TableRowSorter<TableModel> sorterStats;
	
	private static final String DATE_TIME_FORMAT = "dd.MM.yyyy. HH:mm";
	private static final String DATE_TIME_FORMAT_ZAKAZIVANJE = "dd.MM.yyyy.";
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
	private static final DateTimeFormatter DATE_TIME_FORMATTER_ZAKAZIVANJE = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_ZAKAZIVANJE);

	
	public PocetnaStranicaPacijent(Pacijent korisnik) {
		Toolkit t = Toolkit.getDefaultToolkit();
		Dimension screenSize = t.getScreenSize();
		
		k = korisnik;
		
		mainPacijent = new JFrame("Početna Stranica");
		setVisible(true);
		setResizable(false);
		setTitle("Pacijent");
		setSize(screenSize.width /2, screenSize.width /3);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(PocetnaStranicaPacijent.this.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent e) {
				int a = JOptionPane.showConfirmDialog(mainPacijent, "Da li ste sigurni da želite da napustite aplikaciju?", "Potvrda", JOptionPane.YES_NO_OPTION);
				
				if (a == JOptionPane.YES_OPTION) {
					System.exit(0);
				} else {
					setVisible(true);
					setEnabled(true);
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
		
		
        JTextField filterField = new JTextField(20);
        JLabel pretraziTab = new JLabel("Pretraži tabelu:");
        filterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = filterField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });

		tBarPacijent = new JToolBar();
		tBarPacijent.setLayout(new MigLayout("insets 0"));
		btnPocetna = new JButton(new ImageIcon("img/homepage.png"));
		btnPocetna.setFocusPainted(false);
		btnPocetna.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tblTermini = tblTermini();
				mainSection.removeAll();
				tBarPacijent.remove(filterField);
				tBarPacijent.remove(pretraziTab);
				tBarPacijent.add(pretraziTab, "gap 200");
				tBarPacijent.add(filterField);
				mainSection.add(tblTermini, "grow, push");
				revalidate();
				repaint();
			}});
		
		tBarPacijent.add(btnPocetna);
        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setPreferredSize(new Dimension(1, 50));
        tBarPacijent.add(separator, "pushy, gapright 5");
		tBarPacijent.add(pretraziTab, "gap 200");
		tBarPacijent.add(filterField);
		tBarPacijent.setFloatable(false);
		
		mainSection = new JPanel();
		mainSection.setLayout(new MigLayout());

		tblTerapije = tblTerapije();
		
		mainSection.add(tblTermini(), "grow, push");
		
		menuBarPacijent = new JMenuBar();
		JMenu menuPacijent = new JMenu("Menu");
		
		JMenuItem m0 = new JMenuItem("Sačuvaj");
		m0.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(PocetnaStranicaPacijent.this, "Podaci uspešno sačuvani", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
			}});

		JMenuItem m1 = new JMenuItem("Log out");
		m1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				int a = JOptionPane.showConfirmDialog(mainPacijent, "Da li ste sigurni da želite da se izlogujete?", "Potvrda", JOptionPane.YES_NO_OPTION);
					
					if (a == JOptionPane.YES_OPTION) {
						PocetnaStranicaPacijent.this.dispose();
						new Prozor();
					}
			}
		});
				
		JMenuItem m2 = new JMenuItem("Exit");
		m2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int a = JOptionPane.showConfirmDialog(mainPacijent, "Da li ste sigurni da želite da napustite aplikaciju?", "Potvrda", JOptionPane.YES_NO_OPTION);
				
				if (a == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});

		terminiMenu = new JMenu("Termini");
		JMenuItem terminiItem = new JMenuItem("Prikaži moje termine");
		terminiItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
					tblTermini = tblTermini();
					mainSection.removeAll();
					tBarPacijent.remove(filterField);
					tBarPacijent.remove(pretraziTab);
					tBarPacijent.add(pretraziTab, "gap 200");
					tBarPacijent.add(filterField);
					mainSection.add(tblTermini, "grow, push");
					revalidate();
					repaint();
			}});
		
		JMenuItem slobodniTerminiMenuItem = new JMenuItem("Prikaži slobodne termine");
		slobodniTerminiMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
					tblTermini = tblSlobodniTermini();
					mainSection.removeAll();
					tBarPacijent.remove(filterField);
					tBarPacijent.remove(pretraziTab);
					tBarPacijent.add(pretraziTab, "gap 200");
					tBarPacijent.add(filterField);
					mainSection.add(tblTermini, "grow, push");
					revalidate();
					repaint();
			}});
		
		JMenuItem statistikaMenuItem = new JMenuItem("Prikaži statistiku termina");
		statistikaMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainSection.removeAll();
				tBarPacijent.remove(filterField);
				tBarPacijent.remove(pretraziTab);
				
				
				
				HashMap<String, Integer> lekari = new HashMap<>();
				HashMap<String, Integer> zakazaniLekari = new HashMap<>();
				
				for (Korisnik k : DomZdravlja.getKorisnici()) {
					if (k.getUloga() == Uloga.LEKAR) {
						lekari.put(k.getKorisnickoIme(), 0);
					}
				}
				
				for (Termin t : DomZdravlja.getTermini()) {
					if (t.getPacijent() != null && t.getPacijent().getKorisnickoIme().compareTo(k.getKorisnickoIme()) == 0) {
						for (String key : lekari.keySet()) {
							if (t.getLekar().getKorisnickoIme().compareTo(key) == 0 && t.getStatus() == Status.Zakazan && t.getDatumTermina().isBefore(LocalDateTime.now())) {
								int cnt = lekari.get(key);
								lekari.put(key, cnt + 1);
							}
						}
					}
				}
				
				for (String key : lekari.keySet()) {
					if (lekari.get(key) != 0) {
						zakazaniLekari.put(key, lekari.get(key));
					}
				}
				
				tblStats = ucitajTabeluStats(zakazaniLekari);
				mainSection.add(tblStats, "grow, push");
				
				revalidate();
				repaint();
			}});
		
		terapijeMenu = new JMenu("Terapije");
		JMenuItem terapijeItem = new JMenuItem("Prikaži moje terapije");
		terapijeItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

					tblTerapije = tblTerapije();
					mainSection.removeAll();
					tBarPacijent.remove(filterField);
					tBarPacijent.remove(pretraziTab);
					mainSection.add(tblTerapije, "grow, push");
					revalidate();
					repaint();
			}});
		
		terminiMenu.add(terminiItem);
		terminiMenu.add(slobodniTerminiMenuItem);
		terminiMenu.addSeparator();
		terminiMenu.add(statistikaMenuItem);
		terapijeMenu.add(terapijeItem);
		
		
		menuPacijent.add(m0);
		menuPacijent.addSeparator();
		menuPacijent.add(m1);
		menuPacijent.add(m2);
		menuBarPacijent.add(menuPacijent);
		menuBarPacijent.add(terminiMenu);
		menuBarPacijent.add(terapijeMenu);
		setJMenuBar(menuBarPacijent);
		
		add(tBarPacijent, BorderLayout.NORTH);
		add(mainSection, BorderLayout.CENTER);
	};
	
	private JScrollPane tblTermini() {
		
		ArrayList<Integer> redovi = new ArrayList<Integer>();;
		
		String[] zaglavlja = new String[] {"ID", "Lekar", "Status", "Datum i vreme Termina"};
		
		ArrayList<String> terminiLn = null;
		try {
			terminiLn = DomZdravlja.procitajTermine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Object[][] sadrzaj = new Object[terminiLn.size()][zaglavlja.length];
		
		for (int i = 0; i < sadrzaj.length; i++) {
			String[] info = terminiLn.get(i).split(",");
			String lekar = null;
			if (info[1].compareTo(" ") != 0) {
				if (Integer.parseInt(info[1]) == k.getId() && Status.values()[Integer.parseInt(info[3])] != Status.Slobodan) {
					for (Korisnik k : DomZdravlja.getKorisnici()) {
						if (k.getId() == Integer.parseInt(info[2])) {
							sadrzaj[i][1] = k.getKorisnickoIme();
							lekar = k.getKorisnickoIme();
							break;
						}
					}
					sadrzaj[i][0] = info[0];
					sadrzaj[i][2] = Status.values()[Integer.parseInt(info[3])];
					sadrzaj[i][3] = LocalDateTime.parse(info[4], DATE_TIME_FORMATTER);
				} else {
					redovi.add(i);
				}
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
                    	PocetnaStranicaPacijent.this.setEnabled(false);
                    	frameInfoTermin = new JFrame();
                    	frameInfoTermin.setSize(new Dimension(500, 600));
                    	frameInfoTermin.setVisible(true); 
                    	frameInfoTermin.setDefaultCloseOperation(frameInfoTermin.DISPOSE_ON_CLOSE);
                    	frameInfoTermin.setLocationRelativeTo(PocetnaStranicaPacijent.this);
                    	frameInfoTermin.setResizable(false);
                    	frameInfoTermin.addWindowListener(new WindowListener() {

							@Override
							public void windowOpened(WindowEvent e) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void windowClosing(WindowEvent e) {
								PocetnaStranicaPacijent.this.setEnabled(true);
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
                    	
                    	JPanel pnlTerminInfo = new JPanel();
                    	pnlTerminInfo.setLayout(new MigLayout());
                    	
                    	indexTerminaZaPrikaz = 0;
                    	for (Termin t: DomZdravlja.getTermini()) {
                    		if (t.getId() == Integer.parseInt(model.getValueAt(izabranRed, 0).toString())) {
                    			break;
                    		}
                    		indexTerminaZaPrikaz += 1;
                    	}
                    	
                    	JLabel idTermina = new JLabel("ID Termina: ");
                    	idTermina.setFont(new Font("Bold", Font.PLAIN, 15));
                    	JLabel ID = new JLabel(Integer.toString(DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getId()));
                    	
                    	JLabel pacijentTermin = new JLabel("Pacijent: ");
                    	pacijentTermin.setFont(new Font("Bold", Font.PLAIN, 15));
                    	JLabel pacijent = null;
                    	if (model.getValueAt(izabranRed, 2).toString().compareTo("Slobodan")== 0) {
                    		pacijent = new JLabel("/");
                    	} else {
                        	pacijent = new JLabel(DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getPacijent().getKorisnickoIme());
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
		
		sorter = new TableRowSorter<>(model);
		tabela.setRowSorter(sorter);
		
		sorter.setComparator(0, new Comparator<String>() {
            @Override
            public int compare(String id1, String id2) {
                try {
                    Integer intId1 = Integer.parseInt(id1);
                    Integer intId2 = Integer.parseInt(id2);
                    return intId1.compareTo(intId2);
                } catch (NumberFormatException e) {
                    return id1.compareTo(id2);
                }
            }
        });
		
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
	};
	
	private JScrollPane tblTerapije() {
		ArrayList<Integer> redovi = new ArrayList<Integer>();
		
		String[] zaglavlja = new String[] {"ID", "Prepisao lekar", "Prepisano pacijentu", "Opis terapije"};
		
		ArrayList<String> terapijeLn = null;
		try {
			terapijeLn = DomZdravlja.procitajTerapije();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Object[][] sadrzaj = new Object[terapijeLn.size()][zaglavlja.length];
		
		for (int i = 0; i < sadrzaj.length; i++) {
			String[] info = terapijeLn.get(i).split(",");
			if (Integer.parseInt(info[2]) == k.getzKarton().getId()) {
				sadrzaj[i][0] = info[0];
				for (Korisnik k : DomZdravlja.getKorisnici()) {
					if (k.getId() == Integer.parseInt(info[1])) {
						sadrzaj[i][1] = k.getKorisnickoIme();
					}
				}
				sadrzaj[i][2] = k.getKorisnickoIme();
				sadrzaj[i][3] = info[3];
			} else {
				redovi.add(i);
			}
		}
	
		modelTerapija = new DefaultTableModel(sadrzaj, zaglavlja);
		tabela = new JTable(modelTerapija);
		
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
                    	PocetnaStranicaPacijent.this.setEnabled(false);
                    	frameInfoTerapija = new JFrame();
                    	frameInfoTerapija.setSize(new Dimension(500, 600));
                    	frameInfoTerapija.setVisible(true); 
                    	frameInfoTerapija.setDefaultCloseOperation(frameInfoTermin.DISPOSE_ON_CLOSE);
                    	frameInfoTerapija.setLocationRelativeTo(PocetnaStranicaPacijent.this);
                    	frameInfoTerapija.setResizable(false);
                    	frameInfoTerapija.addWindowListener(new WindowListener() {

							@Override
							public void windowOpened(WindowEvent e) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void windowClosing(WindowEvent e) {
								PocetnaStranicaPacijent.this.setEnabled(true);
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
                    	
                    	JLabel lblTerapijaInfo = new JLabel("Informacije o terapiji");
                    	lblTerapijaInfo.setFont(new Font("Bold", Font.PLAIN, 30));
                    	lblTerapijaInfo.setHorizontalAlignment(SwingUtilities.CENTER);
                    	
                    	JPanel pnlTerapijaInfo = new JPanel();
                    	pnlTerapijaInfo.setLayout(new MigLayout());
                    	
                    	indexTerapijeZaPrikaz = 0;
                    	for (Terapija t: DomZdravlja.getTerapije()) {
                    		if (t.getId() == Integer.parseInt(modelTerapija.getValueAt(izabranRed, 0).toString())) {
                    			break;
                    		}
                    		indexTerapijeZaPrikaz += 1;
                    	}
                    	System.out.println(indexTerapijeZaPrikaz);
                    	
                    	JLabel idTerapije = new JLabel("ID Terapije: ");
                    	idTerapije.setFont(new Font("Bold", Font.PLAIN, 15));
                    	JLabel ID = new JLabel(Integer.toString(DomZdravlja.getTerapije().get(indexTerapijeZaPrikaz).getId()));
                    	
                    	JLabel pacijentTerapije = new JLabel("Prepisano: ");
                    	pacijentTerapije.setFont(new Font("Bold", Font.PLAIN, 15));
                    	JLabel pacijent = new JLabel(DomZdravlja.getTerapije().get(indexTerapijeZaPrikaz).getzKarton().getPacijent().getKorisnickoIme());
                    	
                    	JLabel lekarTerapije = new JLabel("Prepisao: ");
                    	lekarTerapije.setFont(new Font("Bold", Font.PLAIN, 15));
                    	JLabel lekar = new JLabel(DomZdravlja.getTerapije().get(indexTerapijeZaPrikaz).getLekar().getKorisnickoIme());
                    	
                    	JLabel opisTerapije = new JLabel("Opis Terapije: ");
                    	opisTerapije.setFont(new Font("Bold", Font.PLAIN, 15));
                    	JLabel opis = new JLabel(DomZdravlja.getTerapije().get(indexTerapijeZaPrikaz).getOpisTerapije());
             	
                    	
                    	pnlTerapijaInfo.add(idTerapije, "gap 60");
                    	pnlTerapijaInfo.add(ID, "wrap, gap 60");
                    	pnlTerapijaInfo.add(lekarTerapije, "gap 60");
                    	pnlTerapijaInfo.add(lekar, "wrap, gap 60");
                    	pnlTerapijaInfo.add(opisTerapije, "gap 60");
                    	pnlTerapijaInfo.add(opis, "wrap, gap 60");
                    	
                    	frameInfoTerapija.add(pnlTerapijaInfo,BorderLayout.CENTER);
                    	frameInfoTerapija.add(lblTerapijaInfo,BorderLayout.NORTH);
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
		int velicina = modelTerapija.getRowCount();
		while (i < velicina) {
			if (modelTerapija.getValueAt(i, 0) == null) {
				modelTerapija.removeRow(i);
				velicina -= 1;
			} else {
				i++;
			}
		}
		
		return scrollPane;
	};
	
	private JScrollPane tblSlobodniTermini() {
		ArrayList<Integer> redovi = new ArrayList<Integer>();;
		
		String[] zaglavlja = new String[] {"ID", "Lekar", "Status", "Datum i vreme Termina"};
		
		ArrayList<String> terminiLn = null;
		try {
			terminiLn = DomZdravlja.procitajTermine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Object[][] sadrzaj = new Object[terminiLn.size()][zaglavlja.length];
		
		for (int i = 0; i < sadrzaj.length; i++) {
			String[] info = terminiLn.get(i).split(",");
			String lekar = null;
				if (Status.values()[Integer.parseInt(info[3])] == Status.Slobodan && LocalDateTime.parse(info[4], DATE_TIME_FORMATTER).isAfter(LocalDateTime.now())) {
					for (Korisnik k : DomZdravlja.getKorisnici()) {
						if (k.getId() == Integer.parseInt(info[2])) {
							sadrzaj[i][1] = k.getKorisnickoIme();
							lekar = k.getKorisnickoIme();
							break;
						}
					}
					sadrzaj[i][0] = info[0];
					sadrzaj[i][2] = Status.values()[Integer.parseInt(info[3])];
					sadrzaj[i][3] = LocalDateTime.parse(info[4], DATE_TIME_FORMATTER);
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
                    	PocetnaStranicaPacijent.this.setEnabled(false);
                    	frameInfoTermin = new JFrame();
                    	frameInfoTermin.setSize(new Dimension(500, 600));
                    	frameInfoTermin.setVisible(true); 
                    	frameInfoTermin.setDefaultCloseOperation(frameInfoTermin.DISPOSE_ON_CLOSE);
                    	frameInfoTermin.setLocationRelativeTo(PocetnaStranicaPacijent.this);
                    	frameInfoTermin.setResizable(false);
                    	frameInfoTermin.addWindowListener(new WindowListener() {

							@Override
							public void windowOpened(WindowEvent e) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void windowClosing(WindowEvent e) {
								PocetnaStranicaPacijent.this.setEnabled(true);
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
                    	
                    	JPanel pnlTerminInfo = new JPanel();
                    	pnlTerminInfo.setLayout(new MigLayout());
                    	
                    	indexTerminaZaPrikaz = 0;
                    	for (Termin t: DomZdravlja.getTermini()) {
                    		if (t.getId() == Integer.parseInt(model.getValueAt(izabranRed, 0).toString())) {
                    			break;
                    		}
                    		indexTerminaZaPrikaz += 1;
                    	}
                    	
                    	JLabel idTermina = new JLabel("ID Termina: ");
                    	idTermina.setFont(new Font("Bold", Font.PLAIN, 15));
                    	JLabel ID = new JLabel(Integer.toString(DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getId()));
                    	
                    	JLabel pacijentTermin = new JLabel("Pacijent: ");
                    	pacijentTermin.setFont(new Font("Bold", Font.PLAIN, 15));
                    	JLabel pacijent = null;
                    	if (model.getValueAt(izabranRed, 2).toString().compareTo("Slobodan")== 0) {
                    		pacijent = new JLabel("/");
                    	} else {
                        	pacijent = new JLabel(DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getPacijent().getKorisnickoIme());
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
                    	
                    	JButton popuniTermin = new JButton("Zauzmi Termin");
                    	if (DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getStatus() == Status.Slobodan && DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getDatumTermina().isAfter(LocalDateTime.now())){
                    		popuniTermin.setEnabled(true);
                    	} else {
                    		popuniTermin.setEnabled(false);
                    	};
                    	popuniTermin.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								String[] datumstr = datum.getText().split("T");
								int a = JOptionPane.showConfirmDialog(frameInfoTermin, "Da li ste sigurni da želite da popunite termin \n u " + datumstr[0] + " " + datumstr[1] + " kod lekara " + lekar.getText() + "?", "Potvrda", JOptionPane.OK_CANCEL_OPTION);
								if (a == JOptionPane.YES_OPTION) {
									DomZdravlja.getTermini().get(indexTerminaZaPrikaz).setPacijent(k);
									DomZdravlja.getTermini().get(indexTerminaZaPrikaz).setStatus(Status.Zakazan);
									DomZdravlja.sacuvaj();
									model.removeRow(izabranRed);
									frameInfoTermin.dispose();
									setEnabled(true);
									setVisible(true);
								}
							}
                    		
                    	});
                  
                    	
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
                    	pnlTerminInfo.add(popuniTermin, "gap 100");
                    	
                    	
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
		
		sorter = new TableRowSorter<>(model);
		tabela.setRowSorter(sorter);
		
		sorter.setComparator(0, new Comparator<String>() {
            @Override
            public int compare(String id1, String id2) {
                try {
                    Integer intId1 = Integer.parseInt(id1);
                    Integer intId2 = Integer.parseInt(id2);
                    return intId1.compareTo(intId2);
                } catch (NumberFormatException e) {
                    return id1.compareTo(id2);
                }
            }
        });
		
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

	private JScrollPane ucitajTabeluStats(HashMap<String, Integer> lekari) {
		
		String[] zaglavlja = new String[] {"Lekar", "Broj obavljenih pregleda"};
		
		ArrayList<String> redovi = new ArrayList<String>();
		for (String key : lekari.keySet()) {
			redovi.add(key + "," + String.valueOf(lekari.get(key)));
		}
		
		Object[][] sadrzaj = new Object[redovi.size()][zaglavlja.length];
		
		for (int i = 0; i < sadrzaj.length; i++) {
			String[] info = redovi.get(i).split(",");
			
			sadrzaj[i][0] = info[0];
			sadrzaj[i][1] = info[1];
			
		}
	
		modelStats = new DefaultTableModel(sadrzaj, zaglavlja);
		tabelaStats = new JTable(modelStats);
		
		tabelaStats.setRowSelectionAllowed(true);

		tabelaStats.setColumnSelectionAllowed(false);

		tabelaStats.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tabelaStats.setDefaultEditor(Object.class, null);
//		tabela.addMouseListener(new MouseListener() {
//
//			@Override
//			public void mouseClicked(MouseEvent e) {
//                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
//                	//Castujemo u JTable zato sto od nje dolazi event
//                	JTable red = (JTable)e.getSource();
//                    izabranRed = red.getSelectedRow();
//                    if (izabranRed != -1) {
//                    	PocetnaStranicaPacijent.this.setEnabled(false);
//                    	frameInfoTermin = new JFrame();
//                    	frameInfoTermin.setSize(new Dimension(500, 600));
//                    	frameInfoTermin.setVisible(true); 
//                    	frameInfoTermin.setDefaultCloseOperation(frameInfoTermin.DISPOSE_ON_CLOSE);
//                    	frameInfoTermin.setLocationRelativeTo(PocetnaStranicaPacijent.this);
//                    	frameInfoTermin.setResizable(false);
//                    	frameInfoTermin.addWindowListener(new WindowListener() {
//
//							@Override
//							public void windowOpened(WindowEvent e) {
//								// TODO Auto-generated method stub
//								
//							}
//
//							@Override
//							public void windowClosing(WindowEvent e) {
//								PocetnaStranicaPacijent.this.setEnabled(true);
//							}
//
//							@Override
//							public void windowClosed(WindowEvent e) {
//							}
//
//							@Override
//							public void windowIconified(WindowEvent e) {
//								// TODO Auto-generated method stub
//								
//							}
//
//							@Override
//							public void windowDeiconified(WindowEvent e) {
//								// TODO Auto-generated method stub
//								
//							}
//
//							@Override
//							public void windowActivated(WindowEvent e) {
//								// TODO Auto-generated method stub
//								
//							}
//
//							@Override
//							public void windowDeactivated(WindowEvent e) {
//								// TODO Auto-generated method stub
//								
//							}});
//                    	
//                    	JLabel lblTerminInfo = new JLabel("Informacije o terminu");
//                    	lblTerminInfo.setFont(new Font("Bold", Font.PLAIN, 30));
//                    	lblTerminInfo.setHorizontalAlignment(SwingUtilities.CENTER);
//                    	
//                    	JPanel pnlTerminInfo = new JPanel();
//                    	pnlTerminInfo.setLayout(new MigLayout());
//                    	
//                    	indexTerminaZaPrikaz = 0;
//                    	for (Termin t: DomZdravlja.getTermini()) {
//                    		if (t.getId() == Integer.parseInt(model.getValueAt(izabranRed, 0).toString())) {
//                    			break;
//                    		}
//                    		indexTerminaZaPrikaz += 1;
//                    	}
//                    	
//                    	JLabel idTermina = new JLabel("ID Termina: ");
//                    	idTermina.setFont(new Font("Bold", Font.PLAIN, 15));
//                    	JLabel ID = new JLabel(Integer.toString(DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getId()));
//                    	
//                    	JLabel pacijentTermin = new JLabel("Pacijent: ");
//                    	pacijentTermin.setFont(new Font("Bold", Font.PLAIN, 15));
//                    	JLabel pacijent = null;
//                    	if (model.getValueAt(izabranRed, 2).toString().compareTo("Slobodan")== 0) {
//                    		pacijent = new JLabel("/");
//                    	} else {
//                        	pacijent = new JLabel(DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getPacijent().getKorisnickoIme());
//                    	}
//                    	
//                    	JLabel lekarTermin = new JLabel("Lekar: ");
//                    	lekarTermin.setFont(new Font("Bold", Font.PLAIN, 15));
//                    	JLabel lekar = new JLabel(DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getLekar().getKorisnickoIme());
//                    	
//                    	JLabel datumTermina = new JLabel("Datum Termina: ");
//                    	datumTermina.setFont(new Font("Bold", Font.PLAIN, 15));
//                    	JLabel datum = new JLabel(DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getDatumTermina().toString());
//                    	
//                    	JLabel statusTermin = new JLabel("Status Termina: ");
//                    	statusTermin.setFont(new Font("Bold", Font.PLAIN, 15));
//                    	JLabel status = new JLabel(DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getStatus().toString());
//                    	
//                    	JButton popuniTermin = new JButton("Zauzmi Termin");
//                    	if (DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getStatus() == Status.Slobodan && DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getDatumTermina().isAfter(LocalDateTime.now())){
//                    		popuniTermin.setEnabled(true);
//                    	} else {
//                    		popuniTermin.setEnabled(false);
//                    	};
//                    	popuniTermin.addActionListener(new ActionListener() {
//
//							@Override
//							public void actionPerformed(ActionEvent e) {
//								String[] datumstr = datum.getText().split("T");
//								int a = JOptionPane.showConfirmDialog(frameInfoTermin, "Da li ste sigurni da želite da popunite termin \n u " + datumstr[0] + " " + datumstr[1] + " kod lekara " + lekar.getText() + "?", "Potvrda", JOptionPane.OK_CANCEL_OPTION);
//								if (a == JOptionPane.YES_OPTION) {
//									DomZdravlja.getTermini().get(indexTerminaZaPrikaz).setPacijent(k);
//									DomZdravlja.getTermini().get(indexTerminaZaPrikaz).setStatus(Status.Zakazan);
//									DomZdravlja.sacuvaj();
//									model.removeRow(izabranRed);
//									frameInfoTermin.dispose();
//									setEnabled(true);
//									setVisible(true);
//								}
//							}
//                    		
//                    	});
//                  
//                    	
//                    	pnlTerminInfo.add(idTermina, "gap 100");
//                    	pnlTerminInfo.add(ID, "wrap, gap 100");
//                    	pnlTerminInfo.add(pacijentTermin, "gap 100");
//                    	pnlTerminInfo.add(pacijent, "wrap, gap 100");
//                    	pnlTerminInfo.add(lekarTermin, "gap 100");
//                    	pnlTerminInfo.add(lekar, "wrap, gap 100");
//                    	pnlTerminInfo.add(datumTermina, "gap 100");
//                    	pnlTerminInfo.add(datum, "wrap, gap 100");
//                    	pnlTerminInfo.add(statusTermin, "gap 100");
//                    	pnlTerminInfo.add(status, "wrap, gap 100");
//                    	pnlTerminInfo.add(popuniTermin, "gap 100");
//                    	
//                    	
//                    	frameInfoTermin.add(pnlTerminInfo,BorderLayout.CENTER);
//                    	frameInfoTermin.add(lblTerminInfo,BorderLayout.NORTH);
//                    }
//                }
//			}
//
//			@Override
//			public void mousePressed(MouseEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void mouseReleased(MouseEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void mouseEntered(MouseEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void mouseExited(MouseEvent e) {
//				// TODO Auto-generated method stub
//				
//			}});
//		
		sorterStats = new TableRowSorter<>(modelStats);
		tabelaStats.setRowSorter(sorterStats);
		
		sorterStats.setComparator(0, new Comparator<String>() {
            @Override
            public int compare(String id1, String id2) {
                try {
                    Integer intId1 = Integer.parseInt(id1);
                    Integer intId2 = Integer.parseInt(id2);
                    return intId1.compareTo(intId2);
                } catch (NumberFormatException e) {
                    return id1.compareTo(id2);
                }
            }
        });
		
		JScrollPane scrollPane = new JScrollPane(tabelaStats);
		

		
		return scrollPane;
		
	}
}
