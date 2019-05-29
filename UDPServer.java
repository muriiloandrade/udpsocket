import java.io.IOException;
import java.net.SocketException;
import java.util.InputMismatchException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

public class UDPServer {
  public static void main(String[] args) {
    String porta= "";
    if(args.length>0) porta = args[0];
    DatagramSocket socket;

    try {
      if (!porta.isEmpty()) {
        socket = new DatagramSocket(Integer.parseInt(porta));
      } else {
        socket = new DatagramSocket(6789);
        //Deixando sem parâmetro ele escolhe uma porta aleatória
      }

      System.out.printf("Servidor online e aguardando por cliente na porta %d!\n", socket.getLocalPort());
      byte[] buffer = new byte[1024];
      ArrayList<UDPServerThread> threads = new ArrayList<>();

      new Thread(new Runnable() {
        @Override
        public void run() {
          while (true) {
            int count = 0;
            try {
              for (UDPServerThread thread : threads) {
                if (thread.isAlive()) {
                  count++;
                }
              }
              System.out.println("Requisições sendo atendidas: " + count);
              Thread.sleep(2000);
            } catch (InterruptedException e) {
              System.err.println("Erro: " + e.getMessage());
            }
          }
        }
      }).start();

      while (true) {
        DatagramPacket pctVeio = new DatagramPacket(buffer, buffer.length);
        socket.receive(pctVeio);
        System.out.println("Requisição recebida do cliente: " + pctVeio.getAddress());

        UDPServerThread thread = new UDPServerThread(pctVeio);
        threads.add(thread);
        thread.start();
      }
    } catch (SocketException e) {
      System.err.println("Erro no Socket: " + e.getMessage());
    } catch (IOException e) {
      System.err.println("Erro de IO: " + e.getMessage());
    } catch (InputMismatchException e) {
      System.err.println("Erro ao inserir a porta: " + e.getMessage());
    }
  }
}