package com.eska.piperweb.api.monitor.kubernetes

import com.eska.piperweb.core.domain.usecases.FindKubernetesJobUseCase
import io.fabric8.kubernetes.api.model.batch.v1.Job
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/kubernetes/jobs")
@Tag(name = "Kubernetes Job APIs", description = "Endpoints to interact with Kubernetes Jobs")
class KubernetesJobController(
    private val findKubernetesJobUseCase: FindKubernetesJobUseCase
) {

    @GetMapping
    @Operation(
        summary = "Retrieve all Kubernetes jobs",
        description = "Fetches all Kubernetes jobs available in the cluster",
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "List of Kubernetes jobs retrieved successfully"
            ),
            ApiResponse(
                responseCode = "500",
                description = "An internal error occurred while fetching Kubernetes jobs"
            )
        ]
    )
    fun getAllJobs(): List<Job> = findKubernetesJobUseCase.findAll()
}