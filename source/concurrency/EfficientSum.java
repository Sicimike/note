import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * 一千万个数高效求和
 *
 * @author sicimike
 */
@Slf4j
public class EfficientSum {

    // 求和的个数
    private static final int SUM_COUNT = 10000000;
    // 每个task求和的规模
    private static final int SIZE_PER_TASK = 5000000;
    // ForkJoin线程池
    private static final ForkJoinPool forkJoinPool = new ForkJoinPool();
    // Executor线程池
    private static ThreadPoolExecutor executor = null;

    static {
        // 核心线程数 CPU数量 + 1
        int corePoolSize = Runtime.getRuntime().availableProcessors() + 1;
        executor = new ThreadPoolExecutor(corePoolSize, corePoolSize, 3, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    }

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        int[] arr = new int[SUM_COUNT];
        List<Integer> list = new ArrayList<>(SUM_COUNT);
        int currNum = 0;
        for (int i = 0; i < SUM_COUNT; i++) {
            currNum = random.nextInt(200);
            arr[i] = currNum;
            list.add(currNum);
        }

        // 单线程执行
        singleThreadSum(arr);
        // Executor线程池执行
        concurrencySum(arr);
        // stream执行
        streamSum(list);
        // 并行stream执行
        parallelStreamSum(list);
        // forkjoin线程池执行
        forkJoinSum(arr);

    }

    /**
     * 多线程的方式累加（改进版）
     *
     * @param arr 一千万个随机数
     * @throws InterruptedException
     */
    public static int concurrencySum(int[] arr) throws InterruptedException {
        long start = System.currentTimeMillis();
        int sum = 0;
        // 拆分任务
        List<List<int[]>> taskList = Lists.partition(Arrays.asList(arr), SIZE_PER_TASK);
        // 任务总数
        final int taskSize = taskList.size();
        final CountDownLatch latch = new CountDownLatch(taskSize);
        // 相当于LongAdder中的Cell[]
        int[] result = new int[taskSize];
        for (int i = 0; i < taskSize; i++) {
            int[] task = taskList.get(i).get(0);
            final int index = i;
            executor.submit(() -> {
                try {
                    for (int num : task) {
                        result[index] += num;
                    }
                } finally {
                    latch.countDown();
                }
            });
        }
        // 等待所以子线程执行完成
        latch.await();
        for (int i : result) {
            sum += i;
        }
        long end = System.currentTimeMillis();
        log.info("多线程方式计算结果：{}, 耗时：{} 秒", sum, (end - start) / 1000.0);
        // 关闭线程池
        executor.shutdown();
        return sum;
    }

    /**
     * 单线程的方式累加
     *
     * @param arr 一千万个随机数
     */
    public static int singleThreadSum(int[] arr) {
        long start = System.currentTimeMillis();
        int sum = 0;
        int length = arr.length;
        for (int i = 0; i < length; i++) {
            sum += arr[i];
        }
        long end = System.currentTimeMillis();
        log.info("单线程方式计算结果：{}, 耗时：{} 秒", sum, (end - start) / 1000.0);
        return sum;
    }

    public static int streamSum(List<Integer> list) {
        long start = System.currentTimeMillis();
        int sum = list.stream().mapToInt(num -> num).sum();
        long end = System.currentTimeMillis();
        log.info("stream方式计算结果：{}, 耗时：{} 秒", sum, (end - start) / 1000.0);
        return sum;
    }

    public static int parallelStreamSum(List<Integer> list) {
        long start = System.currentTimeMillis();
        int sum = list.parallelStream().mapToInt(num -> num).sum();
        long end = System.currentTimeMillis();
        log.info("parallel stream方式计算结果：{}, 耗时：{} 秒", sum, (end - start) / 1000.0);
        return sum;
    }

    public static int forkJoinSum(int[] arr) {
        long start = System.currentTimeMillis();
        Integer sum = forkJoinPool.invoke(new SicForkJoinTask(arr, 0, SUM_COUNT));
        long end = System.currentTimeMillis();
        log.info("forkjoin方式计算结果：{}, 耗时：{} 秒", sum, (end - start) / 1000.0);
        return sum;
    }

    /**
     * forkjoin任务
     */
    static class SicForkJoinTask extends RecursiveTask<Integer> {
        // 子任务计算区间开始
        private Integer left;
        // 子任务计算区间结束
        private Integer right;
        private int[] arr;

        @Override
        protected Integer compute() {
            if (right - left < SIZE_PER_TASK) {
                int sum = 0;
                for (int i = left; i < right; i++) {
                    sum += arr[i];
                }
                return sum;
            }
            int middle = left + (right - left) / 2;
            SicForkJoinTask leftTask = new SicForkJoinTask(arr, left, middle);
            SicForkJoinTask rightTask = new SicForkJoinTask(arr, middle, right);
            invokeAll(leftTask, rightTask);
            Integer leftResult = leftTask.join();
            Integer rightResult = rightTask.join();
            return leftResult + rightResult;
        }

        public SicForkJoinTask(int[] arr, Integer left, Integer right) {
            this.arr = arr;
            this.left = left;
            this.right = right;
        }
    }
}
