package sneer.bricks.compiler;

import java.io.File;
import java.util.List;

import sneer.bricks.classpath.Classpath;

public interface Compiler {
	
	Result compile(List<File> sourceFiles, File destination) throws CompilerException;
	
	Result compile(File sourceRoot, File destination) throws CompilerException;

	Result compile(File sourceRoot, File destination, Classpath classpath) throws CompilerException;

}
