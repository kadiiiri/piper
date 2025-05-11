package com.github.piper

import java.io.File
import kotlin.script.experimental.intellij.ScriptDefinitionsProvider
import org.jetbrains.kotlin.utils.PathUtil

class PiperScriptDefinitionProvider : ScriptDefinitionsProvider {
    companion object {
        const val ID = "PiperScriptDefinition"
    }

    override val id: String
        get() = ID

    override fun getDefinitionClasses(): Iterable<String> {
        return listOf("com.github.piper.PiperScript")
    }

    override fun getDefinitionsClassPath(): Iterable<File> {
        val jarFile = PathUtil.getResourcePathForClass(PiperScript::class.java)
        return listOf(jarFile)
    }

    override fun useDiscovery(): Boolean {
        return false
    }
}
