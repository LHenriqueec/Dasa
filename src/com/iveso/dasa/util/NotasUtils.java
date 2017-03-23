package com.iveso.dasa.util;

import java.util.List;
import java.util.stream.Collectors;

import com.iveso.dasa.entity.Nota;

public class NotasUtils {

	private static List<Nota> notas;

	public static void carregarNotas(List<Nota> notas) {
		if (NotasUtils.notas == null || notas.isEmpty()) {
			NotasUtils.notas = notas;
		}
	}

	public static List<Nota> completeNota(String query) {
		return notas.stream().filter(nota -> nota.getNumeroNota().contains(query)).collect(Collectors.toList());
	}

	public static List<Nota> getNotas() {
		return notas;
	}
}
