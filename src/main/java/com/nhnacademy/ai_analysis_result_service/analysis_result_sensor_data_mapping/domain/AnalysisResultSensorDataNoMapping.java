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
@Table(name = "analysis_result_sensor_data_no_mappings")
public class AnalysisResultSensorDataNoMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sensor_data_no")
    private Long sensorDataNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analysis_result_id")
    private AnalysisResult analysisResult;

    public static AnalysisResultSensorDataNoMapping of(Long sensorDataNo, AnalysisResult analysisResult){
        return new AnalysisResultSensorDataNoMapping(null, sensorDataNo, analysisResult);
    }
}