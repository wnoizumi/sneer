package sneer.bricks.skin.menu.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import basis.lang.Consumer;

import sneer.bricks.hardware.gui.actions.Action;
import sneer.bricks.pulp.reactive.Signal;
import sneer.bricks.skin.menu.MenuGroup;

public abstract class AbstractMenuGroup<T extends JComponent> implements MenuGroup<T> {

	private Map<Integer, JMenuItem> _menuItemsByIndex = new TreeMap<Integer, JMenuItem>();

	@Override
	public void addAction(int index, final String caption, final Runnable delegate) {
		addAction(index, new Action(){
			@Override
			public String caption() {
				return caption;
			}

			@Override
			public void run() {
				delegate.run();
			}
		});
	}

	
	@Override
	public void addActionWithCheckBox(int positionInMenu, String caption, Signal<Boolean> isChecked, final Runnable action) {
		//final JCheckBoxMenuItem menuItem = my(SynthMenus.class).createCheckboxMenuItem();
		final JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem();
			
		menuItem.setText(caption);
		menuItem.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent ignored) {
			action.run();
		}});
		isChecked.addReceiver(new Consumer<Boolean>() { @Override public void consume(Boolean bool) {
			menuItem.setSelected(bool);
		}});
		addMenuItem(positionInMenu, menuItem);
	}
	
	
	@Override
	public void addAction(int positionInMenu, final Action action) {
		final JMenuItem menuItem = new JMenuItem();
		menuItem.setText(action.caption());
		menuItem.addPropertyChangeListener(new PropertyChangeListener(){ @Override public void propertyChange(PropertyChangeEvent ignored) {
			menuItem.setText(action.caption());
		}});
		menuItem.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent ignored) {
			action.run();
		}});
		
		addMenuItem(positionInMenu, menuItem);
	}

	@Override
	public void addGroup(int positionInMenu, MenuGroup<JMenu> group) {
		addMenuItem(positionInMenu, group.getWidget());
	}

	synchronized
	private void addMenuItem(Integer positionInMenu, final JMenuItem menuItem) {
		while (_menuItemsByIndex.containsKey(positionInMenu))
			positionInMenu++;
		_menuItemsByIndex.put(positionInMenu, menuItem);
		rebuildMenu();
	}

	
	private void rebuildMenu() {
		removeAllIntems();
		insertItemsInAscendingOrder();
	}

	private void removeAllIntems() {
		getWidget().removeAll();
	}

	private void insertItemsInAscendingOrder() {
		// _menuItemsByIndex keeps the menu items sorted by their given index
		for (JMenuItem menuItem : _menuItemsByIndex.values())
			getWidget().add(menuItem);
	}

}
