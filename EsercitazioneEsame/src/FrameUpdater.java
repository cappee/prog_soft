public interface FrameUpdater {

    void display(String type, String filename, Double dim);
    void stop();
    void error();

}
