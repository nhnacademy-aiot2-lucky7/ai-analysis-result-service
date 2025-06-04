package com.nhnacademy.ai_analysis_result_service.common.utils.event.service.impl;

import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.AnalysisResultDto;
import com.nhnacademy.ai_analysis_result_service.common.enums.EventLevel;
import com.nhnacademy.ai_analysis_result_service.common.utils.event.dto.EventDTO;
import com.nhnacademy.ai_analysis_result_service.common.utils.event.producer.EventProducer;
import com.nhnacademy.ai_analysis_result_service.common.utils.event.service.EventService;
import com.nhnacademy.ai_analysis_result_service.common.utils.generator.event.service.EventDetailGeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventProducer eventProducer;
    private final EventDetailGeneratorService eventDetailGeneratorService;

    private static final String SOURCE_ID = "ai-analysis-result";
    private static final String SOURCE_TYPE = "ai-analysis-result";

    @Override
    public <T extends AnalysisResultDto> void sendCreateResultEvent(AnalysisType type, T dto, String departmentId) {
        String eventDetail = eventDetailGeneratorService.generate(type, dto);

        EventDTO eventDTO = new EventDTO(
                EventLevel.INFO,
                eventDetail,
                SOURCE_ID,
                SOURCE_TYPE,
                departmentId,
                LocalDateTime.now()
        );

        eventProducer.sendEvent(eventDTO);
        log.info("Event message: {}", eventDetail);
    }
}
