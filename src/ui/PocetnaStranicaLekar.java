package ui;

import java.awt.*; 
import java.awt.event.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Properties;

import javax.swing.*;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import model.*;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class PocetnaStranicaLekar extends JFrame{
	private JFrame mainLekar;
	private Korisnik korisnik;
	
	private JButton btnPocetna;
	private JToolBar tBarLekar;
	private JMenu menuTermini;
	private JMenu menuTerapije;
	private JMenu menuIzvestaj;
	private JMenuItem menuTerminPrikaz;
	private JMenuItem menuTerapijaPrikaz;
	private JMenuItem menuIzvestajPrikaz;
	private JScrollPane scrollPane;
	
	private JButton btnZakazi;
	private JFrame frameZakazi;
	private JFrame frameTermin;
	
	private DefaultTableModel model1;
	private JTable tabela;
	private int izabranRed;
	private JScrollPane tblTermini;
	private JFrame frameInfoTermin;
	private int indexTerminaZaPrikaz;
	private  JButton btnDodajTerapiju;
	private JFrame frameEditTermin;
	
	private JFrame frameInfoTerapija;
	private JFrame frameIzmenaTerapija;
	private JFrame frameDodajTerapiju;
	private int indexTerapijeZaPrikaz;
	
	private JFrame frameIntervalIzvestaj;
	private JTextField filterField;
	private JLabel lblPretraga;
	private DefaultTableModel model2;
	private JScrollPane tblIzvestaj;
	private TableRowSorter<TableModel> izvestajSorter;

	
	private static final String DATE_TIME_FORMAT = "dd.MM.yyyy. HH:mm";
	private static final String DATE_TIME_FORMAT_ZAKAZIVANJE = "dd.MM.yyyy.";
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
	private static final DateTimeFormatter DATE_TIME_FORMATTER_ZAKAZIVANJE = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_ZAKAZIVANJE);
	
	public PocetnaStranicaLekar(Korisnik k) {
		Toolkit t = Toolkit.getDefaultToolkit();
		Dimension screenSize = t.getScreenSize();
		
		korisnik = k;
		
		mainLekar = new JFrame("Početna Stranica");
		setVisible(true);
		setTitle("Lekar");
		setResizable(false);
		setSize(screenSize.width /2, screenSize.width /3);
		setDefaultCloseOperation(PocetnaStranicaLekar.DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent e) {
				int a = JOptionPane.showConfirmDialog(mainLekar, "Da li ste sigurni da želite da napustite aplikaciju?", "Potvrda", JOptionPane.YES_NO_OPTION);
				
				if (a == JOptionPane.YES_OPTION) {
					System.exit(0);
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
		
		scrollPane = tblTermini();
		JPanel mainSection = new JPanel();
		mainSection.setLayout(new MigLayout());
		mainSection.add(scrollPane, "grow, push");
		
		filterField = new JTextField(20);
        filterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = filterField.getText();
                if (text.trim().length() == 0) {
                    izvestajSorter.setRowFilter(null);
                } else {
                    izvestajSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });
		lblPretraga = new JLabel("Pretraži Termine: ");
		
		btnPocetna = new JButton(new ImageIcon("img/homepage.png"));
		btnPocetna.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						mainSection.removeAll();
						mainSection.add(tblTermini(), "grow, push");
						btnZakazi.setEnabled(true);
						btnDodajTerapiju.setEnabled(false);
						tBarLekar.remove(filterField);
						tBarLekar.remove(lblPretraga);
						revalidate();
						repaint();
					}});
			}
			
		});
		
		btnZakazi = new JButton(new ImageIcon("img/zakaziTermin.png"));
		btnZakazi.setFocusPainted(false);
		btnZakazi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				setEnabled(false);
				
				frameZakazi = new JFrame();
				frameZakazi.setVisible(true);
				frameZakazi.setSize(new Dimension(500, 600));
				frameZakazi.setLocationRelativeTo(PocetnaStranicaLekar.this);
				frameZakazi.setResizable(false);
				frameZakazi.setLayout(new MigLayout());
				frameZakazi.addWindowListener(new WindowListener() {

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
			
				JLabel lblKreirajTermin = new JLabel("Kreiraj Termin");
				lblKreirajTermin.setFont(new Font("bold",Font.PLAIN, 30));
				
				JLabel lblImePacijenta = new JLabel("Izaberi Pacijenta: ");
				lblImePacijenta.setFont(new Font("bold", Font.PLAIN,15));
				
				DefaultComboBoxModel<String> pacijenti = new DefaultComboBoxModel<String>();
				pacijenti.addElement("-----");
				for (Termin t: DomZdravlja.getTermini()) {
					if (t.getLekar().getId() == korisnik.getId()) {
						int i = 0;
						while (i < pacijenti.getSize()) {
							if (t.getPacijent() != null && pacijenti.getElementAt(i).compareTo((t.getPacijent().getKorisnickoIme())) == 0) {
								break;
							}
							i++;
						}
						
						if (i == pacijenti.getSize() && t.getPacijent() != null) {
							pacijenti.addElement(t.getPacijent().getKorisnickoIme());
						}
					}
				}
				JComboBox cmbPacijenti = new JComboBox(pacijenti);
				
				
				JLabel datumPregleda = new JLabel("Datum Termina: ");
				datumPregleda.setFont(new Font("bold", Font.PLAIN, 15));
				Properties p = new Properties();
				p.put("text.today", "Today");
				p.put("text.month", "Month");
				p.put("text.year", "Year");
				UtilDateModel model = new UtilDateModel();
				JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
				JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
				
				JLabel vremePregleda = new JLabel("Vreme pregleda: ");
				vremePregleda.setFont(new Font("bold", Font.PLAIN, 15));
				String[] vreme = {"07:00", "08:00", "10:00", "15:00", "17:00"};
				JComboBox cmbVreme = new JComboBox(vreme);
				
				JButton btnKreiraj = new JButton("Kreiraj");
				btnKreiraj.setFont(new Font("Bold", Font.PLAIN, 17));
				btnKreiraj.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						int id = DomZdravlja.getTermini().get(DomZdravlja.getTermini().size() - 1).getId() + 1;
						Pacijent p = null;
						Status s = null;
						if (cmbPacijenti.getSelectedIndex() == 0) {
							s = Status.Slobodan;
						} else {
							s = Status.Zakazan;
						} 						

						for (Korisnik k: DomZdravlja.getKorisnici()) {
							if (k.getKorisnickoIme().compareTo((String) cmbPacijenti.getSelectedItem()) == 0) {
								p = (Pacijent)k;
								System.out.println(p.getKorisnickoIme());
							}
						}
						Lekar l = (Lekar)korisnik;
						
						Date selectedDate = (Date) datePicker.getModel().getValue();
						String formatiraniDatum = null;
						LocalDateTime datumTermina = null;
								            
			            // Check if a date is selected
			            if (selectedDate != null && p != null && s != Status.Slobodan) {
			                // Format the date
			                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy.");
			                formatiraniDatum = dateFormat.format(selectedDate) + " " + cmbVreme.getSelectedItem().toString();
			                datumTermina = LocalDateTime.parse(formatiraniDatum, DATE_TIME_FORMATTER);
			                
							int terminCnt = 0;
							for (Termin t : DomZdravlja.getTermini()){
								if (t.getDatumTermina().equals(LocalDateTime.parse(formatiraniDatum, DATE_TIME_FORMATTER)) && t.getLekar().getKorisnickoIme().compareTo(l.getKorisnickoIme()) == 0 && t.getStatus() != Status.Otkazan) {
									terminCnt ++;
								}
							}	
			                if (terminCnt == 0) {
				                if (datumTermina.isAfter(LocalDateTime.now())) {
						            Termin t = new Termin(id, p, l, s, datumTermina);
									DomZdravlja.getTermini().add(t);
						            p.getzKarton().addTermin(t);
									JOptionPane.showMessageDialog(PocetnaStranicaLekar.this, "Uspešno ste zakazali termin!", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
									DomZdravlja.sacuvaj();
									model1.addRow(new Object[] {id, p.getKorisnickoIme(), s.toString(), formatiraniDatum});
									frameZakazi.dispose();
	//								model.(new Object[]{t.getId(),l.getKorisnickoIme(), s.toString(), datumTermina.toString()});
									setVisible(true);
									setEnabled(true);
				                }
				                else {
				                	JOptionPane.showMessageDialog(frameZakazi, "Molim vas izaberite predstojeći datum!", "Upozorenje", JOptionPane.WARNING_MESSAGE);
				                }
			                } else {
			                	JOptionPane.showMessageDialog(frameZakazi, "Odabrani termin već postoji!!", "Obaveštenje", JOptionPane.WARNING_MESSAGE);
			                }
			            } else if (selectedDate != null && p == null && s == Status.Slobodan) {
			                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy.");
			                formatiraniDatum = dateFormat.format(selectedDate) + " " + cmbVreme.getSelectedItem().toString();
			                datumTermina = LocalDateTime.parse(formatiraniDatum, DATE_TIME_FORMATTER);
			                
							int terminCnt = 0;
							for (Termin t : DomZdravlja.getTermini()){
								if (t.getDatumTermina().equals(LocalDateTime.parse(formatiraniDatum, DATE_TIME_FORMATTER)) && t.getLekar().getKorisnickoIme().compareTo(l.getKorisnickoIme()) == 0 && t.getStatus() != Status.Otkazan) {
									terminCnt ++;
								}
							}
							if (terminCnt == 0) {
				                if (datumTermina.isAfter(LocalDateTime.now())) {
						            Termin t = new Termin(id, l, s, datumTermina);
									DomZdravlja.getTermini().add(t);
									DomZdravlja.sacuvaj();
									JOptionPane.showMessageDialog(PocetnaStranicaLekar.this, "Uspešno ste postavili novi termin!", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
									model1.addRow(new Object[] {id, "/", s.toString(), formatiraniDatum});
									frameZakazi.dispose();
									setVisible(true);
									setEnabled(true);
				                }
				                else {
				                	JOptionPane.showMessageDialog(frameZakazi, "Molim vas izaberite predstojeći datum!", "Upozorenje", JOptionPane.WARNING_MESSAGE);
				                }
							} else {
								JOptionPane.showMessageDialog(frameZakazi, "Odabrani termin već postoji!", "Obaveštenje", JOptionPane.WARNING_MESSAGE);
							}
			            } else {
			            	JOptionPane.showMessageDialog(frameInfoTermin, "Molim vas popunite sva polja!", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
			            }
						
					}});
				
				frameZakazi.add(lblKreirajTermin, "gap 130, wrap, gapy 0 30");
				frameZakazi.add(lblImePacijenta, "gap 170, wrap");
				frameZakazi.add(cmbPacijenti,"gap 185, wrap");
				frameZakazi.add(datumPregleda, "gap 170, wrap, gapy 15");
				frameZakazi.add(datePicker, "gap 130, wrap");
				frameZakazi.add(vremePregleda, "gap 170, wrap, gapy 15");
				frameZakazi.add(cmbVreme,"gap 185, wrap");
				frameZakazi.add(btnKreiraj, "gap 182, gapy 15");
				
			}});
		
		btnDodajTerapiju = new JButton(new ImageIcon("img/dodajTerapiju.png"));
		btnDodajTerapiju.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setEnabled(false);
				
				frameDodajTerapiju = new JFrame();
				frameDodajTerapiju.setSize(500,600);
				frameDodajTerapiju.setLocationRelativeTo(PocetnaStranicaLekar.this);
				frameDodajTerapiju.setVisible(true);
				frameDodajTerapiju.setLayout(new MigLayout());
				frameDodajTerapiju.addWindowListener(new WindowListener() {

					@Override
					public void windowOpened(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void windowClosing(WindowEvent e) {
							setEnabled(true);
							setVisible(true);
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
				
            	JLabel lblTerapijeDodaj = new JLabel("Dodaj terapiju");
            	lblTerapijeDodaj.setFont(new Font("Bold", Font.PLAIN, 30));
            	lblTerapijeDodaj.setHorizontalAlignment(SwingUtilities.CENTER);
            	
            	JLabel idTerapije = new JLabel("ID Terapije: ");
            	idTerapije.setFont(new Font("Bold", Font.PLAIN, 15));
            	JLabel ID = new JLabel(String.valueOf(DomZdravlja.getTerapije().get(DomZdravlja.getTerapije().size() - 1).getId() + 1));
            	
            	JLabel pacijentTerapije = new JLabel("Prepisano pacijentu: ");
            	pacijentTerapije.setFont(new Font("Bold", Font.PLAIN, 15));
            	DefaultComboBoxModel<String> pacijenti = new DefaultComboBoxModel<String>();
				for (Termin t: DomZdravlja.getTermini()) {
					if (t.getLekar().getId() == korisnik.getId()) {
						int i = 0;
						while (i < pacijenti.getSize()) {
							if (t.getPacijent() != null && pacijenti.getElementAt(i).compareTo((t.getPacijent().getKorisnickoIme())) == 0) {
								break;
							}
							i++;
						}
						
						if (i == pacijenti.getSize() && t.getPacijent() != null) {
							pacijenti.addElement(t.getPacijent().getKorisnickoIme());
						}
					}
	 			}
				JComboBox cmbPacijenti = new JComboBox(pacijenti);
				cmbPacijenti.setSelectedItem(DomZdravlja.getTerapije().get(indexTerapijeZaPrikaz).getzKarton().getPacijent().getKorisnickoIme());
				
            	JLabel opisTerapije = new JLabel("Opis Terapije: ");
            	opisTerapije.setFont(new Font("Bold", Font.PLAIN, 15));
            	JTextArea opisEdit = new JTextArea();
            	opisEdit.setPreferredSize(new Dimension(200, 200));
            	
            	JButton potvrdiKreiraj = new JButton("Kreiraj");
            	potvrdiKreiraj.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						int id = DomZdravlja.getTerapije().get(DomZdravlja.getTerapije().size() - 1).getId() + 5;
						String opisTerapije = opisEdit.getText();
						Lekar lekar = (Lekar)korisnik;
						ZdravstveniKarton zKarton = null;
						Pacijent p = null;
						for (Korisnik k: DomZdravlja.getKorisnici()) {
							if (k.getKorisnickoIme().compareTo(cmbPacijenti.getSelectedItem().toString()) == 0) {
								p = (Pacijent)k;
								zKarton = p.getzKarton();
							}
						}
						
						
						
						if (opisTerapije.compareTo("") != 0) {
							Terapija t = new Terapija(id, opisTerapije, lekar, zKarton);
							DomZdravlja.getTerapije().add(t);
							JOptionPane.showMessageDialog(frameDodajTerapiju, "Uspešno ste dodelili terapiju!", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
							model1.addRow(new Object[] {id, lekar.getKorisnickoIme(), zKarton.getPacijent().getKorisnickoIme(), "..."});
							frameDodajTerapiju.dispose();
							DomZdravlja.sacuvaj();
							setEnabled(true);
							setVisible(true);
						} else {
							JOptionPane.showMessageDialog(frameDodajTerapiju, "Greška prilikom kreiranja terapije", "Upozorenje", JOptionPane.WARNING_MESSAGE);
						}
					}
            		
            	});
            	
            	frameDodajTerapiju.add(lblTerapijeDodaj, "gap 140,wrap");
            	frameDodajTerapiju.add(idTerapije, "gap 100");
            	frameDodajTerapiju.add(ID, "wrap");
            	frameDodajTerapiju.add(pacijentTerapije, "gap 100");
            	frameDodajTerapiju.add(cmbPacijenti, "wrap");
            	frameDodajTerapiju.add(opisTerapije, "gap 100,wrap");
            	frameDodajTerapiju.add(opisEdit, "gap 100, wrap");
            	frameDodajTerapiju.add(potvrdiKreiraj, "gap 210");
				
			}
			
		});
		
		tBarLekar = new JToolBar();
		tBarLekar.setLayout(new MigLayout("insets 0"));
		tBarLekar.setDoubleBuffered(true);
		tBarLekar.setFloatable(false);
		tBarLekar.add(btnPocetna);
        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setPreferredSize(new Dimension(1, 50));
        tBarLekar.add(separator, "pushy, gapright 5");
		tBarLekar.add(btnZakazi);
		btnDodajTerapiju.setEnabled(false);
		tBarLekar.add(btnDodajTerapiju);
		
		JMenuBar menuBarLekar = new JMenuBar();
		JMenu menuLekar = new JMenu("Menu");
		menuTerminPrikaz = new JMenuItem("Prikaži sve termine");
		menuTerminPrikaz.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						mainSection.removeAll();
						mainSection.add(tblTermini(), "grow, push");
						btnZakazi.setEnabled(true);
						btnDodajTerapiju.setEnabled(false);
						tBarLekar.remove(filterField);
						tBarLekar.remove(lblPretraga);
						revalidate();
						repaint();
					}});
			}
			
		});
		menuTermini = new JMenu("Termini");
		menuTermini.add(menuTerminPrikaz);
		
		menuTerapijaPrikaz = new JMenuItem("Prikaži sve terapije");
		menuTerapijaPrikaz.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						mainSection.removeAll();
						btnZakazi.setEnabled(false);
						btnDodajTerapiju.setEnabled(true);
						tBarLekar.remove(filterField);
						tBarLekar.remove(lblPretraga);
						mainSection.add(tblTerapije(), "grow, push");
						revalidate();
						repaint();
					}
					
				});
			}});
		
		
		menuTerapije = new JMenu("Terapije");
		menuTerapije.add(menuTerapijaPrikaz);
		
		menuIzvestaj = new JMenu("Izveštaj");
		menuIzvestajPrikaz = new JMenuItem("Generiši izveštaj");
		menuIzvestajPrikaz.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frameIntervalIzvestaj = new JFrame();
			
				setEnabled(false);
				
				frameIntervalIzvestaj = new JFrame();
				frameIntervalIzvestaj.setSize(500,300);
				frameIntervalIzvestaj.setLocationRelativeTo(PocetnaStranicaLekar.this);
				frameIntervalIzvestaj.setVisible(true);
				frameIntervalIzvestaj.setLayout(new MigLayout());
				frameIntervalIzvestaj.setResizable(false);
				frameIntervalIzvestaj.addWindowListener(new WindowListener() {

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
				
				JLabel lblInterval = new JLabel("Odaberite interval");
				lblInterval.setFont(new Font("bold", Font.PLAIN, 25));
				
				JLabel lblOd = new JLabel("Od:");

				Properties p = new Properties();
				p.put("text.today", "Today");
				p.put("text.month", "Month");
				p.put("text.year", "Year");
				UtilDateModel model = new UtilDateModel();
				JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
				JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
				
				JLabel lblDo = new JLabel("Do:");
				
				Properties p1 = new Properties();
				p1.put("text.today", "Today");
				p1.put("text.month", "Month");
				p1.put("text.year", "Year");
				UtilDateModel model1 = new UtilDateModel();
				JDatePanelImpl datePanel1 = new JDatePanelImpl(model1, p1);
				JDatePickerImpl datePicker1 = new JDatePickerImpl(datePanel1, new DateLabelFormatter());
				
				JButton btnPotvrdiInterval = new JButton("Potvrdi");
				btnPotvrdiInterval.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						String formatiraniDatum = null;
						LocalDateTime datumTermina = null;
						Date selectedDate = (Date) datePicker.getModel().getValue();
		                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy.");
		                formatiraniDatum = dateFormat.format(selectedDate)  + " 00:00";
		                datumTermina = LocalDateTime.parse(formatiraniDatum, DATE_TIME_FORMATTER);
		                
						String formatiraniDatum1 = null;
						LocalDateTime datumTermina1 = null;
						Date selectedDate1 = (Date) datePicker1.getModel().getValue();
		                SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd.MM.yyyy.");
		                formatiraniDatum1 = dateFormat1.format(selectedDate1)  + " 23:59";
		                datumTermina1 = LocalDateTime.parse(formatiraniDatum1, DATE_TIME_FORMATTER);
		                
		                JScrollPane izvestaj = tblIzvestaj(datumTermina, datumTermina1);
		                if (izvestaj != null) {
		    				SwingUtilities.invokeLater(new Runnable() {

		    					@Override
		    					public void run() {
		    						mainSection.removeAll();
		    						btnZakazi.setEnabled(false);
		    						btnDodajTerapiju.setEnabled(false);
		    						mainSection.add(izvestaj, "grow, push");
		    						tBarLekar.add(lblPretraga , "gap 130");
		    				        tBarLekar.add(filterField);
		    						validate();
		    						frameIntervalIzvestaj.dispose();
		    						setEnabled(true);
		    						setVisible(true);
		    					}
		    					
		    				});
		                } else {
    						frameIntervalIzvestaj.dispose();
    						setEnabled(true);
    						setVisible(true);
    						JOptionPane.showMessageDialog(PocetnaStranicaLekar.this, "Izveštaj ne može biti generisan na datom intervalu!", "Obaveštenje", JOptionPane.WARNING_MESSAGE);
		                }
		                
					}
					
				});
				
				frameIntervalIzvestaj.add(lblInterval, "gap 135, wrap");
				frameIntervalIzvestaj.add(lblOd, "gap 220, wrap");
				frameIntervalIzvestaj.add(datePicker, "gap 140, wrap");
				frameIntervalIzvestaj.add(lblDo, "gap 220, wrap");
				frameIntervalIzvestaj.add(datePicker1, "gap 140, wrap");
				frameIntervalIzvestaj.add(btnPotvrdiInterval, "gap 200");
				
			}});
		menuIzvestaj.add(menuIzvestajPrikaz);
		
		JMenuItem m0 = new JMenuItem("Sačuvaj");
		m0.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) { 
				JOptionPane.showMessageDialog(PocetnaStranicaLekar.this, "Podaci uspešno sačuvani", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
			}});
		
		JMenuItem m1 = new JMenuItem("Log out");
		m1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
					int a = JOptionPane.showConfirmDialog(mainLekar, "Da li ste sigurni da želite da se izlogujete?", "Potvrda", JOptionPane.YES_NO_OPTION);
					
					if (a == JOptionPane.YES_OPTION) {
						PocetnaStranicaLekar.this.dispose();

						new Prozor();
					}
			}
		});
				
		
		JMenuItem m2 = new JMenuItem("Exit");
		m2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int a = JOptionPane.showConfirmDialog(mainLekar, "Da li ste sigurni da želite da napustite aplikaciju?", "Potvrda", JOptionPane.YES_NO_OPTION);
				
				if (a == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		menuLekar.add(m0);
		menuLekar.addSeparator();
		menuLekar.add(m1);
		menuLekar.add(m2);
		menuBarLekar.add(menuLekar);
		menuBarLekar.add(menuTermini);
		menuBarLekar.add(menuTerapije);
		menuBarLekar.add(menuIzvestaj);
		setJMenuBar(menuBarLekar);
		
		add(tBarLekar, BorderLayout.NORTH);
		add(mainSection, BorderLayout.CENTER);
	}
	
	private JScrollPane tblTermini() {
		
		ArrayList<Integer> redovi = new ArrayList<Integer>();;
		
		String[] zaglavlja = new String[] {"ID", "Pacijent", "Status", "Datum i vreme Termina"};
		
		ArrayList<String> terminiLn = null;
		try {
			terminiLn = DomZdravlja.procitajTermine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Object[][] sadrzaj = new Object[terminiLn.size()][zaglavlja.length];
		
		for (int i = 0; i < sadrzaj.length; i++) {
			String[] info = terminiLn.get(i).split(",");
			if (info[1].compareTo(" ") != 0) {
				if (Integer.parseInt(info[2]) == korisnik.getId()) {
					for (Korisnik k : DomZdravlja.getKorisnici()) {
						if (k.getId() == Integer.parseInt(info[1])) {
							sadrzaj[i][1] = k.getKorisnickoIme();
							break;
						}
					}
					sadrzaj[i][0] = info[0];
					sadrzaj[i][2] = Status.values()[Integer.parseInt(info[3])];
					sadrzaj[i][3] = LocalDateTime.parse(info[4], DATE_TIME_FORMATTER);
				}
			}
			else if (info[1].compareTo(" ") == 0 && Integer.parseInt(info[2]) == korisnik.getId()){
				sadrzaj[i][0] = info[0];
				sadrzaj[i][1] = "/";
				sadrzaj[i][2] = Status.values()[Integer.parseInt(info[3])];
				sadrzaj[i][3] = LocalDateTime.parse(info[4], DATE_TIME_FORMATTER);
			}
		}
	
		model1 = new DefaultTableModel(sadrzaj, zaglavlja);
		tabela = new JTable(model1);
		
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
                    	PocetnaStranicaLekar.this.setEnabled(false);
                    	frameInfoTermin = new JFrame();
                    	frameInfoTermin.setSize(new Dimension(500, 600));
                    	frameInfoTermin.setVisible(true); 
                    	frameInfoTermin.setDefaultCloseOperation(frameInfoTermin.DISPOSE_ON_CLOSE);
                    	frameInfoTermin.setLocationRelativeTo(PocetnaStranicaLekar.this);
                    	frameInfoTermin.setResizable(false);
                    	frameInfoTermin.addWindowListener(new WindowListener() {

							@Override
							public void windowOpened(WindowEvent e) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void windowClosing(WindowEvent e) {
								PocetnaStranicaLekar.this.setEnabled(true);
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
                    		if (t.getId() == Integer.parseInt(model1.getValueAt(izabranRed, 0).toString())) {
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
                    	if (model1.getValueAt(izabranRed, 2).toString().compareTo("Slobodan")== 0) {
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
                    	
                    	JButton otkaziTermin = new JButton("Otkaži Termin");
                    	if (DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getDatumTermina().isAfter(LocalDateTime.now()) && DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getStatus() == Status.Zakazan) {
                    		otkaziTermin.setEnabled(true);
                    	} 
                    	else {
                    		otkaziTermin.setEnabled(false);
                    	}
                    	otkaziTermin.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								if (DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getStatus() == Status.Zakazan && DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getDatumTermina().isAfter(LocalDateTime.now())) {
									int a = JOptionPane.showConfirmDialog(frameInfoTermin, "Da li ste sigurni da želite da otkažete termin?", "Potvrda", JOptionPane.YES_NO_OPTION);
										if (a == JOptionPane.YES_OPTION) {
											DomZdravlja.getTermini().get(indexTerminaZaPrikaz).setStatus(Status.Otkazan);
											model1.setValueAt("Otkazan", izabranRed, 2);
											JOptionPane.showMessageDialog(frameInfoTermin, "Termin je uspešno otkazan!", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
											DomZdravlja.sacuvaj();
											setEnabled(true);
											frameInfoTermin.dispose();
											validate();
										}
								}
							}});
                    	
                    	JButton izmeniTermin = new JButton("Izmeni Termin");
                    	if (DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getDatumTermina().isAfter(LocalDateTime.now()) && DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getStatus() == Status.Zakazan || DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getStatus() == Status.Slobodan) {
                    		izmeniTermin.setEnabled(true);
                    	} 
                    	else {
                    		izmeniTermin.setEnabled(false);
                    	}
                    	izmeniTermin.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								setEnabled(false);
								
								frameEditTermin = new JFrame();
								frameInfoTermin.setEnabled(false);
								frameEditTermin.addWindowListener(new WindowListener() {

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
								frameEditTermin.setResizable(false);
								frameEditTermin.setSize(new Dimension(500, 600));
								frameEditTermin.setVisible(true);
								frameEditTermin.setLocationRelativeTo(PocetnaStranicaLekar.this);
								frameEditTermin.setLayout(new MigLayout());
								
								JLabel lblIzmeniTermin = new JLabel("Izmeni Termin");
								lblIzmeniTermin.setFont(new Font("Bold",Font.PLAIN, 30));
								
								JLabel lblImePacijenta = new JLabel("Izaberi Pacijenta: ");
								lblImePacijenta.setFont(new Font("Bold", Font.PLAIN,15));
								
								DefaultComboBoxModel<String> pacijenti = new DefaultComboBoxModel<String>();
								pacijenti.addElement("-----");
								for (Termin t: DomZdravlja.getTermini()) {
									if (t.getLekar().getId() == korisnik.getId()) {
										int i = 0;
										while (i < pacijenti.getSize()) {
											if (t.getPacijent() != null && pacijenti.getElementAt(i).compareTo((t.getPacijent().getKorisnickoIme())) == 0) {
												break;
											}
											i++;
										}
										
										if (i == pacijenti.getSize() && t.getPacijent() != null) {
											pacijenti.addElement(t.getPacijent().getKorisnickoIme());
										}
									}
								}
								JComboBox cmbPacijenti = new JComboBox(pacijenti);
								if (DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getPacijent() != null) {
									cmbPacijenti.setSelectedItem(DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getPacijent().getKorisnickoIme());
								} else {
									cmbPacijenti.setSelectedIndex(0);
								}
								
								String datum =  DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getDatumTermina().toString();
								String[] datumPars = datum.split("T");
								
								JLabel datumPregleda = new JLabel("Datum Termina: ");
								datumPregleda.setFont(new Font("bold", Font.PLAIN, 15));
								Properties p = new Properties();
								p.put("text.today", "Today");
								p.put("text.month", "Month");
								p.put("text.year", "Year");
								UtilDateModel modelDate = new UtilDateModel();
								JDatePanelImpl datePanel = new JDatePanelImpl(modelDate, p);
								JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
								String[] daniPars = datumPars[0].split("-");
								modelDate.setDate(Integer.valueOf(daniPars[0]), Integer.valueOf(daniPars[1]) - 1, Integer.valueOf(daniPars[2]));
								modelDate.setSelected(true);
								
								JLabel vremePregleda = new JLabel("Vreme pregleda: ");
								vremePregleda.setFont(new Font("bold", Font.PLAIN, 15));
								String[] vreme = {"07:00", "08:00", "10:00", "15:00", "17:00"};
								JComboBox cmbVreme = new JComboBox(vreme);
								cmbVreme.setSelectedItem(datumPars[1]);
								
								JButton btnIzmeni = new JButton("Izmeni");
								btnIzmeni.setFont(new Font("Bold", Font.PLAIN, 17));
								btnIzmeni.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										Pacijent p = null;
										Status s = null;
										if (cmbPacijenti.getSelectedIndex() == 0) {
											s = Status.Slobodan;
										} else {
											s = Status.Zakazan;
										} 						

										for (Korisnik k: DomZdravlja.getKorisnici()) {
											if (k.getKorisnickoIme().compareTo((String) cmbPacijenti.getSelectedItem()) == 0) {
												p = (Pacijent)k;
												System.out.println(p.getKorisnickoIme());
											}
										}
										Lekar l = (Lekar)korisnik;
										
										Date selectedDate = (Date) datePicker.getModel().getValue();
										String formatiraniDatum = null;
										LocalDateTime datumTermina = null;
							            
							            // Check if a date is selected
							            if (selectedDate != null && p != null && s != Status.Slobodan) {
							                // Format the date
							                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy.");
							                formatiraniDatum = dateFormat.format(selectedDate) + " " + cmbVreme.getSelectedItem().toString();
							                datumTermina = LocalDateTime.parse(formatiraniDatum, DATE_TIME_FORMATTER);
							                if (datumTermina.isAfter(LocalDateTime.now())) {
								                DomZdravlja.getTermini().get(indexTerminaZaPrikaz).setDatumTermina(datumTermina);
								                DomZdravlja.getTermini().get(indexTerminaZaPrikaz).setPacijent(p);
								                DomZdravlja.getTermini().get(indexTerminaZaPrikaz).setStatus(s);
								                DomZdravlja.sacuvaj();
												JOptionPane.showMessageDialog(PocetnaStranicaLekar.this, "Uspešno ste ažurirali termin!", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
//												JOptionPane.showMessageDialog(PocetnaStranicaLekar.this, "Uspešno ste zakazali termin!", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
												DomZdravlja.sacuvaj();
								                model1.setValueAt(p.getKorisnickoIme().toString(), izabranRed, 1);
								                model1.setValueAt(s.toString(), izabranRed, 2);
								                model1.setValueAt(datumTermina, izabranRed, 3);
												frameEditTermin.dispose();
												frameInfoTermin.dispose();
												setVisible(true);
												setEnabled(true);		
							                } else {
							                	JOptionPane.showMessageDialog(frameInfoTermin, "Molim vas odaberite predstojeći datum!", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
							                }								
							            } else if (selectedDate != null && p == null && s == Status.Slobodan) {
							                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy.");
							                formatiraniDatum = dateFormat.format(selectedDate) + " " + cmbVreme.getSelectedItem().toString();
							                datumTermina = LocalDateTime.parse(formatiraniDatum, DATE_TIME_FORMATTER);
							                if (datumTermina.isAfter(LocalDateTime.now())) {
								                DomZdravlja.getTermini().get(indexTerminaZaPrikaz).setDatumTermina(datumTermina);
								                DomZdravlja.getTermini().get(indexTerminaZaPrikaz).setPacijent(null);
								                DomZdravlja.getTermini().get(indexTerminaZaPrikaz).setStatus(s);
												DomZdravlja.sacuvaj();
												JOptionPane.showMessageDialog(PocetnaStranicaLekar.this, "Uspešno ste ažurirali termin!", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
								                model1.setValueAt("/", izabranRed, 1);
								                model1.setValueAt(s.toString(), izabranRed, 2);
								                model1.setValueAt(datumTermina, izabranRed, 3);
												frameEditTermin.dispose();
												frameInfoTermin.dispose();
												setVisible(true);
												setEnabled(true);
							                } else {
							                	JOptionPane.showMessageDialog(frameInfoTermin, "Molim vas odaberite predstojeći datum!", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
							                }
										} else {
							            	JOptionPane.showMessageDialog(frameInfoTermin, "Molim vas popunite sva polja!", "Obaveštenje", JOptionPane.WARNING_MESSAGE);
							            }
									
									}});
								
								
								frameEditTermin.add(lblIzmeniTermin, "gap 130, wrap, gapy 0 30");
								frameEditTermin.add(lblImePacijenta, "gap 170, wrap");
								frameEditTermin.add(cmbPacijenti, "gap 185, wrap");
								frameEditTermin.add(datumPregleda, "gap 170, wrap, gapy 15");
								frameEditTermin.add(datePicker, "gap 130, wrap");
								frameEditTermin.add(vremePregleda, "gap 170, wrap, gapy 15");
								frameEditTermin.add(cmbVreme,"gap 185, wrap");
								frameEditTermin.add(btnIzmeni,"gap 182, gapy 15");
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
                    	pnlTerminInfo.add(otkaziTermin, "gap 100");
                    	pnlTerminInfo.add(izmeniTermin);
                    	
                    	
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
		
		JScrollPane scrollPane = new JScrollPane(tabela);
		
		int i = 0;
		int velicina = model1.getRowCount();
		while (i < velicina) {
			if (model1.getValueAt(i, 0) == null) {
				model1.removeRow(i);
				velicina -= 1;
			} else {
				i++;
			}
		}
		
		return scrollPane;
	};

	private JScrollPane tblTerapije() {
		ArrayList<Integer> redovi = new ArrayList<Integer>();;
		
		String[] zaglavlja = new String[] {"ID", "Prepisao Lekar", "Prepisano za Pacijenta", "Opis Terapije"};
		
		ArrayList<String> terapijeLn = null;
		try {
			terapijeLn = DomZdravlja.procitajTerapije();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Object[][] sadrzaj = new Object[terapijeLn.size()][zaglavlja.length];
		
		for (int i = 0; i < sadrzaj.length; i++) {
			String[] info = terapijeLn.get(i).split(",");
			if (Integer.parseInt(info[1]) == korisnik.getId()) {
				sadrzaj[i][0] = info[0];
				sadrzaj[i][1] = null;
				for (Korisnik k: DomZdravlja.getKorisnici()) {
					if(k.getId() == Integer.parseInt(info[1])) {
						sadrzaj[i][1] = k.getKorisnickoIme();
					};
				}
				sadrzaj[i][2] = null;
				for (ZdravstveniKarton zk: DomZdravlja.getZdravstveniKartoni()) {
					if(zk.getId() == Integer.parseInt(info[2])) {
						sadrzaj[i][2] = zk.getPacijent().getKorisnickoIme();
					};
				}
				sadrzaj[i][3] = "...";
			} else {
				redovi.add(i);
			}
		}
	
		model1 = new DefaultTableModel(sadrzaj, zaglavlja);
		tabela = new JTable(model1);
		
		tabela.setRowSelectionAllowed(true);

		tabela.setColumnSelectionAllowed(false);

		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tabela.setDefaultEditor(Object.class, null);
		
		JScrollPane scrollPane = new JScrollPane(tabela);
		
		tabela.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                	//Castujemo u JTable zato sto od nje dolazi event
                	JTable red = (JTable)e.getSource();
                    izabranRed = red.getSelectedRow();
                    if (izabranRed != -1) {
                    	PocetnaStranicaLekar.this.setEnabled(false);
                    	frameInfoTerapija = new JFrame();
                    	frameInfoTerapija.setSize(new Dimension(500, 600));
                    	frameInfoTerapija.setVisible(true); 
                    	frameInfoTerapija.setDefaultCloseOperation(frameInfoTermin.DISPOSE_ON_CLOSE);
                    	frameInfoTerapija.setLocationRelativeTo(PocetnaStranicaLekar.this);
                    	frameInfoTerapija.setResizable(false);
                    	frameInfoTerapija.addWindowListener(new WindowListener() {

							@Override
							public void windowOpened(WindowEvent e) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void windowClosing(WindowEvent e) {
								PocetnaStranicaLekar.this.setEnabled(true);
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
                    	
                    	JLabel lblTerapijeInfo = new JLabel("Informacije o terapiji");
                    	lblTerapijeInfo.setFont(new Font("Bold", Font.PLAIN, 30));
                    	lblTerapijeInfo.setHorizontalAlignment(SwingUtilities.CENTER);
                    	
                    	JPanel pnlTerapijaInfo = new JPanel();
                    	pnlTerapijaInfo.setLayout(new MigLayout());
                    	
                    	indexTerapijeZaPrikaz = 0;
                    	for (Terapija t: DomZdravlja.getTerapije()) {
                    		if (t.getId() == Integer.parseInt(model1.getValueAt(izabranRed, 0).toString())) {
                    			break;
                    		}
                    		indexTerapijeZaPrikaz += 1;
                    	}
                    	
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
                    	JLabel opis = new JLabel(DomZdravlja.getTerapije().get(indexTerapijeZaPrikaz).getOpisTerapije());
                    	
                    	JButton izmeniTerapiju = new JButton("Izmeni Terapiju");
                    	izmeniTerapiju.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								
								frameInfoTerapija.setEnabled(false);
								frameIzmenaTerapija = new JFrame();
								frameIzmenaTerapija.setLayout(new MigLayout());
								frameIzmenaTerapija.setSize(new Dimension(480,580));
								frameIzmenaTerapija.setLocationRelativeTo(frameInfoTerapija);
								frameIzmenaTerapija.setResizable(false);
								frameIzmenaTerapija.addWindowListener(new WindowListener() {

									@Override
									public void windowOpened(WindowEvent e) {
										// TODO Auto-generated method stub
										
									}

									@Override
									public void windowClosing(WindowEvent e) {
										frameInfoTerapija.setEnabled(true);
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
								frameIzmenaTerapija.setVisible(true);
								
								
								JLabel lblTerapijeEdit = new JLabel("Izmeni terapiju");
		                    	lblTerapijeEdit.setFont(new Font("Bold", Font.PLAIN, 30));
		                    	lblTerapijeEdit.setHorizontalAlignment(SwingUtilities.CENTER);
		                    	
		                    	JLabel idTerapije = new JLabel("ID Terapije: ");
		                    	idTerapije.setFont(new Font("Bold", Font.PLAIN, 15));
		                    	JLabel ID = new JLabel(Integer.toString(DomZdravlja.getTerapije().get(indexTerapijeZaPrikaz).getId()));
		                    	
		                    	JLabel pacijentTerapije = new JLabel("Prepisano: ");
		                    	pacijentTerapije.setFont(new Font("Bold", Font.PLAIN, 15));
		                    	DefaultComboBoxModel<String> pacijenti = new DefaultComboBoxModel<String>();
		        				for (Termin t: DomZdravlja.getTermini()) {
		        					if (t.getLekar().getId() == korisnik.getId()) {
		        						int i = 0;
		        						while (i < pacijenti.getSize()) {
		        							if (t.getPacijent() != null && pacijenti.getElementAt(i).compareTo((t.getPacijent().getKorisnickoIme())) == 0) {
		        								break;
		        							}
		        							i++;
		        						}
		        						
		        						if (i == pacijenti.getSize() && t.getPacijent() != null) {
		        							pacijenti.addElement(t.getPacijent().getKorisnickoIme());
		        						}
		        					}
		        				}
		        				JComboBox cmbPacijenti = new JComboBox(pacijenti);
		        				cmbPacijenti.setSelectedItem(DomZdravlja.getTerapije().get(indexTerapijeZaPrikaz).getzKarton().getPacijent().getKorisnickoIme());
		                    	
		                    	JLabel lekarTerapije = new JLabel("Prepisao: ");
		                    	lekarTerapije.setFont(new Font("Bold", Font.PLAIN, 15));
		                    	JLabel lekar = new JLabel(DomZdravlja.getTerapije().get(indexTerapijeZaPrikaz).getLekar().getKorisnickoIme());
		        				
		                    	JLabel opisTerapije = new JLabel("Opis Terapije: ");
		                    	opisTerapije.setFont(new Font("Bold", Font.PLAIN, 15));
		                    	JTextArea opisEdit = new JTextArea();
		                    	opisEdit.setPreferredSize(new Dimension(200, 200));
		                    	opisEdit.setText(DomZdravlja.getTerapije().get(indexTerapijeZaPrikaz).getOpisTerapije());
		                    	
		                    	JButton potvrdiEdit = new JButton("Potvrdi");
		                    	potvrdiEdit.addActionListener(new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										
										for (Terapija t: DomZdravlja.getTerapije()) {
											if (t.getId() == Integer.parseInt(ID.getText())) {
												DomZdravlja.getTerapije().get(indexTerapijeZaPrikaz).getzKarton().removeTerapija(t);
											}
										}
										
										
										for (Korisnik k: DomZdravlja.getKorisnici()) {
											if (k.getKorisnickoIme() == cmbPacijenti.getSelectedItem().toString()) {
												Pacijent p = (Pacijent)k;
												DomZdravlja.getTerapije().get(indexTerapijeZaPrikaz).setzKarton(p.getzKarton());
												for (Terapija t: DomZdravlja.getTerapije()) {
													if (t.getId() == Integer.parseInt(ID.getText())) {
														DomZdravlja.getTerapije().get(indexTerapijeZaPrikaz).getzKarton().addTerapija(t);
													}
												}
												DomZdravlja.getTerapije().get(indexTerapijeZaPrikaz).setOpisTerapije(opisEdit.getText());
												DomZdravlja.sacuvaj();
												JOptionPane.showMessageDialog(frameIzmenaTerapija, "Terapija uspešno izmenjena", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
												model1.setValueAt(p.getKorisnickoIme(),izabranRed, 2);
												frameInfoTerapija.dispose();
												frameIzmenaTerapija.dispose();
												setEnabled(true);
												setVisible(true);
											}
										}
									}
		                    		
		                    	});
		                    
		                    	
		                    	
		                    	frameIzmenaTerapija.add(lblTerapijeEdit, "gap 140, wrap");
		                    	frameIzmenaTerapija.add(idTerapije, "gap 100");
		                    	frameIzmenaTerapija.add(ID, "wrap");
		                    	frameIzmenaTerapija.add(pacijentTerapije, "gap 100");
		                    	frameIzmenaTerapija.add(cmbPacijenti, "wrap");
		                    	frameIzmenaTerapija.add(lekarTerapije, "gap 100");
		                    	frameIzmenaTerapija.add(lekar, "wrap");
		                    	frameIzmenaTerapija.add(opisTerapije, "gap 100,wrap");
		                    	frameIzmenaTerapija.add(opisEdit, "gap 100,wrap");
		                    	frameIzmenaTerapija.add(potvrdiEdit, "gap 210");
							}});
                   
                    	
                    	pnlTerapijaInfo.add(idTerapije, "gap 100");
                    	pnlTerapijaInfo.add(ID, "wrap, gap 100");
                    	pnlTerapijaInfo.add(pacijentTerapije, "gap 100");
                    	pnlTerapijaInfo.add(pacijent, "wrap, gap 100");
                    	pnlTerapijaInfo.add(lekarTerapije, "gap 100");
                    	pnlTerapijaInfo.add(lekar, "wrap, gap 100");
                    	pnlTerapijaInfo.add(opisTerapije, "gap 100");
                    	pnlTerapijaInfo.add(opis, "wrap, gap 100");
                    	pnlTerapijaInfo.add(izmeniTerapiju, "gap 100");
                    	
                    	
                    	frameInfoTerapija.add(pnlTerapijaInfo,BorderLayout.CENTER);
                    	frameInfoTerapija.add(lblTerapijeInfo,BorderLayout.NORTH);
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
		
		int i = 0;
		int velicina = model1.getRowCount();
		while (i < velicina) {
			if (model1.getValueAt(i, 0) == null) {
				model1.removeRow(i);
				velicina -= 1;
			} else {
				i++;
			}
		}
		
		return scrollPane;
	}
	
	private JScrollPane tblIzvestaj(LocalDateTime odInterval, LocalDateTime doInterval) {
			ArrayList<Integer> redovi = new ArrayList<Integer>();;
			
			String[] zaglavlja = new String[] {"ID", "Pacijent", "Status", "Datum i vreme Termina"};
			
			ArrayList<String> terminiLn = null;
			try {
				terminiLn = DomZdravlja.procitajTermine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			Object[][] sadrzaj = new Object[terminiLn.size()][zaglavlja.length];
			
			for (int i = 0; i < sadrzaj.length; i++) {
				String[] info = terminiLn.get(i).split(",");
				if (LocalDateTime.parse(info[4], DATE_TIME_FORMATTER).isAfter(odInterval) && LocalDateTime.parse(info[4], DATE_TIME_FORMATTER).isBefore(doInterval)) {
					if (info[1].compareTo(" ") != 0) {
						if (Integer.parseInt(info[2]) == korisnik.getId()) {
							for (Korisnik k : DomZdravlja.getKorisnici()) {
								if (k.getId() == Integer.parseInt(info[1])) {
									sadrzaj[i][1] = k.getKorisnickoIme();
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
					else if (info[1].compareTo(" ") == 0 && Integer.parseInt(info[2]) == korisnik.getId()){
						sadrzaj[i][0] = info[0];
						sadrzaj[i][1] = "/";
						sadrzaj[i][2] = Status.values()[Integer.parseInt(info[3])];
						sadrzaj[i][3] = LocalDateTime.parse(info[4], DATE_TIME_FORMATTER);
					}
				} else {
					redovi.add(i);
					System.out.println(i);
				}
			}
	
			model2 = new DefaultTableModel(sadrzaj, zaglavlja);
			tabela = new JTable(model2);
			
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
	                    	PocetnaStranicaLekar.this.setEnabled(false);
	                    	frameInfoTermin = new JFrame();
	                    	frameInfoTermin.setSize(new Dimension(500, 600));
	                    	frameInfoTermin.setVisible(true); 
	                    	frameInfoTermin.setDefaultCloseOperation(frameInfoTermin.DISPOSE_ON_CLOSE);
	                    	frameInfoTermin.setLocationRelativeTo(PocetnaStranicaLekar.this);
	                    	frameInfoTermin.setResizable(false);
	                    	frameInfoTermin.addWindowListener(new WindowListener() {
	
								@Override
								public void windowOpened(WindowEvent e) {
									// TODO Auto-generated method stub
									
								}
	
								@Override
								public void windowClosing(WindowEvent e) {
									PocetnaStranicaLekar.this.setEnabled(true);
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
	                    		if (t.getId() == Integer.parseInt(model1.getValueAt(izabranRed, 0).toString())) {
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
	                    	if (model1.getValueAt(izabranRed, 2).toString().compareTo("Slobodan")== 0) {
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
	                    	
	                    	JButton otkaziTermin = new JButton("Otkaži Termin");
	                    	if (DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getDatumTermina().isAfter(LocalDateTime.now()) && DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getStatus() == Status.Zakazan) {
	                    		otkaziTermin.setEnabled(true);
	                    	} 
	                    	else {
	                    		otkaziTermin.setEnabled(false);
	                    	}
	                    	otkaziTermin.addActionListener(new ActionListener() {
	
								@Override
								public void actionPerformed(ActionEvent e) {
									if (DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getStatus() == Status.Zakazan && DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getDatumTermina().isAfter(LocalDateTime.now())) {
										int a = JOptionPane.showConfirmDialog(frameInfoTermin, "Da li ste sigurni da želite da otkažete termin?", "Potvrda", JOptionPane.YES_NO_OPTION);
											if (a == JOptionPane.YES_OPTION) {
												DomZdravlja.getTermini().get(indexTerminaZaPrikaz).setStatus(Status.Otkazan);
												model1.setValueAt("Otkazan", izabranRed, 2);
												JOptionPane.showMessageDialog(frameInfoTermin, "Termin je uspešno otkazan!", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
												DomZdravlja.sacuvaj();
												setEnabled(true);
												frameInfoTermin.dispose();
												validate();
											}
									}
								}});
	                    	
	                    	JButton izmeniTermin = new JButton("Izmeni Termin");
	                    	if (DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getDatumTermina().isAfter(LocalDateTime.now()) && DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getStatus() == Status.Zakazan || DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getStatus() == Status.Slobodan) {
	                    		izmeniTermin.setEnabled(true);
	                    	} 
	                    	else {
	                    		izmeniTermin.setEnabled(false);
	                    	}
	                    	izmeniTermin.addActionListener(new ActionListener() {
	
								@Override
								public void actionPerformed(ActionEvent e) {
									setEnabled(false);
									
									frameEditTermin = new JFrame();
									frameInfoTermin.setEnabled(false);
									frameEditTermin.addWindowListener(new WindowListener() {
	
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
									frameEditTermin.setResizable(false);
									frameEditTermin.setSize(new Dimension(500, 600));
									frameEditTermin.setVisible(true);
									frameEditTermin.setLocationRelativeTo(PocetnaStranicaLekar.this);
									frameEditTermin.setLayout(new MigLayout());
									
									JLabel lblIzmeniTermin = new JLabel("Izmeni Termin");
									lblIzmeniTermin.setFont(new Font("Bold",Font.PLAIN, 30));
									
									JLabel lblImePacijenta = new JLabel("Izaberi Pacijenta: ");
									lblImePacijenta.setFont(new Font("Bold", Font.PLAIN,15));
									
									DefaultComboBoxModel<String> pacijenti = new DefaultComboBoxModel<String>();
									pacijenti.addElement("-----");
									for (Termin t: DomZdravlja.getTermini()) {
										if (t.getLekar().getId() == korisnik.getId()) {
											int i = 0;
											while (i < pacijenti.getSize()) {
												if (t.getPacijent() != null && pacijenti.getElementAt(i).compareTo((t.getPacijent().getKorisnickoIme())) == 0) {
													break;
												}
												i++;
											}
											
											if (i == pacijenti.getSize() && t.getPacijent() != null) {
												pacijenti.addElement(t.getPacijent().getKorisnickoIme());
											}
										}
									}
									JComboBox cmbPacijenti = new JComboBox(pacijenti);
									if (DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getPacijent() != null) {
										cmbPacijenti.setSelectedItem(DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getPacijent().getKorisnickoIme());
									} else {
										cmbPacijenti.setSelectedIndex(0);
									}
									
									String datum =  DomZdravlja.getTermini().get(indexTerminaZaPrikaz).getDatumTermina().toString();
									String[] datumPars = datum.split("T");
									
									JLabel datumPregleda = new JLabel("Datum Termina: ");
									datumPregleda.setFont(new Font("bold", Font.PLAIN, 15));
									Properties p = new Properties();
									p.put("text.today", "Today");
									p.put("text.month", "Month");
									p.put("text.year", "Year");
									UtilDateModel modelDate = new UtilDateModel();
									JDatePanelImpl datePanel = new JDatePanelImpl(modelDate, p);
									JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
									String[] daniPars = datumPars[0].split("-");
									modelDate.setDate(Integer.valueOf(daniPars[0]), Integer.valueOf(daniPars[1]) - 1, Integer.valueOf(daniPars[2]));
									modelDate.setSelected(true);
									
									JLabel vremePregleda = new JLabel("Vreme pregleda: ");
									vremePregleda.setFont(new Font("bold", Font.PLAIN, 15));
									String[] vreme = {"07:00", "08:00", "10:00", "15:00", "17:00"};
									JComboBox cmbVreme = new JComboBox(vreme);
									cmbVreme.setSelectedItem(datumPars[1]);
									
									JButton btnIzmeni = new JButton("Izmeni");
									btnIzmeni.setFont(new Font("Bold", Font.PLAIN, 17));
									btnIzmeni.addActionListener(new ActionListener() {
	
										@Override
										public void actionPerformed(ActionEvent e) {
											Pacijent p = null;
											Status s = null;
											if (cmbPacijenti.getSelectedIndex() == 0) {
												s = Status.Slobodan;
											} else {
												s = Status.Zakazan;
											} 						
	
											for (Korisnik k: DomZdravlja.getKorisnici()) {
												if (k.getKorisnickoIme().compareTo((String) cmbPacijenti.getSelectedItem()) == 0) {
													p = (Pacijent)k;
													System.out.println(p.getKorisnickoIme());
												}
											}
											Lekar l = (Lekar)korisnik;
											
											Date selectedDate = (Date) datePicker.getModel().getValue();
											String formatiraniDatum = null;
											LocalDateTime datumTermina = null;
								            
								            // Check if a date is selected
								            if (selectedDate != null && p != null && s != Status.Slobodan) {
								                // Format the date
								                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy.");
								                formatiraniDatum = dateFormat.format(selectedDate) + " " + cmbVreme.getSelectedItem().toString();
								                datumTermina = LocalDateTime.parse(formatiraniDatum, DATE_TIME_FORMATTER);
								                if (datumTermina.isAfter(LocalDateTime.now())) {
									                DomZdravlja.getTermini().get(indexTerminaZaPrikaz).setDatumTermina(datumTermina);
									                DomZdravlja.getTermini().get(indexTerminaZaPrikaz).setPacijent(p);
									                DomZdravlja.getTermini().get(indexTerminaZaPrikaz).setStatus(s);
									                DomZdravlja.sacuvaj();
													JOptionPane.showMessageDialog(PocetnaStranicaLekar.this, "Uspešno ste ažurirali termin!", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
	//												JOptionPane.showMessageDialog(PocetnaStranicaLekar.this, "Uspešno ste zakazali termin!", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
													DomZdravlja.sacuvaj();
									                model1.setValueAt(p.getKorisnickoIme().toString(), izabranRed, 1);
									                model1.setValueAt(s.toString(), izabranRed, 2);
									                model1.setValueAt(datumTermina, izabranRed, 3);
													frameEditTermin.dispose();
													frameInfoTermin.dispose();
													setVisible(true);
													setEnabled(true);		
								                } else {
								                	JOptionPane.showMessageDialog(frameInfoTermin, "Molim vas odaberite predstojeći datum!", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
								                }								
								            } else if (selectedDate != null && p == null && s == Status.Slobodan) {
								                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy.");
								                formatiraniDatum = dateFormat.format(selectedDate) + " " + cmbVreme.getSelectedItem().toString();
								                datumTermina = LocalDateTime.parse(formatiraniDatum, DATE_TIME_FORMATTER);
								                if (datumTermina.isAfter(LocalDateTime.now())) {
									                DomZdravlja.getTermini().get(indexTerminaZaPrikaz).setDatumTermina(datumTermina);
									                DomZdravlja.getTermini().get(indexTerminaZaPrikaz).setPacijent(null);
									                DomZdravlja.getTermini().get(indexTerminaZaPrikaz).setStatus(s);
													DomZdravlja.sacuvaj();
													JOptionPane.showMessageDialog(PocetnaStranicaLekar.this, "Uspešno ste ažurirali termin!", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
									                model1.setValueAt("/", izabranRed, 1);
									                model1.setValueAt(s.toString(), izabranRed, 2);
									                model1.setValueAt(datumTermina, izabranRed, 3);
													frameEditTermin.dispose();
													frameInfoTermin.dispose();
													setVisible(true);
													setEnabled(true);
								                } else {
								                	JOptionPane.showMessageDialog(frameInfoTermin, "Molim vas odaberite predstojeći datum!", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
								                }
											} else {
								            	JOptionPane.showMessageDialog(frameInfoTermin, "Molim vas popunite sva polja!", "Obaveštenje", JOptionPane.WARNING_MESSAGE);
								            }
										
										}});
									
									
									frameEditTermin.add(lblIzmeniTermin, "gap 130, wrap, gapy 0 30");
									frameEditTermin.add(lblImePacijenta, "gap 170, wrap");
									frameEditTermin.add(cmbPacijenti, "gap 185, wrap");
									frameEditTermin.add(datumPregleda, "gap 170, wrap, gapy 15");
									frameEditTermin.add(datePicker, "gap 130, wrap");
									frameEditTermin.add(vremePregleda, "gap 170, wrap, gapy 15");
									frameEditTermin.add(cmbVreme,"gap 185, wrap");
									frameEditTermin.add(btnIzmeni,"gap 182, gapy 15");
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
	                    	pnlTerminInfo.add(otkaziTermin, "gap 100");
	                    	pnlTerminInfo.add(izmeniTermin);
	                    	
	                    	
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
			
			izvestajSorter = new TableRowSorter<>(model2);
			tabela.setRowSorter(izvestajSorter);
			
			izvestajSorter.setComparator(0, new Comparator<String>() {
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
			int velicina = model2.getRowCount();
			while (i < velicina) {
				if (model2.getValueAt(i, 0) == null) {
					model2.removeRow(i);
					velicina -= 1;
				} else {
					i++;
				}
			}
			
			if (model2.getRowCount() != 0) {
				return scrollPane;
			}
			
			return null;
	}}

class DateLabelFormatter extends AbstractFormatter {

    private String datePattern = "yyyy-MM-dd";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }

        return "";
    }

}
