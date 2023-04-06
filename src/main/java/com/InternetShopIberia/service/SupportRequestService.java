package com.InternetShopIberia.service;

import com.InternetShopIberia.dto.SupportRequestDto;
import com.InternetShopIberia.model.SupportRequest;
import com.InternetShopIberia.model.User;
import com.InternetShopIberia.repository.SupportRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupportRequestService {
    @Autowired
    private SupportRequestRepository supportRequestRepository;

    public SupportRequest registerRequest(SupportRequestDto supportRequestDto, User user) {
        SupportRequest supportRequest = new SupportRequest();
        supportRequest.setCode(generateCode());
        supportRequest.setEmail(supportRequestDto.getEmail());
        supportRequest.setMessage(supportRequestDto.getMessage());
        supportRequest.setSubject(supportRequestDto.getSubject());
        supportRequest.setUser(user);
        return supportRequestRepository.save(supportRequest);
    }

    private Long generateCode() {
        return supportRequestRepository.findAll().size()+1L;
    }
}
