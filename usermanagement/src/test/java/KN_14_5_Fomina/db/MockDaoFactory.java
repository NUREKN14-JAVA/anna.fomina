package KN_14_5_Fomina.db;

import com.mockobjects.dynamic.Mock;

public class MockDaoFactory extends DaoFactory {

	private Mock mockUserDao;

	public MockDaoFactory() {
		mockUserDao = new Mock(UserDao.class);
	}

	@Override
	public UserDao getUserDao() {
		return (UserDao) mockUserDao.proxy();
	}

	public Mock getMockUserDao() {
		return mockUserDao;
	}

}
