package com.cred.engine.service;

import com.cred.engine.dto.ProposalDTO;
import com.cred.engine.map.GenderMap;
import com.cred.engine.map.MaritalStatusMap;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class EngineService extends RootService{

    public ProposalDTO processCreditProposal(ProposalDTO proposalDTO){
        Integer creditPercent = 65;
        BigDecimal thousand = new BigDecimal(1000);
        BigDecimal fourThousand = new BigDecimal(4000);
        BigDecimal eightThousand = new BigDecimal(8000);
        Integer dependents = proposalDTO.getDependents();
        if(proposalDTO.getDependents() >= 4){
            dependents = 4;
        }
        Double dependentsCalc = Math.pow((0.5*(double)dependents), (double)dependents);
        if(proposalDTO.getIncome().compareTo(thousand) > 0 && proposalDTO.getIncome().compareTo(fourThousand) <= 0){
            creditPercent -= (int) Math.round(dependentsCalc/3);
        }else if(proposalDTO.getIncome().compareTo(fourThousand) > 0 && proposalDTO.getIncome().compareTo(eightThousand) <= 0){
            creditPercent+=5 - (int) Math.round(dependentsCalc/5);
        }else if(proposalDTO.getIncome().compareTo(eightThousand) > 0){
            creditPercent += 15;
        }else{
            proposalDTO.setApproved(false);
            proposalDTO.setLimit(super.getMessageFromUserLocale("low.income"));
            return proposalDTO;
        }


        if(proposalDTO.getAge() > 60){
            creditPercent -= 14;
        }else if(proposalDTO.getAge() < 60 && proposalDTO.getAge() >= 30) {
            creditPercent += 3;
        }else if(proposalDTO.getAge() < 30 && proposalDTO.getAge() >= 18){
            creditPercent -= 5;
        }

        if(proposalDTO.getMaritalStatus().equalsIgnoreCase(MaritalStatusMap.DIVORCED.name()) && proposalDTO.getGender().equalsIgnoreCase(GenderMap.M.name())){
            creditPercent -= 8;
        }
        if(creditPercent < 50){
            proposalDTO.setApproved(false);
            proposalDTO.setLimit(super.getMessageFromUserLocale("disapproved.by.credit.policy"));
            return proposalDTO;
        }

        proposalDTO.setApproved(true);
        proposalDTO.setLimit(proposalDTO.getIncome().subtract((proposalDTO.getIncome().multiply((new BigDecimal(creditPercent).divide(new BigDecimal(100)))))).toString());
        return proposalDTO;
    }

}
