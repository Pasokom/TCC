package statics;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.util.Calendar;

public class NotifyUser {

	public static SystemTray tray;
	private static TrayIcon trayIcon;
	
	public static void init() {
		
		tray = SystemTray.getSystemTray();
		
		/* Icone */
		Image image = Toolkit.getDefaultToolkit().createImage(NotifyUser.class.getResource("/images/Logo.png"));
		
		/* Menu de opcoes */
		PopupMenu popupMenu = new PopupMenu();
		MenuItem mSair = new MenuItem("Sair");
		mSair.addActionListener(e -> {
			System.exit(0);
		});
		
		popupMenu.add(mSair);
		
		trayIcon = new TrayIcon(image, "logo", popupMenu);
		trayIcon.setImageAutoSize(true);
		
		trayIcon.setToolTip("CyberLife Calendar");
		
		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		verify();
	}
	
	private static void verify() {
		
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				int minute = -1;
				
				while(true) {
					
					if(minute != Calendar.getInstance().get(Calendar.MINUTE)) {
						
						sendNotification("Importante", "passou um minuto", MessageType.ERROR);
						minute = Calendar.getInstance().get(Calendar.MINUTE);
						try {
							Thread.sleep(1000 * (60 - Calendar.getInstance().get(Calendar.SECOND)));
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
		
		thread.start();
	}
	
	public static void sendNotification(String caption, String text, MessageType messageType) {
		
		trayIcon.displayMessage(caption, text, messageType);
	}
}
