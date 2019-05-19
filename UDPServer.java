import java.io.IOException;
import java.net.SocketException;
import java.util.InputMismatchException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Scanner;

public class UDPServer {
  public static void main(String[] args) {
    int porta;
    DatagramSocket socket;
    Scanner input = new Scanner(System.in);
    
    System.out.print("Informar porta? [S/N]: ");
    String flag = input.next();

    try {
      if(flag.equalsIgnoreCase("s")) {
        System.out.print("Digite um inteiro entre 1025 e 65535 para a porta: ");
        porta = input.nextInt();
        socket = new DatagramSocket(porta);
      }
      else socket = new DatagramSocket();
      
      System.out.printf("Servidor online e aguardando por cliente na porta %d!\n", socket.getLocalPort());
      byte[] buffer = new byte[1024];
      while (true) {
        DatagramPacket pctVeio = new DatagramPacket(buffer, buffer.length);
        socket.receive(pctVeio);
        System.out.println("Requisição recebida do cliente: " + pctVeio.getAddress());
        System.out.println("Threads ativas: " + Thread.activeCount());
        new UDPServerThread(pctVeio).start();
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