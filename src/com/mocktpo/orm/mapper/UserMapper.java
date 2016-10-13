package com.mocktpo.orm.mapper;

import com.mocktpo.orm.domain.UserModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface UserMapper {

    @Update({
            "CREATE TABLE IF NOT EXISTS MT_USER (",
            "MT_USER_ID BIGINT AUTO_INCREMENT,",
            "MT_USER_NAME VARCHAR,",
            "MT_USER_EMAIL VARCHAR,",
            "MT_USER_LICENSE VARCHAR",
            ")"
    })
    void schema();

    @Update({
            "DROP TABLE MT_USER IF EXISTS"
    })
    void drop();

    @Insert({
            "INSERT INTO MT_USER (",
            "MT_USER_ID, MT_USER_NAME, MT_USER_EMAIL, MT_USER_LICENSE",
            ") VALUES (",
            "#{id}, #{name}, #{email}, #{license}",
            ")"
    })
    @Options(
            useGeneratedKeys = true,
            keyProperty = "id"
    )
    void insert(UserModel user);

    @Select(
            "SELECT COUNT(*) FROM MT_USER"
    )
    long count();
}
