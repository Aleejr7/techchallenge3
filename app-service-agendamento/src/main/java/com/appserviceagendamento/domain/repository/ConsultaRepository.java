package com.appserviceagendamento.domain.repository;

import com.appserviceagendamento.domain.entity.ConsultaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultaRepository extends JpaRepository<ConsultaModel,Long> {
}
