package com.github.piperweb.domain.service

import com.github.piperweb.application.repository.DagRepository
import com.github.piperweb.application.usecase.CreateDagUseCase
import com.github.piperweb.application.usecase.DeleteDagUseCase
import com.github.piperweb.application.usecase.FindAllDagsUseCase
import com.github.piperweb.application.usecase.FindDagByIdUseCase
import com.github.piperweb.application.usecase.FindDagByNameUseCase
import com.github.piperweb.application.usecase.UpdateDagUseCase
import com.github.piperweb.domain.mapper.DagMapper.toEntity
import com.github.piperweb.domain.mapper.DagMapper.toModel
import com.github.piperweb.domain.model.Dag
import java.util.*
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class DagService(
    private val dagRepository: DagRepository
) : FindDagByIdUseCase,
    FindDagByNameUseCase,
    FindAllDagsUseCase,
    CreateDagUseCase,
    DeleteDagUseCase,
    UpdateDagUseCase {

    override fun findById(dagId: UUID): Dag? = dagRepository.findByIdOrNull(dagId)?.toModel()

    override fun findByName(name: String): Dag? = dagRepository.findByName(name)?.toModel()

    override fun findAll(): List<Dag> = dagRepository.findAll().map { it.toModel() }

    override fun create(dag: Dag): Dag = dagRepository.saveAndFlush(dag.toEntity()).toModel()

    override fun update(dag: Dag): Dag = dagRepository.saveAndFlush(dag.toEntity()).toModel()

    override fun delete(name: String) = dagRepository.deleteByName(name)

}