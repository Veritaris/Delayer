package me.veritaris.delayer

import me.veritaris.delayer.bukkitLogger.Color

fun <T> Array<T>.second(): T {
    return this[1]
}

fun Number.toBool(): Boolean {
    return this == 0
}

fun String.colorLogWrapped(color: Color): String {
    return "${color}$this${color}"
}

fun String.colored(color: Color, colorFinish: Color = Color.WHITE): String {
    return "${color}$this${colorFinish}"
}

fun String.coloredLog(color: Color, colorFinish: Color = Color.WHITE): String {
    return "${color}$this${colorFinish}"
}

fun String.messageColored(color: org.bukkit.Color): String {
    return "${color}$this${org.bukkit.Color.WHITE}"
}

fun String.messageColorWrapped(color: org.bukkit.Color): String {
    return "${color}$this${color}"
}
