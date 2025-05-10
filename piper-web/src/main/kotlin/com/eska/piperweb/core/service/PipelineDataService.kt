package com.eska.piperweb.core.service

import com.eska.piperweb.adapter.database.entities.PipelineDataRepository
import com.eska.piperweb.core.PipelineMapper.toDomain
import com.eska.piperweb.core.PipelineMapper.toDomains
import com.eska.piperweb.core.PipelineMapper.toEntity
import com.eska.piperweb.core.domain.Pipeline
import com.eska.piperweb.core.domain.PipelineStatus.ENABLED
import com.eska.piperweb.core.usecase.DeletePipelineUseCase
import com.eska.piperweb.core.usecase.DisablePipelineUseCase
import com.eska.piperweb.core.usecase.EnablePipelineUseCase
import com.eska.piperweb.core.usecase.FindAllPipelinesUseCase
import com.eska.piperweb.core.usecase.FindPipelineByNameUseCase
import com.eska.piperweb.core.usecase.SavePipelineUseCase
import com.eska.piperweb.core.usecase.UpdatePipelineUseCase
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service


@Service
class PipelineDataService(val pipelineDataRepository: PipelineDataRepository)
    : FindPipelineByNameUseCase,
    FindAllPipelinesUseCase,
    SavePipelineUseCase,
    EnablePipelineUseCase,
    DisablePipelineUseCase,
    DeletePipelineUseCase,
    UpdatePipelineUseCase {

    override fun findAll() = pipelineDataRepository.findAll().toDomains()
    override fun findByName(name: String) = pipelineDataRepository.findByName(name)?.toDomain()
    override fun save(pipeline: Pipeline) = pipelineDataRepository.save(pipeline.toEntity()).toDomain()

    @Transactional
    override fun enable(name: String): Pipeline {
        val pipeline = pipelineDataRepository.findByName(name)
            ?.toDomain()
            ?: throw IllegalStateException("Pipeline with name: '$name' not found.")

        require(pipeline.status != ENABLED) { "Pipeline with name: '$name' is already enabled." }

        pipelineDataRepository.enable(name)

        val updatedPipeline = pipelineDataRepository.findByName(name)
            ?.toDomain()
            ?: throw IllegalStateException("Pipeline with name: '$name' not found after an enable operation was applied.")

        return updatedPipeline
    }

    @Transactional
    override fun disable(name: String): Pipeline {
        pipelineDataRepository.disable(name)

        val pipeline = pipelineDataRepository.findByName(name)
            ?.toDomain()
            ?: throw IllegalStateException("Pipeline with name: '$name' not found.")

        if (pipeline.status == ENABLED)
            throw IllegalStateException("Pipeline with name: '$name' is already disabled.")

        return pipeline
    }

    override fun delete(name: String) {
        pipelineDataRepository.deleteByName(name)
    }

    override fun update(pipeline: Pipeline): Pipeline {
        throw UnsupportedOperationException("Pipeline update is not supported yet.")
    }

}