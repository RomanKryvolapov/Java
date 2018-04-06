package com.company;
// made by https://www.youtube.com/watch?v=Ft8D_Toqa0k&index=91&list=PL786bPIlqEjRDXpAKYbzpdTaOYsWyjtCX
import java.util.concurrent.RecursiveTask;

public class Main {


    static long a = 100_000_000_000L;
    static int processors = Runtime.getRuntime().availableProcessors();
    public static void main(String[] args) {
        timeANDmemory.start();



// в несколько потоков - 26 секунд
//        ForkJoinPool pool = new ForkJoinPool(processors);
//        System.out.println(pool.invoke(new Fork(0,a)));



// в 1 поток - 45 секунд
//        long j = 0L;
//        for (long i = 0; i < a; i++) {
//            j += i;
//        }


        
        timeANDmemory.stop();
    }


}

class Fork extends RecursiveTask <Long>{

    long from, to;

    public Fork(long from, long to) {
        this.from = from;
        this.to = to;
    }

    @Override
    protected Long compute() {

        if (to - from <= Main.a / Main.processors) {

            long j = 0L;
            for (long i = from; i < to; i++) {
                j += i;

            }
            return j;
        }
        else {
            long middle = (to + from) / 2;
            Fork first = new Fork(from, middle);
            first.fork();
            Fork second = new Fork(middle + 1, to);
            long secondValue = second.compute();
            return first.join() + secondValue;
        }


    }
}


