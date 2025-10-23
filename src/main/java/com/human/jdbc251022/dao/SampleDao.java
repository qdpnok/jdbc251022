package com.human.jdbc251022.dao;

import com.human.jdbc251022.model.Sample;

public class SampleDao {

    public static void main(String[] args) {

        // 1. 샘플 등록 (INSERT) 테스트
        System.out.println("### 1. 샘플 등록 테스트 (INSERT) ###");
        Sample newSample = new Sample();

        newSample.setBatchId(1001);
        newSample.setResultId(200001);

        int insertResult = sampleDao.insertSample(newSample);

        int newSampleId = 300001; // 테스트를 위해 이미 DB에 있는 ID 사용

        if (insertResult > 0) {
            System.out.println("✅ 새 샘플 항목 등록 성공. (SAMPLE_DATE는 DB의 DEFAULT SYSDATE 사용)");
        } else {
            System.out.println("❌ 샘플 항목 등록 실패. (BATCH_ID/RESULT_ID 외래 키 확인)");
        }

        System.out.println("\n" + "---".repeat(15) + "\n");

        // 2. 채취 날짜 수정 (UPDATE) 테스트
        System.out.println("### 2. 채취 날짜 수정 테스트 (UPDATE) ###");

        int updateId = 300002; // 수정할 샘플 ID
        int newDateValue = 20251105; // ⚠️ 새로운 날짜를 'YYYYMMDD' 형식의 정수(int)로 표현

        int updateResult = sampleDao.updateSampleDate(updateId, newDateValue);

        if (updateResult > 0) {
            System.out.println("✅ 샘플 ID: " + updateId + "의 채취 날짜를 " + newDateValue + "로 수정 성공.");
        } else {
            System.out.println("❌ 샘플 ID: " + updateId + " 수정 실패. (ID를 확인하세요)");
        }

        System.out.println("\n" + "---".repeat(15) + "\n");

        // 3. 샘플 상세 정보 조회 (SELECT) 테스트
        System.out.println("### 3. 샘플 상세 조회 테스트 (SELECT) ###");

        int selectId = 300003;
        Sample fetchedSample = sampleDao.selectSample(selectId);

        if (fetchedSample != null) {
            System.out.println("✅ 샘플 ID: " + selectId + " 조회 성공.");
            System.out.println("   > 샘플 ID: " + fetchedSample.getSampleId());
            System.out.println("   > 생산 배치 ID: " + fetchedSample.getBatchId());
            System.out.println("   > 생산 결과 ID: " + fetchedSample.getResultId());
            System.out.println("   > 샘플 채취일 (NUMBER): " + fetchedSample.getSampleDate()); // ⚠️ int로 출력
        } else {
            System.out.println("❌ 샘플 ID: " + selectId + " 조회 실패.");
        }
    }
}
