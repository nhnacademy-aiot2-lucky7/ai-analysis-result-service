package com.nhnacademy.ai_analysis_result_service.analysis_result.repository;

import com.nhnacademy.ai_analysis_result_service.analysis_result.domain.AnalysisResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 분석 결과를 조회하기 위한 JPA 리포지토리입니다.
 * Native SQL 기반으로 분석 결과 DTO를 직접 반환합니다.
 */
@Repository
public interface AnalysisResultRepository extends JpaRepository<AnalysisResult, Long>, CustomAnalysisResultRepository {

}

