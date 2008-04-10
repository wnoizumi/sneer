package functional.freedom2;

import static org.junit.Assert.assertSame;

import org.junit.Test;

import wheel.reactive.Signal;

import functional.SovereignFunctionalTest;

public abstract class Freedom2Test extends SovereignFunctionalTest {

	
	@Test(timeout = 3000)
	public void testNicknames() {
		
		_a.giveNicknameTo(_b, "Bruno");
		_b.giveNicknameTo(_a, "Aninha");
		_a.giveNicknameTo(_c, "Carla");
		_c.giveNicknameTo(_a, "Miguxa");
		_c.giveNicknameTo(_d, "Dedé");
		
		waitForValue("Bruno Barros", _a.navigateAndGetName("Bruno"));
		waitForValue("Ana Almeida", _b.navigateAndGetName("Carla Costa/Miguxa"));
		waitForValue("Ana Almeida", _a.navigateAndGetName("Bruno/Aninha"));
		waitForValue("Bruno Barros", _a.navigateAndGetName("Bruno/Aninha/Bruno"));
		waitForValue("Denis Dalton", _a.navigateAndGetName("Bruno/Carla Costa/Dedé"));
		
		_a.giveNicknameTo(_b, "Bruninho");
		waitForValue("Bruno Barros", _a.navigateAndGetName("Bruninho"));
	}

	private void waitForValue(Object expectedValue, Signal<? extends Object> signal) {
		while (true) {
			if (expectedValue == null && signal.currentValue() == null) return;
			if (expectedValue != null && expectedValue.equals(signal.currentValue())) return;
			Thread.yield();
		}
	}

}
