package co.kr.lotteon.service.policy;

import co.kr.lotteon.dto.member.TermsDTO;
import co.kr.lotteon.dto.policy.PolicyTermsDTO;
import co.kr.lotteon.entity.member.TermsEntity;
import co.kr.lotteon.mapper.MemberMapper;
import co.kr.lotteon.mapper.PolicyMapper;
import co.kr.lotteon.repository.member.TermsRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class PolicyService {

    @Autowired
    private PolicyMapper policyMapper;


    public List<PolicyTermsDTO> selectPolicyBuyer() {

        List<PolicyTermsDTO> terms = policyMapper.selectPolicyBuyer();

        return terms;
    }
    public List<PolicyTermsDTO> selectPolicyFinance() {

        List<PolicyTermsDTO> terms = policyMapper.selectPolicyFinance();

        return terms;
    }
    public List<PolicyTermsDTO> selectPolicyLocation() {

        List<PolicyTermsDTO> terms = policyMapper.selectPolicyLocation();

        return terms;
    }
    public List<PolicyTermsDTO> selectPolicyPrivacy() {

        List<PolicyTermsDTO> terms = policyMapper.selectPolicyPrivacy();

        return terms;
    }
    public List<PolicyTermsDTO> selectPolicySeller() {

        List<PolicyTermsDTO> terms = policyMapper.selectPolicySeller();

        return terms;
    }


}


