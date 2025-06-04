package com.nhnacademy.ai_analysis_result_service.analysis_result.domain;

import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.common.SensorInfo;
import com.nhnacademy.ai_analysis_result_service.analysis_result_sensor_data_mapping.domain.AnalysisResultSensorDataMapping;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 센서 기반 AI 분석 결과를 저장하는 엔티티입니다.
 * 분석 유형, 센서 데이터 번호 리스트, 분석 결과 요약 및 메타 정보, 분석 시점 등을 포함합니다.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "ai_analysis_result")
public class AnalysisResult {

    /**
     * 분석 결과 식별자 (자동 증가 ID)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "department_id")
    private String departmentId;

    /**
     * 분석 타입 (단일 센서, 센서 관계 분석 등)
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "analysis_type")
    private AnalysisType analysisType;

    /**
     * 분석이 수행된 시각
     */
    @Column(name = "analyzed_at")
    private Long analyzedAt;

    @OneToMany(mappedBy = "analysisResult", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnalysisResultSensorDataMapping> analysisResultSensorDataMappingList = new ArrayList<>();

    /**
     * 분석 요약 메시지
     */
    @Column(name = "result_summary", columnDefinition = "TEXT")
    private String resultSummary;

    /**
     * 분석 결과 전체를 직렬화한 JSON 문자열
     */
    @Column(name = "result_json", columnDefinition = "JSON")
    private String resultJson;

    /**
     * 분석 관련 메타데이터 (모델명, 센서 등)
     */
    @Column(name = "meta_json", columnDefinition = "JSON")
    private String metaJson;

    /**
     * 데이터가 저장된 시각 (자동 생성)
     */
    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    private AnalysisResult(AnalysisType type, String departmentId, Long analyzedAt, List<SensorInfo> sensorInfos, String resultSummary, String resultJson, String metaJson) {
        this.analysisType = type;
        this.departmentId = departmentId;
        this.analyzedAt = analyzedAt;
        this.analysisResultSensorDataMappingList = sensorInfos.stream().map(s -> AnalysisResultSensorDataMapping.of(s.getGatewayId(), s.getSensorId(), s.getSensorType(), this)).toList();
        this.resultSummary = resultSummary;
        this.resultJson = resultJson;
        this.metaJson = metaJson;
    }

    public static AnalysisResult of(AnalysisType type, String departmentId, Long analyzedAt, List<SensorInfo> sensorInfos, String resultSummary, String resultJson, String metaJson) {
        return new AnalysisResult(type, departmentId, analyzedAt, sensorInfos, resultSummary, resultJson, metaJson);
    }
}
