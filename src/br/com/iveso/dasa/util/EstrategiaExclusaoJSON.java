package br.com.iveso.dasa.util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import br.com.iveso.dasa.annotation.ExcluirJSON;

public class EstrategiaExclusaoJSON implements ExclusionStrategy {

	@Override
	public boolean shouldSkipClass(Class<?> type) {
		return false;
	}

	@Override
	public boolean shouldSkipField(FieldAttributes fa) {
		return fa.getAnnotation(ExcluirJSON.class) != null;
	}

}
