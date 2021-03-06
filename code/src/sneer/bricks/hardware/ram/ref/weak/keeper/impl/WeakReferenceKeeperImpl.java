package sneer.bricks.hardware.ram.ref.weak.keeper.impl;

import static basis.environments.Environments.my;

import java.util.Map;
import java.util.WeakHashMap;

import basis.lang.Closure;

import sneer.bricks.hardware.clock.timer.Timer;
import sneer.bricks.hardware.cpu.lang.contracts.WeakContract;
import sneer.bricks.hardware.ram.ref.weak.keeper.WeakReferenceKeeper;

public class WeakReferenceKeeperImpl implements WeakReferenceKeeper { 

	private final Map<Object, Object> _weakMap = new WeakHashMap<Object, Object>();
	@SuppressWarnings("unused")	private final WeakContract _timerContract;

	
	{
		_timerContract = my(Timer.class).wakeUpEvery(5000, new Closure() { @Override public void run() {
			forceWeakMapToCleanStaleReferences();
		}});
	}

	
	@Override
	public <T> T keep(T holder, Object toBeHeld) {
		_weakMap.put(holder, toBeHeld);

		return holder;
	}

	private void forceWeakMapToCleanStaleReferences() {
		_weakMap.size();
	}
}
