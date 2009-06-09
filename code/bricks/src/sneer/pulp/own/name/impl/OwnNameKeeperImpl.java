package sneer.pulp.own.name.impl;

import static sneer.commons.environments.Environments.my;
import sneer.hardware.cpu.lang.Consumer;
import sneer.pulp.contacts.Contact;
import sneer.pulp.own.name.OwnNameKeeper;
import sneer.pulp.reactive.Register;
import sneer.pulp.reactive.Signal;
import sneer.pulp.reactive.Signals;

class OwnNameKeeperImpl implements OwnNameKeeper {

//	private final TupleSpace _space = my(TupleSpace.class); {
//		_space.keep(OwnName.class);
//		_space.addSubscription(OwnName.class, ownNameSubscriber());
//	}
	
	private Register<String> _name = my(Signals.class).newRegister("");
	
	@Override
	public Signal<String> name() {
		return _name.output();
	}

	@Override
	public Consumer<String> nameSetter() {
		return _name.setter();
	}

	@Override
	public Signal<String> nameOf(Contact contact) {
		throw new sneer.commons.lang.exceptions.NotImplementedYet(); // Implement
	}
	
//	private Consumer<? super OwnName> ownNameSubscriber() {
//		return new Consumer<OwnName>() { @Override public void consume(OwnName value) {
//			// Implement
//		}};
//	}

}