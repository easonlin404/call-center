package callcenter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

import callcenter.call.Call;
import callcenter.employee.Employee;
import callcenter.employee.Fresher;
import callcenter.employee.ProductManager;
import callcenter.employee.Technicallead;

/**
 * CallCenterService
 * 
 * @author EasonLin
 *
 */
public class CallCenterService implements Runnable {

  public enum EmployeeType {
    FRESHER, TL, PM,
  }

  private PriorityBlockingQueue<Call> callQueue = new PriorityBlockingQueue<Call>();


  private List<Employee> allEmployeeList = new ArrayList<Employee>();

  private List<Fresher> fresherList;
  private Technicallead technicallead;
  private ProductManager productManager;



  private boolean shutdown;



  public CallCenterService(List<Fresher> fresherList, Technicallead technicallead,
      ProductManager productManager) {
    this.fresherList = fresherList;
    this.technicallead = technicallead;
    this.productManager = productManager;

    allEmployeeList.addAll(fresherList);
    allEmployeeList.add(technicallead);
    allEmployeeList.add(productManager);

    startWaitingCall(allEmployeeList);

  }

  public void serviceCall(Call call) {
    callQueue.put(call);
  }


  /**
   * 開始等待接電話
   * 
   * @param employees
   */
  private void startWaitingCall(List<? extends Employee> employees) {
    for (Employee employee : employees)
      new Thread(employee).start();
  }


  /**
   * call無法處理,上升給上級處理
   * 
   * @param call
   */
  private void escalate(Call call) {
    switch (call.getType()) {
      case FRESHER:
        call.setType(EmployeeType.TL);
        break;
      case TL:
        call.setType(EmployeeType.PM);
        break;
    }
  }

  @Override
  public void run() {
    while (!shutdown) {
      try {
        Call call = callQueue.take();

        boolean allEmployeeBussy = true;

        for (Employee employee : allEmployeeList) {
          if (employee.getType() == call.getType()) {
            // 如果有空而且能解決問題, 處理電話問題
            if (employee.isFree()) {
              if (employee.canSolveProblem(call.getMsg())) {
                employee.receiveCall(call);
                allEmployeeBussy = false;
              } else {
                // pop
                escalate(call);
                break;
              }
              break;
            }
          }
        }

        // 所有職員都沒空(或是不能處理問題), 丟回queue裡面,並且增加優先級別
        if (allEmployeeBussy) {
          call.addPriority();
          callQueue.put(call);
        }

      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public void shutdown() {
    shutdown = true;
    for (Employee employee : allEmployeeList)
      employee.terminate();
    
  }

}
