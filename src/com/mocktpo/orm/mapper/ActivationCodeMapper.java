package com.mocktpo.orm.mapper;

import com.mocktpo.orm.domain.ActivationCode;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ActivationCodeMapper {

    @Update({
            "CREATE TABLE IF NOT EXISTS MT_ACTIVATION_CODE (",
            "MT_EMAIL VARCHAR(64),",
            "MT_CONTENT VARCHAR(1024),",
            "MT_DATE_CREATED TIMESTAMP,",
            "MT_DATE_UPDATED TIMESTAMP",
            ")"
    })
    void schema();


    @Update({
            "DROP TABLE MT_ACTIVATION_CODE IF EXISTS"
    })
    void drop();

    @Insert({
            "INSERT INTO MT_ACTIVATION_CODE (",
            "MT_EMAIL,",
            "MT_CONTENT,",
            "MT_DATE_CREATED,",
            "MT_DATE_UPDATED",
            ") VALUES (",
            "#{email},",
            "#{content},",
            "#{dateCreated},",
            "#{dateUpdated}",
            ")"
    })
    void insert(ActivationCode lic);

    @Select({
            "SELECT",
            "MT_EMAIL AS email,",
            "MT_CONTENT AS content,",
            "MT_DATE_CREATED AS dateCreated,",
            "MT_DATE_UPDATED AS dateUpdated",
            "FROM MT_ACTIVATION_CODE",
            "ORDER BY MT_DATE_UPDATED DESC"
    })
    List<ActivationCode> find();

    @Update({
            "UPDATE MT_ACTIVATION_CODE",
            "SET",
            "MT_EMAIL = #{email},",
            "MT_CONTENT = #{content},",
            "MT_DATE_CREATED = #{dateCreated},",
            "MT_DATE_UPDATED = #{dateUpdated}"
    })
    int update(ActivationCode lic);

    @Select(
            "SELECT COUNT(*) FROM MT_ACTIVATION_CODE"
    )
    long count();
}
