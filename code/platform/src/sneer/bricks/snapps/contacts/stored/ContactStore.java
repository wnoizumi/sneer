package sneer.bricks.snapps.contacts.stored;

import sneer.bricks.pulp.reactive.Signal;
import sneer.bricks.software.bricks.snappstarter.Snapp;
import sneer.foundation.brickness.Brick;

@Brick
@Snapp
public interface ContactStore {

	void save();

	Signal<Boolean> failToRestoreContacts();

}