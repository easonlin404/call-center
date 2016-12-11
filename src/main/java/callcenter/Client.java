package callcenter;

import callcenter.call.Call;
import callcenter.service.CallCenterService;

/**
 * Client, 模擬打電話
 * @author EasonLin
 *
 */
public class Client  implements Runnable{
  private CallCenterService callCenter;
  private Call call;
  
  public Client(CallCenterService callCenter, Call call){ 
    this.callCenter = callCenter;
    this.call = call;
  }
  @Override
  public void run() {
    callCenter.serviceCall(call);
  }
}
