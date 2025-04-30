package app.service_test;

import bus_app.dto.departments.DepartmentDto;
import bus_app.exceptions.NoDataException;
import bus_app.models.Department;
import bus_app.repositories.DepartmentRepository;
import bus_app.services.DepartmentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService service;

    @Test
    @DisplayName("Departments - Service - Read test")
    public void departmentReadTest() {
        Department testDep1 = getDepForTest("3829292");
        Department testDep2 = getDepForTest("gjiej33");

        Mockito.when(departmentRepository.findAll()).thenReturn(List.of(testDep1, testDep2));

        assertThat(service.readAllDepartments(), equalTo(List.of(testDep1, testDep2)));
    }

    @Test
    @DisplayName("Departments - Service - Create test - Correct")
    public void departmentCreateCorrectTest() {
        DepartmentDto departmentDto = new DepartmentDto("name", "address");

        Department testDep = new Department(null, "name", "address", false);
        Department testDepWithId = new Department(1L, "name", "address", false);

        Mockito.when(departmentRepository.saveAndFlush(testDep)).thenReturn(testDepWithId);

        Department department = service.addDepartment(departmentDto);
        assertThat(department.getName(), equalTo(departmentDto.getName()));
        assertThat(department.getAddress(), equalTo(departmentDto.getAddress()));
    }

    @Test
    @DisplayName("Departments - Service - Update test - Correct")
    public void departmentUpdateCorrectTest() {
        DepartmentDto departmentDto = new DepartmentDto("new_name", null);
        Department oldDep = new Department(1L, "name", "address", false);

        Mockito.when(departmentRepository.findById(1L)).thenReturn(Optional.of(oldDep));

        oldDep.setName("new_name");
        Mockito.when(departmentRepository.saveAndFlush(oldDep)).thenReturn(oldDep);

        Department newDep = service.updateDepartment(1L, departmentDto);

        assertThat(newDep.getName(), equalTo(departmentDto.getName()));
        assertThat(newDep.getAddress(), equalTo(oldDep.getAddress()));
    }

    @Test
    @DisplayName("Departments - Service - Update test - No element")
    public void departmentUpdateNoElementTest() {
        DepartmentDto departmentDto = new DepartmentDto("new_name", null);

        Mockito.when(departmentRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            service.updateDepartment(1L, departmentDto);

            assert false;
        } catch (NoDataException ex) {
            assertThat(ex.getMessage(), equalTo("No department with id: 1!"));
        }
    }

    @Test
    @DisplayName("Departments - Service - Delete test - Correct")
    public void departmentDeleteCorrectTest() {
        Department oldDep = new Department(1L, "name", "address", false);
        Mockito.when(departmentRepository.findById(1L)).thenReturn(Optional.of(oldDep));

        service.deleteDepartment(1L);
    }

    @Test
    @DisplayName("Departments - Service - Delete test - No element")
    public void departmentDeleteNoElementTest() {
        Mockito.when(departmentRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            service.deleteDepartment(1L);

            assert false;
        } catch (NoDataException ex) {
            assertThat(ex.getMessage(), equalTo("No department with id: 1!"));
        }
    }

    private Department getDepForTest(String name) {
        return new Department(1L, name, "dep_address", false);
    }
}