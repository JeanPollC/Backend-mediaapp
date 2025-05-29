package com.mitocode.repo;

import com.mitocode.model.Medic;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.stereotype.Repository;

public interface IMedicRepo extends IGenericRepo<Medic, Integer> {


}
