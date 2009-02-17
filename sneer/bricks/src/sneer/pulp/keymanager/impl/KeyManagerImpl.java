package sneer.pulp.keymanager.impl;

import static wheel.lang.Environments.my;

import java.util.HashMap;
import java.util.Map;

import sneer.pulp.contacts.Contact;
import sneer.pulp.crypto.Crypto;
import sneer.pulp.crypto.Sneer1024;
import sneer.pulp.keymanager.KeyManager;
import sneer.pulp.keymanager.PublicKey;
import wheel.lang.Producer;
import wheel.reactive.EventNotifier;
import wheel.reactive.EventSource;
import wheel.reactive.impl.EventNotifierImpl;

class KeyManagerImpl implements KeyManager {

	private PublicKey _ownKey;
	
	private final Map<Contact, PublicKey> _keyByContact = new HashMap<Contact, PublicKey>();

	private EventNotifier<Contact> _keyChanges = new EventNotifierImpl<Contact>();

	private final Crypto _crypto = my(Crypto.class);


	@Override
	public synchronized PublicKey ownPublicKey() {
		if (_ownKey == null)
			_ownKey = generateMickeyMouseKey();

		return _ownKey;
	}

	private PublicKey generateMickeyMouseKey() {
		String string = "" + System.currentTimeMillis() + System.nanoTime() + hashCode();
	
		return generateMickeyMouseKey(string);
	}

	@Override
	public PublicKey keyGiven(Contact contact) {
		return _keyByContact.get(contact);
	}


	@Override
	public synchronized void addKey(Contact contact, PublicKey publicKey) {
		if(keyGiven(contact) != null) throw new IllegalArgumentException("There already was a public key registered for contact: " + contact.nickname().currentValue());
		_keyByContact.put(contact, publicKey);
		_keyChanges.notifyReceivers(contact);
	}


	@Override
	public synchronized Contact contactGiven(PublicKey peersPublicKey) {
		for (Contact candidate : _keyByContact.keySet())
			if(_keyByContact.get(candidate).equals(peersPublicKey))
				return candidate;
		
		return null;
	}

	@Override
	public PublicKey unmarshall(byte[] bytes) {
		return new PublicKeyImpl(_crypto.unmarshallSneer1024(bytes));
	}

	@SuppressWarnings("deprecation")
	@Override
	public PublicKey generateMickeyMouseKey(String string) {
		Sneer1024 sneer1024 = _crypto.digest(string.getBytes());
		return new PublicKeyImpl(sneer1024);
	}

	@Override
	public EventSource<Contact> keyChanges() {
		return _keyChanges .output();
	}

	@Override
	public Contact contactGiven(PublicKey peersPublicKey, Producer<Contact> producerToUseIfAbsent) {
		Contact result = contactGiven(peersPublicKey);
		if (result != null) return result;
		
		result = producerToUseIfAbsent.produce();
		addKey(result, peersPublicKey);
		return result;	}
}
