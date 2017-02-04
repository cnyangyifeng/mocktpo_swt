package com.mocktpo.orm.mapper;

import com.mocktpo.orm.domain.UserTestSession;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserTestSessionMapper {

    @Update({
            "CREATE TABLE IF NOT EXISTS MT_USER_TEST_SESSION (",
            "MT_SID INT PRIMARY KEY AUTO_INCREMENT,",
            "MT_EMAIL VARCHAR(64),",
            "MT_TID INT,",
            "MT_TITLE VARCHAR(64),",
            "MT_FILE_ALIAS VARCHAR(64),",
            "MT_TIMER_HIDDEN BOOLEAN,",
            "MT_READING_TIME INT,",
            "MT_LISTENING_TIME_1 INT,",
            "MT_LISTENING_TIME_2 INT,",
            "MT_SPEAKING_READING_TIME_1 INT,",
            "MT_SPEAKING_READING_TIME_2 INT,",
            "MT_WRITING_READING_TIME INT,",
            "MT_INTEGRATED_WRITING_TIME INT,",
            "MT_INDEPENDENT_WRITING_TIME INT,",
            "MT_VOLUME DOUBLE,",
            "MT_VOLUME_CONTROL_HIDDEN BOOLEAN,",
            "MT_STARS INT,",
            "MT_LAST_VIEW_ID INT",
            ")"
    })
    void schema();

    @Update({
            "DROP TABLE MT_USER_TEST_SESSION IF EXISTS"
    })
    void drop();

    @Insert({
            "INSERT INTO MT_USER_TEST_SESSION (",
            "MT_EMAIL,",
            "MT_TID,",
            "MT_TITLE,",
            "MT_FILE_ALIAS,",
            "MT_TIMER_HIDDEN,",
            "MT_READING_TIME,",
            "MT_LISTENING_TIME_1,",
            "MT_LISTENING_TIME_2,",
            "MT_SPEAKING_READING_TIME_1,",
            "MT_SPEAKING_READING_TIME_2,",
            "MT_WRITING_READING_TIME,",
            "MT_INTEGRATED_WRITING_TIME,",
            "MT_INDEPENDENT_WRITING_TIME,",
            "MT_VOLUME,",
            "MT_VOLUME_CONTROL_HIDDEN,",
            "MT_STARS,",
            "MT_LAST_VIEW_ID",
            ") VALUES (",
            "#{email},",
            "#{tid},",
            "#{title},",
            "#{fileAlias},",
            "#{timerHidden},",
            "#{readingTime},",
            "#{listeningTime1},",
            "#{listeningTime2},",
            "#{speakingReadingTime1},",
            "#{speakingReadingTime2},",
            "#{writingReadingTime},",
            "#{integratedWritingTime},",
            "#{independentWritingTime},",
            "#{volume},",
            "#{volumeControlHidden},",
            "#{stars},",
            "#{lastViewId}",
            ")"
    })
    void insert(UserTestSession userTestSession);

    @Update({
            "UPDATE MT_USER_TEST_SESSION",
            "SET",
            "MT_EMAIL = #{email},",
            "MT_TID = #{tid},",
            "MT_TITLE = #{title},",
            "MT_FILE_ALIAS = #{fileAlias},",
            "MT_TIMER_HIDDEN = #{timerHidden},",
            "MT_READING_TIME = #{readingTime},",
            "MT_LISTENING_TIME_1 = #{listeningTime1},",
            "MT_LISTENING_TIME_2 = #{listeningTime2},",
            "MT_SPEAKING_READING_TIME_1 = #{speakingReadingTime1},",
            "MT_SPEAKING_READING_TIME_2 = #{speakingReadingTime2},",
            "MT_WRITING_READING_TIME = #{writingReadingTime},",
            "MT_INTEGRATED_WRITING_TIME = #{integratedWritingTime},",
            "MT_INDEPENDENT_WRITING_TIME = #{independentWritingTime},",
            "MT_VOLUME = #{volume},",
            "MT_VOLUME_CONTROL_HIDDEN = #{volumeControlHidden},",
            "MT_STARS = #{stars},",
            "MT_LAST_VIEW_ID = #{lastViewId}",
            "WHERE",
            "MT_SID = #{sid}"
    })
    void update(UserTestSession userTestSession);

    @Select({
            "SELECT",
            "MT_SID AS sid,",
            "MT_EMAIL AS email,",
            "MT_TID AS tid,",
            "MT_TITLE AS title,",
            "MT_FILE_ALIAS AS fileAlias,",
            "MT_TIMER_HIDDEN AS timerHidden,",
            "MT_READING_TIME AS readingTime,",
            "MT_LISTENING_TIME_1 AS listeningTime1,",
            "MT_LISTENING_TIME_2 AS listeningTime2,",
            "MT_SPEAKING_READING_TIME_1 AS speakingReadingTime1,",
            "MT_SPEAKING_READING_TIME_2 AS speakingReadingTime2,",
            "MT_WRITING_READING_TIME AS writingReadingTime,",
            "MT_INTEGRATED_WRITING_TIME AS integratedWritingTime,",
            "MT_INDEPENDENT_WRITING_TIME AS independentWritingTime,",
            "MT_VOLUME AS volume,",
            "MT_VOLUME_CONTROL_HIDDEN AS volumeControlHidden,",
            "MT_STARS AS stars,",
            "MT_LAST_VIEW_ID AS lastViewId",
            "FROM MT_USER_TEST_SESSION",
            "ORDER BY MT_TID ASC"
    })
    List<UserTestSession> find();

    @Select(
            "SELECT COUNT(*) FROM MT_USER_TEST_SESSION"
    )
    long count();

    @Delete({
            "DELETE FROM MT_USER_TEST_SESSION",
            "WHERE",
            "MT_TID = #{tid}"
    })
    void delete(@Param("tid") int tid);
}
