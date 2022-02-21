package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class SellerService {

	private SellerDao selDao = DaoFactory.createSellerDao();

	public List<Seller> findAll() {

		return selDao.findAll();

	}

	public void saveOrUpdate(Seller obj) {
		if (obj.getId() == null) {
			selDao.insert(obj);
		}
		else {
			selDao.update(obj);
		}
	}
	
	public void remove(Seller obj) {
		selDao.deleteById(obj.getId());
	}
	
}
