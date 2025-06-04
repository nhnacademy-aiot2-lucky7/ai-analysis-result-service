package com.nhnacademy.ai_analysis_result_service.common.utils.event.producer;


import com.nhnacademy.ai_analysis_result_service.common.utils.event.dto.EventDTO;

public interface EventProducer {
    void sendEvent(EventDTO dto);
}
