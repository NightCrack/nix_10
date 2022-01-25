package ua.com.alevel.facade;

import ua.com.alevel.view.dto.request.EmployeeRequestDto;
import ua.com.alevel.view.dto.response.EmployeeResponseDto;

public interface EmployeeFacade extends DependentFacade<EmployeeRequestDto, EmployeeResponseDto, Long, Long> {
}
