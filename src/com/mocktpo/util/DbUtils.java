package com.mocktpo.util;

import com.mocktpo.orm.mapper.ActivationCodeMapper;
import com.mocktpo.orm.mapper.UserTestAnswerMapper;
import com.mocktpo.orm.mapper.UserTestSessionMapper;
import com.mocktpo.util.constants.RC;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.net.URLDecoder;

public class DbUtils {

    private static SqlSessionFactory factory;

    private DbUtils() {
    }

    public static void init() {
        try {

            factory = new SqlSessionFactoryBuilder().build(DbUtils.class.getResourceAsStream(URLDecoder.decode(RC.CONFIG_DIR + RC.DATABASE_CONFIG_FILE, "utf-8")));
            Configuration c = factory.getConfiguration();

            c.addMapper(ActivationCodeMapper.class);
            c.addMapper(UserTestSessionMapper.class);
            c.addMapper(UserTestAnswerMapper.class);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static SqlSession getSqlSession() {
        return factory.openSession();
    }
}
