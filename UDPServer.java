import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Scanner;

public class UDPServer {
  public static void main(String[] args) {
    DatagramSocket socket = null;
    Scanner input = new Scanner(System.in);
    int porta = 0;
    
    System.out.print("Informar porta? [S/N]: ");
    String flag = input.next();

    if(flag.equalsIgnoreCase("s")) {
      try {
        System.out.print("Digite o número desejado para a porta: ");
        porta = input.nextInt();
      } catch (Exception e) {
        System.err.println("Erro ao inserir a porta: " + e.getMessage());
      }
    }
    else {
      porta = 6789;
    }

    System.out.printf("Servidor online e aguardando por cliente na porta %d!\n", porta);
    
    try {
      socket = new DatagramSocket(porta);
      byte[] buffer = new byte[1024];
      while (true) {
        DatagramPacket pctVeio = new DatagramPacket(buffer, buffer.length);
        socket.receive(pctVeio);
        System.out.println("Requisição recebida do cliente: " + pctVeio.getAddress());
        new UDPServerThread(pctVeio).start();
      }
    } catch (SocketException e) { 
      System.err.println("Erro no Socket: " + e.getMessage()); 
    } catch (IOException e) { 
      System.err.println("Erro de IO: " + e.getMessage()); 
    } 
  }
}