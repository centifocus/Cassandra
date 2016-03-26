package socketNIO;

import java.util.concurrent.atomic.AtomicInteger;

//单例
public class ProcessorGroup {
	private static final ProcessorGroup instance = new ProcessorGroup();
	private static final Processor[] processorsPool = new Processor[2];
	private static final AtomicInteger processorDistributor = new AtomicInteger();//原子整数
	
	static{
		for(int i = 0; i < processorsPool.length; i++){
			String name = "processor_" + i;
			processorsPool[i] = new Processor(name);
			processorsPool[i].start();
		}
	}
	
	private ProcessorGroup(){
		
	}
	
	public static ProcessorGroup getInstance() {
		return instance;
	}
	
	public Processor nextProcessor() {
		int k = processorDistributor.getAndIncrement() % processorsPool.length;//getAndIncrement()原子方式加1
		return processorsPool[k];
	}
	
	
}
