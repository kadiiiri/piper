package com.github.piperweb.domain.usecase

import com.github.piperweb.domain.model.Dag
import java.util.*

interface CreateDagUseCase {
    fun create(dag: Dag): Dag
}

interface UpdateDagUseCase {
    fun update(dag: Dag): Dag
}

interface DeleteDagUseCase {
    fun delete(name: String)
}

interface FindDagByIdUseCase {
    fun findById(dagId: UUID): Dag?
}

interface FindDagByNameUseCase {
    fun findByName(name: String): Dag?
}

interface FindAllDagsUseCase {
    fun findAll(): List<Dag>
}
