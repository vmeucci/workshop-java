package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentService {

	private DepartmentDao depDao = DaoFactory.createDepartmentDao();

	public List<Department> findAll() {

		return depDao.findAll();

	}

}
