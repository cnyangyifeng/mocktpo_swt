package com.mocktpo.orm.mapper;

import com.mocktpo.orm.domain.UserTestSession;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserTestSessionMapper {

    @Update({
            "CREATE TABLE IF NOT EXISTS MT_USER_TEST_SESSION (",
            "MT_SID INT PRIMARY KEY AUTO_INCREMENT,",
            "MT_TID INT,",
            "MT_TITLE VARCHAR(64),",
            "MT_FILE_ALIAS VARCHAR(64),",
            "MT_STARS INT,",
            "MT_START_TIME BIGINT,",
            "MT_LAST_VISIT_TIME BIGINT,",
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
            "MT_READING_SECTION_ENABLED BOOLEAN,",
            "MT_LISTENING_SECTION_ENABLED BOOLEAN,",
            "MT_SPEAKING_SECTION_ENABLED BOOLEAN,",
            "MT_WRITING_SECTION_ENABLED BOOLEAN,",
            "MT_LAST_VIEW_ID INT,",
            "MT_MAX_VIEW_ID INT",
            ")"
    })
    void schema();

    @Update({
            "DROP TABLE MT_USER_TEST_SESSION IF EXISTS"
    })
    void drop();

    @Insert({
            "INSERT INTO MT_USER_TEST_SESSION (",
            "MT_TID,",
            "MT_TITLE,",
            "MT_FILE_ALIAS,",
            "MT_STARS,",
            "MT_START_TIME,",
            "MT_LAST_VISIT_TIME,",
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
            "MT_READING_SECTION_ENABLED,",
            "MT_LISTENING_SECTION_ENABLED,",
            "MT_SPEAKING_SECTION_ENABLED,",
            "MT_WRITING_SECTION_ENABLED,",
            "MT_LAST_VIEW_ID,",
            "MT_MAX_VIEW_ID",
            ") VALUES (",
            "#{tid},",
            "#{title},",
            "#{fileAlias},",
            "#{stars},",
            "#{startTime},",
            "#{lastVisitTime},",
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
            "#{readingSectionEnabled},",
            "#{listeningSectionEnabled},",
            "#{speakingSectionEnabled},",
            "#{writingSectionEnabled},",
            "#{lastViewId},",
            "#{maxViewId}",
            ")"
    })
    @Options(useGeneratedKeys = true, keyProperty = "sid")
    void insert(UserTestSession userTestSession);

    @Update({
            "UPDATE MT_USER_TEST_SESSION",
            "SET",
            "MT_TID = #{tid},",
            "MT_TITLE = #{title},",
            "MT_FILE_ALIAS = #{fileAlias},",
            "MT_STARS = #{stars},",
            "MT_START_TIME = #{startTime},",
            "MT_LAST_VISIT_TIME = #{lastVisitTime},",
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
            "MT_READING_SECTION_ENABLED = #{readingSectionEnabled},",
            "MT_LISTENING_SECTION_ENABLED = #{listeningSectionEnabled},",
            "MT_SPEAKING_SECTION_ENABLED = #{speakingSectionEnabled},",
            "MT_WRITING_SECTION_ENABLED = #{writingSectionEnabled},",
            "MT_LAST_VIEW_ID = #{lastViewId},",
            "MT_MAX_VIEW_ID = #{maxViewId}",
            "WHERE",
            "MT_SID = #{sid}"
    })
    void update(UserTestSession userTestSession);

    @Select({
            "SELECT",
            "MT_SID AS sid,",
            "MT_TID AS tid,",
            "MT_TITLE AS title,",
            "MT_FILE_ALIAS AS fileAlias,",
            "MT_STARS AS stars,",
            "MT_START_TIME AS startTime,",
            "MT_LAST_VISIT_TIME AS lastVisitTime,",
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
            "MT_READING_SECTION_ENABLED AS readingSectionEnabled,",
            "MT_LISTENING_SECTION_ENABLED AS listeningSectionEnabled,",
            "MT_SPEAKING_SECTION_ENABLED AS speakingSectionEnabled,",
            "MT_WRITING_SECTION_ENABLED AS writingSectionEnabled,",
            "MT_LAST_VIEW_ID AS lastViewId,",
            "MT_MAX_VIEW_ID AS maxViewId",
            "FROM MT_USER_TEST_SESSION",
            "ORDER BY MT_LAST_VISIT_TIME DESC"
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
    void deleteByTid(@Param("tid") int tid);

    @Delete({
            "DELETE FROM MT_USER_TEST_SESSION",
            "WHERE",
            "MT_SID = #{sid}"
    })
    void delete(UserTestSession userTestSession);
}
