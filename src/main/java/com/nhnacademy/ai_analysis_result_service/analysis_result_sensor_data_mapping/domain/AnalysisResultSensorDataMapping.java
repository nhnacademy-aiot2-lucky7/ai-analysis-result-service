package com.nhnacademy.ai_analysis_result_service.analysis_result_sensor_data_mapping.domain;

import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.AnalysisResult;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "analysis_result_sensor_data_mappings")
public class AnalysisResultSensorDataMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gateway_id")
    private Long gatewayId;

    @Column(name = "sensor_id")
    private String sensorId;

    @Column(name = "sensor_type")
    private String sensorType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analysis_result_id")
    private AnalysisResult analysisResult;

    public static AnalysisResultSensorDataMapping of(Long gatewayId, String sensorId, String sensorType, AnalysisResult analysisResult){
        return new AnalysisResultSensorDataMapping(null, gatewayId, sensorId, sensorType, analysisResult);
    }
}