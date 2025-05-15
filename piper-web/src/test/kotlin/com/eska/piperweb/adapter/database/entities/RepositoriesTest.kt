package com.eska.piperweb.adapter.database.entities

import com.eska.piperweb.domain.model.JobStatus
import com.eska.piperweb.domain.model.PipelineStatus.DISABLED
import com.eska.piperweb.domain.model.PipelineStatus.ENABLED
import jakarta.transaction.Transactional
import java.util.UUID.randomUUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class PipelineDataRepositoryTest {

    @Autowired
    private lateinit var pipelineRepository: PipelineDataRepository

    @Autowired
    private lateinit var operatorRepository: OperatorDataRepository

    @Test
    @Transactional
    fun `should enable pipeline and return updated entity`() {
        val operator = OperatorEntity(
            id = randomUUID(),
            name = "test-operator"
        )
        operatorRepository.save(operator)

        val pipeline = PipelineEntity(
            id = randomUUID(),
            name = "test-pipeline",
            createdAt = "2024-03-20T10:00:00Z",
            status = DISABLED,
            operator = operator
        )
        pipelineRepository.save(pipeline)

        pipelineRepository.enable(pipeline.name)

        val result = pipelineRepository.findByName(pipeline.name)

        assertEquals(ENABLED, result?.status)
    }

    @Test
    @Transactional
    fun `should throw when pipeline is already enabled`() {
        val operator = OperatorEntity(
            id = randomUUID(),
            name = "test-operator"
        )
        operatorRepository.save(operator)

        val pipeline = PipelineEntity(
            id = randomUUID(),
            name = "test-pipeline",
            createdAt = "2024-03-20T10:00:00Z",
            status = ENABLED,
            operator = operator
        )
        pipelineRepository.save(pipeline)
        val updatedRecords = pipelineRepository.enable("test-pipeline")

        val result = pipelineRepository.findByName(pipeline.name)
        assertEquals(ENABLED, result?.status)
        assertEquals(0, updatedRecords)
    }

    @Test
    @Transactional
    fun `should throw when pipeline does not exist`() {
        val updatedRecords = pipelineRepository.enable("non-existent-pipeline")
        assertEquals(0, updatedRecords)
    }

    @Test
    @Transactional
    fun `should disable pipeline`() {
        val operator = OperatorEntity(
            id = randomUUID(),
            name = "test-operator"
        )
        operatorRepository.save(operator)

        val pipeline = PipelineEntity(
            id = randomUUID(),
            name = "test-pipeline",
            createdAt = "2024-03-20T10:00:00Z",
            status = ENABLED,
            operator = operator
        )
        pipelineRepository.save(pipeline)

        val updatedRecords = pipelineRepository.disable(pipeline.name)

        val result = pipelineRepository.findByName(pipeline.name)
        assertEquals(DISABLED, result?.status)
        assertEquals(1, updatedRecords)
    }

    @Test
    @Transactional
    fun `should not disable pipeline when already disabled`() {
        val operator = OperatorEntity(
            id = randomUUID(),
            name = "test-operator"
        )
        operatorRepository.save(operator)

        val pipeline = PipelineEntity(
            id = randomUUID(),
            name = "test-pipeline",
            createdAt = "2024-03-20T10:00:00Z",
            status = DISABLED,
            operator = operator
        )
        pipelineRepository.save(pipeline)

        val updatedRecords = pipelineRepository.disable(pipeline.name)

        val result = pipelineRepository.findByName(pipeline.name)
        assertEquals(DISABLED, result?.status)
        assertEquals(0, updatedRecords)
    }

    @Test
    @Transactional
    fun `should not disable pipeline when it does not exist`() {
        val updatedRecords = pipelineRepository.disable("non-existent-pipeline")
        assertEquals(0, updatedRecords)
    }

    @Test
    @Transactional
    fun `should delete pipeline by name`() {
        val operator = OperatorEntity(
            id = randomUUID(),
            name = "test-operator"
        )
        operatorRepository.save(operator)

        val pipeline = PipelineEntity(
            id = randomUUID(),
            name = "test-pipeline-to-delete",
            createdAt = "2024-03-20T10:00:00Z",
            status = ENABLED,
            operator = operator
        )
        pipelineRepository.save(pipeline)

        pipelineRepository.deleteByName(pipeline.name)

        val result = pipelineRepository.findByName(pipeline.name)
        assertNull(result)
    }
}

@DataJpaTest
class JobDataRepositoryTest {

    @Autowired
    private lateinit var jobRepository: JobDataRepository

    @Test
    @Transactional
    fun `should find job by name`() {
        val job = Job(
            id = randomUUID(),
            name = "test-job",
            namespace = "default",
            createdAt = "2024-03-20T10:00:00Z",
            startTime = "2024-03-20T10:01:00Z",
            status = JobStatus.ACTIVE
        )
        jobRepository.save(job)

        val result = jobRepository.findByName(job.name)

        assertNotNull(result)
        assertEquals(job.id, result.id)
        assertEquals(job.name, result.name)
        assertEquals(job.namespace, result.namespace)
        assertEquals(job.createdAt, result.createdAt)
        assertEquals(job.startTime, result.startTime)
        assertEquals(job.status, result.status)
    }

    @Test
    @Transactional
    fun `should return null when job does not exist`() {
        val result = jobRepository.findByName("non-existent-job")
        assertNull(result)
    }
}

@DataJpaTest
class OperatorDataRepositoryTest {

    @Autowired
    private lateinit var operatorRepository: OperatorDataRepository

    @Test
    @Transactional
    fun `should find operator by name`() {
        val operator = OperatorEntity(
            id = randomUUID(),
            name = "test-operator"
        )
        operatorRepository.save(operator)

        val result = operatorRepository.findByName(operator.name)

        assertNotNull(result)
        assertEquals(operator.id, result.id)
        assertEquals(operator.name, result.name)
    }

    @Test
    @Transactional
    fun `should return null when operator does not exist`() {
        val result = operatorRepository.findByName("non-existent-operator")
        assertNull(result)
    }
}
