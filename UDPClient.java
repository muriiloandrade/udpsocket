import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {
  public static void main(String[] args) {
    DatagramSocket socket;
    String msg = "", endDestino = "", portDestino = "";

    switch (args.length) {
      case 0:
        System.out.println("Caí no case 0");
        msg = "Ping!";
        endDestino = "localhost";
        portDestino = "6789";
        break;
      case 1:
        System.out.println("Caí no case 1");
        msg = args[0];
        endDestino = "localhost";
        portDestino = "6789";
        break;
      case 2:
        System.out.println("Caí no case 2");
        msg = args[0];
        endDestino = args[1];
        portDestino = "6789";
        break;
      case 3:
        System.out.println("Caí no case 3");
        msg = args[0];
        endDestino = args[1];
        portDestino = args[2];
        break;
    }
    
    try {
      socket = new DatagramSocket();
      byte[] message = msg.getBytes();
      InetAddress host = InetAddress.getByName(endDestino);

      DatagramPacket pctVai = new DatagramPacket(message, message.length, host, Integer.parseInt(portDestino));
      socket.setSoTimeout(30000); //Tempo de timeout caso não obtenha resposta
      socket.send(pctVai);
      System.out.println("Requisição enviada ao servidor. Aguardando resposta!");

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