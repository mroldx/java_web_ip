package com.ip.demo;

import org.apache.ibatis.annotations.Insert;

public interface userMapper {

    @Insert("INSERT into o_user (ip,login_time,login_city) values(#{ip},#{login_time},#{login_city})")
    void add(user user);

}
