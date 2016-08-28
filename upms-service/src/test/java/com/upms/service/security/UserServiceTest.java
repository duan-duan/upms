package com.upms.service.security;

import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.upms.entity.security.User;
import com.upms.service.AbstractServiceTest;
import com.upms.service.api.security.UserService;

public class UserServiceTest extends AbstractServiceTest {

	@Autowired
	private UserService<User, Integer> userService;

	@Ignore
	@Test
	public void getList() {
		User user = new User();
		List<User> users = userService.queryListByEntity(user);
		User u = this.userService.queryById(1);
		// this.userService.deleteById(1000);
		printInfo(u.getName() + "__" + users.size()
				+ ":asdfasdfasdfasd################:" + users);
	}

	@Test
	public void getPageList() {
		User user = new User();
		Map<String, Object> users = userService.pageQueryEntity(10l, 10, user);
		printInfo(users.size() + ":asdfasdfasdfasd################:" + users);
	}

	@Test
	public void createUser() {
		User user = new User();
		user.setAccount("AAAA");
		user.setPassword("aaaa");
		user.setEmail("aaa");
		this.userService.createEntity(user);
	}
}
