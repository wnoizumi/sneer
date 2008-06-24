package sneer.skin.dashboard.impl;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JWindow;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import net.sourceforge.napkinlaf.NapkinLookAndFeel;

import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.JXTaskPaneContainer;
import org.jdesktop.swingx.action.AbstractActionExt;
import org.jdesktop.swingx.painter.Painter;

import sneer.bricks.threadpool.ThreadPool;
import sneer.lego.Inject;
import sneer.skin.dashboard.Dashboard;
import sneer.skin.image.DefaultIcons;
import sneer.skin.image.ImageFactory;
import sneer.skin.menu.Menu;
import sneer.skin.menu.MenuFactory;
import wheel.io.ui.Action;
import wheel.io.ui.impl.TrayIconImpl;
import wheel.io.ui.impl.TrayIconImpl.SystemTrayNotSupported;

public class DashboardImpl implements Dashboard, Runnable {
	
	private static transient final int _WIDTH = 250;
	private static transient final int _HOFFSET = 30;
	
	@Inject
	static private ThreadPool threadPool;
	
	@Inject
	static private MenuFactory<JComponent> menuFactory;
	
	@Inject
	static private ImageFactory imageFactory;
	
	private Dimension screenSize;
	private Rectangle bounds;
	private boolean isLocked;
	
	private transient Window window;
	private transient JFrame jframe;
	private transient JWindow jwindow;
	private transient Menu<JComponent> menubar;
	private transient Menu<JComponent> sneermenu;
	

	public DashboardImpl() {
		threadPool.registerActor(this);
		isLocked = false;
	}

	private void initialize() {
		try {
			UIManager.setLookAndFeel(new NapkinLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			//ignore: using default L&F
		}
		
		initWindows();	
		resizeWindow();

		TrayIconImpl tray = null;
		try {
			tray = new TrayIconImpl(imageFactory.getImageUrl(DefaultIcons.logo16x16));
		} catch (SystemTrayNotSupported e1) {
			changeWindowCloseEventToMinimizeEvent();
			return;
		}
		
		addOpenWindowAction(tray);
		addLockUnlockAction();
		addExitAction(tray);
	}

	private void initWindows() {
		jframe = new JFrame();
		jwindow = new JWindow();
		
		if(isLocked){
			window = jwindow;
		}else{
			window = jframe;
		}
		
		JPanel pane = (JPanel) jframe.getContentPane();
		pane.setLayout(new BorderLayout());
		
		createSneerMenu(pane);
		createDemoTaskPane(pane);
	}

	@SuppressWarnings("unchecked")
	private void createDemoTaskPane(JPanel pane) {
        JXTaskPaneContainer container = new JXTaskPaneContainer();
        container.setBackgroundPainter(new Painter(){
			@Override
			public void paint(Graphics2D g, Object object, int width, int height) {
				//ignore
			}}
        );
        container.setOpaque(false);
        container.setBorder(null);
        container.setAlpha(0.9f);
        
        JXTaskPane taskPane = new JXTaskPane();
        taskPane.setTitle("TODO Tasks");
        taskPane.add(new TODOAction("Prepare slides for JavaPolis"));
        taskPane.add(new TODOAction("Buy Christmas presents"));
        taskPane.add(new TODOAction("Meet with Brian about SwingLabs"));
        container.add(taskPane);
        
        taskPane = new JXTaskPane();
        taskPane.setTitle("Key Dates");
        taskPane.add(new TODOAction("December 25"));
        taskPane.add(new TODOAction("January 1"));
        taskPane.add(new TODOAction("Febuary 14"));
        taskPane.add(new TODOAction("March 26"));
        container.add(taskPane);
        
        taskPane = new JXTaskPane();
        taskPane.setTitle("Notes");
        JTextArea textArea = new JTextArea(15, 20);
		taskPane.add(new JScrollPane(textArea));
        container.add(taskPane);
        
        pane.add(new JScrollPane(container));
    }
	
    private static final class TODOAction extends AbstractActionExt {
		private static final long serialVersionUID = 1L;
		public TODOAction(String name) {
            setName(name);
        }
        public void actionPerformed(ActionEvent actionEvent) {}
    }
    
	private void createSneerMenu(JPanel pane) {
		menubar = menuFactory.createMenuBar();
		menubar.getWidget().add(new JLabel(imageFactory.getIcon(DefaultIcons.logoTray)));
		
		sneermenu = menuFactory.createMenuGroup("Menu");
		menubar.addGroup(sneermenu);
		
		pane.add(menubar.getWidget(), BorderLayout.NORTH);
	}
	
	private void resizeWindow() {
		Dimension newSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		if(bounds==null || screenSize==null || !screenSize.equals(newSize)){
			screenSize  = newSize;
			bounds = new Rectangle((int) screenSize.getWidth() - _WIDTH, 0, _WIDTH,	
								   (int) screenSize.getHeight() - _HOFFSET);
		}
		window.setBounds(bounds);
	}
	
	private void persistWindowsProperties() {
		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				bounds = window.getBounds();
				isLocked = (window instanceof JWindow);
			}
		});
	}

	private void changeWindowCloseEventToMinimizeEvent() {
		jframe.setDefaultCloseOperation ( WindowConstants.DO_NOTHING_ON_CLOSE );
		jframe.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				bounds = window.getBounds();
				jframe.setState(Frame.ICONIFIED);
			}
		});
	}
	
	private void changeJFrameToJWindow() {
		bounds = jframe.getBounds();
		jframe.setVisible(false);
		Container tmp = jframe.getContentPane();
		tmp.getParent().remove(tmp);
		jframe.dispose();
		jframe = new JFrame();
		jwindow.setContentPane(tmp);
		jwindow.setBounds(bounds);
		jwindow.setVisible(true);
		window = jwindow;
		isLocked = true;
	}
	
	private void changeJWindowToJFrame() {
		bounds = jwindow.getBounds();
		jwindow.setVisible(false);
		Container tmp = jwindow.getContentPane();
		tmp.getParent().remove(tmp);
		jwindow.dispose();
		jwindow = new JWindow();
		jframe.setContentPane(tmp);
		jframe.setBounds(bounds);
		jframe.setVisible(true);
		window = jframe;
		isLocked = false;
	}
	
	private void addLockUnlockAction() {
		Action cmd = new Action(){
			@Override
			public String caption() {
				return (window==jframe)?"Lock!":"Unlock!";
			}
			@Override
			public void run() {
				if(window==jframe){
					changeJFrameToJWindow();
				}else{
					changeJWindowToJFrame();
				}
			}
		};
		sneermenu.addAction(cmd);
	}
	
	private void addOpenWindowAction(TrayIconImpl tray) {
		Action cmd = new Action(){
			@Override
			public String caption() {
				return "Open!";
			}
			@Override
			public void run() {
				window.setVisible(true);
				if(window==jframe){
					jframe.setState(Frame.NORMAL);
				}
				window.requestFocus();
			}
		};
		tray.setDefaultAction(cmd);
		tray.addAction(cmd);
	}

	private void addExitAction(TrayIconImpl tray) {
		Action cmd = new Action(){
			@Override
			public String caption() {
				return "Exit...";
			}
			@Override
			public void run() {
				persistWindowsProperties();
				System.exit(0);
			}
		};
		tray.addAction(cmd);
		sneermenu.addSeparator();
		sneermenu.addAction(cmd);
	}

	@Override
	public void run() {
		initialize();
	}
}