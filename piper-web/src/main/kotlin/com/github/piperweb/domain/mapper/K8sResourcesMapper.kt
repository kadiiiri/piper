package com.github.piperweb.domain.mapper

import com.github.piper.primitives.kubernetes.K8sTaskResources
import com.github.piperweb.adapter.persistence.entity.K8sResourcesEntity

object K8sResourcesMapper {

    fun K8sTaskResources.toEntity() = K8sResourcesEntity(
        minCpuCores = minCpuCores,
        minMemory = minMemory,
        maxCpuCores = maxCpuCores,
        maxMemory = maxMemory
    )

    fun K8sResourcesEntity.toModel() = K8sTaskResources(
        minCpuCores = minCpuCores,
        minMemory = minMemory,
        maxCpuCores = maxCpuCores,
        maxMemory = maxMemory
    )
}


