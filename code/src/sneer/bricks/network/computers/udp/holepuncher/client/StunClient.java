package sneer.bricks.network.computers.udp.holepuncher.client;

import java.net.DatagramPacket;
import java.nio.ByteBuffer;

import basis.brickness.Brick;
import basis.lang.Consumer;

@Brick
public interface StunClient {

	long REQUEST_PERIOD = 30 * 1000;

	void initSender(Consumer<DatagramPacket> sender);

	void handle(ByteBuffer stunPacket);
	
}
