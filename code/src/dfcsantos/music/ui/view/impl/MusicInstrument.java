package dfcsantos.music.ui.view.impl;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import sneer.bricks.skin.main.dashboard.InstrumentPanel;
import sneer.bricks.skin.main.instrumentregistry.Instrument;
import sneer.bricks.skin.menu.MenuGroup;
import dfcsantos.music.ui.view.MusicViewListener;



class MusicInstrument implements Instrument {

	private static final int MAX_VOLUME = 10;

	private final MusicViewListener listener;

	
	MusicInstrument(MusicViewListener listener) {
		this.listener = listener;
	}


	public static void main(String[] args) {
			final JFrame jFrame = new JFrame();
			jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			MusicInstrument instrument = new MusicInstrument(null);
			instrument.init(new InstrumentPanel() {
				@Override public Container contentPane() { return jFrame.getContentPane(); }
				@Override public MenuGroup<JPopupMenu> actions() { return null; }
			});
			jFrame.setBounds(100, 100, 200, instrument.defaultHeight());
			jFrame.pack();
			jFrame.setVisible(true);
	}

	
	@Override
	public void init(InstrumentPanel container) {
		Container pane = container.contentPane();
		pane.setLayout(new GridLayout(4, 1));
		pane.add(folderDropDown());
		pane.add(new JLabel("We Built this City - bla bla... 11:23"));
		pane.add(playerControls());
		pane.add(emotiControls());
		
		initMenu(container.actions());
	}


	private void initMenu(MenuGroup<JPopupMenu> actions) {
		//actions.addAction(30, "Downloads...", action);
		actions.addAction(10, "Choose Tracks Folder...", new Runnable() { @Override public void run() {
			listener.chooseTracksFolder();
		}});
		actions.addActionWithCheckBox(20, "Exchange Tracks", listener.isExchangingTracks(), new Runnable() { @Override public void run() {
			listener.toggleTrackExchange();
		}});
		
//		Set tracks folder. - Abre no início um FileChooser - Opcao de menu
//		Exchange Tracks on/off - Default on - Opcao de menu
//		See downloads in progress - Opcao de menu
	}


	private Component emotiControls() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 0));
		panel.add(new JButton(":D"));
		panel.add(new JButton(":P"));
		panel.add(new JButton(":("));
		return panel;
	}


	private JComboBox folderDropDown() {
		JComboBox result = new JComboBox();
		result.addItem("<7 Downloaded Tracks>");
		result.addItem("rock/nacional/Ira");
		result.addItem("rock/Queen");
		result.addItem("samba/raiz");
		return result;
	}


	private JPanel playerControls() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 0));
		panel.add(new JButton(">"));
		panel.add(new JButton(">>"));
		panel.add(new JButton("stop"));
		panel.add(new JButton("shuf"));
		JSlider vol = new JSlider(SwingConstants.HORIZONTAL, 0, MAX_VOLUME, 0);
		vol.setPreferredSize(new Dimension(60, vol.getPreferredSize().height));
		panel.add(vol);
		return panel;
	}

	@Override
	public int defaultHeight() {
		return 120;
	}

	@Override
	public String title() {
		return "Music";
	}

}