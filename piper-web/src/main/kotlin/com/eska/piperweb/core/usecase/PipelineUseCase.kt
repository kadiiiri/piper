package com.eska.piperweb.core.usecase

import com.eska.piperweb.core.domain.Pipeline

interface FindPipelineByNameUseCase {
    fun findByName(name: String): Pipeline?
}

interface FindAllPipelinesUseCase {
    fun findAll(): List<Pipeline>
}

interface DeletePipelineUseCase {
    fun delete(name: String)
}

interface EnablePipelineUseCase {
    fun enable(name: String): Pipeline
}

interface DisablePipelineUseCase {
    fun disable(name: String): Pipeline
}

interface SavePipelineUseCase {
    fun save(pipeline: Pipeline): Pipeline
}

interface UpdatePipelineUseCase {
    fun update(pipeline: Pipeline): Pipeline
}