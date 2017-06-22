package com.iveso.dasa.processor;

import com.iveso.dasa.entity.ItemRecibo;
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
	
	public ItemProcessor getItemProcessor(ItemRecibo item) {
		return new ItemProcessor(item);
	}
	
	public ReciboProcessor getReciboProcessor(Recibo recibo) {
		return new ReciboProcessor(recibo);
	}
}
