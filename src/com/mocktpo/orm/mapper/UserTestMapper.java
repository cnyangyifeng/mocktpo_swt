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
            "#{alias},",
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
    void insert(UserTest ut);

    @Select({
            "SELECT",
            "MT_EMAIL AS email,",
            "MT_TID AS tid,",
            "MT_TITLE AS title,",
            "MT_ALIAS AS alias,",
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
            "MT_TID = #{tid}"
    })
    void update(UserTest ut);

    @Select(
            "SELECT COUNT(*) FROM MT_USER_TEST"
    )
    long count();
}
