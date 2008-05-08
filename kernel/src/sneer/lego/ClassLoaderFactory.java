package sneer.lego;

import java.io.File;

public interface ClassLoaderFactory {

	ClassLoader sneerApi();

	ClassLoader brickClassLoader(Class<?> brickClass, File brickDirectory);
}
