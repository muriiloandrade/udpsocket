import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPClient {
  public static void main(String[] args) {
    DatagramSocket socket;
    Scanner input = new Scanner(System.in);
    
    System.out.print("Digite a mensagem: ");
    String msg = input.nextLine();
    if (msg.isEmpty()) msg = "Ping!";
    
    System.out.print("Digite o endereço de destino: ");
    String endDestino = input.nextLine();
    if(endDestino.isEmpty()) endDestino = "localhost";
    
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
      byte[] message = msg.getBytes();
      InetAddress host = InetAddress.getByName(endDestino);
      
      DatagramPacket pctVai = new DatagramPacket(message, message.length, host, portDestino);
      socket.setSoTimeout(30000); //Tempo de timeout caso não obtenha resposta
      socket.send(pctVai);

      byte[] buffer = new byte[1024];
      DatagramPacket pctVeio = new DatagramPacket(buffer, buffer.length);
      socket.receive(pctVeio);
      Thread.sleep(10000); //Delay para que os clientes rodem simultaneamente
      System.out.println("Resposta: " + new String(pctVeio.getData()).trim());
      socket.close();
    } catch (IOException | InterruptedException e) {
      System.err.println("Erro: " + e.getMessage()); 
    } 
  }  
}