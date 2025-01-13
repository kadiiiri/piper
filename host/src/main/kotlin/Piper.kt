package com.github

import PiperScript
import java.io.File
import kotlin.script.experimental.api.EvaluationResult
import kotlin.script.experimental.api.ResultWithDiagnostics
import kotlin.script.experimental.api.ScriptDiagnostic
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost
import kotlin.script.experimental.jvmhost.createJvmCompilationConfigurationFromTemplate


fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Usage: custom-host <script.piper.kts>")
        return
    }

    val scriptFile = File(args[0])
    if (!scriptFile.exists()) {
        println("Script file '${args[0]}' does not exist.")
        return
    }

    println("Executing script '${args[0]}'...")

    val res = evalFile(scriptFile)

    res.reports.forEach {
        if (it.severity > ScriptDiagnostic.Severity.DEBUG) {
            println(" : ${it.message}" + if (it.exception == null) "" else ": ${it.exception}")
        }
    }
}

fun evalFile(scriptFile: File): ResultWithDiagnostics<EvaluationResult> {
    val compilationConfiguration = createJvmCompilationConfigurationFromTemplate<PiperScript> {}
    return BasicJvmScriptingHost().eval(scriptFile.toScriptSource(), compilationConfiguration, null)
}