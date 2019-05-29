import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.io.IOException;

class UDPServerThread extends Thread{
  private DatagramPacket pctVeio;
  
  public UDPServerThread(DatagramPacket pctVeio) {
    this.pctVeio = pctVeio;
  }

  @Override
  public void run() {
    //Devolve o pacote que chegou
    DatagramPacket pctVai = new DatagramPacket(
      pctVeio.getData(), 
      pctVeio.getLength(), 
      pctVeio.getAddress(), 
      pctVeio.getPort()
    );
    
    try {
      DatagramSocket destino = new DatagramSocket();
      destino.send(pctVai);
      Thread.sleep(5000); //Delay para que seja possível contar quantas threads ativas tem
      destino.close();
    } catch (IOException | InterruptedException e) {
      System.err.println("Erro: " + e.getMessage()); 
    }
  }
}