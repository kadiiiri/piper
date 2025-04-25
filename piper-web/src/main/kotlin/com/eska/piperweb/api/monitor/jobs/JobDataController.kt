package com.eska.piperweb.api.monitor.jobs

import com.eska.piperweb.adapter.database.entities.Job
import com.eska.piperweb.core.domain.usecases.FindAllJobsUseCase
import com.eska.piperweb.core.domain.usecases.FindJobByNameUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/jobs")
@Tag(name = "Job APIs", description = "Endpoints to manage and monitor Jobs")
class JobDataController(
    private val findAllJobsUseCase: FindAllJobsUseCase,
    private val findJobByNameUseCase: FindJobByNameUseCase,
) {
    @GetMapping("/name")
    @Operation(
        summary = "Get job by name",
        description = "Fetches a job by its name",
        parameters = [Parameter(name = "name", description = "Name of the job", required = true)],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Job found",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = Job::class))]
            )
        ]
    )
    fun getJobByName(@RequestParam("name") name: String): Job? = findJobByNameUseCase.find(name)

    @GetMapping
    @Operation(
        summary = "Retrieve all jobs",
        description = "Fetches a list of all available jobs in the system",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "List of jobs retrieved successfully",
                content = [Content(
                    mediaType = "application/json",
                    array = ArraySchema(schema = Schema(implementation = Job::class))
                )]
            )
        ]
    )
    fun getAllJobs(): List<Job> = findAllJobsUseCase.findAll()
}