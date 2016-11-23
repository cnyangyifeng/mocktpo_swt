package com.mocktpo.orm.mapper;

import com.mocktpo.orm.domain.ActivationCode;
import com.mocktpo.util.DbUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class ActivationCodeMapperTest {

    private ActivationCodeMapper mapper;
    private SqlSession session;
    private String c;

    @Before
    public void setUp() {
        DbUtils.init();
        session = DbUtils.getSqlSession();
        mapper = session.getMapper(ActivationCodeMapper.class);
        c = "owJ4nJvAy8zAxTg1QdHwqfXbAMbTB2SSxHMyk1PzilPdMnNS/RJzU3U90/Pyi1JT\n" +
            "wj/Hpyrr6ipApRXSgPJcyiGlqQr+ySUKBiYKhqZWpoZWBoYKzsEhCkYGhmZcuYlZ\n" +
            "+UXxZalFxZn5ebaGXKm5iZk5toZmpkbGluaWZg6FhXrJ+blcGYlFKeWJRam2ZkYG\n" +
            "FkYpyaa6KRbJJrrGqUlGuommBua6RoaGScnGKQYWxkapXIkFBfF5QHfZ5uYnZ5cU\n" +
            "5HOVJeZkpsSXZBTll6Zn2FpCAVdqSmYJyFqgDbmpRcmZiTlcnYxaLAyMXAwirEwg\n" +
            "3/DKJOdVJualV2ampealM3BxCsAC4qQH+z99cfXalBNtzV3ln3YqZdxZHL2TsWzq\n" +
            "n4zGoKsi8xLXGS+ImROxUWg5W49IF4vJCpOI/TOvn85gPV4g+eN+gPrZtJVmqQr6\n" +
            "Kx5kZYmFJqlVa/p5pn1lu7w88tOL/MtXrjgX5j2ds+fYlhNFt38uWdO8QUJxnXKZ\n" +
            "bqmMy9R/Xpb6587Y9vxujGw4OeeH3udGjcuymo/kuAwOM7CXXc2vvMM8K6ROwN9Q\n" +
            "RLi31FVkup+I2ZW47PjUHyypFxpmZMoe2ifKHH7oMFO6z9nqfLeP4VVZd77NcCyJ\n" +
            "eOIgtEno/vsZLPem98e1TZFgzTQJyAmLUI/fyq8paGUq+3H2Jv1ns+rEGbKLn81Z\n" +
            "ZDQnlk8KAMYTz0I=\n" + "=/sNk\n";
    }

    @Test
    public void testSchema() {
        mapper.schema();
    }

    @Test
    public void testDrop() {
        mapper.drop();
    }

    @Test
    public void testInsert() {
        ActivationCode lic = new ActivationCode();
        lic.setEmail("cnyangyifeng@163.com");
        lic.setContent(c);
        lic.setDateCreated(new Date());
        lic.setDateUpdated(new Date());
        mapper.insert(lic);
    }

    @Test
    public void testFind() {
        List<ActivationCode> list = mapper.find();
        for (ActivationCode lic : list) {
            System.out.println(lic);
        }
    }

    @Test
    public void testUpdate() {
        ActivationCode lic = new ActivationCode();
        lic.setEmail("cnyangyifeng@163.com");
        String c = "n/a";
        lic.setContent(c);
        mapper.update(lic);
    }

    @Test
    public void testCount() {
        long count = mapper.count();
        System.out.println(count);
    }

    @After
    public void tearDown() {
        session.commit();
        session.close();
    }
}
