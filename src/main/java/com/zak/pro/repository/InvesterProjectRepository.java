/**
 * Created by: nuwan_r
 * Created on: 3/9/2021
 */
package com.zak.pro.repository;

import com.zak.pro.entity.InvesterProject;
import com.zak.pro.entity.Investor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Repository
public interface InvesterProjectRepository extends JpaRepository<InvesterProject, Long> {

    List<InvesterProject> findByInvesterId(Long investerId);

    List<InvesterProject> findByProjectId(Long projectId);

    List<InvesterProject> findByProjectIdAndInvesterId(Long projectId, long investorId);

}
