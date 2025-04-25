package com.eska.piperweb.adapter.database.entities

import com.eska.piperweb.core.domain.JobStatus
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.*


//@Entity
//data class Pipeline(
//    @Id @GeneratedValue(strategy = GenerationType.UUID)
//    val id: UUID? = null,
//    val name: String,
//    @OneToOne(cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY)
//    val rootOperator: Operator,
//)
//
//
//@Entity
//data class Operator(
//    @Id @GeneratedValue(strategy = GenerationType.UUID)
//    val id: UUID? = null,
//    val name: String,
//    @OneToOne(cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY)
//    val pipeline: Pipeline,
//    @OneToMany(cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY)
//    val children: List<Operator>,
//    @OneToOne(cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY)
//    val parent: Operator?,
//)

@Entity
data class Job(
    @Id
    val id: UUID,
    val name: String,
    val namespace: String,
    val creationTimeStamp: String,
    val startTime: String,
    val status: JobStatus,
)

