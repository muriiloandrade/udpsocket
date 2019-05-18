import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPClient {
  public static void main(String[] args) {
    DatagramSocket socket = null;
    Scanner input = new Scanner(System.in);
    
    System.out.print("Digite a mensagem: ");
    String msg = input.nextLine();
    if (msg.isBlank() || msg.isEmpty()) msg = "Ping!";
    
    System.out.print("Digite o endereço de destino: ");
    String endDestino = input.nextLine();
    if(endDestino.isBlank() || endDestino.isEmpty()) endDestino = "localhost";
    
    System.out.print("Informar porta? [S/N]: ");
    String flag = input.next();

    int portDestino = 0;
    if (flag.equalsIgnoreCase("s")) {
      System.out.print("Digite o número desejado para a porta: ");
      try {
        portDestino = input.nextInt();
      } catch (Exception e) {
        System.err.println("Erro ao inserir a porta: " + e.getMessage());
      }
    }
    else {
      portDestino = 6789;
    }

    try {
      socket = new DatagramSocket();
      socket.setSoTimeout(3000);
      byte[] message = msg.getBytes();
      InetAddress host = InetAddress.getByName(endDestino);

      DatagramPacket pctVai = new DatagramPacket(message, message.length, host, portDestino);
      socket.send(pctVai);

      byte[] buffer = new byte[1024];
      DatagramPacket pctVeio = new DatagramPacket(buffer, buffer.length);
      socket.receive(pctVeio);
      System.out.println("Resposta: " + new String(pctVeio.getData()).trim());
      Thread.sleep(15000);
      socket.close();
    } catch (Exception e) { 
      System.err.println("Erro: " + e.getMessage()); 
    } 
  }  
}