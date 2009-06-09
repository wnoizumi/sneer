package sneer.bricks.pulp.distribution.filtering.impl;

import java.util.HashSet;
import java.util.Set;

import sneer.bricks.pulp.distribution.filtering.TupleFilterManager;
import sneer.foundation.brickness.Tuple;

class TupleFilterManagerImpl implements TupleFilterManager {

	private final Set<Class<? extends Tuple>> _blockedTypes = new HashSet<Class<? extends Tuple>>();

	@Override
	public void block(Class<? extends Tuple> tupleType) {
		_blockedTypes.add(tupleType);
	}

	@Override
	public boolean isBlocked(Tuple tuple) {
		Class<? extends Tuple> clazz = tuple.getClass();
		if (_blockedTypes.contains(clazz)) return true;
		
		for (Class<?> blocked : _blockedTypes) {
			if (!blocked.isAssignableFrom(clazz)) continue;
			
			_blockedTypes.add(clazz);
			return true;
		}
		
		return false;  //Optimize the "not blocked" case with a _nonBlockedTypes set for example.
	}

}