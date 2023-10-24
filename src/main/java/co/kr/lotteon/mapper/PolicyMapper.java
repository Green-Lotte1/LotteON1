package co.kr.lotteon.mapper;

import co.kr.lotteon.dto.policy.PolicyTermsDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PolicyMapper {

    public List<PolicyTermsDTO> selectPolicy();
}
