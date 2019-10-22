import java.rmi.RemoteException;


/**
 *
 */
public class Switcher implements SwitcherInterface {

  /**
   * Print a simple message to verify if it works (object is arrive at destination)
   */
  public void check() {
    System.out.println("Check switcher ok");
  }

  /**
   *
   */
   public String hello(String name) {
     return "Hello " + name;
   }
}
