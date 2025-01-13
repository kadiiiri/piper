package util

fun String.appendUID(): String {
    val charset = ('a'..'z') + ('0'..'9')
    return this + "-" + charset.shuffled().take(4).joinToString("")
}