package com.mocktpo.orm.mapper;

import com.mocktpo.orm.domain.LicenseCode;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface LicenseCodeMapper {

    @Update({
            "CREATE TABLE IF NOT EXISTS MT_LICENSE_CODE (",
            "MT_CONTENT VARCHAR(1024)",
            ")"
    })
    void schema();

    @Insert({
            "INSERT INTO MT_LICENSE_CODE (",
            "MT_CONTENT",
            ") VALUES (",
            "#{content}",
            ")"
    })
    void create(LicenseCode lic);

    @Select({
            "SELECT",
            "MT_CONTENT AS content",
            "FROM MT_LICENSE_CODE"
    })
    List<LicenseCode> find();

    @Update({
            "UPDATE MT_LICENSE_CODE",
            "SET",
            "MT_CONTENT = #{content}"
    })
    int update(LicenseCode lic);

    @Select(
            "SELECT COUNT(*) FROM MT_LICENSE_CODE"
    )
    long count();

    @Delete({
            "DELETE FROM MT_LICENSE_CODE"
    })
    void deleteAll();
}
