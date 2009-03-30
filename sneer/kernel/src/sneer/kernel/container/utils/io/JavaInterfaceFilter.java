package sneer.kernel.container.utils.io;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import wheel.io.codegeneration.JavaFilter;
import wheel.io.codegeneration.MetaClass;


public class JavaInterfaceFilter extends JavaFilter {
	
	public JavaInterfaceFilter(File root) {
		super(root);
	}

	@SuppressWarnings("unused")
	@Override
	protected void handleClass(MetaClass metaClass, int depth, Collection<MetaClass> results) throws IOException {
		if(metaClass.isInterface())
			results.add(metaClass);
	}
}