package sneer.bricks.pulp.tuples;

import java.util.List;

import sneer.foundation.brickness.Brick;
import sneer.foundation.brickness.Tuple;
import sneer.foundation.lang.Consumer;

@Brick
public interface TupleSpace {

	void publish(Tuple newOrignalTupleByTheKing);
	void acquire(Tuple someTupleThatCameFromAContact);

	<T extends Tuple> void addSubscription(Class<T> tupleType, Consumer<? super T> subscriber);
	<T extends Tuple> void removeSubscriptionAsync(Object subscriber);
	
	int transientCacheSize();

	void keep(Class<? extends Tuple> tupleType);
	List<Tuple> keptTuples();
	
	void waitForAllDispatchingToFinish();
	
}