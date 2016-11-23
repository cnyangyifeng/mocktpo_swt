package com.mocktpo.orm.mapper;

import com.mocktpo.orm.domain.UserTest;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserTestMapper {

    @Update({
            "CREATE TABLE IF NOT EXISTS MT_USER_TEST (",
            "MT_EMAIL VARCHAR(64),",
            "MT_TID INT,",
            "MT_TITLE VARCHAR(64),",
            "MT_ALIAS VARCHAR(64),",
            "MT_TIMER_HIDDEN BOOLEAN,",
            "MT_READING_TIME INT,",
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
            "MT_EMAIL,",
            "MT_TID,",
            "MT_TITLE,",
            "MT_ALIAS,",
            "MT_TIMER_HIDDEN,",
            "MT_READING_TIME,",
            "MT_LAST_VIEW_ID",
            ") VALUES (",
            "#{email},",
            "#{tid},",
            "#{title},",
            "#{alias},",
            "#{timerHidden},",
            "#{readingTime},",
            "#{lastViewId}",
            ")"
    })
    void insert(UserTest ut);

    @Select({
            "SELECT",
            "MT_EMAIL AS email,",
            "MT_TID AS tid,",
            "MT_TITLE AS title,",
            "MT_ALIAS AS alias,",
            "MT_TIMER_HIDDEN AS timerHidden,",
            "MT_READING_TIME AS readingTime,",
            "MT_LAST_VIEW_ID AS lastViewId",
            "FROM MT_USER_TEST",
            "ORDER BY MT_TID ASC"
    })
    List<UserTest> find();

    @Update({
            "UPDATE MT_USER_TEST",
            "SET",
            "MT_EMAIL = #{email},",
            "MT_TID = #{tid},",
            "MT_TITLE = #{title},",
            "MT_ALIAS = #{alias},",
            "MT_TIMER_HIDDEN = #{timerHidden},",
            "MT_READING_TIME = #{readingTime},",
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
