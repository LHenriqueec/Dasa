package com.iveso.dasa.processor;

import com.iveso.dasa.entity.Recibo;

public class ProcessorFactory{
	
	private static ProcessorFactory instance;
	
	private ProcessorFactory() {}
	
	public static ProcessorFactory getInstance() {
		if(instance == null) {
			instance = new ProcessorFactory();
		}
		
		return instance;
	}
	
	public ItemProcessor getItemProcessor() {
		return new ItemProcessor();
	}
	
	public ReciboProcessor getReciboProcessor(Recibo recibo) {
		return new ReciboProcessor(recibo);
	}
	
	public NotaProcessor getNotaProcessor() {
		return new NotaProcessor();
	}
}
