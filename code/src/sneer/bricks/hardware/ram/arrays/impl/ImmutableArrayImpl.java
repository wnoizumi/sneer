/**
 * 
 */
package sneer.bricks.hardware.ram.arrays.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import sneer.bricks.hardware.ram.arrays.ImmutableArray;

final class ImmutableArrayImpl<T> implements ImmutableArray<T> {
	
	private final T[] _elements;

	ImmutableArrayImpl(Collection<T> elements) {
		_elements = (T[]) elements.toArray(new Object[elements.size()]);
	}

	public ImmutableArrayImpl(T[] elements) {
		_elements = elements.clone();
	}


	@Override
	public Iterator<T> iterator() {
		return Collections.unmodifiableCollection(Arrays.asList(_elements)).iterator();
	}

	
	@Override
	public int length() {
		return _elements.length;
	}

	
	@Override //ImmutableArray
	public T[] toArray() {
		return _elements.clone();
	}

	
	@Override //Collection
	public <E> E[] toArray(E[] a) {
		return Arrays.asList(_elements).toArray(a); //Optimize
	}
	
	
	@Override
	public boolean contains(Object o) {
		for (T element : _elements) {
			if (o == null) {
				if (element == null) return true;
			} else
				if (o.equals(element)) return true;
		}
		
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object object : c)
			if (!contains(object)) return false;
		
		return true;
	}

	@Override
	public boolean isEmpty() {
		return _elements.length == 0;
	}

	@Override
	public int size() {
		return _elements.length;
	}
	
	@Override	public boolean add(T e) { throw new UnsupportedOperationException(); }
	@Override public boolean addAll(Collection<? extends T> c) { throw new UnsupportedOperationException(); }
	@Override 	public void clear() { throw new UnsupportedOperationException(); }
	@Override	public boolean remove(Object o) { throw new UnsupportedOperationException(); }
	@Override	public boolean removeAll(Collection<?> c) { throw new UnsupportedOperationException(); }
	@Override	public boolean retainAll(Collection<?> c) {	throw new UnsupportedOperationException();	}

}