package sneer.bricks.hardwaresharing.files.tests;

import static sneer.foundation.environments.Environments.my;

import java.io.File;
import java.io.IOException;

import sneer.bricks.hardwaresharing.files.client.FileClient;
import sneer.bricks.hardwaresharing.files.server.FileServer;
import sneer.bricks.pulp.crypto.Sneer1024;
import sneer.bricks.pulp.own.name.OwnNameKeeper;
import sneer.bricks.pulp.tuples.TupleSpace;
import sneer.foundation.environments.Environment;
import sneer.foundation.environments.Environments;
import sneer.foundation.lang.Closure;

public class RemoteCopyTest extends FileCopyTestBase {

	@Override
	protected void copyFromFileMap(final Sneer1024 hashOfContents, final File destination) throws IOException {
		@SuppressWarnings("unused")
		FileServer server = my(FileServer.class);
		
		Environment remote = newTestEnvironment(my(TupleSpace.class), my(OwnNameKeeper.class));
		Environments.runWith(remote, new Closure<IOException>() { @Override public void run() throws IOException {
			my(FileClient.class).fetch(destination, hashOfContents);
		}});
	}
	
/*	public void dontRequestIfHasIt() throws IOException {
		@SuppressWarnings("unused")
		FileServer server = my(FileServer.class);
		final Sneer1024 hash = _fileMap.put(anySmallFile());
		
		Environment remote = newTestEnvironment(my(TupleSpace.class), my(OwnNameKeeper.class));
		Environments.runWith(remote, new Closure<IOException>() { @Override public void run() throws IOException {
			my(FileMap.class).put(anySmallFile());
			my(FileClient.class).fetch(anySmallFile(), hash);
		}});

	}*/

}
