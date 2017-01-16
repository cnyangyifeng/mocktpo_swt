package com.mocktpo.orm.mapper;

import com.mocktpo.orm.domain.UserTest;
import com.mocktpo.orm.domain.UserTestAnswer;
import org.apache.ibatis.annotations.*;

public interface UserTestAnswerMapper {

    @Update({
            "CREATE TABLE IF NOT EXISTS MT_USER_TEST_ANSWER (",
            "MT_EMAIL VARCHAR(64),",
            "MT_TID INT,",
            "MT_VIEW_ID INT,",
            "MT_SECTION_TYPE INT,",
            "MT_ANSWER VARCHAR",
            ")"
    })
    void schema();


    @Update({
            "DROP TABLE MT_USER_TEST_ANSWER IF EXISTS"
    })
    void drop();

    @Insert({
            "INSERT INTO MT_USER_TEST_ANSWER (",
            "MT_EMAIL,",
            "MT_TID,",
            "MT_VIEW_ID,",
            "MT_SECTION_TYPE,",
            "MT_ANSWER",
            ") VALUES (",
            "#{email},",
            "#{tid},",
            "#{viewId},",
            "#{sectionType},",
            "#{answer}",
            ")"
    })
    void insert(UserTestAnswer userTestAnswer);

    @Select({
            "SELECT",
            "MT_ANSWER AS answer",
            "FROM MT_USER_TEST_ANSWER",
            "WHERE",
            "MT_EMAIL = #{email}",
            "AND MT_TID = #{tid}",
            "AND MT_VIEW_ID = #{lastViewId}"
    })
    String find(UserTest userTest);

    @Update({
            "UPDATE MT_USER_TEST_ANSWER",
            "SET",
            "MT_ANSWER = #{answer}",
            "WHERE",
            "MT_EMAIL = #{userTest.email}",
            "AND MT_TID = #{userTest.tid}",
            "AND MT_VIEW_ID = #{userTest.lastViewId}"
    })
    void update(@Param("userTest") UserTest userTest, @Param("answer") String answer);

    @Select(
            "SELECT COUNT(*) FROM MT_USER_TEST_ANSWER"
    )
    long count();
}
