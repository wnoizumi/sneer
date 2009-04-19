package main;

import sneer.brickness.impl.BrickPlacementException;
import sneer.brickness.impl.Brickness;
import wheel.io.Jars;


public class Sneer {

	public static void main(String[] ignored) throws Exception {
		Brickness container = new Brickness(new SneerStoragePath());
		
		placeBricks(container, businessBricks());
		placeBricks(container, communicationBricks());
	}

	static public void placeBricks(Brickness container, Class<?>... bricks) throws BrickPlacementException {
		for (Class<?> brick : bricks)
			container.placeBrick(Jars.classpathRootFor(brick), brick.getName());
	}

	static public Class<?>[] businessBricks() {
		return new Class<?>[] {
				sneer.pulp.events.EventNotifiers.class,
				sneer.skin.image.ImageFactory.class,
				sneer.pulp.reactive.Signals.class,
				sneer.pulp.reactive.collections.ReactiveCollections.class,
				sneer.pulp.logging.Logger.class,
				sneer.pulp.internetaddresskeeper.InternetAddressKeeper.class,
				sneer.pulp.crypto.Crypto.class,
				sneer.pulp.reactive.listsorter.ListSorter.class,
				sneer.pulp.reactive.gates.logic.LogicGates.class,
				sneer.pulp.retrier.RetrierManager.class,
				sneer.skin.sound.loopback.LoopbackTester.class,
				sneer.pulp.port.PortKeeper.class,
				sneer.pulp.httpclient.HttpClient.class,
				sneer.pulp.network.Network.class,
				sneer.skin.menu.MenuFactory.class,
				sneer.skin.main.menu.MainMenu.class,
				sneer.skin.sound.speaker.Speaker.class,
				sneer.pulp.distribution.filtering.TupleFilterManager.class,
				snapps.whisper.speex.Speex.class,
				sneer.pulp.datastructures.cache.CacheFactory.class,
				sneer.pulp.propertystore.PropertyStore.class,
				sneer.pulp.datastore.DataStore.class,
				sneer.pulp.contacts.ContactManager.class,
				snapps.contacts.actions.ContactActionManager.class,
				sneer.pulp.events.EventNotifiers.class,
				sneer.pulp.keymanager.KeyManager.class,
				sneer.pulp.reactive.signalchooser.SignalChooserManagerFactory.class,
				snapps.watchme.codec.ImageCodec.class,
				sneer.pulp.exceptionhandling.ExceptionHandler.class,
				sneer.pulp.connection.SocketOriginator.class,
				sneer.pulp.serialization.Serializer.class,
				sneer.skin.main.instrumentregistry.InstrumentRegistry.class,
				sneer.skin.rooms.ActiveRoomKeeper.class,
				sneer.skin.widgets.reactive.ReactiveWidgetFactory.class,
				snapps.watchme.gui.windows.RemoteWatchMeWindows.class,
				sneer.hardware.cpu.timebox.Timebox.class,
				sneer.pulp.clock.Clock.class,
				sneer.pulp.blinkinglights.BlinkingLights.class,
				sneer.pulp.memory.MemoryMeter.class,
				sneer.skin.sound.kernel.Audio.class,
				sneer.pulp.bandwidth.BandwidthCounter.class,
				sneer.skin.screenshotter.Screenshotter.class,
				sneer.pulp.own.name.OwnNameKeeper.class,
				sneer.pulp.connection.ConnectionManager.class,
				snapps.contacts.gui.comparator.ContactComparator.class,
				sneer.pulp.threadpool.ThreadPool.class,
				sneer.skin.sound.player.SoundPlayer.class,
				sneer.pulp.clockticker.ClockTicker.class,
				sneer.pulp.connection.SocketAccepter.class,
				sneer.pulp.connection.reachability.ReachabilitySentinel.class,
				sneer.pulp.connection.SocketReceiver.class,
				sneer.pulp.tuples.TupleSpace.class,
				snapps.watchme.WatchMe.class,
				sneer.skin.sound.mic.Mic.class,
				snapps.wind.Wind.class,
				snapps.whisper.speextuples.SpeexTuples.class,
				sneer.pulp.probe.ProbeManager.class,
				sneer.pulp.logging.robust.RobustLogger.class
		};
	}

	private static Class<?>[] communicationBricks() {
		return new Class<?>[] {
				sneer.pulp.dyndns.ownaccount.DynDnsAccountKeeper.class,
				sneer.pulp.dyndns.checkip.CheckIp.class,
				sneer.pulp.dyndns.updater.Updater.class,
				sneer.pulp.dyndns.ownip.OwnIpDiscoverer.class,
				sneer.pulp.dyndns.client.DynDnsClient.class,

				sneer.hardware.gui.guithread.GuiThread.class,
				sneer.hardware.gui.images.Images.class,
				sneer.skin.colors.Colors.class,
				snapps.contacts.gui.delete.DeleteContactWindow.class,
				snapps.contacts.gui.ContactsGui.class,
				snapps.contacts.gui.info.ContactInfoWindow.class,
				snapps.wind.gui.WindGui.class,
//				snapps.watchme.gui.WatchMeGui.class,
				snapps.whisper.gui.WhisperGui.class,
				snapps.meter.memory.gui.MemoryMeterGui.class,
				snapps.meter.bandwidth.gui.BandwidthMeterGui.class,
				snapps.blinkinglights.gui.BlinkingLightsGui.class,

				sneer.skin.windowboundssetter.WindowBoundsSetter.class,
				snapps.welcomewizard.WelcomeWizard.class,
				sneer.hardware.logging.gui.LogConsole.class,
				sneer.hardware.gui.timebox.TimeboxedEventQueue.class,
				sneer.hardware.gui.trayicon.TrayIcons.class,
				sneer.skin.main.dashboard.Dashboard.class,
				
				sneer.skin.main.dashboard.util.GuiUtil.class,
				snapps.contacts.hardcoded.HardcodedContacts.class
		};
	}

}