package callcenter.employee;

import java.util.Random;

import callcenter.call.Call;
import callcenter.service.CallCenterService.EmployeeType;

/**
 * 
 * @author EasonLin
 *
 */
public class Employee implements Runnable {

  private String name;

  private Call call;

  private boolean isContinue = true;
  
  private EmployeeType type;
  
  public Employee(String name) {
    this.name = name;
  }

  /**
   * 是否空閒
   * 
   * @return
   */
  public boolean isFree() {
    return call == null;
  }

  public Call getCall() {
    return call;
  }

  /**
   * 接電話,處理問題
   * 
   * @param call
   */
  public synchronized void receiveCall(Call call) {
    if (call == null)
      throw new IllegalArgumentException("call is null");

    if (isFree()) {
      System.out.println(getName() + "接電話:" + call);
      this.call = call;
      notify();
    } else {
      System.out.println(getName() + "沒有空接電話:" + call);
    }
  }

  /**
   * 依據電話內容, 判斷有沒有辦法解決問題
   * 
   * @param callMsg
   * @return
   */
  public boolean canSolveProblem(String callMsg) {

    // 用亂數決定
    Random dice = new Random();

    // 取1 ~ 10
    int roll = dice.nextInt(10) + 1;
   // System.out.println(roll);

    // 如果大於5,表示無法解決問題
    return roll > 5 ? false : true;
  }

  /**
   * 一個Employee instance, 同一時間只能處理Call, 沒有Call就wating.
   */
  @Override
  public void run() {
    synchronized (this) {
      while (isContinue) {
        while (isFree()) {
          try {
            System.out.println(getName() + "等待接電話");
            wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        call.reply(this);
        call = null;
      }
    }
  }


  public void terminate() {
    isContinue = false;
    
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  
  public EmployeeType getType() {
    return type;
  }

  public void setType(EmployeeType type) {
    this.type = type;
  }


}
