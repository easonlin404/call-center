package callcenter.employee;

import callcenter.service.CallCenterService.EmployeeType;

public class ProductManager extends Employee {

  public ProductManager(String name) {
    super(name);
    setType(EmployeeType.PM);
  }
  
  @Override
  public boolean canSolveProblem(String callMsg) {
    //ProductManager 一定能處理問題
    return true;
  }
}
