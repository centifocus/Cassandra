package forkjoin;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

/*
 * 将100个产品的价格累加，基task的规模为小于10个产品。
 */
public class MyExp {

	public static void main(String[] args) {
		ProductListGenerator generator = new ProductListGenerator();
		List<Product> products=generator.generate(100);
		Task task = new Task(products, 1, products.size());
		ForkJoinPool pool = new ForkJoinPool();
				
		pool.execute(task);
		do {
			System.out.printf("Main: "
					+ "PoolSize: %d; Thread Count: %d; Thread Steal: %d; Parallelism: %d\n",
					pool.getPoolSize(), pool.getActiveThreadCount(), pool.getStealCount(), pool.getParallelism());			
			try {
				TimeUnit.MILLISECONDS.sleep(1);
			} catch (InterruptedException e) {
			
			}
		} while (!task.isDone());		//perhaps with exception
		
		if (task.isCompletedNormally()){		//without exception
			try {
				System.out.printf("Main: result  %d.\n", task.get());
			} catch (InterruptedException | ExecutionException e) {				
				e.printStackTrace();
			}
		}
		
		if(pool.isTerminated()){
			pool.shutdown();
		}		
		
	}
	
}
