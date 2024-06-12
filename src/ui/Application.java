package ui;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import model.*;

public class Application {

	public static void main(String[] args) {
			DomZdravlja.ucitaj();
			
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					 for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
					        if ("Nimbus".equals(info.getName())) {
					            try {
									UIManager.setLookAndFeel(info.getClassName());
								} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
										| UnsupportedLookAndFeelException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
					            break;
					        }
					    }
						@SuppressWarnings("unused")
						Prozor p = new Prozor();
					
				}
				
			});
			
			
//			
//			for(Korisnik k: DomZdravlja.getKorisnici()) {
//				if (k instanceof Pacijent) {
//					Pacijent pa = (Pacijent)k;
//					System.out.println(pa.getTerapije());
//				}
//			}
	}
	
}
