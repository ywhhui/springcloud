package com.szcgc.project.repository;

import com.szcgc.project.constant.SubjectEnum;
import com.szcgc.project.model.RepayRecordInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @Author liaohong
 * @create 2020/10/21 11:08
 */
public interface RepayRecordRepository extends PagingAndSortingRepository<RepayRecordInfo, Integer> {

    List<RepayRecordInfo> findByProjectId(int projectId);

    List<RepayRecordInfo> findByCustomerId(int customerId);

    List<RepayRecordInfo> findByProjectIdAndSubject(int projectId, SubjectEnum subject);

    List<RepayRecordInfo> findByCustomerIdAndSubject(int customerId, SubjectEnum subject);

    @Query("select sum(p.amount) from RepayRecordInfo p where p.projectId=?1 and p.subject=?2")
    Long sumAmountByProjectIdAndSubject(int projectId, SubjectEnum subject);

    @Query("select sum(p.amount) from RepayRecordInfo p where p.customerId=?1 and p.subject=?2")
    Long sumAmountByCustomerIdAndSubject(int customerId, SubjectEnum subject);

//    @Query(value = "select t.amount,t.repay_At as repayAt,t.account_Id as accountId from repayrcdinfo t where t.project_Id=?1 and t.subject=?3 and date_format(t.repay_At, \"%Y%m\")=?2  order by t.repay_At asc",
//           nativeQuery = true)
//    List<Map<String,Object>> findByProjectIdAndMonth(int projectId, String month,String subject);
}
