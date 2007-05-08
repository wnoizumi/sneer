package sneer.kernel.business;

import java.io.Serializable;
import java.util.List;

import wheel.lang.Consumer;
import wheel.lang.Omnivore;
import wheel.reactive.Signal;
import wheel.reactive.list.ListSignal;

public interface Business extends Serializable{

	Signal<String> ownName();
	
	Signal<Integer> sneerPort();

	ListSignal<Contact> contactsSignal();

//----------------------------------
	
	Omnivore<String> ownNameSetter();
	
	Consumer<Integer> sneerPortSetter();
	
	Consumer<ContactInfo> contactAdder();

}