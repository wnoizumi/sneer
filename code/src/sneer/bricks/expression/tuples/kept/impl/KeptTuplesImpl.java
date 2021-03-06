package sneer.bricks.expression.tuples.kept.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sneer.bricks.expression.tuples.Tuple;
import sneer.bricks.expression.tuples.kept.KeptTuples;

public class KeptTuplesImpl implements KeptTuples {

	private static final Tuple[] TUPLE_ARRAY_TYPE = new Tuple[0];
	private List<Tuple> _tuples = Collections.synchronizedList(new ArrayList<Tuple>()); //When the Prevalent nature implements isolation between queries and transactions, this can be made a non-synchronized collection. Klaus Dec-2010.

	
	@Override
	public void add(Tuple tuple) {
		_tuples.add(tuple);
	}
	
	
	@Override
	public void remove(Tuple tuple) {
		_tuples.remove(tuple); //Optimize
	}


	@Override
	public 	Tuple[] all() {
		return _tuples.toArray(TUPLE_ARRAY_TYPE);
	}


	@Override
	public boolean contains(Tuple tuple) {
		return _tuples.contains(tuple); //Optimize
	}
	
}
