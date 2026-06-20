package Utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
    
    private static final ExecutorService executor;

    static {
        // Lấy số luồng vật lý/logic tối đa mà CPU của máy hiện tại hỗ trợ 
        int availableProcessors = Runtime.getRuntime().availableProcessors();
 
        int threadCount = availableProcessors; 
        
        executor = Executors.newFixedThreadPool(threadCount);
    }

    private ThreadPool() {}

    public static void submit(Runnable task) {
        if (!executor.isShutdown()) {
            executor.submit(task);
        }
    }

    public static void shutdown() {
        if (!executor.isShutdown()) {
            executor.shutdown();
        }
    }
}