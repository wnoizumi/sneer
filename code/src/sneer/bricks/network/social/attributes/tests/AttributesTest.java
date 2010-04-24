package sneer.bricks.network.social.attributes.tests;

import static sneer.foundation.environments.Environments.my;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;

import sneer.bricks.expression.tuples.TupleSpace;
import sneer.bricks.hardware.clock.Clock;
import sneer.bricks.identity.seals.OwnSeal;
import sneer.bricks.identity.seals.Seal;
import sneer.bricks.identity.seals.contacts.ContactSeals;
import sneer.bricks.network.social.Contact;
import sneer.bricks.network.social.Contacts;
import sneer.bricks.network.social.attributes.Attribute;
import sneer.bricks.network.social.attributes.Attributes;
import sneer.bricks.pulp.reactive.Register;
import sneer.bricks.pulp.reactive.Signals;
import sneer.bricks.software.folderconfig.tests.BrickTest;
import sneer.foundation.environments.Environment;
import sneer.foundation.environments.Environments;
import sneer.foundation.lang.ClosureX;
import sneer.foundation.lang.exceptions.Refusal;
import dfcsantos.tracks.Track;
import dfcsantos.tracks.Tracks;
import dfcsantos.wusic.notification.playingtrack.PlayingTrack;

public class AttributesTest extends BrickTest {

	private final Class<? extends Attribute<String>> _anyAttribute = new Attribute<String>() {}.getClass();
	private final Register<Track> _attributeValue = my(Signals.class).newRegister(null);

	private Contact _localContact;
	private Attributes _remoteAttributes;

	@Ignore
	@Test
	public void attributeBroadcast() throws Exception {
		my(Attributes.class).myAttributeSetter(_anyAttribute);

		Environment remote = newTestEnvironment(my(TupleSpace.class), my(Clock.class));
		configureStorageFolder(remote, "remote/data");

		final Seal localSeal = my(OwnSeal.class).get().currentValue();
		Environments.runWith(remote, new ClosureX<Refusal>() { @Override public void run() throws Refusal {
			_localContact = my(Contacts.class).produceContact("local");
			my(ContactSeals.class).put("local", localSeal);
			_remoteAttributes = my(Attributes.class);

			testPlayingTrack("track1");
			testPlayingTrack("track2");
			testPlayingTrack("track2");
			testPlayingTrack("track3");
			testPlayingTrack("");
			testPlayingTrack("track4");

			testNullPlayingTrack();
		}});

		crash(remote);
	}

	private void testPlayingTrack(String trackName) {
		setPlayingTrack(trackName.isEmpty() ? "" : trackName + ".mp3");
		my(TupleSpace.class).waitForAllDispatchingToFinish();
		assertEquals(trackName, playingTrackReceivedFromLocal());
	}

	private void testNullPlayingTrack() {
		my(Clock.class).advanceTime(1);
		_attributeValue.setter().consume(null);
		my(TupleSpace.class).waitForAllDispatchingToFinish();
		assertEquals("", playingTrackReceivedFromLocal());
	}

	private String playingTrackReceivedFromLocal() {
		return _remoteAttributes.attributeValueFor(_localContact, PlayingTrack.class, String.class).currentValue();
	}

	private void setPlayingTrack(String trackName) {
		_attributeValue.setter().consume(my(Tracks.class).newTrack(new File(trackName)));
	}

}