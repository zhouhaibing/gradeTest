package com.zhb.mybatis.test;

import java.util.List;

public interface UserMapper {
	
	 List<User> findUsers()throws Exception;
}
