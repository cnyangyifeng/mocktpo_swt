package com.mocktpo.orm.mapper;

import com.mocktpo.orm.domain.UserTestAnswer;
import com.mocktpo.orm.domain.UserTestSession;
import org.apache.ibatis.annotations.*;

public interface UserTestAnswerMapper {

    @Update({
            "CREATE TABLE IF NOT EXISTS MT_USER_TEST_ANSWER (",
            "MT_SID BIGINT,",
            "MT_VIEW_ID INT,",
            "MT_SECTION_TYPE INT,",
            "MT_ANSWER VARCHAR",
            ")"
    })
    void schema();

    @Insert({
            "INSERT INTO MT_USER_TEST_ANSWER (",
            "MT_SID,",
            "MT_VIEW_ID,",
            "MT_SECTION_TYPE,",
            "MT_ANSWER",
            ") VALUES (",
            "#{sid},",
            "#{viewId},",
            "#{sectionType},",
            "#{answer}",
            ")"
    })
    void create(UserTestAnswer userTestAnswer);

    @Update({
            "UPDATE MT_USER_TEST_ANSWER",
            "SET",
            "MT_ANSWER = #{answer}",
            "WHERE",
            "MT_SID = #{userTestSession.sid}",
            "AND MT_VIEW_ID = #{userTestSession.lastViewId}"
    })
    void update(@Param("userTestSession") UserTestSession userTestSession, @Param("answer") String answer);

    @Select({
            "SELECT",
            "MT_SID AS sid,",
            "MT_VIEW_ID AS viewId,",
            "MT_SECTION_TYPE AS sectionType,",
            "MT_ANSWER AS answer",
            "FROM MT_USER_TEST_ANSWER",
            "WHERE",
            "MT_SID = #{sid}",
            "AND MT_VIEW_ID = #{lastViewId}"
    })
    UserTestAnswer find(UserTestSession userTestSession);

    @Select(
            "SELECT COUNT(*) FROM MT_USER_TEST_ANSWER"
    )
    long count();

    @Select({
            "SELECT",
            "MT_SID AS sid,",
            "MT_VIEW_ID AS viewId,",
            "MT_SECTION_TYPE AS sectionType,",
            "MT_ANSWER AS answer",
            "FROM MT_USER_TEST_ANSWER",
            "WHERE",
            "MT_SID = #{userTestSession.sid}",
            "AND MT_VIEW_ID = #{viewId}"
    })
    UserTestAnswer findByViewId(@Param("userTestSession") UserTestSession userTestSession, @Param("viewId") int viewId);

    @Delete({
            "DELETE FROM MT_USER_TEST_ANSWER",
            "WHERE",
            "MT_SID = #{userTestSession.sid}"
    })
    void delete(@Param("userTestSession") UserTestSession userTestSession);
}
