package com.cred.engine.controller;


import com.cred.engine.dto.ErrorDTO;
import com.cred.engine.dto.ProposalDTO;
import com.cred.engine.service.EngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestController()
@RequestMapping("/process")
public class EngineController implements NewCreditEngineAPI{

    @Autowired
    private EngineService engineService;
    @Autowired
    private MessageSource messageSource;

    @PostMapping()
    public ResponseEntity process(@RequestBody ProposalDTO proposalDTO, HttpServletRequest request){
        if(request.getRequestURI().equals("/newcreditengine/api/v1/process")){
            return new ResponseEntity(this.engineService.processCreditProposal(proposalDTO), HttpStatus.ACCEPTED);
        }
        ErrorDTO errorDetails = new ErrorDTO(LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                messageSource.getMessage("uri.unauthorized", null, LocaleContextHolder.getLocale()),
                "/process");
        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
