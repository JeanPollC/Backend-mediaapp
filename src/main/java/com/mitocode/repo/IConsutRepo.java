package com.mitocode.repo;

import com.mitocode.dto.ConsultProcDTO;
import com.mitocode.dto.IConsultProcDTO;
import com.mitocode.model.Consult;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface IConsutRepo extends IGenericRepo<Consult, Integer> {

    @Query("FROM Consult c WHERE c.patient.dni = :dni OR LOWER(c.patient.firstName) LIKE %:fullname% OR LOWER(c.patient.lastName) LIKE %:fullname%")
    List<Consult> search(@Param("dni") String dni,@Param("fullname") String fullname);


    @Query("FROM Consult c WHERE c.consultDate BETWEEN :date1 AND :date2")
    List<Consult> searchByDates(@Param("date1")LocalDateTime date1, @Param("date2") LocalDateTime date2);

    @Query(value = "SELECT * FROM fn_list();", nativeQuery = true)
    List<ConsultProcDTO> callProcedureOrFuntionNative();

    @Query(value = "SELECT * FROM fn_list();", nativeQuery = true)
    List<IConsultProcDTO> callProcedureOrFuntionProjection();

}
