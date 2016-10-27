package com.mocktpo.orm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.mocktpo.orm.domain.UserTest;

public interface UserTestMapper {

    @Update({
            "CREATE TABLE IF NOT EXISTS MT_USER_TEST (",
            "MT_TID INT,",
            "MT_TITLE VARCHAR(64),",
            "MT_USER_NAME VARCHAR(64),",
            "MT_LAST_VIEW_ID INT",
            ")"
    })
    void schema();


    @Update({
            "DROP TABLE MT_USER_TEST IF EXISTS"
    })
    void drop();

    @Insert({
            "INSERT INTO MT_USER_TEST (",
            "MT_TID,",
            "MT_TITLE,",
            "MT_USER_NAME,",
            "MT_LAST_VIEW_ID",
            ") VALUES (",
            "#{tid},",
            "#{title},",
            "#{userName},",
            "#{lastViewId}",
            ")"
    })
    void insert(UserTest ut);

    @Select({
            "SELECT",
            "MT_TID AS tid,",
            "MT_TITLE AS title,",
            "MT_USER_NAME AS userName,",
            "MT_LAST_VIEW_ID AS lastViewId",
            "FROM MT_USER_TEST",
            "ORDER BY MT_TID ASC"
    })
    List<UserTest> find();

    @Update({
            "UPDATE MT_USER_TEST",
            "SET",
            "MT_TITLE = #{title},",
            "MT_USER_NAME = #{userName},",
            "MT_LAST_VIEW_ID = #{lastViewId}",
            "WHERE",
            "MT_TID = #{tid}"
    })
    int update(UserTest ut);

    @Select(
            "SELECT COUNT(*) FROM MT_USER_TEST"
    )
    long count();
}
