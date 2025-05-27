//package com.nhnacademy.ai_analysis_result_service.dto.result;
//
//import com.nhnacademy.ai_analysis_result_service.dto.common.SensorInfo;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
///**
// * 여러 센서 간의 상관관계를 기반으로 이상을 감지한 분석 결과 DTO입니다.
// * 상관행렬, 이상 쌍, 분석에 사용된 기간 등의 정보를 포함합니다.
// */
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//public class SensorRelationAnomalyResult implements AnalysisResultDto {
//
//    /**
//     * 사용한 분석 모델명 (예: Pearson, Copula, Graph-based 등)
//     */
//    private String model;
//
//    /**
//     * 분석 대상 전체 센서 쌍 간 상관계수 목록
//     */
//    private List<SensorPairCorrelation> correlationMatrix;
//
//    /**
//     * 이상으로 판단된 센서 쌍 목록
//     */
//    private List<SensorPairCorrelation> anomalousPairs;
//
//    /**
//     * 분석에 사용된 센서 목록
//     */
//    private List<SensorInfo> relatedSensors;
//
//    /**
//     * 모델 학습에 사용된 기간 (형식: yyyy-MM-dd ~ yyyy-MM-dd)
//     */
//    private String trainingPeriod;
//
//    /**
//     * 실제 분석 대상이 되는 평가 기간 (형식: yyyy-MM-dd ~ yyyy-MM-dd)
//     */
//    private String evaluationPeriod;
//
//    /**
//     * 센서 관계 기반으로 이상이 감지되었는지 여부
//     */
//    private boolean anomalyDetected;
//
//    /**
//     * 분석이 수행된 시점
//     */
//    private LocalDateTime analyzedAt;
//
//    /**
//     * 이상 감지 여부 반환
//     * @return 이상 감지 시 true
//     */
//    @Override
//    public boolean isAnomalyDetected() {
//        return anomalyDetected;
//    }
//
//    /**
//     * 분석 시점 반환
//     * @return 분석 수행 시각
//     */
//    @Override
//    public LocalDateTime getAnalyzedAt() {
//        return analyzedAt;
//    }
//
//    /**
//     * 센서 간의 상관계수를 나타내는 클래스입니다.
//     * 두 센서 쌍과 그 사이의 상관계수를 저장합니다.
//     */
//    @Getter
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public static class SensorPairCorrelation {
//
//        /**
//         * 센서 A
//         */
//        private SensorInfo sensorA;
//
//        /**
//         * 센서 B
//         */
//        private SensorInfo sensorB;
//
//        /**
//         * 상관계수 (예: Pearson 계수, -1.0 ~ 1.0 범위)
//         */
//        private Double correlation;
//    }
//}