package callcenter;

import java.util.ArrayList;
import java.util.List;

import callcenter.call.Call;
import callcenter.employee.Fresher;
import callcenter.employee.ProductManager;
import callcenter.employee.Technicallead;
import callcenter.service.CallCenterService;

public class Application {

  public static void main(String[] args) throws InterruptedException {
    List<Fresher> fresherList = new ArrayList<Fresher>();

    Technicallead technicallead = new Technicallead("TL");
    ProductManager productManager = new ProductManager("PM");

    // 假設有30個Fresher
    for (int i = 1; i <= 5; i++)
      fresherList.add(new Fresher("Fresher" + i));


    CallCenterService callCenter =
        new CallCenterService(fresherList, technicallead, productManager);

    new Thread(callCenter).start();
    
    // 模擬同時發5個Call
    for (int i = 1; i <= 5; i++) {
      new Thread(new Client(callCenter, new Call("msg" + i))).start();
    }



    new Thread().sleep(3 * 1000);
    callCenter.shutdown();
    
    System.exit(1);
  }
}
