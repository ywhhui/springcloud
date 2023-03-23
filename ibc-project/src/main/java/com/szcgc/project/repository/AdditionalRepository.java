package com.szcgc.project.repository;

import com.szcgc.project.model.AdditionalInfo;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author JINLINGXUAN
 * @create 2021-03-23
 */
public interface AdditionalRepository extends PagingAndSortingRepository<AdditionalInfo, Integer> {

    List<AdditionalInfo> findByProjectId(int projectId);

    AdditionalInfo findFirstByProjectIdOrderByIdDesc(int projectId);

    AdditionalInfo findByProjectIdAndFileId(int projectId, int fileId);

    AdditionalInfo findByProjectIdAndFileName(int projectId, String fileName);

    /*@Modifying
    @Transactional
    @Query("update AdditionalInfo p set p.confirmed=?4, p.confirmBy=?3, p.versionTag=p.versionTag+1, p.confirmAt=now()  WHERE p.id in (?1) and p.projectId=?2")
    int updateConfirms(List<Integer> ids, int projectId, int accountId, int confirmed);*/
}
