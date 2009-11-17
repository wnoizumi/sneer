package sneer.bricks.hardwaresharing.files.tests;

import static sneer.foundation.environments.Environments.my;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.junit.Test;

import sneer.bricks.hardware.io.IO;
import sneer.bricks.hardwaresharing.files.map.FileMap;
import sneer.bricks.pulp.blinkinglights.BlinkingLights;
import sneer.bricks.pulp.blinkinglights.Light;
import sneer.bricks.pulp.crypto.Sneer1024;
import sneer.bricks.pulp.reactive.collections.CollectionChange;
import sneer.bricks.software.code.classutils.ClassUtils;
import sneer.bricks.software.folderconfig.tests.BrickTest;
import sneer.foundation.lang.Consumer;

/** Abstract test class names must not end in "Test" or else Hudson will try to instantiate them and fail. :P */

public abstract class FileCopyTestBase extends BrickTest {

	protected final FileMap _fileMap = my(FileMap.class);
	
	
	@Test (timeout = 3000)
	public void testWithSmallFile() throws IOException {
		testWith(anySmallFile());
	}

	@Test (timeout = 5000)
	public void testWithFolder() throws IOException {
		testWith(folderWithAFewFiles());
	}

	@Test (timeout = 5000)
	public void testWithLargeFile() throws IOException {
		testWith(createLargeFile());
	}

	private File createLargeFile() throws IOException {
		File result = newTempFile();
		my(IO.class).files().writeByteArrayToFile(result, randomBytes(1000000));
		return result;
	}
	
	private byte[] randomBytes(int size) {
		byte[] result = new byte[size];
		new Random().nextBytes(result);
		return result;
	}

	private void testWith(File fileOrFolder) throws IOException {
		Sneer1024 hash = _fileMap.put(fileOrFolder);
		assertNotNull(hash);

		File copy = newTempFile();
		my(BlinkingLights.class).lights().addReceiver(new Consumer<CollectionChange<Light>>(){@Override public void consume(CollectionChange<Light> deltas) {
			if (!deltas.elementsAdded().isEmpty())
				throw new IllegalStateException();
		}});
		if (fileOrFolder.isFile())
			copyFileFromFileMap(hash, copy);
		else
			copyFolderFromFileMap(hash, copy);

		assertSameContents(fileOrFolder, copy);
	}

	abstract protected void copyFileFromFileMap(Sneer1024 hashOfContents, File destination) throws IOException;

	abstract protected void copyFolderFromFileMap(Sneer1024 hashOfContents, File destination) throws IOException;

	protected File anySmallFile() {
		return myClassFile();
	}

	private File myClassFile() {
		return my(ClassUtils.class).classFile(getClass());
	}

	private File folderWithAFewFiles() {
		return new File(myClassFile().getParent(), "fixtures");
	}

	private void assertSameContents(File file1, File file2) throws IOException {
		my(IO.class).files().assertSameContents(file1, file2);
	}

	private File newTempFile() {
		return new File(tmpFolder(), "tmp" + System.nanoTime());
	}

}