package callcenter.employee;

import callcenter.service.CallCenterService.EmployeeType;

public class Technicallead extends Employee {

  public Technicallead(String name) {
    super(name);
    setType(EmployeeType.TL);
  }

}
