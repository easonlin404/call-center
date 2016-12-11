package callcenter.employee;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;

import java.util.concurrent.Callable;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import callcenter.call.Call;

public class EmployeeTest {
  @Rule
  public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

  /**
   * 測試Employee接電話
   * 
   * @throws InterruptedException
   */
  @Test
  public void testReceiveCall() throws InterruptedException {

    Employee e1 = new Employee("employee1");
    new Thread(e1).start();
    Call call = new Call("Message 1");
    e1.receiveCall(call);

    await().until(callHandled());

  }

  /**
   * 測試Employee無法接電話(忙線)
   */
  @Test
  public void testBusy() {
    Employee e1 = new Employee("employee1");
    Call call1 = new Call("Message 1");
    Call call2 = new Call("Message 2");
    e1.receiveCall(call1);
    e1.receiveCall(call2);

    assertEquals(true,
        systemOutRule.getLog().contains("employee1沒有空接電話:Call [priority=0, msg=Message 2]"));

  }


  /**
   * 判斷是否已處理電話, system.out有印出'已處理電話', 表示成功處理
   * 
   * @return
   */
  private Callable<Boolean> callHandled() {
    return new Callable<Boolean>() {
      public Boolean call() throws Exception {
        return systemOutRule.getLog().contains("已處理電話:Call [priority=0, msg=Message 1]");
      }
    };
  }
}
