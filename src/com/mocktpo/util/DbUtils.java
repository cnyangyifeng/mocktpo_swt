package com.mocktpo.util;

import com.mocktpo.orm.mapper.ActivationCodeMapper;
import com.mocktpo.orm.mapper.UserMapper;
import com.mocktpo.util.constants.ResourceConstants;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DbUtils {

    private static SqlSessionFactory factory;

    private DbUtils() {
    }

    public static void init() {
        factory = new SqlSessionFactoryBuilder().build(DbUtils.class.getResourceAsStream(ResourceConstants.CONFIG_DIR + ResourceConstants.DATABASE_CONFIG_FILE));
        Configuration c = factory.getConfiguration();

        c.addMapper(ActivationCodeMapper.class);
        c.addMapper(UserMapper.class);
    }

    public static SqlSession getSqlSession() {
        return factory.openSession();
    }
}
