package parallel.littlemr;

import junit.framework.TestCase;
import parallel.littlemr.Collector;
import parallel.littlemr.CountingReducer;
import parallel.littlemr.Emitter;
import parallel.littlemr.IdentityMapper;
import parallel.littlemr.IdentityReducer;
import parallel.littlemr.Job;
import parallel.littlemr.Mapper;
import parallel.littlemr.Reducer;
import parallel.littlemr.SimpleCollector;
import parallel.littlemr.SimpleEmitter;
import parallel.littlemr.Swapper;

public class TestFullPass extends TestCase {

	private static final String[] list1 = { "1", "2", "3" };

	public TestFullPass(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testIdentity() {
		Mapper<Object, Object, Object, Object> mapper = new IdentityMapper<Object, Object, Object, Object>();
		Reducer<Object, Object> reducer = new IdentityReducer<Object, Object>();
		Collector collector = new SimpleCollector();
		Swapper<Object, Object> swapper = new Swapper<Object, Object>(reducer);
		Emitter<Object, Object> emitter = new SimpleEmitter<Object, Object>(
				swapper);
		Job<Object, Object, Object, Object> job = new Job<Object, Object, Object, Object>(
				mapper, reducer, emitter, collector, swapper);
		for (String data : list1) {
			job.input("ikey", data);
		}
		job.swap();
		job = new Job<Object, Object, Object, Object>(mapper, reducer, collector);
		for (String data : list1) {
			job.input("ikey", data);
		}
		job.swap();
	}

	public void testCounting() {
		Mapper<Object, Integer, String, Integer> mapper = new IdentityMapper<Object, Integer, String, Integer>();
		Reducer<String, Integer> reducer = new CountingReducer<String>();
		Collector collector = new SimpleCollector();
		Job<Object, Integer, String, Integer> job = new Job<Object, Integer, String, Integer>(mapper, reducer, collector);
		job.input("A", 1);
		job.input("A", 2);
		job.input("B", 3);
		job.swap();
	}

}
