package bus_app.services;

import bus_app.dto.departments.DepartmentDto;
import bus_app.exceptions.IncorrectBodyException;
import bus_app.exceptions.NoDataException;
import bus_app.models.Department;
import bus_app.repositories.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Класс, предоставляющий интерфейс для обработки полученных через MVC данных от клиента
 * и получения / модификации записей о департаментах
 */
@Service
@RequiredArgsConstructor
public class DepartmentService {

    /**
     * Экземпляр класса для общения со схемой департаментов в БД
     */
    private final DepartmentRepository departmentRepository;

    /**
     * Метод получения списка всех департаментов
     * @return список всех записей о департаментах
     */
    public List<Department> readAllDepartments() {
        return departmentRepository.findAll();
    }

    /**
     * Метод создания новой записи о департаменте
     * @param department объект, содержащий основную информацию для создания новой записи
     * @return объект с информацией о новом департаменте из БД
     */
    public Department addDepartment(DepartmentDto department) {
        try {
            Department newDepartment = new Department(
                    null,
                    department.getName(),
                    department.getAddress(),
                    false
            );

            return departmentRepository.saveAndFlush(newDepartment);
        } catch (DataAccessException ex) {
            throw new IncorrectBodyException(ex.getMessage());
        }
    }

    /**
     * Метод модификации записи о департаменте в БД
     * @param id номер департамента
     * @param department объект, содержащий основную информацию для модификации записи
     * @return объект с информацией о модифицированном департаменте из БД
     */
    public Department updateDepartment(Long id, DepartmentDto department) {
        try {
            Department updatableDepartment = departmentRepository.findById(id).get();

            if (department.getName() != null) {
                updatableDepartment.setName(department.getName());
            }
            if (department.getAddress() != null) {
                updatableDepartment.setAddress(department.getAddress());
            }

            return departmentRepository.saveAndFlush(updatableDepartment);
        } catch (NoSuchElementException ex) {
            throw new NoDataException("No department with id: " + id + "!");
        } catch (DataAccessException ex) {
            throw new IncorrectBodyException(ex.getMessage());
        }
    }

    /**
     * Метод удаления записи о департаменте по его номеру
     * @param id номер департамента, информацию о котором нужно удалить / скрыть
     */
    public void deleteDepartment(Long id) {
        try {
            Department department = departmentRepository.findById(id).get();

            departmentRepository.delete(department);
        } catch (NoSuchElementException ex) {
            throw new NoDataException("No department with id: " + id + "!");
        } catch (DataAccessException ex) {
            throw new IncorrectBodyException(ex.getMessage());
        }
    }
}