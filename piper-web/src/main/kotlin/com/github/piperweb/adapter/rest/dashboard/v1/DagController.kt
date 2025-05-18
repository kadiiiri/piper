package com.github.piperweb.adapter.rest.dashboard.v1

import com.github.piperweb.domain.model.Dag
import com.github.piperweb.application.usecase.FindAllDagsUseCase
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/dag")
class DagController(
    private val findAllDagsUseCase: FindAllDagsUseCase
) {

    @RequestMapping("/list")
    fun list(): List<Dag> = findAllDagsUseCase.findAll()
}