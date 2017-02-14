package com.iveso.dasa.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UFUtils {

	private static Properties props = new Properties();
	private static Path path = Paths.get("/home/luiz/workspace/Dasa/src/uf.properties");
	private static List<String> ufs = new ArrayList<>();
	private static long lastModified = -1;
	
	private UFUtils(){}
	
	public static List<String> getUfs() {
		update();
		return ufs;
	}
	
	private static void update() {
		if (path.toFile().lastModified() > lastModified) {
			try {
				InputStream is = Files.newInputStream(path, StandardOpenOption.READ);
				props.clear();
				props.load(is);
				
				ufs.clear();
				props.values().forEach(uf -> ufs.add(uf.toString()));
				
				ufs.sort((uf1, uf2) -> uf1.compareTo(uf2));
				
				lastModified = path.toFile().lastModified();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
