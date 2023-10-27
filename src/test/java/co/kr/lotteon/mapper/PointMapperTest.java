package co.kr.lotteon.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PointMapperTest {
    @Autowired
    private PointMapper pointMapper;

    @Test
    public void insertUsedPoint(){

        pointMapper.insertUsedPoint("lomong7807", 380, "포인트 사용" ,-10000);

    }
}
