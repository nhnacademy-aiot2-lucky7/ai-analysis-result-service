package com.nhnacademy.ai_analysis_result_service.analysis_result.repository.impl;

import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.AnalysisResult;
import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.QAnalysisResult;
import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.enums.AnalysisType;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.common.SensorInfo;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.response.AnalysisResultResponse;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.response.AnalysisResultSearchResponse;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.response.QAnalysisResultResponse;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.response.QAnalysisResultSearchResponse;
import com.nhnacademy.ai_analysis_result_service.analysis_result.dto.result.SingleSensorPredictResult;
import com.nhnacademy.ai_analysis_result_service.analysis_result.repository.CustomAnalysisResultRepository;
import com.nhnacademy.ai_analysis_result_service.analysis_result_sensor_data_mapping.domain.QAnalysisResultSensorDataMapping;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

@Repository
public class CustomAnalysisResultRepositoryImpl extends QuerydslRepositorySupport implements CustomAnalysisResultRepository {
    private final JPAQueryFactory queryFactory;

    public CustomAnalysisResultRepositoryImpl(JPAQueryFactory queryFactory) {
        super(AnalysisResult.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public AnalysisResultResponse findAnalysisResultResponseById(Long id) {
        QAnalysisResult q = QAnalysisResult.analysisResult;

        return queryFactory
                .select(new QAnalysisResultResponse(
                        q.id,
                        q.departmentId,
                        q.resultJson
                ))
                .from(q)
                .where(q.id.eq(id))
                .fetchOne();
    }

    @Override
    public Page<AnalysisResultSearchResponse> searchResults(
            AnalysisType analysisType,
            String departmentId,
            Long from,
            Long to,
            String sensorId,
            Long gatewayId,
            String sensorType,
            Pageable pageable) {

        QAnalysisResult analysisResult = QAnalysisResult.analysisResult;
        QAnalysisResultSensorDataMapping analysisResultSensorDataMapping = QAnalysisResultSensorDataMapping.analysisResultSensorDataMapping;

        List<AnalysisResultSearchResponse> results = queryFactory
                .select(new QAnalysisResultSearchResponse(
                        analysisResult.id,
                        analysisResult.departmentId,
                        analysisResult.analysisType,
                        analysisResult.analyzedAt,
                        analysisResult.resultSummary
                ))
                .from(analysisResult)
                .leftJoin(analysisResult.analysisResultSensorDataMappingList, analysisResultSensorDataMapping)
                .where(
                        eqAnalysisType(analysisType),
                        eqDepartmentId(departmentId),
                        betweenAnalyzedAt(from, to),
                        eqSensorId(sensorId),
                        eqGatewayId(gatewayId),
                        eqSensorType(sensorType)
                )
                .distinct()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(analysisResult.analyzedAt.desc())
                .fetch();

        Long total = queryFactory
                .select(analysisResult.countDistinct())
                .from(analysisResult)
                .leftJoin(analysisResult.analysisResultSensorDataMappingList, analysisResultSensorDataMapping)
                .where(
                        eqAnalysisType(analysisType),
                        eqDepartmentId(departmentId),
                        betweenAnalyzedAt(from, to),
                        eqSensorId(sensorId),
                        eqGatewayId(gatewayId),
                        eqSensorType(sensorType)
                )
                .fetchOne();

        return new PageImpl<>(results, pageable, total != null ? total : 0);
    }

    @Override
    public List<AnalysisResult> findSingleSensorPredictResult(List<SensorInfo> sensorInfo) {
        QAnalysisResult analysisResult = QAnalysisResult.analysisResult;
        QAnalysisResultSensorDataMapping mapping = QAnalysisResultSensorDataMapping.analysisResultSensorDataMapping;

        // OR 조건 빌드
        BooleanExpression sensorConditions = sensorInfo.stream()
                .map(sensor ->
                        mapping.sensorId.eq(sensor.getSensorId())
                                .and(mapping.sensorType.eq(sensor.getSensorType()))
                                .and(mapping.gatewayId.eq(sensor.getGatewayId()))
                )
                .reduce(BooleanExpression::or)
                .orElse(Expressions.FALSE);  // 안전하게 FALSE로 대체 (절대 null 안 됨)

        // 서브쿼리: 센서별 최신 createdAt 조회
        JPQLQuery<Tuple> subQuery = queryFactory
                .select(mapping.sensorId, mapping.sensorType, mapping.gatewayId, analysisResult.createdAt.max())
                .from(mapping)
                .join(mapping.analysisResult, analysisResult)
                .where(
                        analysisResult.analysisType.eq(AnalysisType.SINGLE_SENSOR_PREDICT)
                                .and(sensorConditions)
                )
                .groupBy(mapping.sensorId, mapping.sensorType, mapping.gatewayId);

        // 메인쿼리: 실제 결과 조회
        return queryFactory
                .selectFrom(analysisResult)
                .join(analysisResult.analysisResultSensorDataMappingList, mapping)
                .where(
                        analysisResult.analysisType.eq(AnalysisType.SINGLE_SENSOR_PREDICT),
                        Expressions.list(mapping.sensorId, mapping.sensorType, mapping.gatewayId, analysisResult.createdAt)
                                .in(subQuery)
                )
                .fetch();
    }

    private BooleanExpression eqAnalysisType(AnalysisType type) {
        return type != null ? QAnalysisResult.analysisResult.analysisType.eq(type) : null;
    }

    private BooleanExpression eqDepartmentId(String departmentId) {
        return StringUtils.hasText(departmentId) ? QAnalysisResult.analysisResult.departmentId.eq(departmentId) : null;
    }

    private BooleanExpression betweenAnalyzedAt(Long from, Long to) {
        if (from != null && to != null) {
            return QAnalysisResult.analysisResult.analyzedAt.between(from, to);
        } else if (from != null) {
            return QAnalysisResult.analysisResult.analyzedAt.goe(from);
        } else if (to != null) {
            return QAnalysisResult.analysisResult.analyzedAt.loe(to);
        }
        return null;
    }

    private BooleanExpression eqSensorId(String sensorId) {
        return StringUtils.hasText(sensorId) ? QAnalysisResultSensorDataMapping.analysisResultSensorDataMapping.sensorId.eq(sensorId) : null;
    }

    private BooleanExpression eqGatewayId(Long gatewayId) {
        return gatewayId != null ? QAnalysisResultSensorDataMapping.analysisResultSensorDataMapping.gatewayId.eq(gatewayId) : null;
    }

    private BooleanExpression eqSensorType(String sensorType) {
        return StringUtils.hasText(sensorType) ? QAnalysisResultSensorDataMapping.analysisResultSensorDataMapping.sensorType.eq(sensorType) : null;
    }
}
