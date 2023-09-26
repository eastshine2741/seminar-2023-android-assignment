package com.example.assignment2

inline fun <reified T> Array<Array<T>>.clone2d(): Array<Array<T>> {
    return this.clone().map { it.clone() }.toTypedArray()
}