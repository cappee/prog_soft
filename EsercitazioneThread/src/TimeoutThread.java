public class TimeoutThread implements Runnable {

    private final Thread download;

    public TimeoutThread(Thread thread) {
        this.download = thread;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            return;
        }

        if (download.isAlive()) {
            download.interrupt();
            System.out.println("timeout");
        }
    }
}
