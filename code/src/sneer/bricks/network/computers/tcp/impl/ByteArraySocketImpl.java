//Copyright (C) 2004 Klaus Wuestefeld
//This is free software. It is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the license distributed along with this file for more details.
//Contributions: Alexandre Nodari.

package sneer.bricks.network.computers.tcp.impl;

import static basis.environments.Environments.my;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import sneer.bricks.hardware.io.IO;
import sneer.bricks.network.computers.tcp.ByteArraySocket;
import sneer.bricks.pulp.blinkinglights.BlinkingLights;
import sneer.bricks.pulp.blinkinglights.LightType;

class ByteArraySocketImpl implements ByteArraySocket {

	private final Socket _socket;
	private final DataOutputStream _outputStream;
	private final DataInputStream _inputStream;
	
	public ByteArraySocketImpl(String serverIpAddress, int serverPort) throws IOException {
		this(new Socket(serverIpAddress, serverPort));
	}

	public ByteArraySocketImpl(Socket socket) throws IOException {
		_socket = socket;
		_outputStream = new DataOutputStream(_socket.getOutputStream());
		_inputStream = new DataInputStream(_socket.getInputStream());
		
		_socket.setTcpNoDelay(true);
	}

	@Override
	public void write(byte[] array) throws IOException {
		int length = array.length;
		if (length > MAX_ARRAY_SIZE) {
			my(BlinkingLights.class).turnOn(LightType.ERROR, "Packet greater than " + MAX_ARRAY_SIZE + " bytes cannot be sent.", "Size: " + length);
			return;
		}
		_outputStream.writeChar((char)length); //Writes a char as a 2-byte value, high byte first.
		
		_outputStream.write(array);
		_outputStream.flush();
	}

	@Override
	public byte[] read() throws IOException {
		int length = _inputStream.readChar();
		
		byte[] result = new byte[length];
		_inputStream.readFully(result);
		return result;
	}

	@Override
	public void close() {
		my(IO.class).crash(_inputStream);
		my(IO.class).crash(_outputStream);
		try {
			_socket.close();
		} catch (IOException e) {
			// Yes. The correct thing is to do nothing.
		}
	}

	String remoteIP() {
		return _socket.getInetAddress().getHostAddress();
	}
}
