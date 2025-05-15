package com.eska.piperweb.api.monitor

import com.eska.piperweb.adapter.database.entities.DagEntity
import com.eska.piperweb.adapter.database.entities.DagRepository
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/dag")
class DagController(
    private val dagRepository: DagRepository
) {

    @RequestMapping("/list")
    fun list(): List<DagEntity> = dagRepository.findAll()
}