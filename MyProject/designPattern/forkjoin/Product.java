package forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Product {
	private String name;
	private int price;
		
	public String getName() {		
		return name;		
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}

class ProductListGenerator {

	public List<Product> generate (int size) {
		final int DefaultPrice = 10; 
		List<Product> ret = new ArrayList<Product>();
	
		for (int i=0; i<size; i++){
			Product product = new Product();
			product.setName("Product" + i);
			product.setPrice(DefaultPrice);
			ret.add(product);		
		}

		return ret;
	}

}
	
class Task extends RecursiveTask<Integer> {
	private static final long serialVersionUID = 1L;
	private List<Product> products;
	private int first;
	private int last;
	private Integer result1;
	private Integer result2;
	
	public Task(List<Product> products, int first, int last) {
		this.products = products;
		this.first = first;
		this.last = last;
	}
	
	@Override
	protected Integer compute() {		
		Integer result = new Integer(0);
		
		if (last - first<10) {
			result =  sumPrices();				
			return result;
		}
		else {//getQueuedTaskCount()返回当前线程分解的还没执行的Task数，且只能在forkjoinTask的compute方法里执行
			int middle=(last+first) / 2;
			System.out.printf("Pending tasks: 	%d; range: %d -> %d\n", getQueuedTaskCount(), first, last);
			Task task1=new Task(products, first, middle);
			Task task2=new Task(products, middle+1, last);
			invokeAll(task1, task2);	//直到其子task执行完成，才继续执行。
			
			try {
				result1 = task1.get(1000, TimeUnit.MILLISECONDS);	//get()超时返回null
				if(result1 == null){
					result1 = new Integer(0);
				}
				result2 = task2.get(1000, TimeUnit.MILLISECONDS);
				if(result2 == null){
					result2 = new Integer(0);
				}
				
				result = groupResult(result1, result2);
			} catch (InterruptedException | ExecutionException | TimeoutException e) {
				e.printStackTrace();
			}
			return result;
		}		
		
	}

	private Integer sumPrices() {
		int tmp = 0;
		for (int i= first; i<=last; i++){
			Product product = products.get(i-1);
			tmp = tmp + product.getPrice();
		}
		return new Integer(tmp);
	}
	
	private Integer groupResult(Integer result1, Integer result2) {		
		return result1 + result2;
	}
	
}
	

	
	
