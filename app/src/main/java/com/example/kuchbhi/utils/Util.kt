package com.example.kuchbhi.utils

fun containsInList(string: String, list: List<String>): Boolean {
    list.forEach { value ->
        if(value == string) {
            return true
        }
    }
    return false
}