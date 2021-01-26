package server;

public class Main {
    public static final int PORT_WORK = 9006;
    public static void main(String[] args) {
        MultyThreadedServer server = new MultyThreadedServer(PORT_WORK);
        new Thread(server).start();
    }
}
