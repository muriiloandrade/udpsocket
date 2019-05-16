import java.net.DatagramPacket;
import java.net.DatagramSocket;

class UDPServerThread extends Thread{
  private DatagramPacket pctVeio;
  
  public UDPServerThread(DatagramPacket pctVeio) {
    this.pctVeio = pctVeio;
  }

  @Override
  public void run() {
    DatagramPacket pctVai = new DatagramPacket(
      pctVeio.getData(), 
      pctVeio.getLength(), 
      pctVeio.getAddress(), 
      pctVeio.getPort()
    );
      
    try {
      DatagramSocket destino = new DatagramSocket();
      destino.send(pctVai);
      destino.close();
    } catch (Exception e) {
      System.err.println("Erro: " + e.getMessage()); 
    }
  }
}