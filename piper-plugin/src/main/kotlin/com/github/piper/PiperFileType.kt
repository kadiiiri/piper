package com.github.piper

import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.fileTypes.ex.FileTypeIdentifiableByVirtualFile
import com.intellij.openapi.vfs.VirtualFile
import javax.swing.Icon

class PiperFileType : LanguageFileType(PiperLanguage()), FileTypeIdentifiableByVirtualFile {

    companion object {
        @JvmField
        val INSTANCE = PiperFileType()
    }

    override fun getName(): String = "Piper File"
    override fun getDescription(): String = "Piper script File"
    override fun getDefaultExtension(): String = "piper.kts"
    override fun getIcon(): Icon? = null
    override fun isMyFileType(vf: VirtualFile): Boolean = vf.name.isPiperScript()
}

class PiperLanguage: Language("piper")

fun String.isPiperScript() = endsWith(".piper.kts", true)
