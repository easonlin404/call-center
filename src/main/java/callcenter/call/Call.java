package callcenter.call;

import callcenter.employee.Employee;
import callcenter.service.CallCenterService.EmployeeType;

/**
 * Call
 * @author EasonLin
 *
 */
public class Call implements Comparable<Call> {

  /**
   * 問題內容
   */
  private String msg;

  /**
   * 問題指派層級
   */
  private EmployeeType type = EmployeeType.FRESHER;

  /**
   * 優先權,數字越大優先處理
   */
  private int priority;

  public Call(String msg) {
    this.msg = msg;
  }

  /**
   * 回覆問題
   */
  public void reply(Employee employee) {
    try {
      // 模擬處理回覆電話所需時間
      new Thread().sleep(1 * 1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println(employee.getName() + "已處理電話:" + this);
  }

  public void addPriority() {
    priority++;
  }

  public int getPriority() {
    return priority;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }


  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }


  public EmployeeType getType() {
    return type;
  }

  public void setType(EmployeeType type) {
    this.type = type;
  }

  @Override
  public int compareTo(Call o) {
    return this.getPriority() - o.getPriority();
  }

  @Override
  public String toString() {
    return "Call [priority=" + priority + ", msg=" + msg + "]";
  }
}
